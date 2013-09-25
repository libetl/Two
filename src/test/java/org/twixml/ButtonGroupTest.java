/*--
 $Id: ButtonGroupTest.java,v 1.2 2005/06/01 00:04:36 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Container;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * The somewhat odd ButtonGroup class doesn't really fit into the way Swixml
 * deals with objects it has instatiated. It therefore deserves its very own
 * test class.
 */
public class ButtonGroupTest extends TestCase {

    public static final String DESCRIPTOR = "samples/swing/xml/mappings.xml";
    private Container          container;
    private TwiXML             se;

    private JRadioButton       am, fm;
    private ButtonGroup        radio;

    public ButtonGroupTest () {
        super ("Test the Mapping and behavior of a ButtonGroup.");
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
        this.se = new TwiXML (this);
        this.container = (Container) this.se.render (MappingTest.DESCRIPTOR);
    }

    /**
     * Clears the container
     */
    public void teardown () {
        this.se = null;
        this.container.removeAll ();
        this.container = null;
    }

    /**
     * Tests if the Fields were correctly initialized/mapped by the SwingEngine.
     */
    public void testMapping () {
        TestCase.assertTrue (
                "IDMap's EntrySet needs to contain the ButtonGroup's ID",
                this.se.getIdMap ().containsKey ("radio"));
        TestCase.assertNotNull (
                "Private Field, whose names have a matching ids the the XML descriptor should be initializd by the SwingEngine.",
                this.radio);
        TestCase.assertNotNull (
                "Public Fields, whose names have matching ids the the XML descriptor should be initializd by the SwingEngine.",
                this.am);
        TestCase.assertNotNull (
                "Public Fields, whose names have matching ids the the XML descriptor should be initializd by the SwingEngine.",
                this.fm);
    }

    /**
     * Test that the buttons are actually being added into the button group
     */
    public void testStructure () {
        final Enumeration<?> e = this.radio.getElements ();
        while (e.hasMoreElements ()) {
            final Object obj = e.nextElement ();
            TestCase.assertTrue (this.am.equals (obj) ^ this.fm.equals (obj));
        }
        TestCase.assertEquals ("There should be two buttons in this group",
                this.radio.getButtonCount (), 2);
    }

    /**
     * Test that one and only one of the buttons in the group can be selected
     */
    public void testXOR () {
        this.am.setSelected (true);
        TestCase.assertTrue (this.am.isSelected ());
        TestCase.assertFalse (
                "Only one RadioButton in the group should be flagged selected.",
                this.fm.isSelected ());
        this.fm.setSelected (true);
        TestCase.assertFalse (
                "Only one RadioButton in the group should be flagged selected.",
                this.am.isSelected ());
        TestCase.assertTrue (this.fm.isSelected ());
    }
}
