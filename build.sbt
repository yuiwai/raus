version in ThisBuild := "0.1"
organization in ThisBuild := "com.yuiwai"
scalaVersion in ThisBuild := "2.12.6"
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
)

lazy val core = (project in file("core"))
  .settings(
    name := "simple-task-core"
  )

lazy val example = (project in file("example"))
  .settings(
    name := "simple-task-example"
  )
  .dependsOn(core)
