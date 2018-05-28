package com.yuiwai.raus

import java.time.LocalDate

case class Date(localDate: LocalDate) {
  def remainingDays: Int = remainingDays(LocalDate.now())
  def remainingDays(from: LocalDate): Int = localDate.compareTo(from)
  def isExpired: Boolean = remainingDays < 0
  def isExpired(now: LocalDate): Boolean = remainingDays(now) < 0
}
object Date {
  def apply(): Date = Date(LocalDate.now())
  def today: Date = Date()
  def tomorrow: Date = Date(LocalDate.now().plusDays(1))
}
