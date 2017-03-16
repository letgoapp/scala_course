package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.MessageText

object MessageTextStub {
  private def create(text: String = StringStub.notEmptyRandomWithMaxLength(128)): MessageText =
    MessageText(text)

  def random: MessageText = create()
}
