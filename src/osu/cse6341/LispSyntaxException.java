package osu.cse6341;
public class LispSyntaxException extends Exception {

    private static final long serialVersionUID = -4819106933558488363L;

    public LispSyntaxException() {
        super();
    }

    public LispSyntaxException(String arg0) {
        super(arg0);
    }

    public LispSyntaxException(Throwable arg0) {
        super(arg0);
    }

    public LispSyntaxException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public LispSyntaxException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}