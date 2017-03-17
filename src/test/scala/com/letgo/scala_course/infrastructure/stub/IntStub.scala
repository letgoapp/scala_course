package com.letgo.scala_course.infrastructure.stub

import scala.util.Random

object IntStub {
  def randomNotZero(max: Int): Int = {
    val random = Random.nextInt(max)

    if (random != 0) {
      random
    } else {
      randomNotZero(max)
    }
  }
}
