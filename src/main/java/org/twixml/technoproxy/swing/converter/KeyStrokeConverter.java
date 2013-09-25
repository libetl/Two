package org.twixml.technoproxy.swing.converter;

import javax.swing.KeyStroke;

import org.twixml.Attribute;
import org.twixml.technoproxy.ProxyCode;

/**
 * The FontConverter class defines / describes
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml 1.0
 */
public class KeyStrokeConverter extends
        ProxyCode<org.twixml.converters.KeyStrokeConverter> {

    public KeyStrokeConverter (org.twixml.converters.KeyStrokeConverter source1) {
        super (source1);
    }

    public Object convert (Attribute attr) throws Exception {
        return KeyStroke.getKeyStroke (attr.getValue ());
    }
}
