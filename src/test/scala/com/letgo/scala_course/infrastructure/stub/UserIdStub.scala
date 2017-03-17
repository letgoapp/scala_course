package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.UserId

object UserIdStub {
  def create(rawUserId: String = s"U${StringStub.randomWithFixedLength(10)}"): UserId = UserId(rawUserId)

  def random: UserId = create()
}
