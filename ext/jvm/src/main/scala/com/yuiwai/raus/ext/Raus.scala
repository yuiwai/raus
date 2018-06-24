package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure.{Persistence, PersistentStorage}
import com.yuiwai.raus.model.{Task, User}

object Raus {
  def withFileStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new FileStorage with JacksonSerializer
  }
}

trait Raus extends Persistence {
  private var user: User = User()
  private def update(f: User => User): Unit = user = f(user)
  def load(key: String): Raus = {
    storage.load(key).foreach { u =>
      update(_ => u)
    }
    this
  }
  def save(key: String): Raus = {
    storage.save(key, user)
    this
  }
  def tasks: Map[UUID, Task] = user.tasks
  def addTask(title: String): Raus = {
    update(_.addTask(title))
    this
  }
  def doneTask(id: String): Raus = {
    update(_.doneTask(UUID.fromString(id)))
    this
  }
}
