/*--
 $Id: MenuTest.java,v 1.1 2005/06/04 22:24:06 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials provided
 with the distribution.

 3. The end-user documentation included with the redistribution,
 if any, must include the following acknowledgment:
        "This product includes software developed by the
         SWIXML Project (http://www.swixml.org/)."
 Alternately, this acknowledgment may appear in the software itself,
 if and wherever such third-party acknowledgments normally appear.

 4. The name "Swixml" must not be used to endorse or promote products
 derived from this software without prior written permission. For
 written permission, please contact <info_AT_swixml_DOT_org>

 5. Products derived from this software may not be called "Swixml",
 nor may "Swixml" appear in their name, without prior written
 permission from the Swixml Project Management.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE SWIXML PROJECT OR ITS
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 individuals on behalf of the Swixml Project and was originally
 created by Wolf Paulus <wolf_AT_swixml_DOT_org>. For more information
 on the Swixml Project, please see <http://www.swixml.org/>.
 */
package org.swixml;

import java.awt.Component;
import java.awt.Container;

import junit.framework.TestCase;

/**
 * Test for some JMenuBar specialties.
 */
public class MenuTest extends TestCase {
	public static final String	DESCRIPTOR	= "xml/dialog.xml";
	private Container	       container;
	private SwingEngine	       se;

	public MenuTest () {
		super ("Test inserting a Menu into a container.");
	}

	public MenuTest (String s) {
		super (s);
	}

	@Override
	public void setUp () throws Exception {
		this.se = new SwingEngine (this);
		this.container = (Container) this.se.render (MenuTest.DESCRIPTOR);
	}

	/**
	 * Clears the container
	 */
	public void teardown () {
		this.container.removeAll ();
		this.container = null;
	}

	/**
	 * Tests if a JMenubar is added into a container, even if the container
	 * doesn't provide a setJMenuBar() method.
	 */
	public void testInclusioin () {
		final Component menubar = (Component) this.se.find ("menubar");
		TestCase.assertNotNull (
		        "<menubar> tag in the descriptor requires the instantiation of a JMenuBar obj.",
		        menubar);
		TestCase.assertNotNull (
		        "Since <menubar> is not the root tag, it needs a parent container",
		        menubar.getParent ());
	}
}
