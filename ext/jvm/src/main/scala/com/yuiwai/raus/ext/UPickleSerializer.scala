package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{Mapper, Serializer}
import com.yuiwai.raus.model._
import upickle.default._

trait UPickleSerializer extends Serializer {
  override protected def serialize(user: User): String = {
    write(Mapper.toMap(user))
  }
  override protected def deserialize(data: String): User = {
    Mapper.fromMap(read[Map[String, Map[String, String]]](data))
  }
}
