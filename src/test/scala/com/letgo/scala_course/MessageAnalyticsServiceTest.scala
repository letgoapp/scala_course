package com.letgo.scala_course

import org.scalatest.{Matchers, WordSpec}

import com.letgo.scala_course.domain.{Message, MessageText, UserId}
import com.letgo.scala_course.domain.service.MessageAnalyticsService

class MessageAnalyticsServiceTest extends WordSpec with Matchers {

  "MessageAnalyticsService" should {

    "count messages of user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      val javi   = UserId("javi")
      val gerard = UserId("gerard")

      val messages = Seq(Message(javi, MessageText("cosa fina")),
                         Message(gerard, MessageText("done > unit")),
                         Message(javi, MessageText("tengo la sala reservada")))

      msgAnalyticsService.countMessagesOfUser(messages, javi) shouldBe 2
    }

    "group empty messages by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      msgAnalyticsService.groupByUserName(Seq.empty) should contain theSameElementsAs Map.empty
    }

    "group single message by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      msgAnalyticsService.groupByUserName(
        Seq(
          Message(
            UserId("Jorge Avila"),
            MessageText("Me tengo que pelar")
          )
        )
      ) should contain theSameElementsAs Map(UserId("Jorge Avila") -> Seq(MessageText("Me tengo que pelar")))
    }

    "group messages by user" in {

      val msgAnalyticsService = new MessageAnalyticsService()

      msgAnalyticsService.groupByUserName(
        Seq(
          Message(
            UserId("Jorge Avila"),
            MessageText("Me tengo que pelar")
          ),
          Message(
            UserId("SergiGP"),
            MessageText("La burbuja va a estallar")
          ),
          Message(
            UserId("SergiGP"),
            MessageText("Ya llegareis")
          ),
          Message(
            UserId("Dani De Ripo"),
            MessageText("El TT es un iman...")
          ),
          Message(
            UserId("JaviCane"),
            MessageText("Cosa fina")
          ),
          Message(
            UserId("Jorge Avila"),
            MessageText("Yo fui a las pruebas de la Real")
          )
        )
      ) should contain theSameElementsAs Map(
        UserId("Jorge Avila") -> Seq(
          MessageText("Me tengo que pelar"),
          MessageText("Yo fui a las pruebas de la Real")
        ),
        UserId("SergiGP") -> Seq(
          MessageText("La burbuja va a estallar"),
          MessageText("Ya llegareis")
        ),
        UserId("Dani De Ripo") -> Seq(
          MessageText("El TT es un iman...")
        ),
        UserId("JaviCane") -> Seq(
          MessageText("Cosa fina")
        )
      )
    }

  }
}
