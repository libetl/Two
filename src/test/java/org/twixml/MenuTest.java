/*--
 $Id: MenuTest.java,v 1.1 2005/06/04 22:24:06 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Component;
import java.awt.Container;

import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Test for some JMenuBar specialties.
 */
public class MenuTest extends TestCase {
    public static final String DESCRIPTOR = "samples/swing/xml/dialog.xml";
    private Container          container;
    private TwiXML             se;

    public MenuTest () {
        super ("Test inserting a Menu into a container.");
    }

    public MenuTest (String s) {
        super (s);
    }

    @Override
    public void setUp () throws Exception {
        this.se = new TwiXML (this);
        this.container = (Container) this.se.render (MenuTest.DESCRIPTOR);
    }

    /**
     * Clears the container
     */
    public void teardown () {
        this.container.removeAll ();
        this.container = null;
    }

    /**
     * Tests if a JMenubar is added into a container, even if the container
     * doesn't provide a setJMenuBar() method.
     */
    public void testInclusion () {
        final Component menubar = (Component) this.se.find ("menubar");
        TestCase.assertNotNull (
                "<menubar> tag in the descriptor requires the instantiation of a JMenuBar obj.",
                menubar);
        TestCase.assertNotNull (
                "Since <menubar> is not the root tag, it needs a parent container",
                menubar.getParent ());
    }
}
