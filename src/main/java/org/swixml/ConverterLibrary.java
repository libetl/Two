/*--
 $Id: ConverterLibrary.java,v 1.1 2004/03/01 07:56:05 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials provided
 with the distribution.

 3. The end-user documentation included with the redistribution,
 if any, must include the following acknowledgment:
        "This product includes software developed by the
         SWIXML Project (http://www.swixml.org/)."
 Alternately, this acknowledgment may appear in the software itself,
 if and wherever such third-party acknowledgments normally appear.

 4. The name "Swixml" must not be used to endorse or promote products
 derived from this software without prior written permission. For
 written permission, please contact <info_AT_swixml_DOT_org>

 5. Products derived from this software may not be called "Swixml",
 nor may "Swixml" appear in their name, without prior written
 permission from the Swixml Project Management.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE SWIXML PROJECT OR ITS
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 individuals on behalf of the Swixml Project and was originally
 created by Wolf Paulus <wolf_AT_swixml_DOT_org>. For more information
 on the Swixml Project, please see <http://www.swixml.org/>.
 */

package org.swixml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.swixml.converters.ActionConverter;
import org.swixml.converters.BorderConverter;
import org.swixml.converters.ColorConverter;
import org.swixml.converters.ComponentConverter;
import org.swixml.converters.DimensionConverter;
import org.swixml.converters.FontConverter;
import org.swixml.converters.ImageConverter;
import org.swixml.converters.ImageIconConverter;
import org.swixml.converters.InsetsConverter;
import org.swixml.converters.KeyStrokeConverter;
import org.swixml.converters.LocaleConverter;
import org.swixml.converters.PointConverter;
import org.swixml.converters.PrimitiveConverter;
import org.swixml.converters.RectangleConverter;
import org.swixml.converters.StringConverter;
import org.swixml.technoproxy.CustomCodeProxy;

/**
 * <p>
 * The <code>ConverterLibrary</code> contains all available Coverters,
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
 * @see org.swixml.Converter
 */
public class ConverterLibrary {
    private static ConverterLibrary instance = new ConverterLibrary ();

    /**
     * @return <code>ConverterLibrary</code> the single instacne of the
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
    public Converter getConverter (Class<?> template) {
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
     *         Converter to produce an instances of the gioven class.
     */
    public boolean hasConverter (Class<?> template) {
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
    public void register (Class<?> template, Converter converter) {
        this.converters.put (template, converter);
    }

    /**
     * Registers a Converter with the ConverterLibrary
     * 
     * @param converter
     *            <code>Converter</code> Instance of Converter able to convert
     *            Strings into objects of the given type
     */
    public void register (Converter converter) {
        this.converters.put (converter.convertsTo (), converter);
    }

    /**
     * Registers <code>Converters</code> with the ConverterLibrary.
     */
    private void registerConverters () {
        this.register (
                CustomCodeProxy.getTypeAnalyser ()
                        .getMostSuperClass ("Action"), new ActionConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ()
                        .getMostSuperClass ("Border"), new BorderConverter ());
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
                CustomCodeProxy.getTypeAnalyser ()
                        .getMostSuperClass ("Insets"), new InsetsConverter ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getMostSuperClass (
                        "KeyStroke"), new KeyStrokeConverter ());
        this.register (
                java.util.Locale.class, new LocaleConverter ());
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
