package com.yuiwai.raus.model

import com.yuiwai.raus.infrastructure.DateBridge

case class Date(year: Int, month: Int, day: Int)(implicit dateBridge: DateBridge) {
  def remainingDays: Int = remainingDays(dateBridge.now())
  def remainingDays(from: Date): Int = dateBridge.compareTo(this, from)
  def isExpired: Boolean = remainingDays < 0
  def isExpired(now: Date): Boolean = remainingDays(now) < 0
  def plusDays(days: Int): Date = dateBridge.plusDays(this, days)
}
object Date {
  def apply()(implicit dateBridge: DateBridge): Date = dateBridge.now()
  def today(implicit dateBridge: DateBridge): Date = Date()
  def tomorrow(implicit dateBridge: DateBridge): Date = dateBridge.now().plusDays(1)
}