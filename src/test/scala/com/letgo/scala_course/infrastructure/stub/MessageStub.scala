package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.{Message, MessageText, UserId}

object MessageStub {
  private def create(userId: UserId = UserIdStub.random, text: MessageText = MessageTextStub.random): Message =
    Message(userId, text)

  def random: Message = create()
}
