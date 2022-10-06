lazy val root = (project in file("."))
  .settings(
    name := "Katacrak",
    scalaVersion := "2.13.9",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    Test / fork := true,
    Test / javaOptions += "-Xmx64M",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.17.0" % "test",
      "com.beachape" %% "enumeratum" % "1.7.0" % "test",
      "com.nrinaudo" %% "kantan.csv" % "0.7.0" % "test",
      "com.nrinaudo" %% "kantan.csv-generic" % "0.7.0" % "test",
      "com.nrinaudo" %% "kantan.csv-enumeratum" % "0.7.0" % "test",
      "com.disneystreaming" %% "weaver-cats" % "0.8.0" % "test"
    )
  )
