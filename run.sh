#!/usr/bin/env bash
set -euo pipefail

isodate() {
  date --iso-8601=s
}

echo "$(isodate) [INFO ] checking for java 16"

set +e
if ! java -version 2>&1 | head -1 | grep -sq "\"16"; then
  echo "$(isodate) [ERROR] java-version is not 16!"
  exit 1
fi
set -e

echo "$(isodate) [INFO ] Cleaning up old class files"
rm -rf src/main/java/*.class

(
  cd src/main/java || exit 1 \
  && echo "$(isodate) [INFO ] Compiling CompilerTest.java JavaSourceFile.java using javac" \
  && javac CompilerTest.java JavaSourceFile.java \
  && echo "$(isodate) [INFO ] Compiling CompileMe.java using CompilerTest/ToolProvider.getSystemJavaCompiler()" \
  && java --add-modules jdk.compiler,jdk.javadoc CompilerTest CompileMe.java
)
