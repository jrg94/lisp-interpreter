# CSE 6341 Lisp Interpreter

Welcome to the Lisp Interpreter README. In this document,
we'll cover exactly how to build and execute the Lisp REPL.

## Build

In order to build the Lisp REPL, you can attempt the following:

```
ant -f build.xml
```

The build file was generated automatically by eclipse, and it
was not thoroughly tested.

Alternatively, you can skip to the next step by leveraging
the `lisp-interpreter.jar` file which is available alongside
the source code.

## Execute

In order to run the Lisp REPL, you'll want to execute the
`lisp-interpreter.jar` file as follows:

```
java -jar lisp-interpreter.jar
```

Once executed, the REPL should launch with a message that reads:

```
Welcome to the CSE6341 Lisp Interpreter by Jeremy Grifski!
You've just launched the Lisp REPL.
To evaluate an s-expression, place a $ on a single line.
To exit the REPL, place $$ on a single line.
```

Following this message, you should see a `lisp-interpreter>`. 
This indicates where you can begin typing. Feel free to input your
text here. 

When you're finished writing your s-expression, enter a dollar sign ($) 
on a single line. This will cause the REPL to "evaluate"
the s-expression you've entered. If successful, you'll see
your s-expression printed in dot notation.

When you're done, enter a pair of dollar signs ($$) on a single line. This
terminates the program. If successful, you should see `Goodbye!`.

Alternatively, you can safely automate the interpreter by piping
several s-expressions from a file such as the following:

```
(2 3)
$
(x 4 5 (3 4))
$
$$
```

Assuming this file is named `lisp.txt`, you can redirect it to
the jar file as follows:

```
java -jar lisp-interpreter.jar < lisp.txt
```

If you run into an problems, let me know!
