package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.Repository

object RepositoryStub {
  def create(user: String, name: String): Repository = Repository(user, name)

  def chat: Repository = create("letgoapp", "chat")
}
