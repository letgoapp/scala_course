package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

import com.letgo.scala_course.domain.MessageText

object MessageTextStub {
  private def create(text: String = StringStub.random(Random.nextInt(50))): MessageText = MessageText(text)

  def random: MessageText = create()
}
