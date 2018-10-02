package com.yuiwai.raus.protobuf

import com.yuiwai.raus.model.User
import utest._

object ProtobufSerializerTest extends TestSuite {
  object Serializer extends ProtobufSerializer {
    def fromUser(user: User): Array[Byte] = serialize(user)
    def toUser(bytes: Array[Byte]): User = deserialize(bytes)
  }
  val tests = Tests {
    "serialize" - {
      val user = User()
        .addGroup("group1")
        .addTask("task1")
      println(Serializer.toUser(Serializer.fromUser(user)))
      assert(user == Serializer.toUser(Serializer.fromUser(user)))
    }
  }
}
