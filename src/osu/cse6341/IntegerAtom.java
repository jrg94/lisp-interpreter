package osu.cse6341;

/**
 * The Integer Atom class which is a type of s-expression.
 *
 * @author Jeremy Grifski
 */
public class IntegerAtom implements SExpression {

    private int value;

    /**
     * The base constructor.
     *
     * @param value an integer
     */
    public IntegerAtom(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value of this s-expression.
     *
     * @return the integer value
     */
    public int getValue() {
        return value;
    }

    @Override
    public SExpression eval(Stack<NonAtom> aList, ArrayList<NonAtom> dList) {
      return this;
    }

    /**
     * The standard override of the equals method.
     *
     * @return true if the two integers are equal
     */
    @Override
    public boolean equals(Object o) {
        boolean isEqual;
        if (o == null) {
            isEqual = false;
        } else if (o instanceof IntegerAtom) {
            IntegerAtom atom = (IntegerAtom) o;
            isEqual = this.getValue() == atom.getValue();
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    /**
     * The standard override of the toString method.
     *
     * @return the integer as a string
     */
    public String toString() {
        return Integer.toString(value);
    }
}
