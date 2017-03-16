package com.letgo.scala_course.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import akka.actor.ActorSystem
import org.joda.time.DateTime
import slack.api.SlackApiClient

import com.letgo.scala_course.domain._
import com.letgo.scala_course.infrastructure.GilbertSlackClientMessageReads.messageReads

class GilbertSlackClient(implicit as: ActorSystem, ec: ExecutionContext) extends SlackClient {
  private val token  = sys.env("LETGO_SCALA_COURSE_SLACK_API_TOKEN")
  private val client = SlackApiClient(token)

  override def fetchLatestChannelMessages(
      channelId: ChannelId,
      from: Option[DateTime] = None,
      to: Option[DateTime] = Some(DateTime.now())): Future[Seq[AuthoredMessage]] = {
    client
      .getChannelHistory(channelId.rawChannelId, oldest = from.map(toUnixEpoch), latest = to.map(toUnixEpoch))
      .map { historyChunk =>
        historyChunk.messages.map(json => json.as[AuthoredMessage])
      }
  }

  override def addMessage(channel: ChannelId, message: Message): Future[Unit] =
    client.postChatMessage(channel.rawChannelId, message.text.rawText).map(_ => ())

  private def toUnixEpoch(dateTime: DateTime): String =
    (dateTime.toInstant.getMillis / 1000).toString // TODO extract to extension method of datetime
}
