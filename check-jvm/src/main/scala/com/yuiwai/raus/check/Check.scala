package com.yuiwai.raus.check

import com.yuiwai.raus.ext.{AsyncRaus, JacksonSerializer}
import com.yuiwai.raus.infrastructure._

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

object Check {
  def main(args: Array[String]): Unit = {
    val asyncRaus = new AsyncRaus with DateBridgeModule {
      override protected val storage: AsyncPersistentStorage =
        new AsyncInMemoryStorage with JacksonSerializer {
          override implicit val ec: ExecutionContext = global
        }
    }
    val key = "myTasks"
    asyncRaus.load(key).foreach { r =>
      assert(r.isInstanceOf[AsyncRaus])
      assert(r.tasks.isEmpty)
      val tasks = r.addTaskByToday("task1").tasks
      assert(tasks.size == 1)
    }
  }
}

