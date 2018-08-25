package com.yuiwai.raus.check

import com.yuiwai.raus.ext.{AsyncRaus, JsonSerializer}
import com.yuiwai.raus.infrastructure.{AsyncInMemoryStorage, AsyncPersistentStorage}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js._

object Check {
  def main(args: Array[String]): Unit = {
    val asyncRaus = new AsyncRaus {
      override protected val storage: AsyncPersistentStorage =
        new AsyncInMemoryStorage with JsonSerializer {
          override implicit val ec: ExecutionContext = global
        }
    }
    val key = "myTasks"
    asyncRaus.load(key).foreach { r =>
      assert(r.isInstanceOf[AsyncRaus])
      assert(r.tasks().isEmpty)
    }
  }
}
