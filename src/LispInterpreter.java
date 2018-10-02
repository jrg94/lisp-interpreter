import java.util.Arrays;

public class LispInterpreter {
    public static void main(String[] args) {
        // TODO: get Lisp code from user
        // TODO: tokenize Lisp code
        // TODO: generate abstract syntax tree from code
        // TODO: evaluate AST
        // TODO: report errors
        String someLispCode = "(2 3)";
        String lispPlusWhitespace = someLispCode.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim();
        String[] lispTokens = lispPlusWhitespace.split("\\s+");
        System.out.println(Arrays.toString(lispTokens));
    }
}
