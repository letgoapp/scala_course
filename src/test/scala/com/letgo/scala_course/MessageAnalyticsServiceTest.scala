package com.letgo.scala_course

import org.scalatest.{Matchers, WordSpec}

import com.letgo.scala_course.domain.service.MessageAnalyticsService
import com.letgo.scala_course.infrastructure.stub.{AuthoredMessageStub, UserIdStub}

class MessageAnalyticsServiceTest extends WordSpec with Matchers {

  "MessageAnalyticsService" should {

    "count messages of user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      val javi = UserIdStub.create("Javi")
      val gerard = UserIdStub.create("Gerard")

      val messages = Seq(
        AuthoredMessageStub.create(userId = javi),
        AuthoredMessageStub.create(userId = gerard),
        AuthoredMessageStub.create(userId = javi)
      )

      msgAnalyticsService.countMessagesOfUser(messages, javi) shouldBe 2
    }

    "group empty messages by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      msgAnalyticsService.groupByUserName(Seq.empty) should contain theSameElementsAs Map.empty
    }

    "group single message by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      val authoredMessage = AuthoredMessageStub.random

      msgAnalyticsService.groupByUserName(Seq(authoredMessage)) should
      contain theSameElementsAs Map(authoredMessage.userId -> Seq(authoredMessage.message))
    }

    "group messages by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      val dani = UserIdStub.create("Dani")
      val gerard = UserIdStub.create("Gerard")

      val daniMessage1 = AuthoredMessageStub.create(userId = dani)
      val daniMessage2 = AuthoredMessageStub.create(userId = dani)
      val daniMessage3 = AuthoredMessageStub.create(userId = dani)
      val gerardMessage1 = AuthoredMessageStub.create(userId = gerard)
      val gerardMessage2 = AuthoredMessageStub.create(userId = gerard)

      val messages = Seq(daniMessage1, daniMessage2, daniMessage3, gerardMessage1, gerardMessage2)

      val groupedMessages = Map(
        dani -> Seq(daniMessage1.message, daniMessage2.message, daniMessage3.message),
        gerard -> Seq(gerardMessage1.message, gerardMessage2.message)
      )

      msgAnalyticsService.groupByUserName(messages) should contain theSameElementsAs groupedMessages
    }
  }
}
