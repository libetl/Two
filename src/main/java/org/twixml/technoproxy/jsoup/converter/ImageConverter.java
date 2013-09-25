package org.twixml.technoproxy.jsoup.converter;

import org.twixml.Attribute;
import org.twixml.Localizer;
import org.twixml.technoproxy.ProxyCode;

/**
 * The FontConverter class defines / describes
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml 1.0
 */
public class ImageConverter extends
        ProxyCode<org.twixml.converters.ImageConverter> {

    public ImageConverter (org.twixml.converters.ImageConverter source1) {
        super (source1);
    }

    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        return attr.getValue () != null ? "background:url('" + attr.getValue ()
                + "')" : null;
    }
}
