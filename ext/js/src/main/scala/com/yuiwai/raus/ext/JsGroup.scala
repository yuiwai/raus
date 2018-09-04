package com.yuiwai.raus.ext

import com.yuiwai.raus.model.Group

import scala.scalajs.js

@js.native
trait JsGroup extends js.Object {
  val name: String = js.native
}
object JsGroup {
  def fromJsGroup(group: JsGroup): Group = Group(group.name)
  def toJsGroup(group: Group): JsGroup =
    js.Dynamic.literal(
      name = group.name
    ).asInstanceOf[JsGroup]

}
