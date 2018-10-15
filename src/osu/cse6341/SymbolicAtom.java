package osu.cse6341;

/**
 * The Symbolic Atom class which is a type of s-expression.
 * 
 * @author Jeremy Grifski
 */
public class SymbolicAtom implements SExpression {
    private String name;

    /**
     * The Symbolic Atom constructor which takes a name as input.
     * 
     * @param name the name of the symbol
     */
    public SymbolicAtom(String name) {
        this.name = name;
    }

    /**
     * A get value function which retrieves the name of the name of the symbol.
     * 
     * @return the name of the symbol
     */
    public String getValue() {
        return name;
    }

    /**
     * The standard override of the toString method.
     * 
     * @return the name of the symbol
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * The standard override of the equals method.
     * 
     * @return true if the two symbols are equal
     */
    @Override
    public boolean equals(Object o) {
        boolean isEqual;
        if (o == null) {
            isEqual = false;
        } else if (o instanceof SymbolicAtom) {
            SymbolicAtom atom = (SymbolicAtom) o;
            if (this.name.equals(atom.getValue())) {
                isEqual = true;
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        return isEqual;
    }
}
