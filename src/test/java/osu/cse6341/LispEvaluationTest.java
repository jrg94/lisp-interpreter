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
    public void testCAR() throws LispEvaluationException {
        String test = "(CAR (2 . 3))";
        IntegerAtom expectedResult = new IntegerAtom(2);
        assertEquals(expectedResult, getEvaluation(test));
    }

}
