package osu.cse6341;

/**
 * A collection of statically defined special forms.
 * 
 * @author Jeremy Grifski
 */
public enum SpecialForms implements AtomMapping {
    QUOTE, COND, DEFUN;

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
