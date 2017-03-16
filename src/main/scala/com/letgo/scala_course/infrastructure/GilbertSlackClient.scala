package com.letgo.scala_course.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import akka.actor.ActorSystem
import slack.api.SlackApiClient
import slack.models.{ActionField, Attachment, ConfirmField}

import com.letgo.scala_course.domain._
import com.letgo.scala_course.infrastructure.GilbertSlackClientMessageReads.messageReads

class GilbertSlackClient(implicit as: ActorSystem, ec: ExecutionContext) extends SlackClient {
  private val token  = sys.env("LETGO_SCALA_COURSE_SLACK_API_TOKEN")
  private val client = SlackApiClient(token)

  override def fetchChannelMessages(channelId: ChannelId): Future[Seq[AuthoredMessage]] =
    client.getChannelHistory(channelId.rawChannelId).map { historyChunk =>
      historyChunk.messages.map(json => json.as[AuthoredMessage])
    }

  override def addMessage(channel: ChannelId, message: Message): Future[Unit] = {
    val attachments = domainActionsToSlackAttachment(message)

    client.postChatMessage(
      channelId = channel.rawChannelId,
      text = message.text.rawText,
      attachments = attachments
    ).map(_ => ())
  }

  private def domainActionsToSlackAttachment(message: Message): Option[Seq[Attachment]] = {
    val actionFields = message.actions.map(
      actions => actions.map { action =>
        val confirmation = if (!action.requireConfirmation) {
          None
        } else {
          Some(ConfirmField(text = "Are you sure?"))
        }

        ActionField(action.name, action.text, `type` = "button", confirm = confirmation)
      }
    )

    actionFields match {
      case Some(actions) => Some(Seq(Attachment(actions = actions)))
      case None => None
    }
  }
}
