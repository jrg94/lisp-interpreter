package osu.cse6341;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The S-Expresion Interface.
 *
 * @author Jeremy Grifski
 */
public interface SExpression {

    public static final SymbolicAtom NIL = new SymbolicAtom("NIL");
    public static final SymbolicAtom T = new SymbolicAtom("T");
    public static final SymbolicAtom QUOTE = new SymbolicAtom("QUOTE");
    public static final SymbolicAtom DEFUN = new SymbolicAtom("DEFUN");
    public static final SymbolicAtom COND = new SymbolicAtom("COND");
    public static final SymbolicAtom CAR = new SymbolicAtom("CAR");
    public static final SymbolicAtom CDR = new SymbolicAtom("CDR");
    public static final SymbolicAtom CONS = new SymbolicAtom("CONS");
    public static final SymbolicAtom ATOM = new SymbolicAtom("ATOM");
    public static final SymbolicAtom NULL = new SymbolicAtom("NULL");
    public static final SymbolicAtom EQ = new SymbolicAtom("EQ");

    public SExpression car() throws LispEvaluationException;
    public SExpression cdr() throws LispEvaluationException;
    public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException;
    public SExpression evaluateList(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException;
    public SExpression evaluateConditions(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException;
    public SymbolicAtom isAtom();
    public SymbolicAtom isNull();

    /**
     * A static method which creates a new NonAtom from two SExpressions.
     *
     * @param left the s-expression to be used on the left
     * @param right the s-expression to be used on the right
     * @return the root of the new binary tree
     * @throws LispEvaluationException
     */
    public static NonAtom cons(SExpression left, SExpression right) throws LispEvaluationException {
        NonAtom root = new NonAtom();
        root.setLeft(left);
        root.setRight(right);
        return root;
    }

    /**
     * A static method which compares two s-expressions for equivalence
     * based on memory address.
     *
     * @param a the first s-expression
     * @param b the second s-expression
     * @return T if true, NIL Otherwise
     */
    public static SymbolicAtom isEqual(SExpression a, SExpression b) {
        if (a == b) {
            return SExpression.T;
        } else {
            return SExpression.NIL;
        }
    }
}
