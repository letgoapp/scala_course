package com.letgo.scala_course

import scala.concurrent.duration._

import akka.actor.ActorSystem
import org.joda.time.DateTime
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.Matchers._
import org.scalatest.WordSpec

import com.letgo.scala_course.application.{RepositoriesWithPullRequestClosedUseCase, SlackMessageAdderUseCase}
import com.letgo.scala_course.domain.SlackClient
import com.letgo.scala_course.infrastructure.GilbertSlackClient
import com.letgo.scala_course.infrastructure.stub.{ChannelIdStub, MessageStub, RepositoryStub}

final class RepositoriesWithPullRequestClosedUseCaseTest extends WordSpec with ScalaFutures with Eventually {

  implicit val patience: PatienceConfig = PatienceConfig(timeout = 10000.milliseconds, interval = 10.milliseconds)

  "repositoriesWithPullRequestClosed" should {
    "return the repositories with an existing pull request closed message" in {
      implicit val actorSystem = ActorSystem()
      implicit val ec          = scala.concurrent.ExecutionContext.global

      val client: SlackClient                       = new GilbertSlackClient()
      val channelId                                 = ChannelIdStub.scalaCourse
      val repositoriesWithPullRequestClosedUseCase = new RepositoriesWithPullRequestClosedUseCase(client)

      val slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

      slackMessageAdderUseCase
        .add(channelId, MessageStub.closedChatPullRequest)
        .futureValue

      val expectedRepository = RepositoryStub.chat

      val from = DateTime.now.minusYears(10) // From the beginning of time
      eventually {
        repositoriesWithPullRequestClosedUseCase
          .repositoriesWithPullRequestClosed(from, channelId)
          .futureValue should contain(expectedRepository)
      }
    }
  }
}
