package com.letgo.scala_course

import scala.concurrent.duration._

import akka.actor.ActorSystem
import org.joda.time.DateTime
import org.scalamock.matchers.Matchers
import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures

import com.letgo.scala_course.application.{CheckRepositoriesMergedPullRequestUseCase, SlackMessageAdderUseCase}
import com.letgo.scala_course.domain.{Message, MessageText, SlackClient}
import com.letgo.scala_course.infrastructure.GilbertSlackClient
import com.letgo.scala_course.infrastructure.stub.{ChannelIdStub, MessageTextStub, RepositoryStub}
import org.scalatest.Matchers._

final class CheckRepositoriesMergedPullRequestUseCaseTest extends WordSpec with ScalaFutures {

  implicit val patience: PatienceConfig = PatienceConfig(timeout = 10000.milliseconds, interval = 10.milliseconds)

  "checkRepositoriesWithMergedPullRequests" should {
    "return an existing merged pull request message" in {
      implicit val actorSystem = ActorSystem()
      implicit val ec          = scala.concurrent.ExecutionContext.global

      val client: SlackClient                       = new GilbertSlackClient()
      val channelId                                 = ChannelIdStub.scalaCourse
      val checkRepositoriesMergedPullRequestUseCase = new CheckRepositoriesMergedPullRequestUseCase(client)

      val slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

      slackMessageAdderUseCase.add(channelId, Message(MessageTextStub.closedChatPullRequest)).futureValue
      val expectedRepository = RepositoryStub.chat

      val from = DateTime.now.minusYears(10)  // From the beginning of time
      checkRepositoriesMergedPullRequestUseCase
        .checkRepositoriesWithMergedPullRequests(from, channelId)
        .futureValue should contain(expectedRepository)
    }
  }
}
