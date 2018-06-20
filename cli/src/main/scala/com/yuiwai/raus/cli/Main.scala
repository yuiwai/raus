package com.yuiwai.raus.cli

import com.yuiwai.raus.ext.Raus

object Main extends Runner {
  def main(args: Array[String]): Unit = run(args.toList)
}
trait Runner {
  val Tasks = "tasks"
  def run(args: List[String]): Unit = {
    args match {
      case Tasks :: opts => tasks(opts)
      case _ => help()
    }
  }
  private def load(key: String): Raus = ???
  private def save(key: String, raus: Raus): Unit = ???
  // TODO パラメータから取得するように
  def key(opts: List[String]): String = "myTask"
  def tasks(opts: List[String]): Unit = {
    load(key(opts))
  }
  def help(): Unit = println("Usage: raus [tasks|add|done] [options]")
}
