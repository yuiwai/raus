package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure._

import scala.concurrent.{ExecutionContext, Future}
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

trait Raus extends Persistence with RausLike[Raus] {
  import JsTask._

  import js.JSConverters._

  @JSExport
  def load(key: String): Raus = {
    update(user => loadUser(key).getOrElse(user))
    this
  }

  @JSExport
  def save(key: String): Raus = {
    saveUser(key, user)
    this
  }

  @JSExport
  def tasks(): js.Array[JsTask] = user.tasks.values.map(toJsTask).toJSArray

  @JSExport
  def addTask(title: String): Unit = update(_.addTask(title))

  @JSExport
  def doneTask(id: String): Unit = update(_.doneTask(UUID.fromString(id)))
}

trait AsyncRaus extends AsyncPersistence with AsyncRausLike[AsyncRaus] {
  import JsTask._

  import js.JSConverters._

  override def load(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus] = {
    asyncUpdate(user => loadUser(key).recover { case _ => user })
  }
  override def save(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus] = {
    saveUser(key, user).map(_ => this)
  }

  @JSExport
  def tasks(): js.Array[JsTask] = user.tasks.values.map(toJsTask).toJSArray

  @JSExport
  def addTask(title: String): Unit = update(_.addTask(title))

  @JSExport
  def doneTask(id: String): Unit = update(_.doneTask(UUID.fromString(id)))
}
