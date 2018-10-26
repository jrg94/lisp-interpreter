package osu.cse6341;

/**
 * A primitives enum.
 *
 * @author Jeremy Grifski
 */
public enum Primitives implements AtomMapping {
    CAR(1), CDR(1), CONS(2), ATOM(1), INT(1), NULL(1), EQ(2), PLUS(2), MINUS(2), TIMES(2), QUOTIENT(2), REMAINDER(
            2), GREATER(2), LESS(2);

    // Stores the length of arguments
    private int argLength;

    /**
     * Primitives constructor.
     */
    private Primitives(int argLength) {
        this.argLength = argLength;
    }

    /**
     * Applies the primitive function given its arguments.
     * 
     * @param args a list of arguments for this primitive function
     * @return the result of evaluation
     * @throws LispEvaluationException if the list of arguments do not match up
     */
    public SExpression applyPrimitiveFunction(SExpression args) throws LispEvaluationException {
        SExpression ret = null;
        boolean isValidArgLength = verifyArgLength(args);
        if (isValidArgLength) {
            ret = getResult(args);
        } else {
            String err = String.format("Invalid list of arguments for %s: %s", this.name(), args);
            throw new LispEvaluationException(err);
        }
        return ret;
    }

    /**
     * Verifies that the argument list matches the expected number of arguments
     * based on argLength.
     * 
     * @param list a list of arguments
     * @return true if the expected number of arguments matches reality
     * @throws LispEvaluationException if we run cdr on an atom
     */
    private boolean verifyArgLength(SExpression list) throws LispEvaluationException {
        SExpression curr = list;
        int length = 0;

        while (!curr.equals(Logic.NIL.getAtom())) {
            length++;
            curr = curr.cdr();
        }

        if (length != this.argLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the result of the this primitive function when applied to a set of
     * arguments.
     * 
     * @param args a list of arguments
     * @return the result of this function
     * @throws LispEvaluationException if any of the poking around fails
     */
    private SExpression getResult(SExpression args) throws LispEvaluationException {
        switch (this) {
        case CAR:
            return args.caar();
        case CDR:
            return args.cdar();
        case CONS:
            return args.car().cons(args.cadr());
        case ATOM:
            return args.car().isAtom();
        case INT:
            return args.car().isInt();
        case NULL:
            return args.car().isNull();
        case EQ:
            return args.car().isEqual(args.cadr());
        case PLUS:
            return args.car().arithmetic('+', args.cadr());
        case MINUS:
            return args.car().arithmetic('-', args.cadr());
        case TIMES:
            return args.car().arithmetic('*', args.cadr());
        case QUOTIENT:
            return args.car().arithmetic('/', args.cadr());
        case REMAINDER:
            return args.car().arithmetic('%', args.cadr());
        case GREATER:
            return args.car().logic('>', args.cadr());
        case LESS:
            return args.car().logic('<', args.cadr());
        default:
            String err = String.format("%s is not implemented", this);
            throw new LispEvaluationException(err);
        }
    }

    /**
     * Overrides the base getAtom method of the AtomMapping interface.
     * 
     * @return this as a Symbolic Atom
     */
    @Override
    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
