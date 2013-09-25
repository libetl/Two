package org.twixml.technoproxy;

public class ProxyCode<T> {

    private final T source;

    public ProxyCode (T source1) {
        this.source = source1;
    }

    protected T getSource () {
        return this.source;
    }
}
