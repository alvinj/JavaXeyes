# this build process requires JDK 14+

name=ScalaXEyes
main_class="com.valleyprogramming.javaxeyes.JavaXeyes"
input_jar="JavaXeyes-assembly-1.0.jar"

# jpackage needs this to be gone:
rm -rf output/${name}.app 2> /dev/null

# try to assemble our JAR file:
echo ""
echo "RUNNING 'SBT ASSEMBLY' ..."
sbt assembly
if [ $? != 0 ]
then
    echo "Compile/assemble failed, exiting"
    exit 1
fi

# 'assembly' worked, so merrily carry on

echo ""
echo "COPYING THE 'ASSEMBLY' JAR FILE ..."
cp target/scala-2.12/${input_jar} input

# package JAR as a macOS app. requires Java 14+
echo ""
echo "RUNNING 'jpackage' TO BUILD THE FINAL APP ..."
jpackage \
    --type app-image \
    --verbose \
    --input input \
    --dest output \
    --name $name \
    --main-jar $input_jar \
    --main-class $main_class \
    --icon input/jeyes.icns \
    --mac-package-name $name \
    --mac-package-identifier $main_class \
    --java-options -Xmx2048m
    # --module-path /Users/al/bin/jdk-14.0.1.jdk/Contents/Home/jmods \
    # --add-modules java.base,javafx.controls,javafx.web,javafx.graphics,javafx.media,java.datatransfer,java.desktop,java.scripting,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom,javafx.fxml,java.naming,java.sql,jdk.charsets \

if [ $? == 0 ]
then
    echo ""
    echo "HOPEFULLY CREATED 'output/$name.app'"
    echo ""
else
    echo ""
    echo "I THINK THERE WAS AN ERROR PACKAGING THE APP ('output/$name.app')"
    echo ""
    exit 1
fi

