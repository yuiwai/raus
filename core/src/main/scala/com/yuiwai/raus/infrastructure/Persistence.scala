package com.yuiwai.raus.infrastructure

trait Persistence
sealed trait PersistentStrategy
object PersistentStrategies {
  case object AllInOneSerialization extends PersistentStrategy
  case object Relational extends PersistentStrategy
}
trait PersistentStorage
