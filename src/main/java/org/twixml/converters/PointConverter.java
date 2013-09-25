/*--
 $Id: PointConverter.java,v 1.1 2004/03/01 07:56:02 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * A Converter that turns a Strings into Point objects
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */
public class PointConverter implements Converter {

    /**
     * Converts a String into an Point object
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value fields needs provides convertable
     *            String
     * @return <code>Object</code> - runtime type is <code>Point</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        if (attr != null) {
            final StringTokenizer st = new StringTokenizer (attr.getValue (),
                    ",");
            int x = 0;
            int y = 0;
            if (st.hasMoreTokens ()) {
                x = Integer.parseInt (st.nextToken ().trim ());
            }
            if (st.hasMoreTokens ()) {
                y = Integer.parseInt (st.nextToken ().trim ());
            }
            return CustomCodeProxy.getTypeAnalyser ().instantiate ("Point", x,
                    y);
        }
        return null;
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Point");
    }
}