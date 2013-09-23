package org.swixml.technoproxy.swing;

import org.swixml.technoproxy.ProxyCode;


public class SwingTagLibrary extends ProxyCode<org.swixml.TagLibrary> {

    public SwingTagLibrary (org.swixml.TagLibrary source1) {
        super (source1);
    }
    
    public void registerTag (Class<?> template, String name) {
        this.getSource ().registerTag (name, new SwingFactory (template));
    }

}
