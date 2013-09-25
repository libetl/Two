/*--
 $Id: LocalizationTest.java,v 1.2 2007/07/16 00:04:15 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Testcase for localization of Strings and comma separated lists of strings.
 */
public class LocalizationTest extends TestCase {
    public static final String DESCRIPTOR = "samples/swing/xml/local.xml";

    private JButton            btn1, btn2, btn3, btn4, btn5;              // set
                                                                           // through
                                                                           // Swixml
    private JTabbedPane        tpane;                                     // set
                                                                           // Through
                                                                           // Swixml
    private final Container    container;

    public LocalizationTest () throws Exception {
        this ("Test Localization of for key strings and comma separated keys");
    }

    public LocalizationTest (String s) throws Exception {
        super (s);
        this.container = (Container) new TwiXML (this)
                .render (LocalizationTest.DESCRIPTOR);
    }

    public void testLocalization () {
        TestCase.assertNotNull ("UI needs to be instantiated", this.container);
        TestCase.assertEquals ("Simple String Lookup should match",
                "Hello World", this.btn1.getText ());
        TestCase.assertEquals ("Simple String Lookup should match",
                "Hello, World", this.btn2.getText ());
        TestCase.assertEquals ("Simple String Lookup should match", "Alpha",
                this.btn3.getText ());
        TestCase.assertEquals ("Key without matching Value should return key",
                "abcdef0123456", this.btn4.getText ());
        TestCase.assertEquals ("Key without matching Value should return key",
                "abcdef,0123456", this.btn5.getText ());
    }

    public void testStringListLocalization () {
        TestCase.assertEquals ("TabbedPane with 3 tabs expected", 3,
                this.tpane.getTabCount ());
        TestCase.assertEquals ("Tab Title was set through Swixml", "Alpha",
                this.tpane.getTitleAt (0));
        TestCase.assertEquals ("Tab Title was set through Swixml", "Bravo",
                this.tpane.getTitleAt (1));
        TestCase.assertEquals ("Tab Title was set through Swixml", "Charlie",
                this.tpane.getTitleAt (2));
    }

}
