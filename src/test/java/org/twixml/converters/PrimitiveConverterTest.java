/*--
 $Id: ButtonGroupTest.java,v 1.2 2005/06/01 00:04:36 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.converters;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import junit.framework.TestCase;

import org.twixml.Attribute;
import org.twixml.converters.PrimitiveConverter;

/**
 * <code>PrimitiveConverterTest</code> JScrollPane.class gets added to the
 * Primitive Converter. However, its constants are not added since they are all
 * defined in an additional interface, i.e. <code>ScrollPaneConstants</code>
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class PrimitiveConverterTest extends TestCase {

    public PrimitiveConverterTest () {
        this (PrimitiveConverterTest.class.getSimpleName ());
    }

    public PrimitiveConverterTest (String string) {
        super (string);

    }

    /**
     * This test worked successfully only after PrimitiveConverter also
     * <code>implements ScrollPaneConstants</code>
     * 
     * @throws Exception
     * @see ScrollPaneConstants
     */
    public void testConstantAvailability () throws Exception {
        final Object obj = new PrimitiveConverter ().convert (
                JScrollPane.class, new Attribute ("orientation",
                        "VERTICAL_SCROLLBAR_ALWAYS"), null);
        TestCase.assertNotNull (obj);
    }
}
