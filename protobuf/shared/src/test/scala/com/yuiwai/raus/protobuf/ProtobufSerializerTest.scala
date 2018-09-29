package com.yuiwai.raus.protobuf

import com.yuiwai.raus.model.User
import utest._

object ProtobufSerializerTest extends TestSuite {
  class Serializer extends ProtobufSerializer {
    def fromUser(user: User): Array[Byte]= serialize(user)
    def toUser(bytes: Array[Byte]): User = deserialize(bytes)
  }
  val tests = Tests {
    "serialize" - {
      new Serializer().fromUser(User())
    }
  }
}
