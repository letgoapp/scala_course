package com.letgo.scala_course

import akka.actor.ActorSystem
import org.joda.time.DateTime
import org.scalatest.{GivenWhenThen, WordSpec}
import org.scalatest.Matchers._
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}

import com.letgo.scala_course.application.{RepositoriesWithPullRequestClosedUseCase, SlackMessageAdderUseCase, SlackMessagesFetcherUseCase, SuggestDeployOnPullRequestClosedUseCase}
import com.letgo.scala_course.infrastructure.GilbertSlackClient
import com.letgo.scala_course.infrastructure.stub.{ChannelIdStub, MessageStub, RepositoryStub}

class SuggestDeployOnPullRequestClosedTest extends WordSpec with GivenWhenThen with ScalaFutures with Eventually {
  implicit private val actorSystem      = ActorSystem("test-actor-system")
  implicit private val executionContext = scala.concurrent.ExecutionContext.global

  implicit override val patienceConfig = PatienceConfig(
    timeout = scaled(Span(3, Seconds)),
    interval = scaled(Span(100, Millis))
  )

  private val scalaCourseChannelId = ChannelIdStub.scalaCourse

  private val client = new GilbertSlackClient

  private def slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

  private def repositoriesWithPullRequestClosedUseCase = new RepositoriesWithPullRequestClosedUseCase(client)

  private def suggestDeployOnPullRequestClosedUseCase = new SuggestDeployOnPullRequestClosedUseCase(
    repositoriesWithPullRequestClosedUseCase,
    slackMessageAdderUseCase
  )

  private def slackMessagesFetcherUseCase = new SlackMessagesFetcherUseCase(client)

  "SuggestDeployOnPullRequestClosedUseCase" should {
    "suggest a deploy when there is a pull requests closed" in {
      implicit val actorSystem = ActorSystem()
      implicit val ec = scala.concurrent.ExecutionContext.global

      Given("a Pull Request closed in the Chat repository")

      slackMessageAdderUseCase.add(scalaCourseChannelId, MessageStub.closedChatPullRequest).futureValue

      When("we execute the SuggestDeployOnPullRequestClosedUseCase")

      suggestDeployOnPullRequestClosedUseCase.suggest(
        from = DateTime.now.minusYears(10),
        channelId = scalaCourseChannelId
      )

      And("fetch the messages of the channel")

      eventually {
        val authoredMessages = slackMessagesFetcherUseCase.fetch(scalaCourseChannelId).futureValue

        val messages = authoredMessages.map(_.message)

        Then("it has published the deploy suggestion for the Chat repository")

        messages should contain(MessageStub.deploySuggestion(RepositoryStub.chat))
      }
    }
  }
}
