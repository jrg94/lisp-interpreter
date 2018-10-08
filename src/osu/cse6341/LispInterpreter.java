package osu.cse6341;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class LispInterpreter {

    public static final SymbolicAtom NIL = new SymbolicAtom("NIL");
    public static final SymbolicAtom T = new SymbolicAtom("T");

    public ArrayList<SymbolicAtom> dList;
    public ArrayList<SymbolicAtom> symbols;

    /**
     * The lisp interpreter constructor. It initializes the symbol table and the
     * dList.
     */
    public LispInterpreter() {
        dList = new ArrayList<SymbolicAtom>();
        dList.add(new SymbolicAtom("cons"));
        dList.add(new SymbolicAtom("car"));
        dList.add(new SymbolicAtom("cdr"));
        symbols = new ArrayList<SymbolicAtom>();
    }

    /**
     * Converts a lisp program to a stack of tokens.
     * 
     * @param lispProgram a lisp program
     * @return a stack of lisp program tokens
     */
    public Stack<String> tokenize(String lispProgram) {
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
    public SExpression parse(Stack<String> lispTokens) throws LispSyntaxException {
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
            curr = NIL;
        } else {
            curr = new NonAtom();
            NonAtom temp = (NonAtom) curr;
            for (int i = 0; i < exps.size(); i++) {
                temp.setLeft(exps.get(i));
                if (i < exps.size() - 1) {
                    temp.setRight(new NonAtom());
                    temp = (NonAtom) temp.getRight();
                } else {
                    temp.setRight(NIL);
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
    public SExpression createAtom(String token) throws LispSyntaxException {
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
            symbols.add(symbol);
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
     * Outputs the AST in dot notation.
     * 
     * @param result the AST
     */
    public void print(SExpression result) {
        System.out.println(result);
    }

    /**
     * The read, evaluate, print function.
     * 
     * @param currExpression
     */
    private void rep(String currExpression) {
        try {
            SExpression root = this.read(currExpression);
            this.print(root);
        } catch (LispSyntaxException e) {
            System.out.println(e);
        }
    }

    /**
     * The repl execution engine.
     */
    private void run() {
        System.out.println("Welcome to the CSE6341 Lisp Interpreter by Jeremy Grifski!");
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
        LispInterpreter lisp = new LispInterpreter();
        lisp.run();
    }
}
