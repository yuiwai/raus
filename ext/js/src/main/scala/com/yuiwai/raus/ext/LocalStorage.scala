package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.PersistentStorage
import com.yuiwai.raus.model.User
import org.scalajs.dom
import org.scalajs.dom.raw.Storage

class LocalStorage extends PersistentStorage {
  private def localStorage: Storage = dom.window.localStorage
  override def load(key: String): Option[User] = ???
  override def save(key: String, user: User): Unit = ???
}
