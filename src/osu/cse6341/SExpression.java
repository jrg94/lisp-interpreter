package osu.cse6341;

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

  // TODO: Add evaluate function
  public SExpression eval(Stack<NonAtom> aList, ArrayList<NonAtom> dList);

}
