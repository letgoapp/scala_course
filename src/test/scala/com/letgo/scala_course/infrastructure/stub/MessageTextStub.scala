package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

import com.letgo.scala_course.domain.MessageText

object MessageTextStub {
  private def create(text: String = StringStub.randomWithMaxLength(Random.nextInt(256))): MessageText =
    MessageText(text)

  def random: MessageText = create()
}
