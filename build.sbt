import sbtcrossproject.{crossProject, CrossType}

version in ThisBuild := "0.2-SNAPSHOT"
organization in ThisBuild := "com.yuiwai"
scalaVersion in ThisBuild := "2.12.6"
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
)

lazy val core = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("core"))
  .settings(
    name := "raus-core"
  )
lazy val coreJS = core.js
lazy val coreJVM = core.jvm

lazy val ext = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full) in file("ext"))
  .settings(
    name := "raus-ext"
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.2"
    )
  )
lazy val extJS = ext.js
lazy val extJVM = ext.jvm

lazy val example = (project in file("example"))
  .settings(
    name := "raus-example"
  )
  .dependsOn(coreJVM)
