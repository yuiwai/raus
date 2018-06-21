package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.Serializer
import com.yuiwai.raus.model._
import upickle.default._

trait UPickleSerializer extends Serializer {
  override protected def serialize(user: User): String = ???
  override protected def deserialize(data: String): User = ???
}
