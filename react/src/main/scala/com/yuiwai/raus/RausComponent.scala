package com.yuiwai.raus

import com.yuiwai.raus.ext.Raus
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("raus.RausComponent")
object RausComponent {
  @JSExport("apply")
  def apply(id: String, raus: Raus, key: String) = component(raus, key)()
    .renderIntoDOM(dom.document.getElementById(id))
  def component(raus: Raus, key: String) =
    ScalaComponent.builder[Unit]("RausComponent")
      .initialState(State(raus, key))
      .renderBackend[Backend]
      .componentDidMount(_.backend.load())
      .build

  case class State(raus: Raus, key: String) {
    def load(): State = {
      raus.load(key)
      this
    }
    def save(): State = {
      raus.save(key)
      this
    }
    def addTask(title: String): State = {
      raus.addTask(title)
      this
    }
  }

  final class Backend(bs: BackendScope[Unit, State]) {
    private val taskRef = Ref[HTMLInputElement]
    def load() = bs.modState(_.load())
    def save() = bs.modState(_.save())
    def addTask() = taskRef.foreachCB { i =>
      bs.modState(_.addTask(i.value))
    }
    def render(s: State) =
      <.div(
        <.div(
          <.input.withRef(taskRef)(),
          <.button(
            ^.onClick --> addTask(),
            "add"
          ),
          <.button(
            ^.onClick --> save(),
            "save"
          )
        ),
        s.raus.tasks.map(task => <.div(task.title)).toTagMod
      )
  }
}
