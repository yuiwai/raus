package com.yuiwai.raus.infrastructure

import com.yuiwai.raus.model.User

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
