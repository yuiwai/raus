package com.yuiwai.raus.infrastructure

trait Persistence {
  def strategy: PersistentStrategy
  def storage: PersistentStorage
}
sealed trait PersistentStrategy
object PersistentStrategies {
  case object AllInOneSerialization extends PersistentStrategy
  case object Relational extends PersistentStrategy
}
trait PersistentStorage
