/*--
 $Id: ConverterLibrary.java,v 1.1 2004/03/01 07:56:05 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.twixml.converters.ActionConverter;
import org.twixml.converters.BorderConverter;
import org.twixml.converters.ColorConverter;
import org.twixml.converters.ComponentConverter;
import org.twixml.converters.DimensionConverter;
import org.twixml.converters.FontConverter;
import org.twixml.converters.ImageConverter;
import org.twixml.converters.ImageIconConverter;
import org.twixml.converters.InsetsConverter;
import org.twixml.converters.KeyStrokeConverter;
import org.twixml.converters.LocaleConverter;
import org.twixml.converters.PointConverter;
import org.twixml.converters.PrimitiveConverter;
import org.twixml.converters.RectangleConverter;
import org.twixml.converters.StringConverter;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * <p>
 * The <code>ConverterLibrary</code> contains all available Converters,
 * converting Strings.
 * </p>
 * <p>
 * General purpose data type converters are able to convert Strings into objects
 * that are usually as parameters when setters are called on objects.
 * 
 * <pre>
 * Available Converter include converters able to produce
 * <ul>
 * <li>Primitives</li>
 * <li>Dimension</li>
 * <li>Color</li>
 * <li>Border</li>
 * <li>etc.</li>
 * </ul>
 * </pre>
 * 
 * <pre>
 * Example String inputs could look like this:
 * <ul>
 * <li>MatteBorder(4,4,4,4,red)</li>
 * <li>FFCCEE</li>
 * <li>BorderLayout.CENTER</li>
 * <li>2,2,2,2</li>
 * </ul>
 * </pre>
 * 
 * Date: Dec 16, 2002
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.Converter
 */
public class ConverterLibrary {
    private static ConverterLibrary instance = new ConverterLibrary ();

    /**
     * @return <code>ConverterLibrary</code> the single instance of the
     *         ConverterLibrary.
     */
    public static synchronized ConverterLibrary getInstance () {
        return ConverterLibrary.instance;
    }

    private final Map<Class<?>, Converter> converters = new HashMap<Class<?>, Converter> ();

    /**
     * The only available Ctor is private to make this a Singleton
     */
    private ConverterLibrary () {
        this.registerConverters ();
    }

    /**
     * Returns a <code>Converter</code> instance, able to produce objects of the
     * given <code>class</code>
     * 
     * @param template
     *            <code>Class</code> Class<?> of the object the
     *            <code>Converter</code> needs to produce.
     * @return <code>Converter</code> - instance of the given Converter class.
     */
    public Converter getConverter (final Class<?> template) {
        return this.converters.get (template);
    }

    /**
     * @return <code>Map</code> - all registered converters.
     * 
     *         <pre>
     * Use a class to get to the converters
     * </pre>
     */
    public Map<Class<?>, Converter> getConverters () {
        return this.converters;
    }

    /**
     * Indicates if a the ConverterLibary has a Converter producing instances of
     * the given Class.
     * 
     * @param template
     *            <code>Class</code>
     * @return <code>boolean</code> true, if the ConverterLibrary has a
     *         Converter to produce an instances of the given class.
     */
    public boolean hasConverter (final Class<?> template) {
        boolean found = this.converters.keySet ().contains (template);
        final Iterator<?> it = this.converters.values ().iterator ();
        while (!found && (it != null) && it.hasNext ()) {
            found = template.isAssignableFrom ( ((Converter) it.next ())
                    .convertsTo ());
        }
        return found;
    }

    /**
     * Registers a Converter with the ConverterLibrary
     * 
     * @param template
     *            <code>Class</code> type of the objects the Converter creates
     * @param converter
     *            <code>Converter</code> Instance of Converter able to convert
     *            Strings into objects of the given type
     */
    public void register (final Class<?> template, final Converter converter) {
        this.converters.put (template, converter);
    }

    /**
     * Registers a Converter with the ConverterLibrary
     * 
     * @param converter
     *            <code>Converter</code> Instance of Converter able to convert
     *            Strings into objects of the given type
     */
    public void register (final Converter converter) {
        this.converters.put (converter.convertsTo (), converter);
    }

    /**
     * Registers <code>Converters</code> with the ConverterLibrary.
     */
    private void registerConverters () {
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Action"),
                new ActionConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Border"),
                new BorderConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Color"),
                new ColorConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass (
                        "Component"), new ComponentConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass (
                        "Dimension"), new DimensionConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Font"),
                new FontConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Image"),
                new ImageConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Icon"),
                new ImageIconConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass (
                        "ImageIcon"), new ImageIconConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Insets"),
                new InsetsConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "KeyStroke"), new KeyStrokeConverter ());
        this.register (java.util.Locale.class, new LocaleConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass ("Point"),
                new PointConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass (
                        "Rectangle"), new RectangleConverter ());
        this.register (String.class, new StringConverter ());
        //
        // Register the PrimitiveConverter class for java primitive types
        //
        this.register (boolean.class, new PrimitiveConverter ());
        this.register (int.class, new PrimitiveConverter ());
        this.register (long.class, new PrimitiveConverter ());
        this.register (float.class, new PrimitiveConverter ());
        this.register (double.class, new PrimitiveConverter ());
    }

}
