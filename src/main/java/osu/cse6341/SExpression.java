package osu.cse6341;

import java.util.Stack;
import java.util.ArrayList;

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

  // TODO: Add evaluate function
  public SExpression evaluate(Stack<NonAtom> aList, ArrayList<NonAtom> dList) throws LispEvaluationException;

}
