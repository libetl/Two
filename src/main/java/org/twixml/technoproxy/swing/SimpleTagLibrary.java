package org.twixml.technoproxy.swing;

import org.twixml.technoproxy.ProxyCode;

public class SimpleTagLibrary extends ProxyCode<org.twixml.TagLibrary> {

    public SimpleTagLibrary (org.twixml.TagLibrary source1) {
        super (source1);
    }

    public void registerTag (Class<?> template, String name) {
        this.getSource ().registerTag (name, new SwingFactory (template));
    }

}
