package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The S-Expression Interface.
 *
 * @author Jeremy Grifski
 */
public abstract class SExpression {

    /**
     * Gets the left node of this node.
     *
     * @return the left node of this s-expression
     * @throws LispEvaluationException if this s-expression is an atom
     */
    public SExpression car() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDR on atom: " + this);
    }

    /**
     * Gets the right node of this node.
     *
     * @return the right node of this s-expression
     * @throws LispEvaluationException if this s-expression is an atom
     */
    public SExpression cdr() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDR on atom: " + this);
    }

    /**
     * Gets the left node of the left node of this node.
     *
     * @return the left node of the left node of this s-expression
     * @throws LispEvaluationException if this s-expression is not 2 or more
     *             levels deep
     */
    public SExpression caar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CAAR on atom: " + this);
    }

    /**
     * Gets the left node of the right node of this node.
     *
     * @return the left node of the right node of this s-expression
     * @throws LispEvaluationException if this s-expression is not 2 or more
     *             levels deep
     */
    public SExpression cadr() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CADR on atom: " + this);
    }

    /**
     * Gets the right node of the left node of this node.
     *
     * @return the right node of the left node of this s-expression
     * @throws LispEvaluationException if this s-expression is not 2 or more
     *             levels deep
     */
    public SExpression cdar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDAR on atom: " + this);
    }

    /**
     * Gets the left node of the right node of the left node of this node.
     *
     * @return the left node of the right node of the left node of this
     *         s-expression
     * @throws LispEvaluationException if this s-expression is not 3 or more
     *             levels deep
     */
    public SExpression cadar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CADAR on atom: " + this);
    }

    /**
     * Finds this s-expression in the dList.
     *
     * @param dList a list of definitions
     * @return the function definition
     * @throws LispEvaluationException
     */
    public SExpression find(ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException("Cannot search dList for: " + this);
    }

    /**
     * Finds this s-expression in the aList.
     *
     * @param aList a list of bindings
     * @return the binding
     * @throws LispEvaluationException
     */
    public SExpression find(Stack<NonAtom> aList) throws LispEvaluationException {
        throw new LispEvaluationException("Cannot search aList for: " + this);
    }

    /**
     * Evaluates this s-expression as a list of conditions.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the result of the evaluation
     * @throws LispEvaluationException
     */
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        throw new LispEvaluationException("Invalid condition: " + this.toString());
    }

    /**
     * Evaluates this s-expression.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the result of the evaluation
     * @throws LispEvaluationException
     */
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to evaluate: " + this);
    }

    /**
     * Evaluates this s-expression as a list.
     *
     * @param aList a list of bindings
     * @param dList a list of definitions
     * @return the result of the evaluation
     * @throws LispEvaluationException
     */
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException(this + " is not a list");
    }

    /**
     * Performs arithmetic based on the supplied operator.
     *
     * @param operator an arithmetic operator
     * @param other another s-expression
     * @return the result of the arithmetic
     * @throws LispEvaluationException
     */
    public IntegerAtom arithmetic(char operator, SExpression other) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to perform " + operator + " on " + this + " and " + other);
    }

    /**
     * Performs logic based on the supplied operator.
     *
     * @param operator a boolean operator
     * @param other another s-expression
     * @return the result of the boolean expression
     * @throws LispEvaluationException
     */
    public SymbolicAtom logic(char operator, SExpression other) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to perform " + operator + " on " + this + " and " + other);
    }

    /**
     * Tests that this s-expression is equivalent to another by address.
     *
     * @param other another s-expression
     * @return T if equal; NIL otherwise
     */
    public SymbolicAtom isEqual(SExpression other) {
        if (this == other) {
            return Logic.T.getAtom();
        } else {
            return Primitives.NIL.getAtom();
        }
    }

    /**
     * Creates a new NonAtom from two SExpressions.
     *
     * @param other the s-expression to be used on the right
     * @return the root of the new binary tree
     * @throws LispEvaluationException
     */
    public NonAtom cons(SExpression other) {
        NonAtom root = new NonAtom();
        root.setLeft(this);
        root.setRight(other);
        return root;
    }

    /**
     * Tests that this s-expression is an atom.
     *
     * @return T if this is an atom; NIL otherwise
     */
    public SymbolicAtom isAtom() {
        return Primitives.NIL.getAtom();
    }

    /**
     * Tests that this s-expression is NIL.
     *
     * @return T if this atom is NIL; NIL otherwise
     */
    public SymbolicAtom isNull() {
        return Primitives.NIL.getAtom();
    }

    /**
     * Tests that this s-expression is an int.
     *
     * @return T if this atom is an int; NIL otherwise
     */
    public SymbolicAtom isInt() {
        return Primitives.NIL.getAtom();
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
    public static void addPairs(SExpression pList, SExpression args, Stack<NonAtom> aList)
            throws LispEvaluationException {
        NonAtom binding = pList.car().cons(args.car());
        aList.push(binding);
        boolean isEndOfPList = pList.cdr().equals(Primitives.NIL.getAtom());
        boolean isEndOfArgList = args.cdr().equals(Primitives.NIL.getAtom());
        if (isEndOfPList && !isEndOfArgList) {
            throw new LispEvaluationException("Function has too many arguments: " + args.cdr());
        } else if (!isEndOfPList && isEndOfArgList) {
            throw new LispEvaluationException("Function needs more arguments: " + pList.cdr());
        } else if (!isEndOfPList && !isEndOfArgList) {
            SExpression.addPairs(pList.cdr(), args.cdr(), aList);
        }
    }
}
