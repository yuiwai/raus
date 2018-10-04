package com.yuiwai.raus.cli

import com.yuiwai.raus.ext.{FileStorage, JacksonSerializer, Raus}
import com.yuiwai.raus.infrastructure.DateBridgeModule

trait Runner {
  val Tasks = "tasks"
  val Add = "add"
  val Done = "done"

  def run(args: List[String]): Unit = {
    args match {
      case Tasks :: opts => tasks(opts)
      case Add :: opts => addTask(opts)
      case Done :: opts => doneTaks(opts)
      case _ => help()
    }
  }
  implicit val storage = new FileStorage with JacksonSerializer
  private def load(key: String): Raus = (new Raus with DateBridgeModule).load(key)
  private def save(key: String, raus: Raus): Unit = raus.save(key)
  // TODO パラメータから取得するように
  def key(opts: List[String]): String = "myTask"
  def tasks(opts: List[String]): Unit = {
    load(key(opts))
      .tasks
      .foreach(println)
  }
  def addTask(opts: List[String]): Unit = {
    opts.headOption.foreach { title =>
      save(key(opts), load(key(opts)).addTask(title))
    }
  }
  def doneTaks(opts: List[String]): Unit = {
    opts.headOption.foreach { id =>
      save(key(opts), load(key(opts)).doneTask(id))
    }
  }
  def help(): Unit = println("Usage: raus [tasks|add|done] [options]")
}
