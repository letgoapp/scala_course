package com.letgo.scala_course.application

import scala.concurrent.ExecutionContext

import org.joda.time.DateTime

import com.letgo.scala_course.domain.{ChannelId, Message, MessageAction, MessageText}

final class SuggestDeployOnPullRequestClosedUseCase(
  repositoriesWithPullRequestClosedUseCase: RepositoriesWithPullRequestClosedUseCase,
  slackMessageAdderUseCase: SlackMessageAdderUseCase
)(implicit ec: ExecutionContext) {
  def suggest(from: DateTime, channelId: ChannelId): Unit = {
    val repositoriesWithPullRequestClosedFuture =
      repositoriesWithPullRequestClosedUseCase.repositoriesWithPullRequestClosed(from, channelId)

    repositoriesWithPullRequestClosedFuture.foreach { repositoriesWithPullRequestClosed =>
      repositoriesWithPullRequestClosed.foreach { repository =>
        val messageText =
          MessageText(s"Would you like to deploy the changes in ${repository.user}/${repository.name}?")
        val dismissDeployAction = MessageAction(text = "Nope, dismiss", name = "no", requireConfirmation = false)
        val confirmDeployAction = MessageAction(text = "Yes, deploy", name = "yes", requireConfirmation = true)

        val suggestion = Message(messageText, Some(Seq(dismissDeployAction, confirmDeployAction)))

        slackMessageAdderUseCase.add(channelId, suggestion)
      }
    }
  }
}
