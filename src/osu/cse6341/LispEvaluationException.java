package osu.cse6341;

/**
 * The Lisp Evaluation Exception class which extends the generic Exception class.
 *
 * @author Jeremy Grifski
 */
public class LispEvaluationException extends Exception {

    private static final long serialVersionUID = -4819106933558488363L;

    /**
     * The base constructor.
     */
    public LispEvaluationException() {
        super();
    }

    /**
     * The message constructor.
     *
     * @param msg the error message
     */
    public LispEvaluationException(String msg) {
        super(msg);
    }

    /**
     * The throwable constructor
     *
     * @param err the error
     */
    public LispEvaluationException(Throwable err) {
        super(err);
    }

    /**
     * The message and throwable constructor.
     *
     * @param msg the error message
     * @param err the error
     */
    public LispEvaluationException(String msg, Throwable err) {
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
    public LispEvaluationException(String msg, Throwable err, boolean suppression, boolean stackTrace) {
        super(msg, err, suppression, stackTrace);
    }

}
