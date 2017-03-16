package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain._

object AuthoredMessageStub {
  def create(userId: UserId = UserIdStub.random, message: Message = MessageStub.random): AuthoredMessage =
    AuthoredMessage(userId, message)

  def random: AuthoredMessage = create()
}
