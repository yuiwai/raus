package com.yuiwai.raus.ext

import com.yuiwai.raus.infrastructure.{Persistence, PersistentStorage}

object Raus {
  def withFileStorage(): Raus = new Raus {
    override protected val storage: PersistentStorage = new FileStorage with UPickleSerializer
  }
}

trait Raus extends Persistence {

}
