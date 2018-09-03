package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{AsyncPersistence, DateBridgeModule, Persistence, PersistentStorage}

import scala.concurrent.{ExecutionContext, Future}

object Raus {
  def withFileStorage(): Raus = new Raus with DateBridgeModule {
    override protected val storage: PersistentStorage = new FileStorage with JacksonSerializer
  }
}

trait Raus extends Persistence with RausLike {
  def load(key: String): Raus.this.type = {
    update(user => loadUser(key).getOrElse(user))
    this
  }
  def save(key: String): Raus.this.type = {
    saveUser(key, user)
    this
  }
}

trait AsyncRaus extends AsyncPersistence with AsyncRausLike {
  override def load(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    asyncUpdate(user => loadUser(key).recover { case _ => user })
  }
  override def save(key: String)(implicit ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    saveUser(key, user).map(_ => this)
  }
}
