/*--
 $Id: ExtMappingTest.java,v 1.1 2005/06/01 00:02:43 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.twixml.AppConstants;
import org.twixml.TwiXML;

import junit.framework.TestCase;

/**
 * Test class running id and refid related tests.
 * 
 * @author Wolf Paulus
 */
public class IdTest extends TestCase {
	public static final String	DESCRIPTOR	= "samples/swing/xml/id.xml";
	private JPanel	           pnl1, pnl11, pnl2;
	private JButton	           btn1, btn2, btn3;
	private boolean	           b1, b2;
	public Action	           a1	       = new AbstractAction () {
		                                       /**
         * 
         */
		                                       private static final long	serialVersionUID	= 6649960716395063704L;

		                                       @Override
		                                       public void actionPerformed (
		                                               ActionEvent e) {
			                                       IdTest.this.b1 = true;
		                                       }
	                                       };

	public IdTest () throws Exception {
		super ("ID Tests");
		AppConstants.DEBUG_MODE = true;
		new TwiXML (this).render (IdTest.DESCRIPTOR);
	}

	public void m1 () {
		this.b2 = true;
	}

	public void testRefId () {
		TestCase.assertNotNull (this.pnl1);
		TestCase.assertNotNull (this.pnl11);
		TestCase.assertNotNull (this.pnl2);

		TestCase.assertEquals (Color.red, this.pnl1.getBackground ());
		TestCase.assertEquals (this.pnl1.getBackground (),
		        this.pnl2.getBackground ());
		TestCase.assertNotSame (this.pnl1.getBackground (),
		        this.pnl11.getBackground ());
		TestCase.assertEquals (this.pnl1.getFont (), this.pnl11.getFont ());
		TestCase.assertNotSame (this.pnl1.getFont (), this.pnl2.getFont ());

		TestCase.assertNotNull (this.btn1.getAction ());
		TestCase.assertNotNull (this.btn2.getAction ());
		TestCase.assertNotNull (this.btn3.getAction ());

		TestCase.assertEquals (this.btn1.getAction (), this.btn3.getAction ());
		TestCase.assertNotSame (this.btn1.getAction (), this.btn2.getAction ());
		TestCase.assertFalse (this.b2);
		this.btn2.doClick ();
		TestCase.assertTrue (this.b2);
		TestCase.assertFalse (this.b1);
		this.btn3.doClick ();
		TestCase.assertTrue (this.b1);
	}
}
