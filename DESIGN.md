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

As expected, the interpreter is built in REPL form. As a result,
the following sections describe each stage of the REPL.

### Read

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

### Eval

The evaluation portion of the Lisp Interpreter works by navigating the
newly constructed abstract syntax tree. The interpreter begins by calling
evaluate on the root node which starts a chain of recursion which evaluates
all children.

Of course, evaluation is more complicated than that. As a result, the Lisp
Interpreter follows the Lisp design notation from the course notes.

### Data Structures

The design notation relies on two key data structures: the aList and the dList.

The aList is short for association list, and it stores all of the bindings for
the interpreter. Bindings are in the form `(parameter . argument)` where
parameter is the symbol used by the function and argument is the argument 
passed to the function.

Meanwhile, the dList--short for definitions list--is a collection of function
definitions in the form `(function . ((parameters) . function_body))`.

The two lists are used in tandem to track function definitions and their usage.

### Recursion

With the two lists in hand, the rest is up to proper recursion. When we evaluate
a node, we begin by checking if it is an atom. With the way this Lisp Interpreter
is designed, this happens implicitly through classes. In other words, the
interpreter dispatches the proper evaluate method based on the type of the s-expression.

If the atom evaluate is executed, the lisp interpreter returns the evaluation of that
atom. Evaluating atoms is usually pretty easy. If they're an integer, the interpreter 
returns that integer. If it's a symbol, the interpreter checks the aList
for that symbol. If it doesn't exist, the interpreter throws an error.
Otherwise, the symbol is valid, and it's integer value is returned.

If the non-atom evaluate is executed, the interpreter checks if the current 
s-expression is a special form (QUOTE, COND, or DEFUN). In each case, there's
a special behavior. 

If the non-atom is not a special form, the interpreter assumes the Symbolic Atom
is a function, and calls apply on its parent. At that point, the function is
tested to see if it is a primitive. If it is, the primitive function is executed.
Otherwise, the function definition is extracted from the dList and executed.

As expected, this process is completed through recursion, so these evaluation
stages occur in a cycle until the entire abstract syntax tree is processed. 

### Print

After evaluation, the interpeter should have a complete s-expression in 
the form of a binary tree. At that point, we begin a very basic recursive 
print which is handled by the atom classes implicitly. In other words, the
interpreter makes a quick call to the `toString` method of the root node, 
and the interpreter gets a complete printing of the tree in dot notation. 
The various `toString` implementations for each atom handle the actual 
logistics of the printing.

In addition, the interpeter prints the resulting s-expression from evaluation directly.

### Loop

As mentioned already, the looping mechanism is how the lisp interpreter handle 
the dollar signs.During each iteration, we wait for a line of just one 
dollar sign before we break into the outer loop which checks for the double 
dollar sign token. 

During this time, the lisp interpreter also catches errors which are printed
plainly to the screen. 

Because functions can only be declared at the top level, the interpreter handles
function definitions here in the loop phase.

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

1. "No such boolean operator defined: " + operator
2. "Illegal dList update"
3. "Unbound atom " + this
4. "Unable to find " + this + " in dList"
5. "Expected Empty List but found " + this.toString()
6. "No such arithmetic operator defined: " + operator
7. "Invalid list of arguments for %s: %s", this.name(), args
8. "%s is not implemented", this
9. "Unable to call CDR on atom: " + this
10. "Unable to call CDR on atom: " + this
11. "Invalid condition: " + this.toString()
12. "Unable to find " + this + " in dList"
13. "Unable to find " + this + " in aList"
14. "Unable to perform " + operator + " on " + this + " and " + other

Due to the complex nature of this interpreter, it's unclear if all of these
exceptions will occur. That said, the provide nice coverage in the 
scenarios that they cover.

## Testing

To ensure everything works, the Lisp Interpreter is thoroughly
tested using JUnit files. In total, there were 47 tests conducted
to ensure accuracy of the interpreter from parsing to evaluation.

In fact, there are even unit tests used to cover individual methods.
In general, however, most testing occurs at the parsing and
evaluation levels. Feel free to explore all the files in the
`src/test/java` directory.

[1]: http://norvig.com/lispy.html
