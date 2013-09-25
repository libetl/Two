/*--
 $Id: ExtMappingTest.java,v 1.1 2005/06/01 00:02:43 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import javax.swing.JButton;

import junit.framework.TestCase;

/**
 * Automatic mapping may have to deal with class hierarchies as well. This Test
 * class extends the MappingTest, which already defines some members that need
 * to be mapped.
 * 
 * @author Wolf Paulus
 */
public class ExtMappingTest extends MappingTest {

	public JButton	b1;
	private JButton	b2;
	private JButton	b5;

	public ExtMappingTest () {
	}

	public ExtMappingTest (String s) {
		super (s);
	}

	@Override
	public void setUp () throws Exception {
		super.setUp ();
	}

	@Override
	public void teardown () {
		super.teardown ();
	}

	/**
	 * Tests if the JButtons were correctly initialized/mapped by the
	 * SwingEngine.
	 */
	@Override
	public void testMappingPublicFields () {
		TestCase.assertNotNull (
		        "Public Fields, whose names have matching ids the the XML descriptor should be initialized by the SwingEngine.",
		        this.b1);
		TestCase.assertNotNull (
		        "Public Fields, whose names have matching ids the the XML descriptor should be initialized by the SwingEngine.",
		        super.b1);
		TestCase.assertNotNull (
		        "Private Field, whose names have a matching ids the the XML descriptor should be initialized by the SwingEngine.",
		        this.b2);
		TestCase.assertNotNull (
		        "Private Field, whose names have a matching ids the the XML descriptor should be initialized by the SwingEngine.",
		        this.b5);
	}
}
