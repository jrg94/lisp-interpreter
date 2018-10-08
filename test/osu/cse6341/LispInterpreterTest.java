package osu.cse6341;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LispInterpreterTest {

    private LispInterpreter interpreter;

    private String getDotNotation(String underTest) throws LispSyntaxException {
        return interpreter.read(underTest).toString();
    }

    @Before
    public void setUp() {
        interpreter = new LispInterpreter();
    }

    @Test
    public void testReadEmptyList() throws LispSyntaxException {
        String emptyList = "()";
        assertEquals("NIL", getDotNotation(emptyList));
    }

    @Test
    public void testReadIntegerAtom() throws LispSyntaxException {
        String integer = "42";
        assertEquals("42", getDotNotation(integer));
    }

    @Test
    public void testReadSingleItemList() throws LispSyntaxException {
        String singleItemList = "(42)";
        assertEquals("(42 . NIL)", getDotNotation(singleItemList));
    }

    @Test
    public void testReadTwoItemList() throws LispSyntaxException {
        String twoItemList = "(42 12)";
        assertEquals("(42 . (12 . NIL))", getDotNotation(twoItemList));
    }

    @Test(expected = LispSyntaxException.class)
    public void testTooManyClosingBraces() throws LispSyntaxException {
        String tooManyClosingBraces = "(2))";
        getDotNotation(tooManyClosingBraces);
    }

    @Test(expected = LispSyntaxException.class)
    public void testUnexpectedEOF() throws LispSyntaxException {
        String unexpectedEOF = "(2 4 5";
        getDotNotation(unexpectedEOF);
    }

    @Test(expected = LispSyntaxException.class)
    public void testTooManyDots() throws LispSyntaxException {
        String tooManyDots = "(2 . . 3)";
        getDotNotation(tooManyDots);
    }

    @Test(expected = LispSyntaxException.class)
    public void testMalformedSymbolicAtom() throws LispSyntaxException {
        String malformedSymbol = "(1d 4)";
        getDotNotation(malformedSymbol);
    }
}