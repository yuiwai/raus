package com.yuiwai.raus.infrastructure

import com.yuiwai.raus.model.User

import scala.concurrent.Future
import scala.language.higherKinds

trait Persistence[R[_]] {
  type Storage = PersistentStorage[R]
  protected def loadUser(key: String)(implicit storage: Storage): R[Option[User]] = storage.load(key)
  protected def saveUser(key: String, user: User)(implicit storage: Storage): R[Unit] = storage.save(key, user)
}
trait PersistentStorage[R[_]] {
  def load(key: String): R[Option[User]]
  def save(key: String, user: User): R[Unit]
}
trait InMemoryStorage[T] extends PersistentStorage[Id] with Serializer[T] {
  private var db: Map[String, T] = Map.empty
  override def load(key: String): Option[User] = db.get(key).map(deserialize)
  override def save(key: String, user: User): Unit = db = db.updated(key, serialize(user))
}
trait AsyncInMemoryStorage[T] extends PersistentStorage[Future] with Serializer[T] {
  import scala.concurrent.ExecutionContext.Implicits.global
  private var db: Map[String, T] = Map.empty
  override def load(key: String): Future[Option[User]] = Future(db.get(key).map(deserialize))
  override def save(key: String, user: User): Future[Unit] = Future {
    db = db.updated(key, serialize(user))
  }
}
trait Serializer[T] {
  protected def serialize(user: User): T
  protected def deserialize(data: T): User
}

trait FanoutStorage[R[_]] extends PersistentStorage[R] {
  def storages: Seq[PersistentStorage[R]]
  def load(key: String): R[Option[User]]
  def save(key: String, user: User): R[Unit]
}
