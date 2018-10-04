package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{DateBridgeModule, Persistence, PersistentStorage, Id}

import scala.concurrent.{ExecutionContext, Future}

object Raus {
  def withFileStorage(): Raus = new Raus with DateBridgeModule {
    override protected val storage: PersistentStorage[Id] = new FileStorage with JacksonSerializer
  }
}

trait Raus extends Persistence[Id] with RausLike {
  def load(key: String)(implicit storage: PersistentStorage[Id]): Raus.this.type = {
    update(user => loadUser(key).getOrElse(user))
    this
  }
  def save(key: String)(implicit storage: PersistentStorage[Id]): Raus.this.type = {
    saveUser(key, user)
    this
  }
}

trait AsyncRaus extends Persistence[Future] with AsyncRausLike {
  override def load(key: String)(implicit storage: PersistentStorage[Future], ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    asyncUpdate(user => loadUser(key).recover { case _ => user })
  }
  override def save(key: String)(implicit storage: PersistentStorage[Future], ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    saveUser(key, user).map(_ => this)
  }
}
