package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain._

object MessageStub {
  private def create(
    text: MessageText = MessageTextStub.random,
    actions: Set[MessageAction] = MessageActionStub.randomSet()
  ): Message = Message(text)

  def random: Message = create()
}
