package com.yuiwai.raus.check

import com.yuiwai.raus.ext.{AsyncRaus, JacksonSerializer}
import com.yuiwai.raus.infrastructure.{AsyncInMemoryStorage, DateBridgeModule}

import scala.concurrent.ExecutionContext.Implicits.global

object Check {
  def main(args: Array[String]): Unit = {
    implicit val storage = new AsyncInMemoryStorage[String] with JacksonSerializer {}
    val asyncRaus = new AsyncRaus with DateBridgeModule
    val key = "myTasks"
    asyncRaus.load(key).foreach { r =>
      assert(r.isInstanceOf[AsyncRaus])
      assert(r.tasks.isEmpty)
      val tasks = r.addTaskByToday("task1").tasks
      assert(tasks.size == 1)
    }
  }
}
