package com.yuiwai.raus.model

import utest._

object UserTest extends TestSuite {
  val tests = Tests {
    "relations" - {
      User().addRelation()
    }
  }
}
