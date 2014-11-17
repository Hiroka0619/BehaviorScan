name := """BehaviorScan"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.scalikejdbc" %% "scalikejdbc"         % "2.2.+",
  "com.h2database"  %  "h2"                  % "1.4.+",
  "ch.qos.logback"  %  "logback-classic"     % "1.1.+",
  "mysql"           % "mysql-connector-java" % "5.1.+"
)
