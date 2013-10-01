/*--
 $Id: FontConverter.java,v 1.1 2004/03/01 07:55:59 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.converters;

import java.awt.GraphicsEnvironment;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.exception.ConverterException;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The FontConverter class defines / describes
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml 1.0
 */
public class FontConverter implements Converter {

    /**
     * Convert the value of the given <code>Attribute</code> object into an
     * output object of the specified type. Returns the <code>Font</code> that
     * the <code>str</code> argument describes. To ensure that this method
     * returns the desired Font, format the <code>str</code> parameter in one of
     * two ways:
     * <p/>
     * "fontfamilyname-style-pointsize" or <br>
     * "fontfamilyname style pointsize"
     * <p>
     * in which <i>style</i> is one of the three case-insensitive strings:
     * <code>"BOLD"</code>, <code>"BOLDITALIC"</code>, or <code>"ITALIC"</code>,
     * and pointsize is a decimal representation of the point size. For example,
     * if you want a font that is Arial, bold, and a point size of 18, you would
     * call this method with: "Arial-BOLD-18".
     * <p/>
     * The default size is 12 and the default style is PLAIN. If you don't
     * specify a valid size, the returned <code>Font</code> has a size of 12. If
     * you don't specify a valid style, the returned Font has a style of PLAIN.
     * If you do not provide a valid font family name in the <code>str</code>
     * argument, this method still returns a valid font with a family name of
     * "dialog". To determine what font family names are available on your
     * system, use the {@link GraphicsEnvironment#getAvailableFontFamilyNames()}
     * method. If <code>str</code> is <code>null</code>, a new <code>Font</code>
     * is returned with the family name "dialog", a size of 12 and a PLAIN
     * style. If <code>str</code> is <code>null</code>, a new <code>Font</code>
     * is returned with the name "dialog", a size of 12 and a PLAIN style.
     * 
     * @param type
     *            <code>Class</code> Data type to which the Attribute's value
     *            should be converted
     * @param attr
     *            <code>Attribute</code> the attribute, providing the value to
     *            be converted.
     * @return the <code>Font</code> object that <code>str</code> describes, or
     *         a new default <code>Font</code> if <code>str</code> is
     *         <code>null</code>.
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            final Localizer localizer) throws ConverterException {
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Font");
    }
}
