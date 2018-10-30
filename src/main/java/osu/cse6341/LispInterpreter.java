package osu.cse6341;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

/**
 * The lisp interpreter class which handles the REPL.
 *
 * @author Jeremy Grifski
 */
public class LispInterpreter {

    public Stack<NonAtom> aList;
    public ArrayList<NonAtom> dList;
    public ArrayList<SymbolicAtom> symbols;

    /**
     * The lisp interpreter constructor. It initializes the symbol table and the
     * dList.
     */
    public LispInterpreter() {
        aList = new Stack<NonAtom>();
        dList = new ArrayList<NonAtom>();
        symbols = new ArrayList<SymbolicAtom>();
    }

    /**
     * Converts a lisp program to a stack of tokens.
     *
     * @param lispProgram a lisp program
     * @return a stack of lisp program tokens
     */
    private Stack<String> tokenize(String lispProgram) {
        String lispPlusWhitespace = lispProgram.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim();
        String[] lispTokensArray = lispPlusWhitespace.split("\\s+");
        ArrayList<String> lispTokensArrayList = new ArrayList<String>(Arrays.asList(lispTokensArray));
        lispTokensArrayList.add("\0");
        Collections.reverse(lispTokensArrayList);
        Stack<String> lispTokens = new Stack<String>();
        lispTokens.addAll(lispTokensArrayList);
        return lispTokens;
    }

    /**
     * Parses the set of tokens and produces the AST.
     *
     * @param lispTokens a stack of lisp tokens
     * @return an AST
     * @throws LispSyntaxException when parsing fails
     */
    private SExpression parse(Stack<String> lispTokens) throws LispSyntaxException {
        SExpression curr;
        if (lispTokens.peek().equals("\0")) {
            throw new LispSyntaxException("Unexpected EOF");
        }

        String token = lispTokens.pop();
        if (token.equals("(")) {
            ArrayList<SExpression> exps = new ArrayList<SExpression>();
            boolean isDotNotation = false;
            while (!lispTokens.peek().equals(")")) {
                if (lispTokens.peek().equals(".") && !isDotNotation) {
                    lispTokens.pop();
                    isDotNotation = true;
                } else if (lispTokens.peek().equals(".") && isDotNotation) {
                    throw new LispSyntaxException("Too many dots");
                } else {
                    SExpression sub = parse(lispTokens);
                    exps.add(sub);
                }
            }
            if (isDotNotation) {
                curr = parseDotNotation(exps);
            } else {
                curr = parseListNotation(exps);
            }
            lispTokens.pop();
        } else if (token.equals(")")) {
            throw new LispSyntaxException("Unexpected token: " + token);
        } else {
            curr = createAtom(token);
        }

        return curr;
    }

    /**
     * A dot notation parsing function.
     *
     * @param exps a list of expressions for a given SExpression
     * @return an SExpression from the list of SExpressions
     * @throws LispSyntaxException when the list of expressions is not 2
     */
    private SExpression parseDotNotation(ArrayList<SExpression> exps) throws LispSyntaxException {
        NonAtom curr;
        if (exps.size() != 2) {
            throw new LispSyntaxException("Inappropriate number of expressions with dot notation: " + exps.toString());
        } else {
            curr = new NonAtom();
            curr.setLeft(exps.get(0));
            curr.setRight(exps.get(1));
        }
        return curr;
    }

    /**
     * A list notation parsing function.
     *
     * @param exps a list of expressions for a given SExpression
     * @return an SExpression from the list of SExpressions
     */
    private SExpression parseListNotation(ArrayList<SExpression> exps) {
        SExpression curr;
        if (exps.isEmpty()) {
            curr = Logic.NIL.getAtom();
        } else {
            curr = new NonAtom();
            NonAtom temp = (NonAtom) curr;
            for (int i = 0; i < exps.size(); i++) {
                temp.setLeft(exps.get(i));
                if (i < exps.size() - 1) {
                    NonAtom next = new NonAtom();
                    temp.setRight(next);
                    temp = next;
                } else {
                    temp.setRight(Logic.NIL.getAtom());
                }
            }
        }
        return curr;
    }

    /**
     * Generates an S expression from a token known to be an atom.
     *
     * @param token a token
     * @return the token as an s expression
     * @throws LispSyntaxException
     */
    private SExpression createAtom(String token) throws LispSyntaxException {
        SExpression atom;
        try {
            int num = Integer.parseInt(token);
            atom = new IntegerAtom(num);
        } catch (NumberFormatException e) {
            atom = searchSymbols(token);
        }
        return atom;
    }

