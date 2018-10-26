package osu.cse6341;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LispEvaluationTest {

    private LispInterpreter interpreter;

    /**
     * A helper method for evaluating lisp code.
     *
     * @param code a complete s-expression
     * @return the SExpression result
     * @throws LispEvaluationException if there is a syntax error
     */
    private SExpression getEvaluation(String code) throws LispEvaluationException {
        try {
            return interpreter.read(code).evaluate(interpreter.aList, interpreter.dList);
        } catch (LispSyntaxException e) {
            throw new LispEvaluationException("Syntax error - unable to test", e);
        }
    }

    /**
     * A helper method for running defun commands.
     *
     * @param code a defun command
     * @throws LispEvaluationException
     */
    private void runDefun(String code) throws LispEvaluationException {
        try {
            SExpression defun = interpreter.read(code);
            interpreter.updateDList(defun);
        } catch (LispSyntaxException e) {
            throw new LispEvaluationException("Syntax error - unable to test", e);
        }
    }

    /**
     * A helper method for adding a function to the dList.
     *
     * @param code a function definition as a string
     * @throws LispEvaluationException if there is a syntax error
     */
    private void populateDList(String code) throws LispEvaluationException {
        try {
            NonAtom decl = (NonAtom) interpreter.read(code);
            interpreter.dList.add(decl);
        } catch (LispSyntaxException e) {
            throw new LispEvaluationException("Syntax error - unable to test", e);
        }
    }

    @Before
    public void setUp() {
        interpreter = new LispInterpreter();
    }

    @Test
    public void testQUOTE() throws LispEvaluationException {
        String test = "(QUOTE (2 . 3))";
        NonAtom expectedResult = new NonAtom();
        expectedResult.setLeft(new IntegerAtom(2));
        expectedResult.setRight(new IntegerAtom(3));
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testCAR() throws LispEvaluationException {
        String test = "(CAR (QUOTE (2 . 3)))";
        IntegerAtom expectedResult = new IntegerAtom(2);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testCDR() throws LispEvaluationException {
        String test = "(CDR (QUOTE (2 . 3)))";
        IntegerAtom expectedResult = new IntegerAtom(3);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testCONS() throws LispEvaluationException {
        String test = "(CONS 2 3)";
        NonAtom expectedResult = new NonAtom();
        expectedResult.setLeft(new IntegerAtom(2));
        expectedResult.setRight(new IntegerAtom(3));
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testFalseNULL() throws LispEvaluationException {
        String test = "(NULL 4)";
        SymbolicAtom expectedResult = Logic.NIL.getAtom();
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testTrueNULL() throws LispEvaluationException {
        String test = "(NULL NIL)";
        SymbolicAtom expectedResult = Logic.T.getAtom();
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testFalseEQ() throws LispEvaluationException {
        String test = "(EQ 2 4)";
        SymbolicAtom expectedResult = Logic.NIL.getAtom();
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testCOND() throws LispEvaluationException {
        String test = "(COND (NIL T) (T NIL))";
        SymbolicAtom expectedResult = Logic.NIL.getAtom();
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testFunctionCall() throws LispEvaluationException {
        String test = "(F 5)";
        String decl = "(F . ((X) . X))";
        populateDList(decl);
        IntegerAtom expectedResult = new IntegerAtom(5);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testPLUS() throws LispEvaluationException {
        String test = "(PLUS 2 3)";
        IntegerAtom expectedResult = new IntegerAtom(5);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testPLUSNested() throws LispEvaluationException {
        String test = "(PLUS 2 (PLUS 3 4))";
        IntegerAtom expectedResult = new IntegerAtom(9);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testMINUS() throws LispEvaluationException {
        String test = "(MINUS 2 3)";
        IntegerAtom expectedResult = new IntegerAtom(-1);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testTIMES() throws LispEvaluationException {
        String test = "(TIMES 2 3)";
        IntegerAtom expectedResult = new IntegerAtom(6);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testQUOTIENT() throws LispEvaluationException {
        String test = "(QUOTIENT 2 3)";
        IntegerAtom expectedResult = new IntegerAtom(0);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testREMAINDER() throws LispEvaluationException {
        String test = "(REMAINDER 2 3)";
        IntegerAtom expectedResult = new IntegerAtom(2);
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testGREATER() throws LispEvaluationException {
        String test = "(GREATER 2 3)";
        assertEquals(Logic.NIL.getAtom(), getEvaluation(test));
    }

    @Test
    public void testLESS() throws LispEvaluationException {
        String test = "(LESS 2 3)";
        assertEquals(Logic.T.getAtom(), getEvaluation(test));
    }

    @Test
    public void testInt() throws LispEvaluationException {
        String test = "(INT 7)";
        assertEquals(Logic.T.getAtom(), getEvaluation(test));
    }

    @Test
    public void testDEFUN() throws LispEvaluationException {
        String defun = "(DEFUN SILLY (A B) (PLUS A B))";
        runDefun(defun);
        String test = "(SILLY 5 6)";
        assertEquals(new IntegerAtom(11), getEvaluation(test));
    }

    @Test
    public void testNestedFunctionCallsWithQUOTE() throws LispEvaluationException {
        String defun = "(DEFUN SILLY (A B) (PLUS A B))";
        runDefun(defun);
        String test = "(SILLY (CAR (QUOTE (5 . 6))) (CDR (QUOTE (5 . 6))) )";
        assertEquals(new IntegerAtom(11), getEvaluation(test));
    }

    @Test
    public void testNestedDEFUN() throws LispEvaluationException {
        String defun1 = "  (DEFUN MINUS2 (A B) (MINUS A B))";
        runDefun(defun1);
        String defun2 = "(DEFUN NOTSOSILLY (A B) \r\n" + "(COND\r\n" + "((EQ A 0) (PLUS B 1))\r\n"
                + "((EQ B 0) (NOTSOSILLY (MINUS2 A 1) 1))\r\n"
                + "(T (NOTSOSILLY (MINUS2 A 1) (NOTSOSILLY A (MINUS2 B 1))))\r\n" + "))";
        runDefun(defun2);
        String test = "(NOTSOSILLY 0 0)";
        assertEquals(new IntegerAtom(1), getEvaluation(test));
    }

    @Test(expected = LispEvaluationException.class)
    public void testEQTooFewArguments() throws LispEvaluationException {
        String eq = "(EQ 1)";
        getEvaluation(eq);
    }

    /**
     * @Test public void testNestedDEFUN2() throws LispEvaluationException {
     *       String defun1 = "(DEFUN MINUS2 (A B) (MINUS A B))";
     *       runDefun(defun1); String defun2 = "(DEFUN NOTSOSILLY (A B) \r\n" +
     *       "(COND\r\n" + "((EQ A 0) (PLUS B 1))\r\n" + "((EQ B 0) (NOTSOSILLY
     *       (MINUS2 A 1) 1))\r\n" + "(T (NOTSOSILLY (MINUS2 A 1) (NOTSOSILLY A
     *       (MINUS2 B 1))))\r\n" + "))"; runDefun(defun2); String test =
     *       "(NOTSOSILLY 1 1)"; assertEquals(new IntegerAtom(3),
     *       getEvaluation(test)); }
     **/
}
