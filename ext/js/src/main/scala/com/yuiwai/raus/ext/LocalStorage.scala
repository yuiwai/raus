package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{Id, PersistentStorage, Serializer}
import com.yuiwai.raus.model.User
import org.scalajs.dom

import scala.util.Try

trait LocalStorage extends PersistentStorage[Id] with Serializer[String] {
  private val localStorage = dom.window.localStorage
  override def load(key: String): Option[User] = Try(localStorage.getItem(key)).map(deserialize).toOption
  override def save(key: String, user: User): Unit = localStorage.setItem(key, serialize(user))
}
