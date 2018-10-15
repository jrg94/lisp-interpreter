# The Design Document

My Lisp interpreter implementation is based largely on [Lis.py][1]. 
However, it is implemented in Java.

## Classes

The core of the design is broken up into 5 classes.

1. The interpreter
2. Integer Atoms
3. Non-Atoms
4. Symbolic Atoms
5. The Lisp Syntax Exception

In addition, there is an S-Expression interface which each of
the atom classes inherits. However, the interface provides no
functionality beyond classification.

## Structure

As expected, the interpreter is built in REPL form. In other words,
for this part of the project, I've implemented the read and print
portions of the REPL. 

As currently implemented, the read stage *only* handles s-expression
parsing. The special case dollar sign ($) parsing is actually handled
at the top level. In other words, read doesn't know anything about
dollar signs. This allows us to focus on true s-expression parsing.

The s-expression parser works by seeking left parentheses and iterating
over the underlying s-expression until a right parentheses is encountered.
During this iteration, recursion occurs when an additional left parentheses
is encountered.

As s-expressions are encountered, they are categorized as either dot notation
or list notation. Depending on the case, we parse them into s-expressions
differently. For instance, if we see a dot, we know we can only have two
atoms. If we have more, we have a problem. 

Upon completion, we should have a complete s-expression in the form of a 
binary tree. At that point, we begin a very basic recursive print which
is handled by are atom classes implicitly. In other words, we make a quick
call to the `toString` method of our root node, and we'll get a complete
printing of our tree in dot notation. The various `toString` implementations
for each atom handle the actual logistics of the printing.

## Errors

The following errors are reported by the Lisp Interpreter:

1. "Unexpected EOF"
2. "Too many dots"
3. "Unexpected token: " + token
4. "Inappropriate number of expressions with dot notation: " + exps.toString()
5. "Token (%s) is not alphanumeric", token
6. "Token (%s) does not start with a letter", token
7. "Unexpected token: " + lispTokens.peek()

In total, we can handle about 7 error cases during parsing alone. 
We'll see what happens during evaluation.

[1]: http://norvig.com/lispy.html