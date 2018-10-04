package com.yuiwai.raus.infrastructure

import com.yuiwai.raus.model.User

import scala.concurrent.Future
import scala.language.higherKinds

trait Persistence[R[_]] {
  type Storage = PersistentStorage[R]
  // protected val storage: PersistentStorage[R]
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

/*
trait AsyncPersistence {
  protected val storage: AsyncPersistentStorage
  protected def loadUser(key: String): Future[User] = storage.load(key)
  protected def saveUser(key: String, user: User): Future[Unit] = storage.save(key, user)
}
trait AsyncPersistentStorage {
  def load(key: String): Future[User]
  def save(key: String, user: User): Future[Unit]
}
trait AsyncInMemoryStorage extends AsyncPersistentStorage with Serializer[String] {
  implicit val ec: ExecutionContext
  private var db: Map[String, String] = Map.empty
  override def load(key: String): Future[User] = Future(db.get(key).map(deserialize).get)
  override def save(key: String, user: User): Future[Unit] = {
    db = db.updated(key, serialize(user))
    Future.successful(())
  }
}
*/

/*
trait NoStorage[R[_]] extends PersistentStorage[R] {
  def load(key: String): Option[User] = None
  def save(key: String, user: User): Unit = ()
}
*/

/*
trait FanoutPersistence[R[_]] extends Persistence[R] {
  protected val storage: PersistentStorage[_] = NoStorage
}
*/