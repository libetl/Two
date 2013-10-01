package org.twixml;

import junit.framework.TestCase;

import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * Test class running include related tests.
 * 
 * @author <a href="mailto:alex73mail@gmail.com">Alex Buloichik</a>
 */
public class IncludeTest extends TestCase {
    public static final String DESCRIPTOR = "samples/swing/xml/include.xml";

    public IncludeTest () {
        AppConstants.DEBUG_MODE = true;
    }

    public void testCreate () throws Exception {
        new SwingTwiXML (this).render (IncludeTest.DESCRIPTOR);
    }
}
