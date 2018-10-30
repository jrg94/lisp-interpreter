#!/bin/sh

javac src/main/java/osu/cse6341/*.java
java -cp src/main/java osu.cse6341.LispInterpreter
git clean -fdx
