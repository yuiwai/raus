package com.yuiwai.raus

sealed trait Custom
case class Action() extends Custom
case class Filter() extends Custom
trait Generator extends Custom

