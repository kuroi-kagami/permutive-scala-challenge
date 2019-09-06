name := "Permutive Analytics Exercise"

version := "0.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  Seq(
    "org.typelevel"          %% "cats-core"   % "1.6.0",
    "org.typelevel"          %% "cats-effect" % "1.2.0",
    "co.fs2"                 %% "fs2-core"    % "1.0.4",
    "co.fs2"                 %% "fs2-io"      % "1.0.4"
  )
}