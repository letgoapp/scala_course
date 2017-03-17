package com.letgo.scala_course.infrastructure.stub

import com.letgo.scala_course.domain.ChannelId

object ChannelIdStub {
  def create(rawChannelId: String): ChannelId = ChannelId(rawChannelId)

  val scalaCourse: ChannelId = create("C3YPYMQ2D")
}
