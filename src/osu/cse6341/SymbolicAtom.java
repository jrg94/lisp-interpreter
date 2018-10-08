package osu.cse6341;
public class SymbolicAtom implements SExpression {
    private String name;

    public SymbolicAtom(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual;
        if (o == null) {
            isEqual = false;
        } else if (o instanceof SymbolicAtom) {
            SymbolicAtom atom = (SymbolicAtom) o;
            if (this.name.equals(atom.getValue())) {
                isEqual = true;
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        return isEqual;
    }
}
