
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
public class ImageConverter extends ProxyCode<org.swixml.converters.ImageConverter> {

 
    public ImageConverter (org.swixml.converters.ImageConverter source1) {
        super (source1);
    }

    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        return attr.getValue () != null ? "background:url('" + attr.getValue () + "')" : null;
    }
}
