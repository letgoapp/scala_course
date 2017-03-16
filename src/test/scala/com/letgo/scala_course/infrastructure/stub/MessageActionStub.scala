package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

import com.letgo.scala_course.domain.MessageAction

object MessageActionStub {
  def create(
    text: String = StringStub.randomWithMaxLength(256),
    name: String = StringStub.randomWithMaxLength(20),
    requireConfirmation: Boolean = Random.nextBoolean()
  ): MessageAction = MessageAction(text, name, requireConfirmation)

  def random: MessageAction = create()

  def randomSet(numElements: Int = Random.nextInt(9)): Set[MessageAction] = (1 to numElements).map(_ => random).toSet
}
