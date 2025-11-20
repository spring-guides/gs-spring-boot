#!/bin/sh
set -e

cd "$(dirname "$0")"

cd ../complete
./mvnw clean package
rm -rf target
./gradlew build
rm -rf build

cd ../complete-kotlin
./gradlew build
rm -rf build

cd ../initial
./mvnw clean compile
rm -rf target
./gradlew compileJava
rm -rf build

cd ../initial-kotlin
./gradlew compileKotlin
rm -rf build