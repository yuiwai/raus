package com.yuiwai.raus.infrastructure

import java.time.LocalDate

import com.yuiwai.raus.model.Date

trait DateBridge {
  def now(): Date
  def plusDays(date: Date, days: Int): Date
  def compareTo(to: Date, from: Date): Int
}

class DateBridgeImpl extends DateBridge {
  import DateBridge._
  override def now(): Date = LocalDate.now().toDate(this)
  override def plusDays(date: Date, days: Int): Date = date.toLocalDate.plusDays(days).toDate(this)
  override def compareTo(to: Date, from: Date): Int = to.toLocalDate.compareTo(from.toLocalDate)
}
object DateBridge {
  implicit class LocalDateWrap(localDate: LocalDate) {
    def toDate(implicit dateBridge: DateBridge): Date = Date(localDate.getYear, localDate.getMonthValue, localDate.getDayOfMonth)
  }
  implicit class DateWrap(date: Date) {
    def toLocalDate: LocalDate = LocalDate.of(date.year, date.month, date.day)
  }
}

trait DateBridgeModule {
  implicit val dateBridge: DateBridge = new DateBridgeImpl
}
