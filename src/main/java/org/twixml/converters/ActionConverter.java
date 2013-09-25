/*--
 $Id: ActionConverter.java,v 1.1 2004/03/01 07:55:57 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.converters;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The ActionConverter is a tagging class that is only used to register the
 * Action.class with the ConverterLibrary
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 1.0
 */
public class ActionConverter implements Converter {
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
    public Object convert (Class<?> type, Attribute attr, Localizer localizer)
            throws Exception {
        return null;
    }

    /**
     * A <code>conversTo</code> method informs about the Class<?> type the
     * converter is returning when its <code>convert</code> method is called
     * 
     * @return <code>Class</code> - the Class<?> the converter is returning when
     *         its convert method is called
     */
    @Override
    public Class<?> convertsTo () {
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Action");
    }
}
