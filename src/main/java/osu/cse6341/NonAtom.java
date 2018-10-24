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
     * Evaluates this NonAtom.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the resulting SExpressions
     * @throws LispEvaluationException if evaluation fails
     */
    @Override
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression ret = null;
        SExpression car = this.car();
        if (car.equals(SExpression.QUOTE)) {
            ret = this.cadr();
        } else if (car.equals(SExpression.COND)) {
            ret = this.cdr().evaluateConditions(aList, dList);
        } else if (car.equals(SExpression.DEFUN)) {
            throw new LispEvaluationException("Illegal dList update");
        } else {
            ret = this.apply(aList, dList);
        }
        return ret;
    }

    /**
     * Applies a function.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the resulting SExpressions
     * @throws LispEvaluationException if function application fails
     */
    private SExpression apply(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression ret = null;
        SymbolicAtom func = SExpression.convertToSymbolicAtom(this.car());
        NonAtom args = SExpression.convertToNonAtom(this.cdr().evaluateList(aList, dList));
        if (func.equals(SExpression.CAR)) {
            ret = args.caar();
        } else if (func.equals(SExpression.CDR)) {
            ret = args.cdar();
        } else if (func.equals(SExpression.CONS)) {
            ret = SExpression.cons(args.car(), args.cadr());
        } else if (func.equals(SExpression.ATOM)) {
            ret = args.car().isAtom();
        } else if (func.equals(SExpression.NULL)) {
            ret = args.car().isNull();
        } else if (func.equals(SExpression.EQ)) {
            ret = SExpression.isEqual(args.car(), args.cadr());
        } else {
            ret = evaluateFunction(aList, dList, func, args);
        }
        return ret;
    }

    /**
     * A helper method which evaluates a function.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @param func a function name
     * @param args a list of arguments
     * @return the result of the function application
     * @throws LispEvaluationException if the evaluation fails
     */
    private SExpression evaluateFunction(Stack<NonAtom> aList, ArrayList<NonAtom> dList, SymbolicAtom func, NonAtom args) throws LispEvaluationException {
        SExpression node = func.find(dList);
        NonAtom decl = SExpression.convertToNonAtom(node);
        NonAtom pList = SExpression.convertToNonAtom(decl.car());
        SExpression body = decl.cdr();
        SExpression.addPairs(pList, args, aList);
        return body.evaluate(aList, dList);
    }

    @Override
    public SExpression car() throws LispEvaluationException {
        return this.left;
    }

    @Override
    public SExpression cdr() throws LispEvaluationException {
        return this.right;
    }

    /**
     * A helper method which gets the left node of the left node.
     *
     * @return the left node of the left node of this node
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    private SExpression caar() throws LispEvaluationException {
        return this.car().car();
    }

    private SExpression cdar() throws LispEvaluationException {
        return this.car().cdr();
    }

    private SExpression cadr() throws LispEvaluationException {
        return this.cdr().car();
    }

    private SExpression cadar() throws LispEvaluationException {
        return this.car().cdr().car();
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
        SExpression left = this.car().evaluate(aList, dList);
        SExpression right = this.cdr().evaluateList(aList, dList);
        return SExpression.cons(left, right);
    }

    @Override
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        SExpression ret = null;
        SExpression test = this.caar().evaluate(aList, dList);
        if (test.equals(SExpression.T)) {
            ret = this.cadar().evaluate(aList, dList);
        } else {
            ret = this.cdr().evaluateConditions(aList, dList);
        }
        return ret;
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
            try {
                isEqual = this.car().equals(atom.car()) && this.cdr().equals(atom.cdr());
            } catch (LispEvaluationException e) {
                isEqual = false;
            }
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
    @Override
    public String toString() {
        return "(" + left.toString() + " . " + right.toString() + ")";
    }

    @Override
    public SymbolicAtom isNull() {
        return SExpression.NIL;
    }
}
