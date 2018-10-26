package osu.cse6341;

/**
 * A primitives enum.
 *
 * @author Jeremy Grifski
 */
public enum Primitives implements AtomMapping {
    CAR(1), CDR(1), CONS(2), ATOM(1), INT(1), NULL(1), EQ(2), PLUS(2), MINUS(2), TIMES(2), QUOTIENT(2), REMAINDER(2), GREATER(2), LESS(2);

    // Stores the length of arguments
    private int argLength;

    /**
     * Primitives constructor.
     */
    private Primitives(int argLength) {
        this.argLength = argLength;
    }

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

    private SExpression getResult(SExpression args) throws LispEvaluationException {
        switch(this) {
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
            default:
                String err = String.format("%s is not implemented", this);
                throw new LispEvaluationException(err);
        }
    }

    /**
     * Gets the atom version of this enum.
     *
     * @return this enum as an atom
     */
    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
