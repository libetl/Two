package org.twixml;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import org.twixml.TwiXML;

import junit.framework.TestCase;

public class ActionGeneratorTest extends TestCase {

	public static final String	DESCRIPTOR	= "samples/swing/xml/action.xml";
	private Container	       container;
	private JButton	           btn1, btn2;
	private int	               counter;

	public Action	           submitAction	= new AbstractAction () {
		                                        /**
		 * 
		 */
		                                        private static final long	serialVersionUID	= -4851338071202845668L;

		                                        @Override
		                                        public void actionPerformed (
		                                                ActionEvent e) {
			                                        ActionGeneratorTest.this
			                                                .submit ();
		                                        }
	                                        };

	public ActionGeneratorTest () {
		super ("Test auto generation of Action wrappers");
	}

	public ActionGeneratorTest (String s) {
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
		this.container = (Container) se.render (ActionGeneratorTest.DESCRIPTOR);
	}

	public void submit () {
		this.counter++;
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
	public void testMapping () {
		TestCase.assertNotNull (
		        "JButton should have been mapped to private fields.", this.btn1);
		TestCase.assertNotNull (
		        "JButton should have been mapped to private fields.", this.btn2);
		TestCase.assertNotNull (
		        "Action should have been mapped to the JButton.",
		        this.btn1.getAction ());
		TestCase.assertNotNull (
		        "Action should have been generated and mapped to the JButton.",
		        this.btn2.getAction ());

		int i = this.counter;
		this.btn1.getAction ().actionPerformed (null);
		TestCase.assertEquals (
		        "Action should have been called and exec. correnctly", ++i,
		        this.counter);
		this.btn2.getAction ().actionPerformed (null);
		TestCase.assertEquals (
		        "Action should have been generated and wrap a client method",
		        ++i, this.counter);
	}
}
