package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The Symbolic Atom class which is a type of s-expression.
 *
 * @author Jeremy Grifski
 */
public class SymbolicAtom extends SExpression {
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
        if (this.equals(Primitives.T.getAtom())) {
            ret = Primitives.T.getAtom();
        } else if (this.equals(Primitives.NIL.getAtom())) {
            ret = Primitives.NIL.getAtom();
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

    @Override
    public SExpression find(Stack<NonAtom> aList) throws LispEvaluationException {
        for (NonAtom binding : aList) {
            if (binding.car().equals(this)) {
                return binding.cdr();
            }
        }
        return null;
    }

    @Override
    public SExpression find(ArrayList<NonAtom> dList) throws LispEvaluationException {
        for (NonAtom decl : dList) {
            if (decl.car().equals(this)) {
                return decl.cdr();
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
        if (this.equals(Primitives.NIL.getAtom())) {
            return Primitives.NIL.getAtom();
        } else {
            throw new LispEvaluationException("Expected Empty List but found " + this.toString());
        }
    }

    @Override
    public SymbolicAtom isAtom() {
        return Primitives.T.getAtom();
    }

    @Override
    public SymbolicAtom isNull() {
        if (this.equals(Primitives.NIL.getAtom())) {
            return Primitives.T.getAtom();
        } else {
            return Primitives.NIL.getAtom();
        }
    }
}
