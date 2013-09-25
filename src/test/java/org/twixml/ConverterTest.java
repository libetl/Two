/*--
 $Id: ExtMappingTest.java,v 1.1 2005/06/01 00:02:43 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.twixml.AppConstants;
import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Test class running converter related tests.
 * 
 * @author Wolf Paulus
 */
public class ConverterTest extends TestCase {

	private static final String	DESCRIPTOR	= "samples/swing/xml/converter.xml";
	private Container	        container;
	private JPanel	            pnl;	                           // auto bound
	                                                               // through
	                                                               // Swixml

	public ConverterTest () {
		super ("Test converter related things");
	}

	public ConverterTest (String s) {
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
		AppConstants.DEBUG_MODE = true;
		final TwiXML se = new TwiXML (this);
		this.container = (Container) se.render (ConverterTest.DESCRIPTOR);

	}

	/**
	 * Clears the container
	 */
	public void teardown () {
		this.container.removeAll ();
		this.container = null;
	}

	/**
	 * Tests the BorderConverter
	 */
	public void testBorderConverter () {
		TestCase.assertNotNull ("JPanel pnl auto bound through Swixml",
		        this.pnl);
		final Border border = this.pnl.getBorder ();
		TestCase.assertNotNull (
		        "panel elem. has a border attribute def. in the XML Descriptor",
		        border);
		TestCase.assertTrue (
		        "XML Descriptor declared a TitledBorder for this Panel", border
		                .getClass ().isAssignableFrom (TitledBorder.class));
		final TitledBorder tb = (TitledBorder) border;
		TestCase.assertEquals ("Title like set in XML", "myTitle",
		        tb.getTitle ());
		TestCase.assertEquals ("Title Font like set in XML",
		        Font.decode ("VERDANA-BOLD-18"), tb.getTitleFont ());
		TestCase.assertEquals ("Title Justification like set in XML",
		        TitledBorder.CENTER, tb.getTitleJustification ());
		TestCase.assertEquals ("Title Position like set in XML",
		        TitledBorder.BELOW_BOTTOM, tb.getTitlePosition ());
	}
}
