package osu.cse6341;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LispInterpreterTest {

    private LispInterpreter interpreter;

    @Before
    public void setUp() {
        interpreter = new LispInterpreter();
    }

    @Test
    public void testReadEmptyList() throws LispSyntaxException {
        String emptyList = "()";
        assertEquals("NIL", interpreter.read(emptyList).toString());
    }

}
