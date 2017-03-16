package com.letgo.scala_course.domain

import scala.concurrent.Future

import org.joda.time.DateTime

trait SlackClient {
  def fetchLatestChannelMessages(channel: ChannelId,
                                 from: Option[DateTime] = None,
                                 to: Option[DateTime] = Some(DateTime.now())): Future[Seq[AuthoredMessage]]
  def addMessage(channel: ChannelId, message: Message): Future[Unit]
}
