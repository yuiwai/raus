package com.yuiwai.raus.react

import com.yuiwai.raus.ext.Raus
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("com.yuiwai.RausComponent")
object RausComponent {
  case class State(raus: Raus, key: String) {
    def load(): State = {
      raus.load(key)
      this
    }
    def save(): State = {
      raus.save(key)
      this
    }
    def addTask(): State = {
      raus.addTask("test")
      this
    }
  }
  class Backend(bs: BackendScope[Unit, State]) {
    def load() = bs.modState(_.load())
    def save() = bs.modState(_.save())
    def addTask() = bs.modState(_.addTask())
    def render(s: State) =
      <.div(
        <.div(
          <.input(),
          <.button(
            ^.onClick --> addTask(),
            "add"
          ),
          <.button(
            ^.onClick --> save(),
            "save"
          )
        ),
        s.raus.tasks().map(task => <.div(task.title)).toTagMod
      )
  }
  @JSExport("apply")
  def apply(id: String, raus: Raus, key: String) =
    component(raus, key)().renderIntoDOM(dom.document.getElementById(id))
  def component(raus: Raus, key: String) =
    ScalaComponent.builder[Unit]("RausComponent")
      .initialState(State(raus, key))
      .renderBackend[Backend]
      .componentDidMount(_.backend.load())
      .build
}
