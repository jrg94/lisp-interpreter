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
                curr.setRight(SExpression.NIL);
            } else {
                curr.setRight(new NonAtom());
                curr = (NonAtom) curr.getRight();
            }
        }
        return root;
    }

    /**
     * A helper method for building a list list of Integer Atoms.
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
                curr.setRight(SExpression.NIL);
            } else {
                curr.setRight(new NonAtom());
                curr = (NonAtom) curr.getRight();
            }
        }
        return root;
    }

    private NonAtom bind(String symbol, int value) {
        NonAtom output = new NonAtom();
        output.setLeft(new SymbolicAtom(symbol));
        output.setRight(new IntegerAtom(value));
        return output;
    }

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
        NonAtom.addPairs(pList, argList, aList);

        NonAtom aToFiveBinding = bind("A", 5);
        Stack<NonAtom> expectedResult = getStack(aToFiveBinding);

        assertEquals(expectedResult, aList);
    }

}
