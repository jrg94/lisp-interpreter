package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The S-Expresion Interface.
 *
 * @author Jeremy Grifski
 */
public abstract class SExpression {

    public SExpression car() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDR on atom: " + this);
    }

    public SExpression cdr() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDR on atom: " + this);
    }

    public SExpression caar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CAAR on atom: " + this);
    }

    public SExpression cadr() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CADR on atom: " + this);
    }

    public SExpression cdar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CDAR on atom: " + this);
    }

    public SExpression cadar() throws LispEvaluationException {
        throw new LispEvaluationException("Unable to call CADAR on atom: " + this);
    }

    public SExpression find(ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException("Cannot search dList for: " + this);
    }

    public SExpression find(Stack<NonAtom> aList) throws LispEvaluationException {
        throw new LispEvaluationException("Cannot search aList for: " + this);
    }

    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList)
            throws LispEvaluationException {
        throw new LispEvaluationException("Invalid condition: " + this.toString());
    }

    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to evaluate: " + this);
    }

    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException {
        throw new LispEvaluationException(this + " is not a list");
    }

    public IntegerAtom arithmetic(char operator, SExpression other) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to perform " + operator + " on " + this + " and " + other);
    }

    public SymbolicAtom logic(char operator, SExpression other) throws LispEvaluationException {
        throw new LispEvaluationException("Unable to perform " + operator + " on " + this + " and " + other);
    }

    public SymbolicAtom isEqual(SExpression other) {
        if (this == other) {
            return Primitive.T.getAtom();
        } else {
            return Primitive.NIL.getAtom();
        }
    }

    /**
     * A static method which creates a new NonAtom from two SExpressions.
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

    public SymbolicAtom isAtom() {
        return Primitive.NIL.getAtom();
    }

    public SymbolicAtom isNull() {
        return Primitive.NIL.getAtom();
    }

    public SymbolicAtom isInt() {
        return Primitive.NIL.getAtom();
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
        boolean isEndOfPList = pList.cdr().equals(Primitive.NIL.getAtom());
        boolean isEndOfArgList = args.cdr().equals(Primitive.NIL.getAtom());
        if (isEndOfPList && !isEndOfArgList) {
            throw new LispEvaluationException("Function has too many arguments: " + args.cdr());
        } else if (!isEndOfPList && isEndOfArgList) {
            throw new LispEvaluationException("Function needs more arguments: " + pList.cdr());
        } else if (!isEndOfPList && !isEndOfArgList) {
            SExpression.addPairs(pList.cdr(), args.cdr(), aList);
        }
    }
}
