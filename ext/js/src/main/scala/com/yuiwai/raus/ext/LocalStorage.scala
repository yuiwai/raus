package com.yuiwai.raus.ext

import org.scalajs.dom
import org.scalajs.dom.raw.Storage

class LocalStorage {
  def localStorage: Storage = dom.window.localStorage
}
