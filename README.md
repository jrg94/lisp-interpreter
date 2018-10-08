# CSE 6341 Lisp Interpreter

Welcome to the Lisp Interpreter README. In this document,
we'll cover exactly how to build and execute the Lisp REPL.

## Build

In order to build the Lisp REPL, you'll want to:

## Execute

In order to run the Lisp REPL, you'll want to:

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

When you're finished writing your s-expression, enter a line of a
single dollar sign ($). This will cause the REPL to "evaluate"
the s-expression you've entered. If successful, you'll see
your s-expression printed in dot notation.

When you're done, enter a line of pair of dollar signs ($$). This
terminates the program. If successful, you should see `Goodbye!`.
