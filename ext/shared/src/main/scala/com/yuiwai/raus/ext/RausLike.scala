package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.model.{Date, Task, User}

import scala.concurrent.{ExecutionContext, Future}

trait RausLike[R <: RausLike[_]] {
  protected var user: User = User()
  protected def update(f: User => User): R = {
    user = f(user)
    this.asInstanceOf[R]
  }
  def load(key: String): R
  def save(key: String): R
  def addTaskByToday(title: String): R = update(_.addTask(title, Date.today))
  def addTaskByTomorrow(title: String): R = update(_.addTask(title, Date.tomorrow))
}

trait AsyncRausLike extends RausLikeOps {
  protected var user: User = User()
  protected def asyncUpdate(f: User => Future[User])
    (implicit ec: ExecutionContext): Future[this.type] = {
    f(user)
      .map { newUser =>
        user = newUser
        this
      }
  }
  protected def update(f: User => User): this.type = {
    user = f(user)
    this
  }
  def load(key: String)(implicit ec: ExecutionContext): Future[this.type]
  def save(key: String)(implicit ec: ExecutionContext): Future[this.type]
}

trait RausLikeOps {
  protected var user: User
  protected def update(f: User => User): this.type
  def tasks: Iterable[Task] = user.tasks.values
  def addTask(title: String): this.type = {
    update(_.addTask(title))
    this
  }
  def doneTask(id: String): this.type = {
    update(_.doneTask(UUID.fromString(id)))
    this
  }
  def addTaskByToday(title: String): this.type = update(_.addTask(title, Date.today))
  def addTaskByTomorrow(title: String): this.type = update(_.addTask(title, Date.tomorrow))
}
