/*--
 $Id: ImageConverter.java,v 1.1 2004/03/01 07:56:00 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * A Converter that turns a Strings in the form of a filename into an Image
 * objects.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */
public class ImageConverter implements Converter {

    /**
     * Converts a String into an ImageIcon through a Resource lookup
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> attribute provides the value to be
     *            converted
     * @param localizer
     *            <code>Localizer</code> allow the use of resource lookups
     * @return <code>Object</code> - an <code>ImageIcon</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        return CustomCodeProxy.doProxy (this, type, attr, localizer);
    }

    /**
     * A <code>Converters</code> conversTo method informs about the Class<?>
     * type the converter is returning when its <code>convert</code> method is
     * called
     * 
     * @return <code>Class</code> - the Class<?> the converter is returning when
     *         its convert method is called
     */
    @Override
    public Class<?> convertsTo () {
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Image");
    }
}
