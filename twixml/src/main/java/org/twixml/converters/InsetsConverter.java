/*--
 $Id: InsetsConverter.java,v 1.1 2004/03/01 07:56:00 wolfpaulus Exp $

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
 * The <code>InsetsConverter</code> class defines a converter that creates
 * Insets objects based on a provided String.
 * 
 * <h3>Examples for Valid XML attribute notations:</h3>
 * 
 * <pre>
 * <ul>
 * <li>border="MatteBorder(4,4,4,4,red)"</li>
 * <li>insets="2,2,2,2"</li>
 * </ul>
 * </pre>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @see org.twixml.ConverterLibrary
 */
public class InsetsConverter implements Converter {

    /**
     * Converts a Strings into an Insets object
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value needs to provide String
     *            containing comma sep. integers
     * @return <code>Object</code> runtime type is subclass of
     *         <code>Insets</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        Object insets = null;
        if (attr != null) {
            final StringTokenizer st = new StringTokenizer (attr.getValue (),
                    "(,)");
            if (5 == st.countTokens ()) { // assume "insets(...)"
                st.nextToken ().trim ();
            }
            final int [] param = Util.ia (st);
            if (4 <= param.length) {
                insets = CustomCodeProxy.getTypeAnalyser ().instantiate (
                        "Insets", param [0], param [1], param [2], param [3]);
            }
        }
        return insets;
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Insets");
    }
}
