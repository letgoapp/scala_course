package com.letgo.scala_course.domain

import scala.concurrent.Future

trait SlackClient {
  def fetchChannelMessages(channel: ChannelId): Future[Seq[AuthoredMessage]]
  def addMessage(channel: ChannelId, message: Message): Future[Unit]
}
