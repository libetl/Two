
package org.swixml.technoproxy.swing.converter;



import javax.swing.KeyStroke;

import org.swixml.Attribute;
import org.swixml.technoproxy.ProxyCode;

/**
 * The FontConverter class defines / describes
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml 1.0
 */
public class KeyStrokeConverter extends ProxyCode<org.swixml.converters.KeyStrokeConverter> {

 
    public KeyStrokeConverter (org.swixml.converters.KeyStrokeConverter source1) {
        super (source1);
    }

    public Object convert (Attribute attr)
            throws Exception {
        return KeyStroke.getKeyStroke (attr.getValue ());
    }
}
