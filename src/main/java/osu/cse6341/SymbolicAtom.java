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

    /**
     * Evaluates this symbol.
     * 
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the result of the evaluation
     */
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

    /**
     * Finds this symbol in the aList.
     * 
     * @param aList a list of bindings
     * @return the binding
     */
    @Override
    public SExpression find(Stack<NonAtom> aList) throws LispEvaluationException {
        for (NonAtom binding : aList) {
            if (binding.car().equals(this)) {
                return binding.cdr();
            }
        }
        return null;
    }

    /**
     * Finds this symbol in the dList.
     * 
     * @param dList a list of definitions
     * @return the function definition
     */
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

    /**
     * Evaluates this s-expression as the tail end of the list.
     * 
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return this if NIL; error otherwise
     * @throws LispEvaluationException if this symbol is not the end of the list
     */
    @Override
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        if (this.equals(Primitives.NIL.getAtom())) {
            return Primitives.NIL.getAtom();
        } else {
            throw new LispEvaluationException("Expected Empty List but found " + this.toString());
        }
    }

    /**
     * Tests if this s-expression is an atom.
     * 
     * @return T
     */
    @Override
    public SymbolicAtom isAtom() {
        return Primitives.T.getAtom();
    }

    /**
     * Tests if this s-expression is NIL.
     * 
     * @return T if it is NIL; NIL otherwise
     */
    @Override
    public SymbolicAtom isNull() {
        if (this.equals(Primitives.NIL.getAtom())) {
            return Primitives.T.getAtom();
        } else {
            return Primitives.NIL.getAtom();
        }
    }

    @Override
    public SymbolicAtom isEqual(SExpression other) {
        if (this.equals(other)) {
            return Primitives.T.getAtom();
        } else {
            return Primitives.NIL.getAtom();
        }
    }
}
