package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

import com.letgo.scala_course.domain.MessageAction

object MessageActionStub {
  def create(
    text: String = StringStub.notEmptyRandomWithMaxLength(30),
    name: String = StringStub.notEmptyRandomWithMaxLength(10),
    requireConfirmation: Boolean = Random.nextBoolean()
  ): MessageAction = MessageAction(text, name, requireConfirmation)

  def random: MessageAction = create()

  def randomSeq(numElements: Int = Random.nextInt(2)): Option[Seq[MessageAction]] = {
    if (numElements == 0) {
      None
    } else {
      Some(randomNonEmptySeq(numElements))
    }
  }

  def randomNonEmptySeq(numElements: Int = IntStub.randomNotZero(2)): Seq[MessageAction] =
    (0 to numElements).map(_ => random)

  val nope: MessageAction = create(text = "Nope, dismiss", name = "no", requireConfirmation = false)

  val yes: MessageAction = create(text = "Yes, deploy", name = "yes", requireConfirmation = true)
}
