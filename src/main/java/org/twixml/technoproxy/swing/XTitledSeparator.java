/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.technoproxy.swing;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.jgoodies.forms.factories.DefaultComponentFactory;

/**
 * A wrapper class for JGoodies Forms
 * <code>ComponentFactory.createSeparator()</code>, which creates a titled
 * separator.
 * 
 * @author Karl Tauber
 */
public class XTitledSeparator extends JComponent {

    /**
	 * 
	 */
    private static final long serialVersionUID = 9175776683003666865L;
    private String            text;
    private int               alignment        = SwingConstants.LEFT;

    public XTitledSeparator () {
        super ();
        this.setLayout (new BorderLayout ());
        this.update ();
    }

    /**
     * Returns the title alignment. One of <code>SwingConstants.LEFT</code>,
     * <code>SwingConstants.CENTER</code> or <code>SwingConstants.RIGHT</code>.
     */
    public int getAlignment () {
        return this.alignment;
    }

    /**
     * Returns the title of the separator.
     */
    public String getText () {
        return this.text;
    }

    /**
     * Sets the title alignment. One of <code>SwingConstants.LEFT</code>,
     * <code>SwingConstants.CENTER</code> or <code>SwingConstants.RIGHT</code>.
     */
    public void setAlignment (int alignment) {
        this.alignment = alignment;
        this.update ();
    }

    /**
     * Sets the title of the separator.
     */
    public void setText (String text) {
        this.text = text;
        this.update ();
    }

    private void update () {
        this.removeAll ();
        this.add (DefaultComponentFactory.getInstance ().createSeparator (
                this.text, this.alignment));
    }
}
