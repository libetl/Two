package org.twixml.technoproxy.jsoup;

public class LayoutManager {

    private String    type;
    private Object [] params;

    public LayoutManager () {
        super ();
    }

    public LayoutManager (String decode) {
        super ();
    }

    public LayoutManager (Object... params1) {
        super ();
        this.params = params1;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public Object [] getParams () {
        return params;
    }

    public void setParams (Object [] params) {
        this.params = params;
    }

    @Override
    public String toString () {
        return "";
    }

}
