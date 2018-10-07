import java.util.Arrays;
import java.util.Scanner;

public class LispInterpreter {

    public SExpression read(String lispProgram) {
        String lispPlusWhitespace = lispProgram.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim();
        String[] lispTokens = lispPlusWhitespace.split("\\s+");
        System.out.println(Arrays.toString(lispTokens));
        return null;
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
            SExpression root = lisp.read(input);
            // int result = lisp.eval(root);
            // lisp.print(result);
        }
    }
}
