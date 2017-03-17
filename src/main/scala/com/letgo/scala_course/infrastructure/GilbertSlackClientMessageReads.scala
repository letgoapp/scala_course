package com.letgo.scala_course.infrastructure

import play.api.libs.functional.syntax._
import play.api.libs.json._

import slack.models.{ActionField, Attachment, ConfirmField}

import com.letgo.scala_course.domain._

object GilbertSlackClientMessageReads {

  /**
    * We're only parsing the first attachment and we assume it's the one with the button actions.
    */
  private def slackAttachmentToDomainActions(
    attachmentsOption: Option[Seq[Attachment]]
  ): Option[Seq[MessageAction]] = {
    if (attachmentsOption.exists(_.isEmpty)) {
      None
    } else {
      attachmentsOption.flatMap { attachments =>
        attachments.headOption.map { attachment =>
          attachment.actions.map { action =>
            MessageAction(action.text, action.name, requireConfirmation = action.confirm.isDefined)
          }
        }
      }
    }
  }

  implicit val messageTextReads = new Reads[MessageText] {
    override def reads(json: JsValue): JsResult[MessageText] = json match {
      case JsString(rawMessageText) => JsSuccess(MessageText(rawMessageText))
      case _ => JsError("The message text is not an string.")
    }
  }

  implicit val confirmFieldReads: Reads[ConfirmField] = (
    (JsPath \ "text").read[String] and
    (JsPath \ "ok_text").read[String] and
    (JsPath \ "dismiss_text").read[String]
    ) { (text, okText, dismissText) =>
    ConfirmField(text = text, ok_text = Some(okText), cancel_text = Some(dismissText))
  }

  /**
    * We're assuming all the action fields are buttons.
    */
  implicit val actionFieldReads: Reads[ActionField] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "text").read[String] and
    (JsPath \ "confirm").readNullable[ConfirmField]
    ) { (name, text, confirm) =>
    ActionField(name = name, text = text, `type` = "button", confirm = confirm)
  }

  implicit val attachmentReads: Reads[Attachment] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "actions").readNullable[Seq[ActionField]]
    ) { (_, actionsOption) =>
    Attachment(actions = actionsOption.getOrElse(Seq.empty))
  }

  implicit val messageReads: Reads[AuthoredMessage] = (
    (JsPath \ "bot_id").readNullable[String] and
    (JsPath \ "user").readNullable[String] and
    (JsPath \ "text").read[MessageText] and
    (JsPath \ "attachments").readNullable[Seq[Attachment]]
    ) { (botIdOption, userIdOption, text, attachments) =>
    val userId = UserId(botIdOption.getOrElse(userIdOption.get))
    val message = Message(text = text, actions = slackAttachmentToDomainActions(attachments))

    AuthoredMessage(userId, message)
  }
}
