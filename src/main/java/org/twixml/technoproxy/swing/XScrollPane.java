/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import java.awt.Color;

import javax.swing.JScrollPane;

/**
 * XScrollPane simply extends JScrollpane to allow setting the backgroundcolor
 * during the construction process
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @since Swixml 1.5 #147
 */
public class XScrollPane extends JScrollPane {
    /**
	 * 
	 */
    private static final long serialVersionUID = -3724577295933384431L;

    /**
     * Sets the background color of this component's viewport.
     * 
     * @param bg
     *            the desired background <code>Color</code>, the background
     *            color of the viewport.
     * @see java.awt.Component#getBackground
     */
    @Override
    public void setBackground (Color bg) {
        super.getViewport ().setBackground (bg);
    }
}
