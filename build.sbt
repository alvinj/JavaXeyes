import sbt.io.Using

name := "JavaXeyes"
version := "1.0"
scalaVersion := "2.12.8"


packageOptions in (Compile, packageBin) +=  {
    val file = new java.io.File("META-INF/MANIFEST.MF")
    val manifest = Using.fileInputStream(file)( in => new java.util.jar.Manifest(in) )
    Package.JarManifest( manifest )
}

