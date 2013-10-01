/*--
 $Id: Converter.java,v 1.2 2004/08/20 05:59:57 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import org.twixml.exception.ConverterException;

/**
 * <p>
 * General purpose data type converter that can be registered and used within
 * the Twixml package to manage the conversion of objects from one type to
 * another.
 * </p>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.2 $
 */
public interface Converter {

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
    Object convert (final Class<?> type, final Attribute attr,
            final Localizer localizer) throws ConverterException;

    /**
     * A <code>Converters</code> conversTo method informs about the Class<?>
     * type the converter is returning when its <code>convert</code> method is
     * called
     * 
     * @return <code>Class</code> - the Class<?> the converter is returning when
     *         its convert method is called
     */
    Class<?> convertsTo ();

}
