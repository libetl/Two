package org.twixml.converters;

public class ConverterException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6118270965886450639L;

    public ConverterException () {
        super ();
    }

    public ConverterException (final String s) {
        super (s);
    }

    public ConverterException (final String s, final Throwable t) {
        super (s, t);
    }

    public ConverterException (final Throwable t) {
        super (t);
    }
}
