/*--
 $Id: ExtMappingTest.java,v 1.1 2005/06/01 00:02:43 wolfpaulus Exp $

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

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import junit.framework.TestCase;

/**
 * Test class running id and refid related tests.
 * 
 * @author Wolf Paulus
 */
public class IdTest extends TestCase {
    public static final String DESCRIPTOR = "xml/id.xml";
    private JPanel             pnl1, pnl11, pnl2;
    private JButton            btn1, btn2, btn3;
    private boolean            b1, b2;
    public Action              a1         = new AbstractAction () {
                                              /**
         * 
         */
                                              private static final long serialVersionUID = 6649960716395063704L;

                                              @Override
                                              public void actionPerformed (
                                                      ActionEvent e) {
                                                  IdTest.this.b1 = true;
                                              }
                                          };

    public IdTest () throws Exception {
        super ("ID Tests");
        SwingEngine.DEBUG_MODE = true;
        new SwingEngine (this).render (IdTest.DESCRIPTOR);
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
