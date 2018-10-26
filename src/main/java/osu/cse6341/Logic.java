package osu.cse6341;

public enum Logic implements AtomMapping {
    T, NIL;

    /**
     * Overrides the base getAtom method of the AtomMapping interface.
     * 
     * @return this as a Symbolic Atom
     */
    @Override
    public SymbolicAtom getAtom() {
        return new SymbolicAtom(this.name());
    }
}
