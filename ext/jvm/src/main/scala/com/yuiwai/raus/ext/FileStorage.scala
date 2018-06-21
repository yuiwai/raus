package com.yuiwai.raus.ext

import better.files.File
import com.yuiwai.raus.infrastructure.{PersistentStorage, Serializer}
import com.yuiwai.raus.model.User

trait FileStorage extends PersistentStorage with Serializer {
  // FIXME ファイル名をどうするか
  private val fileName = "raus.db"
  override def load(key: String): Option[User] = {
    val file = File(fileName)
    if (file.exists) Some(deserialize(file.contentAsString))
    else None
  }
  override def save(key: String, user: User): Unit = ???
}
