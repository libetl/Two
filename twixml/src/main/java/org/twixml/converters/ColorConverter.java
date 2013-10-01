/*--
 $Id: ColorConverter.java,v 1.1 2004/03/01 07:55:58 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;

import org.twixml.AppConstants;
import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The ColorConverter class defines a Converter that turns the Strings into a
 * Color object
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * 
 * 
 * 
 *          <h3>Examples for Valid XML attribute notations:</h3>
 * 
 *          <pre>
 * <b>The following example show valid xml attributes to create Color objects:</b><br>
 * <ul>
 * <li>background="3399CC"</li>
 * <li>background="red"</li>
 * <li>foreground="991144"</li>
 * </ul>
 * </pre>
 */
public class ColorConverter implements Converter {

    /**
     * Returns a <code>Color</code> runtime object
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value needs to provide a String
     * @return runtime type is subclass of <code>Color</code>
     */
    public static Object conv (final Class<?> type, final Attribute attr) {
        Class<?> c = CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                "Color");
        if (attr != null) {
            try {
                final Field field = c.getField (attr.getValue ());
                if (c.equals (field.getType ())
                        && Modifier.isStatic (field.getModifiers ())) {
                    return field.get (c);
                }
            } catch (final NoSuchFieldException e) {
            } catch (final SecurityException e) {
            } catch (final IllegalAccessException e) {
            }
            final StringTokenizer st = new StringTokenizer (attr.getValue (),
                    ",");
            if (1 == st.countTokens ()) {
                try {
                    return CustomCodeProxy.getTypeAnalyser ().instantiate (
                            "Color",
                            Integer.parseInt (st.nextToken ().trim (), 16));
                } catch (final NumberFormatException e) {
                    if (AppConstants.DEBUG_MODE) {
                        System.err.println (e);
                    }
                    return null;
                }
            }
            final int [] para = Util.ia (st);
            if (4 <= para.length) {
                return CustomCodeProxy.getTypeAnalyser ().instantiate ("Color",
                        para [0], para [1], para [2], para [3]);
            }
            if (3 <= para.length) {
                return CustomCodeProxy.getTypeAnalyser ().instantiate ("Color",
                        para [0], para [1], para [2]);
            }
            if (1 <= para.length) {
                return CustomCodeProxy.getTypeAnalyser ().instantiate ("Color",
                        para [0]);
            }
        }
        return null;
    }

    /**
     * Returns a <code>Color</code> runtime object
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value needs to provide a String
     * @return runtime type is subclass of <code>Color</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        return ColorConverter.conv (type, attr);
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Color");
    }

}
