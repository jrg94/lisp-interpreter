package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;

/**
 * The NonAtom class which is a type of s-expression.
 *
 * @author Jeremy Grifski
 */
public class NonAtom extends SExpression {

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
        if (car.equals(Primitives.QUOTE.getAtom())) {
            ret = this.cadr();
        } else if (car.equals(Primitives.COND.getAtom())) {
            System.out.println(this);
            ret = this.cdr().evaluateConditions(aList, dList);
        } else if (car.equals(Primitives.DEFUN.getAtom())) {
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
        SExpression func = this.car();
        SExpression args = this.cdr().evaluateList(aList, dList);
        Primitives function = Arrays.asList(Primitives.values()).filter(value -> value.getAtom().equals(func));
        if (func.equals(Primitives.CAR.getAtom())) {
            ret = args.caar();
        } else if (func.equals(Primitives.CDR.getAtom())) {
            ret = args.cdar();
        } else if (func.equals(Primitives.CONS.getAtom())) {
            ret = args.car().cons(args.cadr());
        } else if (func.equals(Primitives.ATOM.getAtom())) {
            ret = args.car().isAtom();
        } else if (func.equals(Primitives.INT.getAtom())) {
            ret = args.car().isInt();
        } else if (func.equals(Primitives.NULL.getAtom())) {
            ret = args.car().isNull();
        } else if (func.equals(Primitives.EQ.getAtom())) {
            ret = args.car().isEqual(args.cadr());
        } else if (func.equals(Primitives.PLUS.getAtom())) {
            ret = args.car().arithmetic('+', args.cadr());
        } else if (func.equals(Primitives.MINUS.getAtom())) {
            ret = args.car().arithmetic('-', args.cadr());
        } else if (func.equals(Logic.TIMES.getAtom())) {
            ret = args.car().arithmetic('*', args.cadr());
        } else if (func.equals(Primitives.QUOTIENT.getAtom())) {
            ret = args.car().arithmetic('/', args.cadr());
        } else if (func.equals(Primitives.REMAINDER.getAtom())) {
            ret = args.car().arithmetic('%', args.cadr());
        } else if (func.equals(Primitives.GREATER.getAtom())) {
            ret = args.car().logic('>', args.cadr());
        } else if (func.equals(Primitives.LESS.getAtom())) {
            ret = args.car().logic('<', args.cadr());
        } else {
            ret = this.evaluateFunction(aList, dList, func, args);
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
    private SExpression evaluateFunction(Stack<NonAtom> aList, ArrayList<NonAtom> dList, SExpression func,
            SExpression args) throws LispEvaluationException {
        SExpression decl = func.find(dList);
        SExpression pList = decl.car();
        SExpression body = decl.cdr();
        int size = aList.size();
        SExpression.addPairs(pList, args, aList);
        SExpression eval = body.evaluate(aList, dList);
        int totalPairs = aList.size() - size;
        for (int i = totalPairs; i > 0; i--) {
            aList.pop();
        }
        return eval;
    }

    /**
     * Gets the left node of this NonAtom.
     *
     * @return the left node of this node
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    @Override
    public SExpression car() throws LispEvaluationException {
        return this.left;
    }

    /**
     * Gets the right node of this NonAtom.
     *
     * @return the right node of this node
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    @Override
    public SExpression cdr() throws LispEvaluationException {
        return this.right;
    }

    /**
     * Gets the left node of the left node.
     *
     * @return the left node of the left node of this node
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    @Override
    public SExpression caar() throws LispEvaluationException {
        return this.car().car();
    }

    /**
     * Gets the right node of the left node.
     *
     * @return the right node of the left node
     */
    @Override
    public SExpression cdar() throws LispEvaluationException {
        return this.car().cdr();
    }

    /**
     * Gets the left node of the right node.
     *
     * @return the left node of the right node.
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    @Override
    public SExpression cadr() throws LispEvaluationException {
        return this.cdr().car();
    }

    /**
     * Gets the left node of the right node of the left node.
     *
     * @return the left node of the right node of the left node.
     * @throws LispEvaluationException if fails to grab one of the nodes
     */
    @Override
    public SExpression cadar() throws LispEvaluationException {
        return this.car().cdr().car();
    }

    /**
     * Evaluates a list of arguments and returns them as an evaluated list.
     */
    @Override
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        SExpression left = this.car().evaluate(aList, dList);
        SExpression right = this.cdr().evaluateList(aList, dList);
        return left.cons(right);
    }

    /**
     * Evaluates the conditions given by this node.
     *
     * @return the result of the evaluation.
     * @throws LispEvaluationException if evaluation fails
     */
    @Override
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        SExpression ret = null;
        SExpression test = this.caar().evaluate(aList, dList);
        if (test.equals(Logic.T.getAtom())) {
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
}
