package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The Integer Atom class which is a type of s-expression.
 *
 * @author Jeremy Grifski
 */
public class IntegerAtom extends SExpression {

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

    /**
     * Evaluates an integer atom by returning itself.
     * 
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return this
     */
    @Override
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) {
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
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public SymbolicAtom isAtom() {
        return SExpression.T;
    }

    @Override
    public SymbolicAtom isNull() {
        return SExpression.NIL;
    }

}
