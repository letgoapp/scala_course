package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.Message

class MessageCensor(forbiddenKeywords: Set[String]) {

  private val filterRule: Message => Message = { message =>
    Message(
      message.text.split(" ").filterNot(
        word =>
          isForbidden(word)
      ).mkString(" ")
    )
  }

  def isForbidden(word: String): Boolean = forbiddenKeywords.contains(word)

  def filterMessages(messages: Seq[Message]): Seq[Message] = messages.map(filterRule)
}

