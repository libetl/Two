/*--
 $Id: RectangleConverter.java,v 1.1 2004/03/01 07:56:03 wolfpaulus Exp $

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
 * A Converter that turns a Strings in the form: width,height into Rectangle
 * objects. <br>
 * <h3>Examples for Valid XML attribute notations:</h3>
 * 
 * <pre>
 * <ul>
 * <li>bounds="10,10,250,200"</li>
 * </ul>
 * </pre>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */

public final class RectangleConverter implements Converter {

    /**
     * Converts a String into a Rectangle object
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value fields needs provides convertable
     *            String
     * @return <code>Object</code> - runtime type is <code>Dimension</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        if (attr != null) {
            final StringTokenizer st = new StringTokenizer (attr.getValue (),
                    ",");
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            if (st.hasMoreTokens ()) {
                x = Integer.parseInt (st.nextToken ().trim ());
            }
            if (st.hasMoreTokens ()) {
                y = Integer.parseInt (st.nextToken ().trim ());
            }
            if (st.hasMoreTokens ()) {
                width = Integer.parseInt (st.nextToken ().trim ());
            }
            if (st.hasMoreTokens ()) {
                height = Integer.parseInt (st.nextToken ().trim ());
            }
            return CustomCodeProxy.getTypeAnalyser ().instantiate ("Rectangle",
                    x, y, width, height);
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                "Rectangle");
    }
}