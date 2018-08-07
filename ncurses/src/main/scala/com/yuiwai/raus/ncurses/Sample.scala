package com.yuiwai.raus.ncurses

import scala.scalanative.native._

object Sample {
  import Ncurses._
  def main(args: Array[String]): Unit = {

    val screen: Ptr[Window] = initscr()
    val win = newwin(10, 10, 1, 1)
    val pair1 = 1.toShort
    start_color()
    init_pair(pair1, Colors.COLOR_BLACK, Colors.COLOR_RED)
    attron(COLOR_PAIR(pair1))
    cbreak()
    noecho()
    loop()
    attroff(COLOR_PAIR(pair1))
    endwin()
  }
  def loop(): Unit = {
    val char = getch
    mvprintw(10, 10, c"%d", char)
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
  def has_colors(): CBool = extern
  def start_color(): CInt = extern
  def init_pair(pair: CShort, f: CShort, b: CShort): CInt = extern
  def COLOR_PAIR(pair: CShort): CInt = extern
  def attron(attribute: CInt): CInt = extern
  def attroff(attribute: CInt): CInt = extern
  def cbreak(): CInt = extern
  def noecho(): CInt = extern
  def getch: CInt = extern
  def mvprintw(y: CInt, x: CInt, fmt: CString, args: CVararg*): CInt = extern
  def mvaddch(y: CInt, x: CInt, ch: CChar): CInt = extern
}

object Colors {
  val COLOR_BLACK: CShort = 0.toShort
  val COLOR_RED: CShort = 1.toShort
  val COLOR_GREEN: CShort = 2.toShort
  val COLOR_YELLOW: CShort = 3.toShort
  val COLOR_BLUE: CShort = 4.toShort
  val COLOR_MAGENTA: CShort = 5.toShort
  val COLOR_CYAN: CShort = 6.toShort
  val COLOR_WHITE: CShort = 7.toShort
}
