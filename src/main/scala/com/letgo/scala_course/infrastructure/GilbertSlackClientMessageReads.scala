package com.letgo.scala_course.infrastructure

import play.api.libs.functional.syntax._
import play.api.libs.json._

import com.letgo.scala_course.domain.{Message, MessageText, UserId}

object GilbertSlackClientMessageReads {
  implicit val messageReads: Reads[Message] = (
    (JsPath \ "bot_id").readNullable[String] and
    (JsPath \ "user").readNullable[String] and
    (JsPath \ "text").read[String]
    ) { (botIdOption, userIdOption, text) =>
    val rawUserId = botIdOption.getOrElse(userIdOption.get)

    Message(UserId(rawUserId), MessageText(text))
  }
}
