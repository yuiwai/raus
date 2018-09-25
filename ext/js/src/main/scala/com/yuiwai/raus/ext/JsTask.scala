package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.infrastructure.DateBridgeModule
import com.yuiwai.raus.model.{Date, Group, Saved, Task, TaskState}

import scala.scalajs.js

@js.native
trait JsTask extends js.Object {
  val id: String = js.native
  val title: String = js.native
  val groupName: String = js.native
}
object JsTask {
  // FIXME statusを正規のものに
  def fromJsTask(task: JsTask): Task =
    Task(UUID.fromString(task.id), TaskState(task.title, None, Group(task.groupName)), Saved)
  def toJsTask(task: Task): JsTask =
    js.Dynamic.literal(
      title = task.title,
      id = task.id.toString,
      groupName = task.groupName,
      deadline = JsDate.toJsDate(task.deadline)
    ).asInstanceOf[JsTask]
}

@js.native
trait JsDate extends js.Object {
  val year: Int = js.native
  val month: Int = js.native
  val day: Int = js.native
}
object JsDate extends DateBridgeModule {
  def fromJsDate(date: JsDate): Option[Date] =
    if (date.year == 0) None
    else Some(Date(date.year, date.month, date.day))
  def toJsDate(date: Option[Date]): JsDate =
    js.Dynamic.literal().asInstanceOf[JsDate]
}