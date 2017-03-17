package com.letgo.scala_course.domain

case class UserId(rawUserId: String) {
  def isGitHub: Boolean = rawUserId == "GitHub"
}
