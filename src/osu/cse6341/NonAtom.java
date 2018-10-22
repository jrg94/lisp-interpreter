package osu.cse6341;

import com.sun.java_cup.internal.runtime.Symbol;
import java.util.ArrayList;

/**
 * The NonAtom class which is a type of s-expression.
 *
 * @author Jeremy Grifski
 */
public class NonAtom implements SExpression {

    private SExpression left;
    private SExpression right;

    /**
     * Sets the left node of this binary tree.
     *
     * @param left any s-expression
     */
    public void setLeft(SExpression left) {
        this.left = left;
    }

    /**
     * Sets the right node of this binary tree.
     *
     * @param right any s-expression
     */
    public void setRight(SExpression right) {
        this.right = right;
    }

    /**
     * Gets the left node of this binary tree.
     *
     * @return the left s-expression
     */
    public SExpression getLeft() {
        return left;
    }

    /**
     * Gets the right node of this binary tree.
     *
     * @return the right s-expression
     */
    public SExpression getRight() {
        return right;
    }

    @Override
    public SExpression eval(Stack<NonAtom> aList, ArrayList<NonAtom> dList) {
        SExpression ret = null;
        if (this.getLeft() instanceof SymbolicAtom) {
            SymbolicAtom exp = (SymbolicAtom) this.getLeft();
            if (exp.equals(SExpression.QUOTE)) {
                ret = exp.getRight().getLeft();
            } else if (exp.equals(SExpression.COND)) {
                // TODO: call evcon
            } else if (exp.equals(SExpression.DEFUN)) {
                throw new LispEvaluationException("Illegal dList update");
            } else {
                // TODO: call apply
            }
        } else {
          throw new LispEvaluationException("Error in NonAtom Eval!");
        }
        return ret;
    }

    public SExpression apply(SymbolicAtom func, NonAtom x, Stack<NonAtom> aList, ArrayList<NonAtom> dList) {
        SExpression ret = null;
        if (func.equals(SExpression.CAR)) {
          // CAAR(x)
        } else if (func.equals(SExpression.CDR)) {
          // CDAR(x)
        } else if (func.equals(SExpression.CONS)) {
          // CONS(CAR(x), CDR(x))
        } else if (func.equals(SExpression.ATOM)) {
          // ATOM(CAR(X))
        } else if (func.equals(SExpression.NULL)) {
          // NULL(CAR(X))
        } else if (func.equals(SExpression.EQ)) {
          // EQ(CAR(X), CADR(X))
        } else {
          // eval[ cdr[getval[f, dList]], addpairs[car[getval[f, dList]], x, aList], dList]
        }
        return ret;
    }

    public SExpression evlist(SExpression list, Stack<NonAtom> aList, ArrayList<NonAtom> dList) {
        SExpression ret = null;
        if (list instanceof SymbolicAtom) {
            Symbolic nil = (SymbolicAtom) list;
            if (nil.equals(SExpression.NIL)) {
              ret = SExpression.NIL;
            } else {
              throw new LispEvaluationException("Expected Empty List but found " + nil.toString());
            }
        } else {
            // cons[ eval[car[list], aList, dList], evlis[cdr[list], aList, dList] ]
        }
    }

    /**
     * The standard override of the equals method.
     *
     * @return true if the two binary trees are equal
     */
    @Override
    public boolean equals(Object o) {
        boolean isEqual;
        if (o == null) {
            isEqual = false;
        } else if (o instanceof NonAtom) {
            NonAtom atom = (NonAtom) o;
            isEqual = this.left.equals(atom.getLeft()) && this.right.equals(atom.getRight());
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    /**
     * The standard override of the toString method.
     *
     * @return the binary tree as a string
     */
    public String toString() {
        return "(" + left.toString() + " . " + right.toString() + ")";
    }
}
