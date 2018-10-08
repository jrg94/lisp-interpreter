import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class LispInterpreter {

    public static final SymbolicAtom NIL = new SymbolicAtom("NIL");

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
            while (!lispTokens.peek().equals(")")) {
                SExpression sub = parse(lispTokens);
                exps.add(sub);
            }
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
            lispTokens.pop();
        } else if (token.equals(")")) {
            throw new LispSyntaxException("Unexpected token: " + token);
        } else {
            curr = createAtom(token);
        }

        return curr;
    }

    /**
     * Generates an S expression from a token known to be an atom.
     * 
     * @param token a token
     * @return the token as an s expression
     */
    public SExpression createAtom(String token) {
        SExpression atom;
        try {
            int num = Integer.parseInt(token);
            atom = new IntegerAtom(num);
        } catch (NumberFormatException e) {
            atom = new SymbolicAtom(token);
        }
        return atom;
    }

    public SExpression read(String lispProgram) throws LispSyntaxException {
        Stack<String> lispTokens = tokenize(lispProgram);
        SExpression root = parse(lispTokens);
        if (!lispTokens.peek().equals("\0")) {
            throw new LispSyntaxException("Unexpected token: " + lispTokens.peek());
        }
        return root;
    }

    public int eval(SExpression root) {
        return 0;
    }

    public void print(SExpression result) {
        System.out.println(result);
    }

    public static void main(String[] args) {
        // TODO: generate abstract syntax tree from code
        // TODO: evaluate AST
        // TODO: report errors
        LispInterpreter lisp = new LispInterpreter();
        System.out.println("Welcome to the CSE6341 Lisp Interpreter by Jeremy Grifski!");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.isEmpty()) {
            try {
                SExpression root = lisp.read(input);
                lisp.print(root);
            } catch (LispSyntaxException e) {
                System.out.println(e);
            }
            input = in.nextLine();
        }
        in.close();
    }
}
