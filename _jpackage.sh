# this build process requires JDK 14+

name=ScalaXEyes
main_class="com.valleyprogramming.javaxeyes.JavaXeyes"
input_jar="JavaXeyes-assembly-1.0.jar"

rm -rf output/${name}.app 2> /dev/null

# requires Java 14+
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

echo ""
echo "hopefully created 'output/$name.app'"
echo ""
