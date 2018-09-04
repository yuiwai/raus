package com.yuiwai.raus.model

import java.util.UUID

case class Task(id: UUID, state: TaskState, status: Status) {
  def title: String = state.title
  def groupName: String = state.group.name
  def isExpired: Boolean = state.deadline.exists(_.isExpired)
  def isExpired(now: Date): Boolean = state.deadline.exists(_.isExpired(now))
}
case class TaskState(title: String, deadline: Option[Date], group: Group)
object TaskState {
  def apply(title: String): TaskState = TaskState(title, None, NoGroup)
  def apply(title: String, deadline: Date): TaskState = TaskState(title, Some(deadline), NoGroup)
}

