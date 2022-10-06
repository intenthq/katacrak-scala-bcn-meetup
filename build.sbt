lazy val root = (project in file("."))
  .settings(
    name := "Katacrak",
    scalaVersion := "2.13.9",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    libraryDependencies ++= Seq(
      "com.disneystreaming" %% "weaver-cats" % "0.8.0" % "test"
    )
  )
