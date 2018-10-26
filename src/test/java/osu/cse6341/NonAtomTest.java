package osu.cse6341;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

public class NonAtomTest {

    /**
     * A helper method for building a lisp list of Symbolic Atoms.
     *
     * @param symbols a list of string symbols
     * @return the root of the list
     */
    private NonAtom buildList(String... symbols) {
        NonAtom root = new NonAtom();
        NonAtom curr = root;
        for (int i = 0; i < symbols.length; i++) {
            curr.setLeft(new SymbolicAtom(symbols[i]));
            if (i + 1 == symbols.length) {
                curr.setRight(Logic.NIL.getAtom());
            } else {
                NonAtom next = new NonAtom();
                curr.setRight(next);
                curr = next;
            }
        }
        return root;
    }

    /**
     * A helper method for building a list of Integer Atoms.
     *
     * @param symbols a list of integers
     * @return the root of the list
     */
    private NonAtom buildList(int... symbols) {
        NonAtom root = new NonAtom();
        NonAtom curr = root;
        for (int i = 0; i < symbols.length; i++) {
            curr.setLeft(new IntegerAtom(symbols[i]));
            if (i + 1 == symbols.length) {
                curr.setRight(Logic.NIL.getAtom());
            } else {
                NonAtom next = new NonAtom();
                curr.setRight(next);
                curr = next;
            }
        }
        return root;
    }

    /**
     * A helper method for building bindings.
     *
     * @param symbol a SymbolicAtom as a string
     * @param value an IntegerAtom as an integer
     * @return the NonAtom binding
     */
    private NonAtom bind(String symbol, int value) {
        NonAtom output = new NonAtom();
        output.setLeft(new SymbolicAtom(symbol));
        output.setRight(new IntegerAtom(value));
        return output;
    }

    /**
     * From a set of bindings, we construct a stack.
     *
     * @param atoms a list of NonAtom
     * @return the stack of bindings
     */
    private Stack<NonAtom> getStack(NonAtom... atoms) {
        Stack<NonAtom> output = new Stack<NonAtom>();
        for (NonAtom atom : atoms) {
            output.push(atom);
        }
        return output;
    }

    @Test
    public void testAddPairsSingleArgument() throws LispEvaluationException {
        NonAtom pList = buildList("A");
        NonAtom argList = buildList(5);
        Stack<NonAtom> aList = new Stack<NonAtom>();
        SExpression.addPairs(pList, argList, aList);

        NonAtom aToFiveBinding = bind("A", 5);
        Stack<NonAtom> expectedResult = getStack(aToFiveBinding);

        assertEquals(expectedResult, aList);
    }

    @Test
    public void testAddPairsManyArguments() throws LispEvaluationException {
        NonAtom pList = buildList("X", "Y", "Z");
        NonAtom argList = buildList(5, 6, 7);
        Stack<NonAtom> aList = new Stack<NonAtom>();
        SExpression.addPairs(pList, argList, aList);

        Stack<NonAtom> expectedResult = getStack(bind("X", 5), bind("Y", 6), bind("Z", 7));

        assertEquals(expectedResult, aList);
    }

}
