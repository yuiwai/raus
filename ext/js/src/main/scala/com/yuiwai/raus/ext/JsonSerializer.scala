package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.Serializer
import com.yuiwai.raus.model.User

import scala.scalajs.js.JSON

trait JsonSerializer extends Serializer {
  import JsUser._
  override protected def serialize(user: User): String = JSON.stringify(toJsUser(user))
  override protected def deserialize(data: String): User =
    fromJsUser(JSON.parse(data).asInstanceOf[JsUser])
}
