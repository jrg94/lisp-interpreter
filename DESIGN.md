# The Design Document

My Lisp interpreter implementation is based largely on [Lis.py][1]. 
However, it is implemented in Java.

## Files

The core of the design is broken up into two parts: classes and enums.

### Classes

The following list contains all the classes used by the Lisp
Interpreter:

1. The interpreter
2. Integer Atoms
3. Non-Atoms
4. Symbolic Atoms
5. The Lisp Syntax Exception
6. The Lisp Evaluation Exception

In addition, there is an S-Expression abstract class which each 
s-expression class extends. 

### Enums

All built-in structures are implemented via enums:

1. Logic (T, NIL)
2. Special Forms (QUOTE, COND, DEFUN)
3. Primitives (EQ, NULL, PLUS, etc.)

Each enum inherits an atom mapping interface which
forces them to provide themselves as a Symbolic atom for 
comparisons. 

In total, there are 10 Java files which make up the entirety
of the Lisp Interpreter.

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

Errors are broken up into two classes:

1. Lisp Syntax Errors
2. Lisp Evaluation Errors

In the following sections, we'll break these down further:

### Syntax Errors

The following errors are reported by the Lisp Interpreter during parsing:

1. "Unexpected EOF"
2. "Too many dots"
3. "Unexpected token: " + token
4. "Inappropriate number of expressions with dot notation: " + exps.toString()
5. "Token (%s) is not alphanumeric", token
6. "Token (%s) does not start with a letter", token
7. "Unexpected token: " + lispTokens.peek()

In total, we can handle about 7 error cases during parsing alone. 

### Evaluation Errors

On top of the parsing errors, the Lisp interpreter can handle the
following evaluation errors:

[1]: http://norvig.com/lispy.html
