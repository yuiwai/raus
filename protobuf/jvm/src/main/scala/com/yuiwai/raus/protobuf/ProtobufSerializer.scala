package com.yuiwai.raus.protobuf

import com.yuiwai.raus.infrastructure.Serializer
import com.yuiwai.raus.model.User
import raus.user.{User => PUser}
import raus.user.User.{Task => PTask}

trait ProtobufSerializer extends Serializer[Array[Byte]] {
  override protected def serialize(user: User): Array[Byte] =
    PUser(user.tasks.values.toSeq.map(task => PTask(task.id.toString))).toByteArray
  override protected def deserialize(bytes: Array[Byte]): User = ???
}