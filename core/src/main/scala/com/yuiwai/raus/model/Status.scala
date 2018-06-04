package com.yuiwai.raus.model

sealed trait Status
case object Added extends Status
case object Modified extends Status
case object Saved extends Status
