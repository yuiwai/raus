package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.PersistentStorage
import org.scalajs.dom
import org.scalajs.dom.raw.Storage

class LocalStorage extends PersistentStorage {
  def localStorage: Storage = dom.window.localStorage
}
