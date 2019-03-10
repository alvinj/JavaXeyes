# SEE https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javapackager.html

JAVA_HOME=`/usr/libexec/java_home -v 1.8`
APP_DIR_NAME=Jeyes.app

#-deploy -Bruntime=/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home \
javapackager \
  -deploy -Bruntime=${JAVA_HOME} \
  -native image \
  -srcdir . \
  -srcfiles target/scala-2.12/javaxeyes_2.12-1.0.jar \
  -outdir release \
  -outfile ${APP_DIR_NAME} \
  -appclass com.valleyprogramming.javaxeyes.JavaXeyes \
  -Bicon=jeyes.icns \
  -name "Jeyes" \
  -title "Jeyes" \
  -nosign \
  -v


echo ""
echo "If that succeeded, it created \"release/bundles/${APP_DIR_NAME}\""


