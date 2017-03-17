package com.letgo.scala_course.application

import scala.concurrent.{ExecutionContext, Future}

import org.joda.time.DateTime

import com.letgo.scala_course.domain.{AuthoredMessage, ChannelId, Repository, SlackClient}

final class RepositoriesWithPullRequestClosedUseCase(slackClient: SlackClient)(implicit ec: ExecutionContext) {

  private val pullRequestClosedRegex = """\[(.*)\/(.*)\] Pull request closed: #.* by .*""".r

  def repositoriesWithPullRequestClosed(from: DateTime, channelId: ChannelId): Future[Set[Repository]] = {
    slackClient.fetchLatestChannelMessages(channelId, Some(from)).map { messages =>
      messages
        .filter(isPulledRequestClosedMessage)
        .map(extractRepositoryInformationFromMessage)
        .toSet
    }
  }

  private def isPulledRequestClosedMessage(authoredMessage: AuthoredMessage) =
    pullRequestClosedRegex.findFirstIn(authoredMessage.message.text.rawText).isDefined

  private def extractRepositoryInformationFromMessage(authoredMessage: AuthoredMessage) =
    authoredMessage.message.text.rawText match {
      case pullRequestClosedRegex(user, name) => Repository(user, name)
    }
}
