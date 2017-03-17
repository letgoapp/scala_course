package com.letgo.scala_course

import akka.actor.ActorSystem
import org.scalatest.{GivenWhenThen, WordSpec}
import org.scalatest.Matchers._
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}

import com.letgo.scala_course.application.{RepositoriesWithPullRequestClosedUseCase, SlackMessageAdderUseCase, SlackMessagesFetcherUseCase, SuggestDeployOnPullRequestClosedUseCase}
import com.letgo.scala_course.infrastructure.GilbertSlackClient

class SuggestDeployOnPullRequestClosedTest extends WordSpec with GivenWhenThen with ScalaFutures with Eventually {
  implicit private val actorSystem = ActorSystem("test-actor-system")
  implicit private val executionContext = scala.concurrent.ExecutionContext.global

  implicit override val patienceConfig = PatienceConfig(
    timeout = scaled(Span(3, Seconds)),
    interval = scaled(Span(100, Millis))
  )

  private val client = new GilbertSlackClient

  private def slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

  private def repositoriesWithPullRequestClosedUseCase = new RepositoriesWithPullRequestClosedUseCase(client)

  private def suggestDeployOnPullRequestClosedUseCase = new SuggestDeployOnPullRequestClosedUseCase(
    repositoriesWithPullRequestClosedUseCase,
    slackMessageAdderUseCase
  )

  private def slackMessagesFetcherUseCase = new SlackMessagesFetcherUseCase(client)

  "SuggestDeployOnPullRequestClosedUseCase" should {
    "suggest a deploy when there is a pull requests closed" ignore {
    }
  }
}