package com.yuiwai.raus.ext

import better.files.File
import com.yuiwai.raus.infrastructure.{PersistentStorage, Serializer}
import com.yuiwai.raus.model.User
import upickle.default._

trait FileStorage extends PersistentStorage with Serializer {
  // FIXME ファイル名をどうするか
  private val fileName = "raus.db"
  override def load(key: String): Option[User] = {
    val file = File(fileName)
    read[User](file.contentAsString)
  }
  override def save(key: String, user: User): Unit = ???
}
