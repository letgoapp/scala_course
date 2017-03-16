package com.letgo.scala_course.application

import scala.concurrent.{ExecutionContext, Future}

import com.letgo.scala_course.domain.{AuthoredMessage, ChannelId, SlackClient}

class SlackMessagesFetcherUseCase(slackClient: SlackClient)(implicit ec: ExecutionContext) {
  var numberOfApiCalls: Int = 0

  var cache: Option[Future[Seq[AuthoredMessage]]] = None

  def fetch(channelName: ChannelId): Future[Seq[AuthoredMessage]] = {
    numberOfApiCalls += 1
    slackClient.fetchChannelMessages(channelName)
  }

  def fetchWithCache(channelName: ChannelId): Future[Seq[AuthoredMessage]] = cache.getOrElse {
      val message = fetch(channelName)
      cache = Some(message)

      message
    }
}
