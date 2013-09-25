package org.twixml;

import org.twixml.AppConstants;
import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Test class running include related tests.
 * 
 * @author <a href="mailto:alex73mail@gmail.com">Alex Buloichik</a>
 */
public class IncludeTest extends TestCase {
	public static final String	DESCRIPTOR	= "samples/swing/xml/include.xml";

	public IncludeTest () {
		AppConstants.DEBUG_MODE = true;
	}

	public void testCreate () throws Exception {
		new TwiXML (this).render (IncludeTest.DESCRIPTOR);
	}
}
