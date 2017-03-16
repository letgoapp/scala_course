package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.{AuthoredMessage, Message, UserId}

class MessageAnalyticsService {

  def countMessagesOfUser(messages: Seq[AuthoredMessage], userId: UserId): Int =
    messages.count(_.userId == userId)

  def groupByUserName(messages: Seq[AuthoredMessage]): Map[UserId, Seq[Message]] =
    messages.groupBy(_.userId).mapValues(_.map(_.message))
}
