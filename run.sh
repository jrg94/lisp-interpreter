#!/bin/sh

javac src/osu/cse6341/*.java
java -cp src/ osu.cse6341.LispInterpreter
