/** ********* PROJECT INFO ******************/
name := "scala_bootstrap"
version := "1.1"

/** ********* PROJECT SETTINGS ******************/
Configuration.settings

/** ********* PROD DEPENDENCIES *****************/
libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time"        % "2.16.0",
  "com.lihaoyi"            %% "pprint"             % "0.4.4",
  "com.github.gilbertw1"   %% "slack-scala-client" % "0.2.0"
)

/** ********* TEST DEPENDENCIES *****************/
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"                   % "3.0.1" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.4.2" % Test
)

/** ********* COMMANDS ALIASES ******************/
addCommandAlias("t", "test")
addCommandAlias("to", "testOnly")
addCommandAlias("tq", "testQuick")
addCommandAlias("tsf", "testShowFailed")

addCommandAlias("c", "compile")
addCommandAlias("tc", "test:compile")

addCommandAlias("s", "scalastyle")
addCommandAlias("ts", "test:scalastyle")

addCommandAlias("f", "scalafmt")      // Format all files according to ScalaFmt
addCommandAlias("ft", "scalafmtTest") // Test if all files are formatted according to ScalaFmt

addCommandAlias("prep", ";c;tc;s;ts;ft") // All the needed tasks before running the test
