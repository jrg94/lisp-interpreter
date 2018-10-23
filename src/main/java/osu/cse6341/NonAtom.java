package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

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

    /**
     * A helper method for converting SExpressions to NonAtoms.
     */
    private static NonAtom convertToNonAtom(SExpression exp) throws LispEvaluationException {
        if (exp instanceof NonAtom) {
            return (NonAtom) exp;
        } else {
            throw new LispEvaluationException("Expected NonAtom but found: " + exp.toString());
        }
    }

    /**
     * A helper method for converting SExpressions to SymbolicAtoms.
     */
    private static SymbolicAtom convertToSymbolicAtom(SExpression exp) throws LispEvaluationException {
        if (exp instanceof SymbolicAtom) {
            return (SymbolicAtom) exp;
        } else {
            throw new LispEvaluationException("Expected SymbolicAtom but found: " + exp.toString());
        }
    }

    /**
     * A helper method for converting SExpressions to IntegerAtoms.
     */
    private static IntegerAtom convertToIntegerAtom(SExpression exp) throws LispEvaluationException {
        if (exp instanceof IntegerAtom) {
            return (IntegerAtom) exp;
        } else {
            throw new LispEvaluationException("Expected IntegerAtom but found: " + exp.toString());
        }
    }

    @Override
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression ret = null;
        SExpression car = this.getLeft();
        if (car.equals(SExpression.QUOTE)) {
            try {
                NonAtom cdr = NonAtom.convertToNonAtom(this.getRight());
                ret = cdr.getLeft();
            } catch (LispEvaluationException e) {
                throw new LispEvaluationException(
                        "CDR of QUOTE expression is not a NonAtom: " + this.getRight().toString(), e);
            }
        } else if (car.equals(SExpression.COND)) {
            // TODO: call evcon
        } else if (car.equals(SExpression.DEFUN)) {
            throw new LispEvaluationException("Illegal dList update");
        } else {
            ret = this.apply(aList, dList);
        }
        return ret;
    }

    private SExpression apply(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression ret = null;
        SExpression func = this.getLeft();
        NonAtom args = NonAtom.convertToNonAtom(this.getRight().evaluateList(aList, dList));
        if (func.equals(SExpression.CAR)) {
            ret = args.caar();
        } else if (func.equals(SExpression.CDR)) {
            ret = args.cdar();
        } else if (func.equals(SExpression.CONS)) {
            ret = NonAtom.cons(args.getLeft(), args.getRight());
        } else if (func.equals(SExpression.ATOM)) {
            ret = args.getLeft().isAtom();
        } else if (func.equals(SExpression.NULL)) {
            ret = args.getLeft().isNull();
        } else if (func.equals(SExpression.EQ)) {
            throw new LispEvaluationException("Not implemented");
            // EQ(CAR(X), CADR(X))
        } else {
            throw new LispEvaluationException("Not implemented");
            // eval[ cdr[getval[f, dList]], addpairs[car[getval[f, dList]], x,
            // aList], dList]
        }
        return ret;
    }

    /**
     * A static method which creates a new NonAtom from two SExpressions.
     * 
     * @param left the s-expression to be used on the left
     * @param right the s-expression to be used on the right
     * @return the root of the new binary tree
     */
    private static NonAtom cons(SExpression left, SExpression right) {
        NonAtom root = new NonAtom();
        root.setLeft(left);
        root.setRight(right);
        return root;
    }

    private SExpression caar() throws LispEvaluationException {
        SExpression ret = null;
        NonAtom left = NonAtom.convertToNonAtom(this.getLeft());
        ret = left.getLeft();
        return ret;
    }

    private SExpression cdar() throws LispEvaluationException {
        SExpression ret = null;
        NonAtom right = NonAtom.convertToNonAtom(this.getLeft());
        ret = right.getRight();
        return ret;
    }

    /**
     * Returns the NIL SExpression.
     */
    @Override
    public SymbolicAtom isAtom() {
        return SExpression.NIL;
    }

    /**
     * Evaluates a list of arguments and returns them as an evaluated list.
     */
    @Override
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression left = this.getLeft().evaluate(aList, dList);
        SExpression right = this.getRight().evaluateList(aList, dList);
        return NonAtom.cons(left, right);
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

    @Override
    public SymbolicAtom isNull() {
        return SExpression.NIL;
    }
}
