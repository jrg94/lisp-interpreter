public class NonAtom implements SExpression {
    private SExpression left;
    private SExpression right;

    public void setLeft(SExpression left) {
        this.left = left;
    }

    public void setRight(SExpression right) {
        this.right = right;
    }

    public SExpression getLeft() {
        return left;
    }

    public SExpression getRight() {
        return right;
    }

    public String toString() {
        return "(" + left.toString() + " . " + right.toString() + ")";
    }
}
