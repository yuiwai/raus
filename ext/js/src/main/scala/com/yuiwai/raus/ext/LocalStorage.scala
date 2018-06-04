package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{PersistentStorage, Serializer}
import com.yuiwai.raus.model.User
import org.scalajs.dom

import scala.util.Try

trait LocalStorage extends PersistentStorage with Serializer {
  private val localStorage = dom.window.localStorage
  override def load(key: String): Option[User] = Try(localStorage.getItem(key)).map(deserialize).toOption
  override def save(key: String, user: User): Unit = localStorage.setItem(key, serialize(user))
}
