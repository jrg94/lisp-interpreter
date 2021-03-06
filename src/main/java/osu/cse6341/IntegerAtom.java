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

    /**
     * Tests if this s-expression is an atom.
     *
     * @return T
     */
    @Override
    public SymbolicAtom isAtom() {
        return Logic.T.getAtom();
    }

    /**
     * Tests if this s-expression is an int.
     *
     * @return T
     */
    @Override
    public SymbolicAtom isInt() {
        return Logic.T.getAtom();
    }

    /**
     * Applies logic to this s-expression.
     *
     * @param operator an operator
     * @param other another s-expression
     * @return T for logical true; NIL otherwise
     */
    @Override
    public SymbolicAtom logic(char operator, SExpression other) throws LispEvaluationException {
        SymbolicAtom result = null;
        IntegerAtom rightOperand = (IntegerAtom) other;

        switch (operator) {
        case '<':
            result = this.getValue() < rightOperand.getValue() ? Logic.T.getAtom() : Logic.NIL.getAtom();
            break;
        case '>':
            result = this.getValue() > rightOperand.getValue() ? Logic.T.getAtom() : Logic.NIL.getAtom();
            break;
        default:
            throw new LispEvaluationException("No such boolean operator defined: " + operator);
        }
        return result;
    }

    /**
     * Applies arithmetic to this s-expression.
     *
     * @param operator an operator
     * @param other another s-expression
     * @return the result of the arithmetic
     */
    @Override
    public IntegerAtom arithmetic(char operator, SExpression other) throws LispEvaluationException {
        int result = 0;
        IntegerAtom rightOperand = (IntegerAtom) other;

        switch (operator) {
        case '+':
            result = this.getValue() + rightOperand.getValue();
            break;
        case '-':
            result = this.getValue() - rightOperand.getValue();
            break;
        case '*':
            result = this.getValue() * rightOperand.getValue();
            break;
        case '/':
            result = this.getValue() / rightOperand.getValue();
            break;
        case '%':
            result = this.getValue() % rightOperand.getValue();
            break;
        default:
            throw new LispEvaluationException("No such arithmetic operator defined: " + operator);
        }
        return new IntegerAtom(result);
    }

    /**
     * Overrides the isEqual method because integers shouldn't be compared by
     * address.
     *
     * @param other the other SExpression
     * @return T if the integers are equal; NIL otherwise
     */
    @Override
    public SymbolicAtom isEqual(SExpression other) {
        if (this.equals(other)) {
            return Logic.T.getAtom();
        } else {
            return Logic.NIL.getAtom();
        }
    }
}
