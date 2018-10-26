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

    /**
     * Gets the atom version of this enum.
     *
     * @return this enum as an atom
     */
    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
