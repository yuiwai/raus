package com.yuiwai.raus.ncurses

import scala.scalanative.native._

object Sample {
  import Ncurses._
  def main(args: Array[String]): Unit = {

    val screen: Ptr[Window] = initscr()
    noecho()
    loop()
    endwin()
  }
  def loop(): Unit = {
    val char = getch
    print(char)
    print(" ")
    loop()
  }
}

@link("ncurses")
@extern
object Ncurses {
  type Window = CStruct0
  def initscr(): Ptr[Window] = extern
  def endwin(): CInt = extern
  def noecho(): CInt = extern
  def getch: CInt = extern
}
