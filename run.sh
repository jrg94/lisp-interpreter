#!/bin/sh
if [ ! -e lisp-interpreter.jar ]; then
	wget https://github.com/jrg94/lisp-interpreter/releases/download/v1.0.0/lisp-interpreter.jar
fi

java -jar lisp-interpreter.jar
