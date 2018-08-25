package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure.{AsyncPersistence, Persistence, PersistentStorage}
import com.yuiwai.raus.model.Task

import scala.concurrent.{ExecutionContext, Future}

object Raus {
  def withFileStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new FileStorage with JacksonSerializer
  }
}

trait Raus extends Persistence with RausLike[Raus] {
  def load(key: String): Raus = {
    update(user => loadUser(key).getOrElse(user))
    this
  }
  def save(key: String): Raus = {
    saveUser(key, user)
    this
  }
  def tasks: Iterable[Task] = user.tasks.values
  def addTask(title: String): Raus = {
    update(_.addTask(title))
    this
  }
  def doneTask(id: String): Raus = {
    update(_.doneTask(UUID.fromString(id)))
    this
  }
}

trait AsyncRaus extends AsyncPersistence with AsyncRausLike {
  def tasks: Iterable[Task] = user.tasks.values
  override def load(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    asyncUpdate(user => loadUser(key).recover { case _ => user })
  }
  override def save(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    saveUser(key, user).map(_ => this)
  }
}
