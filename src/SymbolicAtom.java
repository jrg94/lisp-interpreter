public class SymbolicAtom implements SExpression {
    private String name;

    public SymbolicAtom(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
