package osu.cse6341;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LispEvaluationTest {

    private LispInterpreter interpreter;

    @Before
    public void setUp() {
        interpreter = new LispInterpreter();
    }

    private SExpression getEvaluation(String code) throws LispEvaluationException {
        try {
            return interpreter.read(code).evaluate(null, null);
        } catch (LispSyntaxException e) {
            throw new LispEvaluationException("Syntax error - unable to test", e);
        }
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
        SymbolicAtom expectedResult = SExpression.NIL;
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testTrueNULL() throws LispEvaluationException {
        String test = "(NULL NIL)";
        SymbolicAtom expectedResult = SExpression.T;
        assertEquals(expectedResult, getEvaluation(test));
    }

    @Test
    public void testFalseEQ() throws LispEvaluationException {
        String test = "(EQ 2 4)";
        SymbolicAtom expectedResult = SExpression.NIL;
        assertEquals(expectedResult, getEvaluation(test));
    }
}