    /**
     * Tests that a string is alphanumeric.
     *
     * @param underTest the string under test
     * @return true if the string only contains alphanumeric digits
     */
    private boolean isAlphanumeric(String underTest) {
        for (char c : underTest.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A helper method for searching the symbol table. If a symbol is found,
     * that symbol is returned. Otherwise, a new symbol is created, added to the
     * symbol table, and returned.
     *
     * @param token a token
     * @return an existing symbolic atom from the symbol table; a new symbolic
     *         atom otherwise
     * @throws LispSyntaxException
     */
    private SymbolicAtom searchSymbols(String token) throws LispSyntaxException {
        for (SymbolicAtom symbol : symbols) {
            if (symbol.getValue().equals(token)) {
                return symbol;
            }
        }
        SymbolicAtom symbol = new SymbolicAtom(token);
        if (Character.isLetter(token.charAt(0))) {
            if (isAlphanumeric(token)) {
                symbols.add(symbol);
            } else {
                String err = String.format("Token (%s) is not alphanumeric", token);
                throw new LispSyntaxException(err);
            }
        } else {
            String err = String.format("Token (%s) does not start with a letter", token);
            throw new LispSyntaxException(err);
        }
        return symbol;
    }

    /**
     * The read stage of the REPL.
     *
     * @param lispProgram a lisp program as a string
     * @return a lisp program as an AST
     * @throws LispSyntaxException when there are syntax errors
     */
    public SExpression read(String lispProgram) throws LispSyntaxException {
        Stack<String> lispTokens = tokenize(lispProgram);
        SExpression root = parse(lispTokens);
        if (!lispTokens.peek().equals("\0")) {
            throw new LispSyntaxException("Unexpected token: " + lispTokens.peek());
        }
        return root;
    }

    /**
     * A method which updates the dList if the given expressions is a NonAtom
     * composed of a DEFUN.
     */
    public SExpression updateDList(SExpression ast) throws LispEvaluationException {
        SExpression success = Logic.NIL.getAtom();
        if (ast.isAtom().equals(Logic.NIL.getAtom())) {
            NonAtom root = (NonAtom) ast;
            if (root.car().equals(SpecialForms.DEFUN.getAtom())) {
                NonAtom decl = new NonAtom();
                NonAtom body = new NonAtom();
                success = root.cdr().car().car();
                SExpression parameters = root.cdr().car().cdr().car();
                System.out.println(parameters);
                SExpression functionBody = root.cdr().cdr().car();
                System.out.println(functionBody);
                body.setLeft(parameters);
                body.setRight(functionBody);
                decl.setLeft(success);
                decl.setRight(body);
                this.dList.add(decl);
            }
        }
        return success;
    }

    /**
     * Evaluates the abstract syntax tree.
     *
     * @param ast an abstract syntax tree
     * @return the result of the evaluation
     */
    public SExpression evaluate(SExpression ast) throws LispEvaluationException {
        return ast.evaluate(this.aList, this.dList);
    }

    /**
     * Outputs the AST in dot notation.
     *
     * @param msg a message to prepend the output
     * @param result the AST
     */
    public void print(String msg, SExpression result) {
        System.out.println(msg + ": " + result);
    }

    /**
     * The read, evaluate, print function.
     *
     * @param currExpression
     */
    private void rep(String currExpression) {
        try {
            SExpression root = this.read(currExpression);
            this.print("Dot Notation", root);
            SExpression funcName = this.updateDList(root);
            if (funcName.equals(Logic.NIL.getAtom())) {
                SExpression result = this.evaluate(root);
                this.print("Result", result);
            } else {
                this.print("Result", funcName);
            }
        } catch (LispSyntaxException | LispEvaluationException | StackOverflowError e) {
            System.out.println(e);
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                System.out.println(e);
            }
        }
    }

    /**
     * The repl execution engine.
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        String input = promptUser(in);
        while (!input.equals("$$")) {
            String currExpression = "";
            while (!input.equals("$")) {
                currExpression += input;
                input = promptUser(in);
            }
            rep(currExpression);
            input = promptUser(in);
        }
        in.close();
        System.out.println("Goodbye!");
    }

    /**
     * A helper function used to issue the input string and grab the next line
     * of text.
     *
     * @param in a scanner for reading standard input
     * @return the text received from the user
     */
    private String promptUser(Scanner in) {
        System.out.print("lisp-interpreter> ");
        String input = in.nextLine();
        return input;
    }

    /**
     * Runs the Lisp REPL.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the CSE6341 Lisp Interpreter by Jeremy Grifski!");
        System.out.println("You've just launched the Lisp REPL.");
        System.out.println("To evaluate an s-expression, place a $ on a single line.");
        System.out.println("To exit the REPL, place $$ on a single line.");
        LispInterpreter lisp = new LispInterpreter();
        lisp.run();
    }
}
