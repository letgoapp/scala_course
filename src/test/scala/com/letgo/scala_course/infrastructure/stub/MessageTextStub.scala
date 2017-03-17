package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.MessageText

object MessageTextStub {
  private def create(text: String = StringStub.notEmptyRandomWithMaxLength(128)): MessageText =
    MessageText(text)

  def random: MessageText = create()

  def closedChatPullRequest: MessageText =
    create("[letgoapp/chat] Pull request closed: #666 some pull request message - p1 by rockstar_unicorn_ninja_coder")
}
