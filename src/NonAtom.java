public class NonAtom implements SExpression {
    private SExpression left;
    private SExpression right;

    public SExpression getLeft() {
        return left;
    }

    public SExpression getRight() {
        return right;
    }
}
