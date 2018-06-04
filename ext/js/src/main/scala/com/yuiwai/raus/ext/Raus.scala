package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure.{InMemoryStorage, Persistence, PersistentStorage}
import com.yuiwai.raus.model.User

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("com.yuiwai.Raus")
object Raus {
  @JSExport
  def withInMemoryStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new InMemoryStorage with JsonSerializer
  }
  @JSExport
  def withLocalStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new LocalStorage with JsonSerializer
  }
}

trait Raus extends Persistence {
  import JsTask._

  import js.JSConverters._

  private var user = User()
  private def update(f: User => User): Unit = user = f(user)

  @JSExport
  def load(key: String): Unit = update(user => loadUser(key).getOrElse(user))

  @JSExport
  def save(key: String): Unit = saveUser(key, user)

  @JSExport
  def tasks(): js.Array[JsTask] = user.tasks.values.map(toJsTask).toJSArray

  @JSExport
  def addTask(title: String): Unit = update(_.addTask(title))

  @JSExport
  def doneTask(id: String): Unit = update(_.doneTask(UUID.fromString(id)))
}
