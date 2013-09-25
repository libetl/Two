/*--
 $Id: ComponentConverter.java,v 1.1 2004/03/01 07:55:58 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The ComponentConverter class defines a dummy converter It's simply here to
 * allow the registration of setter-methods excepting Components
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public class ComponentConverter implements Converter {

    /**
     * Convert the value of the given <code>Attribute</code> object into an
     * output object of the specified type.
     * 
     * @param attr
     *            <code>Attribute</code> the attribute, providing the value to
     *            be converted.
     */
    public static Object conv (Attribute attr) throws Exception {
        return null;
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
     */
    @Override
    public Object convert (Class<?> type, Attribute attr, Localizer localizer)
            throws Exception {
        return ComponentConverter.conv (attr);
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Component");
    }
}
