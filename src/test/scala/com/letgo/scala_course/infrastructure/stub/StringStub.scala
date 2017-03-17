package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

object StringStub {
  def randomWithFixedLength(numChars: Int): String = Random.alphanumeric take numChars mkString ""

  def randomWithMaxLength(maxNumChars: Int): String = randomWithFixedLength(Random.nextInt(maxNumChars))

  def notEmptyRandomWithMaxLength(maxNumChars: Int): String = randomWithFixedLength(IntStub.randomNotZero(maxNumChars))
}
