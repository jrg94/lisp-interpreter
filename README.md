# CSE 6341 Lisp Interpreter

Welcome to the Lisp Interpreter README. In this document,
we'll cover exactly how to build and execute the Lisp REPL.

## Build and Run

In order to build and run the Lisp REPL, you can attempt the following:

```console
$ ./run.sh
```

This file contains all the necessary code to build the java files,
run the interpreter, and clean up the java files following execution.
Feel free to take a look at the file to see how it works.

## REPL

Once executed, the REPL should launch with a message that reads:

```console
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

## File Redirection

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

```console
$ ./run.sh < lisp.txt
```

Be careful not to include your test file in the directory because it
be deleted after testing. The `run.sh` file leverages git to clean
the repo of all files not currently tracked. As a result, new files
will be deleted. 

If you run into an problems, let me know!
