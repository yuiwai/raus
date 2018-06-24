package com.yuiwai.raus.ext

import com.yuiwai.raus.model.User

trait RausLike[R <: RausLike[_]] {
  protected var user: User = User()
  protected def update(f: User => User): R = {
    user = f(user)
    this.asInstanceOf[R]
  }
  def load(key: String): R
  def save(key: String): R
}
