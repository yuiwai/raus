package com.yuiwai.raus

trait Relation
case class DependsOn(from: Task, to: Task) extends Relation
case class OneOf(tasks: Set[Task]) extends Relation
case class ParentOf(children: Set[Task]) extends Relation
case class Similar(tasks: Set[Task]) extends Relation
