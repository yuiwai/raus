package com.yuiwai.raus.ext

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.yuiwai.raus.infrastructure.{Mapper, Serializer}
import com.yuiwai.raus.model._

trait JacksonSerializer extends Serializer[String] {
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  override protected def serialize(user: User): String = {
    mapper.writeValueAsString(Mapper.toMap(user))
  }
  override protected def deserialize(data: String): User = {
    Mapper.fromMap(mapper.readValue(data, classOf[Map[String, Map[String, String]]]))
  }
}
