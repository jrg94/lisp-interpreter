import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class LispInterpreter {

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

    public SExpression parse(Stack<String> lispTokens) throws LispSyntaxException {
        SExpression curr;
        if (lispTokens.peek().equals("\0")) {
            throw new LispSyntaxException("Unexpected EOF");
        }

        String token = lispTokens.pop();
        if (token.equals("(")) {
            curr = new NonAtom();
            while (!lispTokens.peek().equals(")")) {
                parse(lispTokens);
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

    public void print(int result) {
        System.out.println(result);
    }

    public static void main(String[] args) {
        // TODO: get Lisp code from user
        // TODO: tokenize Lisp code
        // TODO: generate abstract syntax tree from code
        // TODO: evaluate AST
        // TODO: report errors
        LispInterpreter lisp = new LispInterpreter();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.isEmpty()) {
            try {
                lisp.read(input);
            } catch (LispSyntaxException e) {
                System.out.println(e);
            }
            input = in.nextLine();
            // int result = lisp.eval(root);
            // lisp.print(result);
        }
        in.close();
    }
}
