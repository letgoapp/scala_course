package com.letgo.scala_course

import akka.actor.ActorSystem
import org.scalatest.{GivenWhenThen, WordSpec}
import org.scalatest.Matchers._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import com.letgo.scala_course.application.{SlackMessageAdderUseCase, SlackMessagesFetcherUseCase}
import com.letgo.scala_course.infrastructure.GilbertSlackClient
import com.letgo.scala_course.infrastructure.stub.{ChannelIdStub, MessageActionStub, MessageStub}

class SlackMessageAdderUseCaseTest extends WordSpec with GivenWhenThen with ScalaFutures {
  implicit private val actorSystem      = ActorSystem("test-actor-system")
  implicit private val executionContext = scala.concurrent.ExecutionContext.global

  implicit override val patienceConfig = PatienceConfig(
    timeout = scaled(Span(3, Seconds)),
    interval = scaled(Span(100, Millis))
  )

  private val client = new GilbertSlackClient

  private def slackMessagesFetcherUseCase = new SlackMessagesFetcherUseCase(client)

  private def slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

  "SlackMessagesAdderUseCase" should {
    "publish a message to a channel" in {
      val scalaCourseChannelId = ChannelIdStub.scalaCourse
      val message = MessageStub.random

      slackMessageAdderUseCase.add(scalaCourseChannelId, message).futureValue

      val messages = slackMessagesFetcherUseCase.fetch(scalaCourseChannelId)

      messages.futureValue.head.message shouldBe message
    }

    "publish a message with an action to a channel" in {
      val scalaCourseChannelId = ChannelIdStub.scalaCourse
      val message = MessageStub.create(actions = Some(MessageActionStub.randomNonEmptySeq()))

      slackMessageAdderUseCase.add(scalaCourseChannelId, message).futureValue

      val messages = slackMessagesFetcherUseCase.fetch(scalaCourseChannelId)

      messages.futureValue.head.message shouldBe message
    }
  }
}
