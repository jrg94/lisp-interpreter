public enum Logic implements AtomMapping {
    T, NIL;

    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
