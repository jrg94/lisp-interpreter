package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

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

    @Override
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression ret = null;
        if (this.equals(SExpression.T)) {
            ret = SExpression.T;
        } else if (this.equals(SExpression.NIL)) {
            ret = SExpression.NIL;
        } else if ((ret = this.find(aList)) != null) {
            // Do nothing
        } else {
            throw new LispEvaluationException("Unbound atom " + this.toString());
        }
        return ret;
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

    public SExpression find(Stack<NonAtom> aList) throws LispEvaluationException {
        for (NonAtom binding : aList) {
            if (binding.getLeft().equals(this)) {
                return binding.getRight();
            }
        }
        return null;
    }

    public SExpression find(ArrayList<NonAtom> dList) throws LispEvaluationException {
        for (NonAtom decl : dList) {
            if (decl.getLeft().equals(this)) {
                return decl.getRight();
            }
        }
        throw new LispEvaluationException("Unable to find " + this + " in dList");
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

    @Override
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        if (this.equals(SExpression.NIL)) {
            return SExpression.NIL;
        } else {
            throw new LispEvaluationException("Expected Empty List but found " + this.toString());
        }
    }

    @Override
    public SymbolicAtom isAtom() {
        return SExpression.T;
    }

    @Override
    public SymbolicAtom isNull() {
        if (this.equals(SExpression.NIL)) {
            return SExpression.T;
        } else {
            return SExpression.NIL;
        }
    }

    @Override
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        throw new LispEvaluationException("Invalid condition: " + this.toString());
    }
}
