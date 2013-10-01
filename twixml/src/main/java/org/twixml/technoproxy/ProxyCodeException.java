package org.twixml.technoproxy;

public class ProxyCodeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8227926777790574121L;

    public ProxyCodeException (final Exception e) {
        super (e);
    }

    public ProxyCodeException (final String string, final Exception e) {
        super (string, e);
    }
}
