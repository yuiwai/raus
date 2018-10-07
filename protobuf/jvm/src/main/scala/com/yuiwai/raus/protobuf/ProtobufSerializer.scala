package com.yuiwai.raus.protobuf

import java.util.UUID

import com.yuiwai.raus.model._
import raus.user.User.{Task => PTask, TaskState => PTaskState, Group => PGroup}
import raus.user.{User => PUser}

trait ProtobufSerializer extends Serializer[Array[Byte]] {
  override protected def serialize(user: User): Array[Byte] = {
    PUser(
      user.tasks.values.toSeq.map { task =>
        PTask(task.id.toString, Some(PTaskState(task.title)))
      },
      user.groups.toSeq.map { group =>
        PGroup(group.name)
      }
    ).toByteArray
  }
  override protected def deserialize(bytes: Array[Byte]): User = {
    val pUser = PUser.parseFrom(bytes)
    User(
      pUser.tasks.map { pTask =>
        val task = pTask.asTask
        task.id -> task
      }.toMap,
      pUser.groups.map(pGroup => Group(pGroup.name)).toSet,
      Set.empty,
      Set.empty,
      Set.empty,
      Set.empty,
      Set.empty,
      Set.empty
    )
  }

  implicit class PTaskWrap(pTask: PTask) {
    // FIXME Statusは暫定
    def asTask: Task = Task(UUID.fromString(pTask.id), pTask.getState.asTaskState, Added)
  }
  implicit class PTaskStateWrap(pTaskState: PTaskState) {
    def asTaskState: TaskState = TaskState(pTaskState.title)
  }
}