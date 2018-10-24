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
        SExpression car = this.getLeft();
        if (car.equals(SExpression.QUOTE)) {
            ret = this.cadr();
        } else if (car.equals(SExpression.COND)) {
            ret = this.getRight().evaluateConditions(aList, dList);
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
        SymbolicAtom func = NonAtom.convertToSymbolicAtom(this.getLeft());
        NonAtom args = NonAtom.convertToNonAtom(this.getRight().evaluateList(aList, dList));
        if (func.equals(SExpression.CAR)) {
            ret = args.caar();
        } else if (func.equals(SExpression.CDR)) {
            ret = args.cdar();
        } else if (func.equals(SExpression.CONS)) {
            ret = NonAtom.cons(args.getLeft(), args.cadr());
        } else if (func.equals(SExpression.ATOM)) {
            ret = args.getLeft().isAtom();
        } else if (func.equals(SExpression.NULL)) {
            ret = args.getLeft().isNull();
        } else if (func.equals(SExpression.EQ)) {
            ret = NonAtom.isEqual(args.getLeft(), args.cadr());
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
        NonAtom decl = NonAtom.convertToNonAtom(node);
        NonAtom pList = NonAtom.convertToNonAtom(decl.getLeft());
        SExpression body = decl.getRight();
        addPairs(pList, args, aList);
        return body.evaluate(aList, dList);
    }

    /**
     * Given a parameter list and a list of arguments, this method adds the new
     * bindings to the association list.
     *
     * @param pList a list of parameters
     * @param args a list of arguments
     * @param aList the association list
     * @throws LispEvaluationException
     */
    public static void addPairs(NonAtom pList, NonAtom args, Stack<NonAtom> aList) throws LispEvaluationException {
        NonAtom binding = NonAtom.cons(pList.getLeft(), args.getLeft());
        aList.push(binding);
        boolean isEndOfPList = pList.getRight().equals(SExpression.NIL);
        boolean isEndOfArgList = args.getRight().equals(SExpression.NIL);
        if (isEndOfPList && !isEndOfArgList) {
            throw new LispEvaluationException("Function has too many arguments: " + args.getRight());
        } else if (!isEndOfPList && isEndOfArgList) {
            throw new LispEvaluationException("Function needs more arguments: " + pList.getRight());
        } else if (!isEndOfPList && !isEndOfArgList) {
            NonAtom nextP = NonAtom.convertToNonAtom(pList.getRight());
            NonAtom nextArg = NonAtom.convertToNonAtom(args.getRight());
            NonAtom.addPairs(nextP, nextArg, aList);
        }
    }

    /**
     * A static method which creates a new NonAtom from two SExpressions.
     *
     * @param left the s-expression to be used on the left
     * @param right the s-expression to be used on the right
     * @return the root of the new binary tree
     * @throws LispEvaluationException
     */
    private static NonAtom cons(SExpression left, SExpression right) throws LispEvaluationException {
        NonAtom root = new NonAtom();
        root.setLeft(left);
        root.setRight(right);
        return root;
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
        SExpression ret = null;
        NonAtom left = NonAtom.convertToNonAtom(this.getLeft());
        ret = left.getLeft();
        return ret;
    }

    private SExpression cdar() throws LispEvaluationException {
        SExpression ret = null;
        NonAtom left = NonAtom.convertToNonAtom(this.getLeft());
        ret = left.getRight();
        return ret;
    }

    private SExpression cadr() throws LispEvaluationException {
        SExpression ret = null;
        NonAtom right = NonAtom.convertToNonAtom(this.getRight());
        ret = right.getLeft();
        return ret;
    }

    private SExpression cadar() throws LispEvaluationException {
        SExpression ret = null;
        NonAtom right = NonAtom.convertToNonAtom(this.cdar());
        ret = right.getLeft();
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

    @Override
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        SExpression ret = null;
        SExpression test = this.caar().evaluate(aList, dList);
        if (test.equals(SExpression.T)) {
            ret = this.cadar().evaluate(aList, dList);
        } else {
            ret = this.getRight().evaluateConditions(aList, dList);
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

    public static SymbolicAtom isEqual(SExpression a, SExpression b) {
        if (a == b) {
            return SExpression.T;
        } else {
            return SExpression.NIL;
        }
    }
}
