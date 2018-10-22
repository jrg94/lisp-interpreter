#!/bin/sh

javac src/osu/cse6341/*.java
java -cp src/ osu.cse6341.LispInterpreter

# Uncomment if you'd prefer to use the jar
# 
# if [ ! -e lisp-interpreter.jar ]; then
#	wget https://github.com/jrg94/lisp-interpreter/releases/download/v1.1.0/lisp-interpreter.jar
# fi
#
# java -jar lisp-interpreter.jar
