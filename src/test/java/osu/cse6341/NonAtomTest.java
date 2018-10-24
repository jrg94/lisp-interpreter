package osu.cse6341;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

public class NonAtomTest {

    private Stack<NonAtom> getStack(NonAtom... atoms) {
        Stack<NonAtom> output = new Stack<NonAtom>();
        for (NonAtom atom : atoms) {
            output.push(atom);
        }
        return output;
    }

    @Test
    public void testAddPairsSingleArgument() throws LispEvaluationException {
        NonAtom pList = new NonAtom();
        NonAtom argList = new NonAtom();
        pList.setLeft(new SymbolicAtom("A"));
        pList.setRight(SExpression.NIL);
        argList.setLeft(new IntegerAtom(5));
        argList.setRight(SExpression.NIL);
        Stack<NonAtom> aList = new Stack<NonAtom>();
        NonAtom.addPairs(pList, argList, aList);

        NonAtom aToFiveBinding = new NonAtom();
        aToFiveBinding.setLeft(new SymbolicAtom("A"));
        aToFiveBinding.setRight(new IntegerAtom(5));
        Stack<NonAtom> expectedResult = getStack(aToFiveBinding);

        assertEquals(expectedResult, aList);
    }

}
