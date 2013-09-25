/*--
 $Id: MappingTest.java,v 1.2 2005/06/01 00:04:15 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Test class running auto-mapping related tests.
 * 
 * @author Wolf Paulus
 */
public class MappingTest extends TestCase implements GlobalActions {
    public static final String DESCRIPTOR = "samples/swing/xml/mappings.xml";
    private Container          container;
    private JMenuItem          miAbout;

    public JButton             b1;
    public transient JButton   b2;
    private JButton            b3;
    private transient JButton  b4;

    public MappingTest () {
        super (
                "Test Mapping og XML Tags to JComponent Objects in the SwingEngine's Client");
    }

    public MappingTest (String s) {
        super (s);
    }

    /**
     * Renders the test GUI into the container field.<br>
     * Note: Like with every testcase, the setup method is going to be performed
     * before the execution of every test..() method.
     * 
     * @throws Exception
     */
    @Override
    public void setUp () throws Exception {
        final TwiXML se = new TwiXML (this);
        this.container = (Container) se.render (MappingTest.DESCRIPTOR);
    }

    /**
     * Clears the container
     */
    public void teardown () {
        this.container.removeAll ();
        this.container = null;
    }

    /**
     * Tests if the JButtons were correctly initialized/mapped by the
     * SwingEngine.
     */
    public void testMappingPublicFields () {
        TestCase.assertNotNull (
                "Public Fields, whose names have matching ids the the XML descriptor should be initializd by the SwingEngine.",
                this.b1);
        TestCase.assertNull (
                "Transient Fields must not be initializd by the SwingEngine.",
                this.b2);
        TestCase.assertNotNull (
                "Private Field, whose names have a matching ids the the XML descriptor should be initializd by the SwingEngine.",
                this.b3);
        TestCase.assertNull (
                "Transient Fields must not be initialized by the SwingEngine.",
                this.b4);
    }

    public void testStaticInterfaceMappings () {
        TestCase.assertNotNull (
                "aboutAction is statically defined in an Interface and should have been mapped into the private JMenuItem's action",
                this.miAbout.getAction ());
    }

}
