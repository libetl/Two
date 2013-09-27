/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.util.HashMap;
import java.util.Map;

import org.twixml.layoutconverters.BorderLayoutConverter;
import org.twixml.layoutconverters.CardLayoutConverter;
import org.twixml.layoutconverters.FlowLayoutConverter;
import org.twixml.layoutconverters.FormLayoutConverter;
import org.twixml.layoutconverters.GridBagLayoutConverter;
import org.twixml.layoutconverters.GridLayoutConverter;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * <p>
 * The <code>LayoutConverterLibrary</code> contains all available
 * {@link LayoutConverter}s.
 * </p>
 * 
 * @author Karl Tauber
 * @see LayoutConverter
 */
public class LayoutConverterLibrary {
    private static LayoutConverterLibrary instance = new LayoutConverterLibrary ();

    /**
     * Returns the single instance of the LayoutConverterLibrary.
     */
    public static synchronized LayoutConverterLibrary getInstance () {
        return LayoutConverterLibrary.instance;
    }

    private final Map<String, LayoutConverter<?>> layoutConverters = new HashMap<String, LayoutConverter<?>> ();

    private final Map<String, LayoutConverter<?>> layoutIDs        = new HashMap<String, LayoutConverter<?>> ();

    /**
     * The only available Ctor is private to make this a Singleton
     */
    private LayoutConverterLibrary () {
        this.registerLayoutConverters ();
    }

    /**
     * Returns a <code>LayoutConverter</code> instance, able to produce objects
     * for the given layout manager <code>class</code>.
     * 
     * @param layoutClass
     *            Class<?> of the object the <code>LayoutConverter</code> needs
     *            to produce.
     * @return Instance of the LayoutConverter class.
     */
    public LayoutConverter<?> getLayoutConverter (final Class<?> layoutClass) {
        return this.layoutConverters.get (layoutClass.getName ());
    }

    /**
     * Returns a <code>LayoutConverter</code> instance, able to produce objects
     * for the given layout manager <code>id</code> (see
     * {@link LayoutConverter#getID()}).
     * 
     * @param id
     *            Identifier of the layout manager the
     *            <code>LayoutConverter</code> needs to produce.
     * @return Instance of the LayoutConverter class.
     */
    public LayoutConverter<?> getLayoutConverterByID (final String id) {
        return this.layoutIDs.get (id.toLowerCase ());
    }

    /**
     * Returns all registered layout converters.
     */
    public Map<String, LayoutConverter<?>> getLayoutConverters () {
        return this.layoutConverters;
    }

    /**
     * Registers a LayoutConverter with the LayoutConverterLibrary.
     * 
     * @param layoutClass
     *            Type of the layout manager the LayoutConverter creates.
     * @param layoutConverter
     *            Instance of LayoutConverter able to convert Strings into
     *            layout managers or layout constraints.
     */
    public void register (final Class<?> layoutClass,
            final LayoutConverter<?> layoutConverter) {
        this.register (layoutClass.getName (), layoutConverter);
    }

    /**
     * Registers a LayoutConverter with the LayoutConverterLibrary.
     * 
     * @param layoutClassName
     *            Type name of the layout manager the LayoutConverter creates.
     * @param layoutConverter
     *            Instance of LayoutConverter able to convert Strings into
     *            layout managers or layout constraints.
     */
    public void register (final String layoutClassName,
            final LayoutConverter<?> layoutConverter) {
        this.layoutConverters.put (layoutClassName, layoutConverter);
        this.layoutIDs.put (layoutConverter.getID ().toLowerCase (),
                layoutConverter);
    }

    /**
     * Registers <code>LayoutConverters</code> with the LayoutConverterLibrary.
     */
    private void registerLayoutConverters () {
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "BorderLayout"), new BorderLayoutConverter<Object> ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "CardLayout"), new CardLayoutConverter<Object> ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "FlowLayout"), new FlowLayoutConverter<Object> ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "GridBagLayout"), new GridBagLayoutConverter<Object> ());
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "GridLayout"), new GridLayoutConverter<Object> ());

        // 3rd party layout managers
        this.register (
                CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                        "FormLayout"), new FormLayoutConverter<Object> ());
    }
}
