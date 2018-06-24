package com.yuiwai.raus.example

import com.yuiwai.raus.cli.Runner

object CLI extends Runner {
  def main(args: Array[String]): Unit = {
    run(args.toList)
  }
}
