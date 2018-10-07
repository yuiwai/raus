package com.yuiwai.raus.check

import com.yuiwai.raus.ext.{AsyncRaus, JsonSerializer}
import com.yuiwai.raus.infrastructure.{AsyncInMemoryStorage, DateBridgeModule}
import com.yuiwai.raus.model.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("com.yuiwai.raus.check.Check")
object Check {
  @JSExport
  def main(): Unit = {
    implicit val storage = new AsyncInMemoryStorage[String] with JsonSerializer {}
    val asyncRaus = new AsyncRaus with DateBridgeModule
    val key = "myTasks"
    asyncRaus.load(key).foreach { r =>
      assert(r.isInstanceOf[AsyncRaus])
      assert(r.tasks.isEmpty)
      val tasks = r.addTaskByToday("task1").tasks
      assert(tasks.size == 1)
    }
    checkSerialization()
  }
  def checkSerialization(): Unit = new JsonSerializer {
    import com.yuiwai.raus.ext.JsUser._
    val user = User()
      .addGroup("test group")
    assert(fromJsUser(toJsUser(user)) == user)
    assert(deserialize("""{"tasks": {}}""").isInstanceOf[User])
  }
}
