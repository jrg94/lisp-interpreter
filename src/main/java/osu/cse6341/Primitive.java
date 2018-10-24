package osu.cse6341;

public enum Primitive {
    NIL, T, QUOTE, DEFUN, COND, CAR, CDR, CONS, ATOM, INT, NULL, EQ, PLUS, MINUS, TIMES, QUOTIENT, REMAINDER, GREATER, LESS;

    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
