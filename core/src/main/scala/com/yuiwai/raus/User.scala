package com.yuiwai.raus

import java.time.LocalDate
import java.util.UUID

case class User(
  tasks: Map[UUID, Task],
  groups: Set[Group],
  relations: Set[Relation],
  customs: Set[Custom],
  deletedTasks: Set[UUID],
  deletedGroups: Set[Group],
  deletedRelations: Set[Relation],
  deletedCustoms: Set[Custom]
) {
  def task: Option[Task] = tasks.headOption.map(_._2)
  def expired: Seq[Task] = tasks.values.filter(_.isExpired).toSeq
  def expired(now: LocalDate): Seq[Task] = tasks.values.filter(_.isExpired(now)).toSeq
  def addTask(title: String): User = addTask(TaskState(title))
  def addTask(title: String, deadline: Date): User = addTask(TaskState(title, deadline))
  def addTask(task: TaskState): User = {
    val id = UUID.randomUUID()
    copy(tasks = tasks.updated(id, Task(id, task, Added)), groups = groups + task.group)
  }
  def doneTask(id: UUID): User = copy(tasks = tasks - id, deletedTasks = deletedTasks + id)
}
object User {
  def apply(): User = new User(Map.empty, Set.empty, Set.empty, Set.empty, Set.empty, Set.empty, Set.empty, Set.empty)
}
