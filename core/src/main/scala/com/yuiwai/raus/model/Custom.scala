package com.yuiwai.raus.model

sealed trait Custom
case class Action() extends Custom
case class Filter() extends Custom
trait Generator extends Custom

