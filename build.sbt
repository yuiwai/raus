import sbtcrossproject.{crossProject, CrossType}

version in ThisBuild := "0.4.0-SNAPSHOT"
organization in ThisBuild := "com.yuiwai"
scalaVersion in ThisBuild := "2.12.6"
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
)

lazy val root = project.in(file("."))
  .aggregate(coreJS, coreJVM, extJS, extJVM)
  .settings(
    name := "raus",
    publish := {},
    publishLocal := {}
  )

lazy val core = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("core"))
  .settings(
    name := "raus-core",
    publishTo := Some(Resolver.file("file", new File("release")))
  )
lazy val coreJS = core.js
lazy val coreJVM = core.jvm

lazy val ext = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full) in file("ext"))
  .settings(
    name := "raus-ext"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.6",
      "com.github.pathikrit" %% "better-files" % "3.5.0"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.2"
    )
  )
lazy val extJS = ext.js.dependsOn(coreJS)
lazy val extJVM = ext.jvm.dependsOn(coreJVM)

lazy val example = (project in file("example"))
  .settings(
    name := "raus-example"
  )
  .dependsOn(coreJVM)

lazy val cli = (project in file("cli"))
  .settings(
    name := "raus-cli"
  )
  .dependsOn(extJVM)

lazy val `example-cli` = (project in file("example-cli"))
  .settings(
    name := "example-cli"
  )
  .dependsOn(cli)

lazy val react = (project in file("react"))
  .settings(
    name := "raus-react",
    libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.2.2",
    jsDependencies ++= Seq(
      "org.webjars.npm" % "react" % "16.2.0"
        / "umd/react.development.js"
        minified "umd/react.production.min.js"
        commonJSName "React",

      "org.webjars.npm" % "react-dom" % "16.2.0"
        / "umd/react-dom.development.js"
        minified "umd/react-dom.production.min.js"
        dependsOn "umd/react.development.js"
        commonJSName "ReactDOM",

      "org.webjars.npm" % "react-dom" % "16.2.0"
        / "umd/react-dom-server.browser.development.js"
        minified "umd/react-dom-server.browser.production.min.js"
        dependsOn "umd/react-dom.development.js"
        commonJSName "ReactDOMServer"),
    dependencyOverrides += "org.webjars.npm" % "js-tokens" % "3.0.2"
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(extJS)
