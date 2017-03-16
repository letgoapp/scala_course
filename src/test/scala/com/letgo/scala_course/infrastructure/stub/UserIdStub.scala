package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.UserId

object UserIdStub {
  def create(rawUserId: String = StringStub.random(11)): UserId = UserId(rawUserId)

  def random: UserId = create()
}
