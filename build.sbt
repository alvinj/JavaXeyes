lazy val root = (project in file("."))
    .settings(
        name := "JavaXeyes",
        version := "1.0",
        scalaVersion := "2.12.15"
    )

Compile / run / mainClass := Some("com.valleyprogramming.javaxeyes.JavaXeyes")
Compile / packageBin / mainClass := Some("com.valleyprogramming.javaxeyes.JavaXeyes")

// https://www.scala-sbt.org/1.x/docs/Forking.html
Compile / run / fork := true

scalacOptions ++= Seq(          // use ++= to add to existing options
  "-encoding", "utf8",          // if an option takes an arg, supply it on the same line
  "-feature",                   // then put the next option on a new line for easy editing
  "-language:implicitConversions",
  "-language:existentials",
  "-unchecked",
)

javacOptions += "-Xlint:deprecation"
javacOptions += "-Xlint:unchecked"



// packageOptions in (Compile, packageBin) +=  {
//     val file = new java.io.File("META-INF/MANIFEST.MF")
//     val manifest = Using.fileInputStream(file)( in => new java.util.jar.Manifest(in) )
//     Package.JarManifest( manifest )
// }




