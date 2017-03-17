package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain._

object MessageStub {
  def create(
    text: MessageText = MessageTextStub.random,
    actions: Option[Seq[MessageAction]] = MessageActionStub.randomSeq()
  ): Message = Message(text, actions)

  def random: Message = create()

  def closedChatPullRequest: Message = create(text = MessageTextStub.closedChatPullRequest, actions = None)

  def deploySuggestion(repository: Repository): Message = create(
    text = MessageTextStub.deploySuggestion(repository: Repository),
    actions = Some(Seq(MessageActionStub.nope, MessageActionStub.yes))
  )
}
