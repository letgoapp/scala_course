package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.{MessageText, Repository}

object MessageTextStub {
  private def create(text: String = StringStub.notEmptyRandomWithMaxLength(128)): MessageText =
    MessageText(text)

  def random: MessageText = create()

  def closedChatPullRequest: MessageText =
    create("[letgoapp/chat] Pull request closed: #666 some pull request message - p1 by rockstar_unicorn_ninja_coder")

  def deploySuggestion(repository: Repository): MessageText =
    create(text = s"Would you like to deploy the changes in ${repository.user}/${repository.name}?")
}
