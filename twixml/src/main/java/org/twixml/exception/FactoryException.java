package org.twixml.exception;

public class FactoryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6118270965886450639L;

    public FactoryException () {
        super ();
    }

    public FactoryException (final String s) {
        super (s);
    }

    public FactoryException (final String s, final Throwable t) {
        super (s, t);
    }

    public FactoryException (final Throwable t) {
        super (t);
    }
}
