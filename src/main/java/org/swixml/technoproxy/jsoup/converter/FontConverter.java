
package org.swixml.technoproxy.jsoup.converter;


import org.swixml.Attribute;
import org.swixml.Localizer;
import org.swixml.technoproxy.ProxyCode;

/**
 * The FontConverter class defines / describes
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml 1.0
 */
public class FontConverter extends ProxyCode<org.swixml.converters.FontConverter> {

 
    public FontConverter (org.swixml.converters.FontConverter source1) {
        super (source1);
    }

    public Object convert (Class<?> type, Attribute attr, Localizer localizer)
            throws Exception {
        return attr != null ? "font:" + attr.getValue () + ";" : "";
    }
}
