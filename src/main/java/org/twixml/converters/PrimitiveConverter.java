/*--
 $Id: PrimitiveConverter.java,v 1.1 2004/03/01 07:56:02 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import java.util.HashMap;
import java.util.Map;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.Parser;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The <code>PrimitiveConverter</code> class defines a converter that creates
 * primitive objects (wrapper), based on a provided input Class<?> and String.
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */

public class PrimitiveConverter implements Converter, KeyEvent, InputEvent,
        ScrollPaneConstants, SwingConstants {

    /** map contains all constant provider types */
    private static Map<String, Class<?>> dictionaries = new HashMap<String, Class<?>> ();
    /**
     * Static Initializer, setting up the initial constant providers
     */
    static {
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("TabbedPane"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("ScrollPane"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("SplitPane"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("GridBagConstraints"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("FlowLayout"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("ListSelectionModel"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("TreeSelectionModel"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("Dialog"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("Frame"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("TitledBorder"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("JFrame"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("WindowConstants"));
        PrimitiveConverter.addConstantProvider (CustomCodeProxy
                .getTypeAnalyser ().getMostSuperClass ("JDialog"));
    }

    /**
     * Adds a new class or interface to the dictionary of primitive providers.
     * 
     * @param clazz
     *            <code>Class</code> providing primitive constants / public
     *            (final) fields
     */
    public static void addConstantProvider (final Class<?> clazz) {
        PrimitiveConverter.dictionaries.put (clazz.getSimpleName (), clazz);
    }

    /**
     * Converts String into java primitive type
     * 
     * @param type
     *            <code>Class</code> target type
     * @param attr
     *            <code>Attribute</code> value field needs to provide
     *            convertable String
     * @param localizer
     *            <code>Localizer</code>
     * @return <code>Object</code> primitive wrapped into wrapper object
     * @throws NoSuchFieldException
     *             in case no class a field matching field name had been
     *             regsitered with this converter
     * @throws IllegalAccessException
     *             if a matching field can not be accessed
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            final Localizer localizer) throws NoSuchFieldException,
            IllegalAccessException {
        Object obj = null;
        if (Parser.LOCALIZED_ATTRIBUTES.contains (attr.getName ()
                .toLowerCase ())) {
            attr.setValue (localizer.getString (attr.getValue ()));
        }

        try {
            if (boolean.class.equals (type)) {
                obj = Boolean.valueOf (attr.getValue ());
            } else if (int.class.equals (type)) {
                obj = Integer.valueOf (attr.getValue ());
            } else if (long.class.equals (type)) {
                obj = Long.valueOf (attr.getValue ());
            } else if (float.class.equals (type)) {
                obj = Float.valueOf (attr.getValue ());
            } else if (double.class.equals (type)) {
                obj = Double.valueOf (attr.getValue ());
            }
        } catch (final Exception e) {
            // intent. empty
        } finally {
            if (obj == null) {
                try {
                    final String s = attr.getValue ();
                    final int k = s.indexOf ('.');
                    final Class<?> pp = PrimitiveConverter.dictionaries.get (s
                            .substring (0, k));
                    obj = pp.getField (s.substring (k + 1)).get (pp);
                } catch (final Exception ex) {
                    //
                    // Try to find the given value as a Constant in
                    // SwingConstants
                    //
                    obj = PrimitiveConverter.class.getField (attr.getValue ())
                            .get (PrimitiveConverter.class);
                }
            }
        }

        return obj;
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
        return Object.class;
    }
}
