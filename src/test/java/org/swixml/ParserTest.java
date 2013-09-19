package org.swixml;

import java.awt.Container;

import junit.framework.TestCase;

/**
 * Misc. Parser Tests
 */
public class ParserTest extends TestCase {
	public static final String	DESCRIPTOR	= "samples/swing/xml/parsethis.xml";
	private Container	       container;

	public ParserTest () {
		super ("Misc. Parser Tests ");
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
		AppConstants.DEBUG_MODE = true;
		final SwingEngine se = new SwingEngine (this);
		this.container = (Container) se.render (ParserTest.DESCRIPTOR);
	}

	/**
	 * Clears the container
	 */
	public void teardown () {
		this.container.removeAll ();
		this.container = null;
	}

	public void testParseWithoutException () {
		TestCase.assertNotNull (this.container);
	}
}
