package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{Id, Persistence, PersistentStorage}

import scala.concurrent.{ExecutionContext, Future}

trait Raus extends Persistence[Id] with RausLike {
  override def load(key: String)(implicit storage: PersistentStorage[Id]): Raus.this.type = {
    update(user => loadUser(key).getOrElse(user))
    this
  }
  override def save(key: String)(implicit storage: PersistentStorage[Id]): Raus.this.type = {
    saveUser(key, user)
    this
  }
}

trait AsyncRaus extends Persistence[Future] with AsyncRausLike {
  override def load(key: String)
    (implicit storage: PersistentStorage[Future], ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    asyncUpdate(user => loadUser(key).map(_.get).recover { case _ => user })
  }
  override def save(key: String)
    (implicit storage: PersistentStorage[Future], ec: ExecutionContext): Future[AsyncRaus.this.type] = {
    saveUser(key, user).map(_ => this)
  }
}
