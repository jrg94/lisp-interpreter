package osu.cse6341;

/**
 * The Lisp Syntax Exception class which extends the generic Exception class.
 * 
 * @author Jeremy Grifski
 */
public class LispSyntaxException extends Exception {

    private static final long serialVersionUID = -4819106933558488363L;

    /**
     * The base constructor.
     */
    public LispSyntaxException() {
        super();
    }

    /**
     * The message constructor.
     * 
     * @param msg the error message
     */
    public LispSyntaxException(String msg) {
        super(msg);
    }

    /**
     * The throwable constructor
     * 
     * @param err the error
     */
    public LispSyntaxException(Throwable err) {
        super(err);
    }

    /**
     * The message and throwable constructor.
     * 
     * @param msg the error message
     * @param err the error
     */
    public LispSyntaxException(String msg, Throwable err) {
        super(msg, err);
    }

    /**
     * The complete constructor.
     * 
     * @param msg the error message
     * @param err the error
     * @param suppression the suppression enable bit
     * @param stackTrace the stackTrace enable bit
     */
    public LispSyntaxException(String msg, Throwable err, boolean suppression, boolean stackTrace) {
        super(msg, err, suppression, stackTrace);
    }

}
