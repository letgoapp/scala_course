package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.MessageText

class MessageCensor(forbiddenKeywords: Set[String]) {

  private val filterRule: MessageText => MessageText = { message =>
    MessageText(message.rawText.split(" ").filterNot(isWordForbidden).mkString(" "))
  }

  private def isWordForbidden(word: String): Boolean = forbiddenKeywords.contains(word)

  def filterMessages(messages: Seq[MessageText]): Seq[MessageText] = messages.map(filterRule)
}
