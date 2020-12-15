#!/usr/bin/env bash
set -euo pipefail

isodate() {
  date --iso-8601=s
}

echo "$(isodate) [INFO ] java -version"
java -version 2>&1

echo "$(isodate) [INFO ] Cleaning up old class files"
rm -rf src/main/java/*.class

(
  cd src/main/java || exit 1 \
  && echo "$(isodate) [INFO ] Compiling CompilerTest.java JavaSourceFile.java using javac" \
  && javac CompilerTest.java JavaSourceFile.java \
  && echo "$(isodate) [INFO ] Compiling CompileMe.java using CompilerTest/ToolProvider.getSystemJavaCompiler()" \
  && java --add-modules jdk.compiler,jdk.javadoc CompilerTest CompileMe.java
)
