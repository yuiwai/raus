import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

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

lazy val protobuf = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full) in file("protobuf"))
  .settings(
    name := "raus-protobuf",
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    PB.protoSources in Compile := Seq((baseDirectory in ThisBuild).value / "protobuf/shared/main/protobuf"),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
  .jvmSettings(
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.5" % "test"
  )
  .jsSettings(
    unmanagedSourceDirectories in Compile += ((baseDirectory in ThisBuild).value / "protobuf/jvm/src/main/scala"),
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %%% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion,
      "com.lihaoyi" %%% "utest" % "0.6.5" % "test"
    )
  )
  .dependsOn(core)
lazy val protobufJS = protobuf.js
lazy val protobufJVM = protobuf.jvm

lazy val ext = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full) in file("ext"))
  .settings(
    name := "raus-ext",
    publishTo := Some(Resolver.file("file", new File("release")))
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.6",
      "com.github.pathikrit" %% "better-files" % "3.5.0"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.2",
      "org.scala-js" %%% "scalajs-java-time" % "0.2.5"
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

lazy val checkJvm = (project in file("check-jvm"))
  .settings(
    name := "raus-check-jvm"
  )
  .dependsOn(extJVM)

lazy val checkJs = (project in file("check-js"))
  .settings(
    name := "raus-check-js",
    scalaJSUseMainModuleInitializer := true,
    mainClass in Compile := Some("com.yuiwai.raus.check.Check")
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(extJS)

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
