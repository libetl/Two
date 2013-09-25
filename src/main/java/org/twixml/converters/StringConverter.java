/*--
 $Id: StringConverter.java,v 1.1 2004/03/01 07:56:03 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.converters;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.Parser;

/**
 * The StringConverter class defines / describes
 * 
 * 
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 */
public class StringConverter implements Converter {
    /** converter's return type */
    public static final Class<?> TEMPLATE = String.class;

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
    public Object convert (Class<?> type, final Attribute attr,
            Localizer localizer) throws Exception {
        //
        // Localize Strings but only if Attribute calls for localization.
        //
        if (Parser.LOCALIZED_ATTRIBUTES.contains (attr.getName ()
                .toLowerCase ())) {
            return localizer.getString (attr.getValue ());
        }
        return attr.getValue ();
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
        return StringConverter.TEMPLATE;
    }
}
