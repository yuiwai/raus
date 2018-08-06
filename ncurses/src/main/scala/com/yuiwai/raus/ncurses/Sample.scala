package com.yuiwai.raus.ncurses

import scala.scalanative.native._

object Sample {
  import Ncurses._
  def main(args: Array[String]): Unit = {

    val screen: Ptr[Window] = initscr()
    val win = newwin(10, 10, 1, 1)
    start_color()
    cbreak()
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
  def newwin(nlines: CInt, ncols: CInt, beginY: CInt, beginX: CInt): Ptr[Window] = extern
  def endwin(): CInt = extern
  def start_color(): CInt = extern
  def cbreak(): CInt = extern
  def noecho(): CInt = extern
  def getch: CInt = extern
}
