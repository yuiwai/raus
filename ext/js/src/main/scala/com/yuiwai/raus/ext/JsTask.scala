package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.model.{Saved, Task, TaskState}

import scala.scalajs.js

@js.native
trait JsTask extends js.Object {
  val id: String = js.native
  val title: String = js.native
}
object JsTask {
  // FIXME statusを正規のものに
  def fromJsTask(task: JsTask): Task =
    Task(UUID.fromString(task.id), TaskState(task.title), Saved)
  def toJsTask(task: Task): JsTask =
    js.Dynamic.literal(
      title = task.title,
      id = task.id.toString
    ).asInstanceOf[JsTask]

}
