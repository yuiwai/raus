package com.yuiwai.raus.infrastructure

import com.yuiwai.raus.model.User

trait Mapper {
  def toMap(user: User): Map[String, Any] = ???
  def fromMap(map: Map[String, Any]): User = ???
}
object Mapper extends Mapper
