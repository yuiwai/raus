package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{PersistentStorage, Serializer}
import com.yuiwai.raus.model.User

trait FileStorage extends PersistentStorage with Serializer {
  override def load(key: String): Option[User] = ???
  override def save(key: String, user: User): Unit = ???
}
