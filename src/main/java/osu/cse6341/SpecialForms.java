public enum SpecialForms implements AtomMapping {
    QUOTE, COND, DEFUN;

    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
