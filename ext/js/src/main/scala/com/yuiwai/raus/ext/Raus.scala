package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure._

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("raus.Raus")
object Raus {
  @JSExport
  def withInMemoryStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new InMemoryStorage with JsonSerializer
  }
  @JSExport
  def withLocalStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new LocalStorage with JsonSerializer
  }
}

trait Raus extends Persistence with RausLike {
  override def load(key: String): Raus.this.type = {
    update(user => loadUser(key).getOrElse(user))
    this
  }
  override def save(key: String): Raus.this.type = {
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
