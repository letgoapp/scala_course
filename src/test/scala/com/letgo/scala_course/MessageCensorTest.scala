package com.letgo.scala_course

import org.scalatest.{GivenWhenThen, WordSpec}
import org.scalatest.Matchers._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import com.letgo.scala_course.domain.service.MessageCensor
import com.letgo.scala_course.domain.MessageText

class MessageCensorTest extends WordSpec with GivenWhenThen with ScalaFutures {

  implicit override val patienceConfig = PatienceConfig(
    timeout = scaled(Span(3, Seconds)),
    interval = scaled(Span(100, Millis))
  )

  "MessageCensorTest" should {
    "censor bad language" in {
      Given("a MessageCensorTest")

      val forbiddenKeywords = Set("fuck", "jorge")
      val censor = new MessageCensor(forbiddenKeywords)

      val myMessages = Seq(
        MessageText("go fuck yourself"),
        MessageText("i love jorge")
      )

      val expectedCensoredMessages = Seq(
        MessageText("go yourself"),
        MessageText("i love")
      )

      val censoredMessages = censor.filterMessages(myMessages)

      censoredMessages should be(expectedCensoredMessages)
    }
  }
}
