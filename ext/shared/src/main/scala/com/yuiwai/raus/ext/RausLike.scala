package com.yuiwai.raus.ext

import com.yuiwai.raus.model.{Date, User}

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

trait AsyncRausLike[R <: AsyncRausLike[_]] {
  protected var user: User = User()
  protected def asyncUpdate(f: User => Future[User])
    (implicit ec: ExecutionContext): Future[R] = {
    f(user)
      .map { newUser =>
        user = newUser
        this.asInstanceOf[R]
      }
  }
  protected def update(f: User => User): R = {
    user = f(user)
    this.asInstanceOf[R]
  }
  def load(key: String)(implicit ec: ExecutionContext): Future[R]
  def save(key: String)(implicit ec: ExecutionContext): Future[R]
}
