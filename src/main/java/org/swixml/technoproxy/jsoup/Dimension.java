package org.swixml.technoproxy.jsoup;

public class Dimension {

    private int width;
    private int height;
    public int getWidth () {
        return width;
    }
    public void setWidth (int width) {
        this.width = width;
    }
    public int getHeight () {
        return height;
    }
    public void setHeight (int height) {
        this.height = height;
    }
    public Dimension (int width, int height) {
        super ();
        this.width = width;
        this.height = height;
    }


}
