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
    public void testDigitStartingSymbolicAtom() throws LispSyntaxException {
        String malformedSymbol = "1CDR";
        getDotNotation(malformedSymbol);
    }

    @Test(expected = LispSyntaxException.class)
    public void testSymbolContainingSymbolicAtom() throws LispSyntaxException {
        String malformedSymbol = "CA#R";
        getDotNotation(malformedSymbol);
    }

    @Test
    public void testNegativeIntegerAtom() throws LispSyntaxException {
        String negativeInteger = "-14";
        assertEquals("-14", getDotNotation(negativeInteger));
    }

    @Test
    public void testPositiveIntegerAtom() throws LispSyntaxException {
        String positiveInteger = "+26";
        assertEquals("26", getDotNotation(positiveInteger));
    }

    @Test
    public void testNestedList() throws LispSyntaxException {
        String nestedList = "(1 (4 6))";
        String expectedDotNotation = "(1 . ((4 . (6 . NIL)) . NIL))";
        assertEquals(expectedDotNotation, getDotNotation(nestedList));
    }

    @Test
    public void testMixedDotAndList() throws LispSyntaxException {
        String mixedNestAndList = "( 2 . (3 4))";
        String expectedDotNotation = "(2 . (3 . (4 . NIL)))";
        assertEquals(expectedDotNotation, getDotNotation(mixedNestAndList));
    }

    @Test(expected = LispSyntaxException.class)
    public void testUnexpectedDot() throws LispSyntaxException {
        String unexpectedDot = "( 2 . (3 4) . 5)";
        getDotNotation(unexpectedDot);
    }

    @Test
    public void testMixedDotAndList2() throws LispSyntaxException {
        String complexMixedNestAndList = "( 2 . ((3 4) . 5))";
        String expectedDotNotation = "(2 . ((3 . (4 . NIL)) . 5))";
        assertEquals(expectedDotNotation, getDotNotation(complexMixedNestAndList));
    }

    @Test(expected = LispSyntaxException.class)
    public void testUnexpectedSymbol() throws LispSyntaxException {
        String unexpectedSymbol = "( 2 . (3 4) $ 5)";
        getDotNotation(unexpectedSymbol);
    }

    @Test
    public void testMixedDotAndList3() throws LispSyntaxException {
        String mixedDotAndList = "( 2 (3 . 4) (5 . 6))";
        String expectedDotNotation = "(2 . ((3 . 4) . ((5 . 6) . NIL)))";
        assertEquals(expectedDotNotation, getDotNotation(mixedDotAndList));
    }
}
