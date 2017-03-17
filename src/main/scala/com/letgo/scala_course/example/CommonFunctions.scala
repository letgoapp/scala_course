package com.letgo.scala_course.example

import scala.collection.mutable

object CommonFunctions extends App {
  def isOdd(n: Int): Boolean = n % 2 != 0

  def square(n: Int): Int = n * n

  def squaredOddNumbersUpToImperative(n: Int): Seq[Int] = {
    var sequence = mutable.Buffer[Int]()
    for (i <- 0 to n) {
      if (isOdd(i)) {
        sequence :+= square(i)
      }
    }
    sequence
  }

  def squaredOddNumbersUpToFilter(n: Int): Seq[Int] = {
    var sequence = mutable.Buffer[Int]()
    for (i <- (0 to n).filter(number => isOdd(number))) {
      sequence :+= square(i)
    }
    sequence
  }

  def squaredOddNumbersUpToFunctional(n: Int): Seq[Int] =
    (0 to n).filter(isOdd).map(square)

  println(squaredOddNumbersUpToImperative(10).toList)

  println(squaredOddNumbersUpToFilter(10).toList)

  println(squaredOddNumbersUpToFunctional(10).toList)
}
