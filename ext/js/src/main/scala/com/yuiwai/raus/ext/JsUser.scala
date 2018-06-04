package com.yuiwai.raus.ext

import java.util.UUID

import com.yuiwai.raus.model.User

import scala.scalajs.js

@js.native
trait JsUser extends js.Object {
  val tasks: js.Dictionary[JsTask] = js.native
}
object JsUser {
  import JsTask._
  import js.JSConverters._
  def fromJsUser(jsUser: JsUser): User =
    User().copy(tasks = jsUser.tasks.map { t =>
      UUID.fromString(t._1) -> fromJsTask(t._2)
    }.toMap)
  def toJsUser(user: User): JsUser =
    js.Dynamic.literal(
      tasks = user.tasks.map(t => t._1.toString -> toJsTask(t._2)).toJSDictionary
    ).asInstanceOf[JsUser]
}
