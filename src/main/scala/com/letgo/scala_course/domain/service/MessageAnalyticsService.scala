package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.{Message, MessageText, UserId}

class MessageAnalyticsService {

  def countMessagesOfUser(messages: Seq[Message], userId: UserId): Int =
    messages.count(_.userId == userId)

  def groupByUserName(messages: Seq[Message]): Map[UserId, Seq[MessageText]] =
    messages.groupBy(_.userId).mapValues(_.map(_.text))
}
