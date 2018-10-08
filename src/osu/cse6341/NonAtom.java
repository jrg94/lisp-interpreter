package osu.cse6341;
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

    @Override
    public boolean equals(Object o) {
        boolean isEqual;
        if (o == null) {
            isEqual = false;
        } else if (o instanceof NonAtom) {
            NonAtom atom = (NonAtom) o;
            isEqual = this.left.equals(atom.getLeft()) && this.right.equals(atom.getRight());
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    public String toString() {
        return "(" + left.toString() + " . " + right.toString() + ")";
    }
}