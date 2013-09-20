
package org.swixml.technoproxy.swing.converter;


import javax.swing.ImageIcon;

import org.swixml.Attribute;
import org.swixml.Localizer;
import org.swixml.converters.ImageIconConverter;
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
        final ImageIcon icon = (ImageIcon) ImageIconConverter.conv (type, attr,
                localizer);
        return icon != null ? icon.getImage () : null;
    }
}
