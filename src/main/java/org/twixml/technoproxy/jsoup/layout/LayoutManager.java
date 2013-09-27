package org.twixml.technoproxy.jsoup.layout;

import java.util.Arrays;

import org.jsoup.nodes.Element;

public class LayoutManager {

    private String    type;
    private String [] params;

    public LayoutManager () {
        super ();
    }

    public LayoutManager (final String... params1) {
        super ();
        this.params = params1;
    }

    public LayoutManager (final String decode) {
        super ();
    }

    public void apply (final Element e, final Element leaf) {
        e.attr ("layout",
                this.getClass ().getSimpleName ()
                        + Arrays.toString (this.params));
    }

    public Object [] getParams () {
        return this.params;
    }

    public String getType () {
        return this.type;
    }

    public void setParams (final Object [] paramsP) {
        this.params = new String [paramsP.length];
        for (int i = 0 ; i < paramsP.length ; i++) {
            this.params [i] = paramsP [i].toString ();
        }
    }

    public void setParams (final String [] params) {
        this.params = params;
    }

    public void setType (final String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return "";
    }

}
