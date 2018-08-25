package com.yuiwai.raus.infrastructure

import com.yuiwai.raus.model.User

import scala.concurrent.{ExecutionContext, Future}

trait Persistence {
  protected val storage: PersistentStorage
  protected def loadUser(key: String): Option[User] = storage.load(key)
  protected def saveUser(key: String, user: User): Unit = storage.save(key, user)
}
trait PersistentStorage {
  def load(key: String): Option[User]
  def save(key: String, user: User): Unit
}
trait InMemoryStorage extends PersistentStorage with Serializer {
  private var db: Map[String, String] = Map.empty
  override def load(key: String): Option[User] = db.get(key).map(deserialize)
  override def save(key: String, user: User): Unit = db = db.updated(key, serialize(user))
}
trait Serializer {
  protected def serialize(user: User): String
  protected def deserialize(data: String): User
}

trait AsyncPersistence {
  protected val storage: AsyncPersistentStorage
  protected def loadUser(key: String): Future[User] = storage.load(key)
  protected def saveUser(key: String, user: User): Future[Unit] = storage.save(key, user)
}
trait AsyncPersistentStorage {
  def load(key: String): Future[User]
  def save(key: String, user: User): Future[Unit]
}
trait AsyncInMemoryStorage extends AsyncPersistentStorage with Serializer {
  implicit val ec: ExecutionContext
  private var db: Map[String, String] = Map.empty
  override def load(key: String): Future[User] = Future(db.get(key).map(deserialize).get)
  override def save(key: String, user: User): Future[Unit] = {
    db = db.updated(key, serialize(user))
    Future.successful(())
  }
}