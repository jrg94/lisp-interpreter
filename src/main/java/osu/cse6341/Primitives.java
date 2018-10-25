package osu.cse6341;

/**
 * A primitives enum.
 * 
 * @author Jeremy Grifski
 */
public enum Primitives {
    NIL, T, QUOTE, DEFUN, COND, CAR, CDR, CONS, ATOM, INT, NULL, EQ, PLUS, MINUS, TIMES, QUOTIENT, REMAINDER, GREATER, LESS;

    /**
     * Gets the atom version of this enum.
     * 
     * @return this enum as an atom
     */
    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
