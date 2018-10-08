import java.util.Arrays;
import java.util.Scanner;

public class LispInterpreter {

    public String[] tokenize(String lispProgram) {
        String lispPlusWhitespace = lispProgram.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim();
        String[] lispTokens = lispPlusWhitespace.split("\\s+");
        return lispTokens;
    }

    public SExpression parse(String[] lispTokens, int index) throws LispSyntaxException {
        SExpression curr;
        if (index >= lispTokens.length) {
            throw new LispSyntaxException("Unexpected EOF");
        } else {
            String token = lispTokens[index];
            if (token.equals("(")) {
                curr = new NonAtom();
                while (true) {
                    index += 1;
                    if (index < lispTokens.length && lispTokens[index].equals(")")) {
                        return curr;
                    } else {
                        parse(lispTokens, index);
                    }
                }
            } else if (token.equals(")")) {
                throw new LispSyntaxException("Unexpected )");
            } else {
                curr = createAtom(token);
            }
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
        String[] lispTokens = tokenize(lispProgram);
        System.out.println(Arrays.toString(lispTokens));
        return parse(lispTokens, 0);
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
