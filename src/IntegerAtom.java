public class IntegerAtom implements SExpression {
    private int value;

    public IntegerAtom(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
