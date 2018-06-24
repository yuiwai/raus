package com.yuiwai.raus.infrastructure

import java.util.UUID

import com.yuiwai.raus.model.{Saved, Task, TaskState, User}

trait Mapper {
  private val Tasks = "tasks"
  def toMap(user: User): Map[String, Map[String, String]] = Map(
    Tasks -> user.tasks.map { case (uuid, task) => uuid.toString -> task.title }
  )
  def fromMap(map: Map[String, Map[String, String]]): User = map.get(Tasks)
    .map { xs =>
      User().copy(tasks = xs.map {
        case (id, title) => UUID.fromString(id) -> Task(UUID.fromString(id), TaskState(title), Saved)
      })
    } getOrElse User()
}
object Mapper extends Mapper
