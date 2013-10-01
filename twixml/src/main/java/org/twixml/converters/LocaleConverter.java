/*--
 $Id: LocaleConverter.java,v 1.1 2004/03/01 07:56:02 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import java.util.Locale;
import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.exception.ConverterException;

/**
 * The LocaleConverter class defines / describes
 * 
 * 
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 */
public class LocaleConverter implements Converter {
    /** converter's return type */
    public static final Class<?> TEMPLATE = Locale.class;

    /**
     * Convert the value of the given <code>Attribute</code> object into an
     * output object of the specified type.
     * 
     * @param attr
     *            <code>Attribute</code> the attribute, providing the value to
     *            be converted.
     * 
     */
    public static Locale conv (final String attrValue)
            throws ConverterException {
        Locale locale = null; // Locale.getDefault();
        if (attrValue != null) {
            final StringTokenizer st = new StringTokenizer (attrValue, ",");
            final int n = st.countTokens ();
            if (1 == n) {
                locale = new Locale (st.nextToken ());
            } else if (2 == n) {
                locale = new Locale (st.nextToken (), st.nextToken ());
            } else if (3 <= n) {
                locale = new Locale (st.nextToken (), st.nextToken (),
                        st.nextToken ());
            }
        }
        return locale;
    }

    /**
     * Convert the value of the given <code>Attribute</code> object into an
     * output object of the specified type.
     * 
     * @param type
     *            <code>Class</code> Data type to which the Attribute's value
     *            should be converted
     * @param attr
     *            <code>Attribute</code> the attribute, providing the value to
     *            be converted.
     * 
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            final Localizer localizer) throws ConverterException {
        return LocaleConverter.conv (attr.getValue ());
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
        return LocaleConverter.TEMPLATE;
    }

}
