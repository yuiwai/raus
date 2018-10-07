package com.yuiwai.raus.example

import java.time.LocalDate

import com.yuiwai.raus.model.{Date, User}

object Example extends App with Fixtures with DateBridgeModule {
  empty()
  addTask()
  addTaskWithDeadline()
  doneTask()

  def empty(): Unit = {
    val user = User()
    assert(user.tasks.isEmpty)
    assert(user.groups.isEmpty)
    assert(user.relations.isEmpty)
    assert(user.customs.isEmpty)
  }

  def addTask(): Unit = {
    val title = "test"
    val user = User().addTask(title)
    assert(user.tasks.size == 1)
    assert(user.groups.size == 1)

    val Some(task) = user.task
    assert(task.title == title)
  }
  def addTaskWithDeadline(): Unit = {
    val title = "test"
    val user = User()
      .addTask(title, Date.today)
      .addTask(title, Date.tomorrow)
    assert(user.expired.isEmpty)
    assert(user.expired(LocalDate.now().plusDays(1).toDate).size == 1)
    assert(user.expired(LocalDate.now().plusDays(2).toDate).size == 2)
  }

  def doneTask(): Unit = new OneTaskAdded {
    assert(user.doneTask(task.id).tasks.isEmpty)
    assert(user.doneTask(task.id).deletedTasks.size == 1)
  }
}

trait Fixtures {
  trait OneTaskAdded {
    val title = "test"
    val user: User = User().addTask(title)
    val Some(task) = user.task
  }
}
