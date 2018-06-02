package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure.{InMemoryStorage, Persistence, PersistentStorage}
import com.yuiwai.raus.model.{Saved, Task, TaskState, User}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("com.yuiwai.Raus")
object Raus extends Raus {
  // FIXME 暫定設定
  override protected val storage: PersistentStorage = new InMemoryStorage {
    // TODO JsonSerializerを切り出す
    override protected def serialize(user: User): String = JSON.stringify(toJsUser(user))
    override protected def deserialize(data: String): User =
      fromJsUser(JSON.parse(data).asInstanceOf[JsUser])
  }
}

trait Raus extends Persistence {
  import js.JSConverters._

  private var user = User()
  private def update(f: User => User): Unit = user = f(user)

  private def fromJsUser(jsUser: JsUser): User =
    User().copy(tasks = jsUser.tasks.map { t =>
      UUID.fromString(t._1) -> fromJsTask(t._2)
    }.toMap)
  private def toJsUser(user: User): JsUser =
    js.Dynamic.literal(
      tasks = user.tasks.map(t => t._1.toString -> toJsTask(t._2)).toJSDictionary
    ).asInstanceOf[JsUser]
  // FIXME statusを正規のものに
  private def fromJsTask(task: JsTask): Task =
    Task(UUID.fromString(task.id), TaskState(task.title), Saved)
  private def toJsTask(task: Task): JsTask =
    js.Dynamic.literal(
      title = task.title,
      id = task.id.toString
    ).asInstanceOf[JsTask]

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

@js.native
trait JsUser extends js.Object {
  val tasks: js.Dictionary[JsTask] = js.native
}

@js.native
trait JsTask extends js.Object {
  val id: String = js.native
  val title: String = js.native
}
