/*--
 $Id: XGridBagConstraints.java,v 1.1 2004/03/01 07:56:08 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * <code>XGridBagConstraints</code> simple extends
 * <code>GridBagConstraints</code> to make bean style getters and setters
 * available for all public fields in the GridBagConstraints class.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public class XGridBagConstraints extends GridBagConstraints {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3913967818576057317L;

    public int getAnchor () {
        return this.anchor;
    }

    public int getFill () {
        return this.fill;
    }

    public int getGridheight () {
        return this.gridheight;
    }

    public int getGridwidth () {
        return this.gridwidth;
    }

    public int getGridx () {
        return this.gridx;
    }

    public int getGridy () {
        return this.gridy;
    }

    public Insets getInsets () {
        return this.insets;
    }

    public int getIpadx () {
        return this.ipadx;
    }

    public int getIpady () {
        return this.ipady;
    }

    public double getWeightx () {
        return this.weightx;
    }

    public double getWeighty () {
        return this.weighty;
    }

    public void setAnchor (int anchor) {
        this.anchor = anchor;
    }

    public void setFill (int fill) {
        this.fill = fill;
    }

    public void setGridheight (int gridheight) {
        this.gridheight = gridheight;
    }

    public void setGridwidth (int gridwidth) {
        this.gridwidth = gridwidth;
    }

    public void setGridx (int gridx) {
        this.gridx = gridx;
    }

    public void setGridy (int gridy) {
        this.gridy = gridy;
    }

    public void setInsets (Insets insets) {
        this.insets = insets;
    }

    public void setIpadx (int ipadx) {
        this.ipadx = ipadx;
    }

    public void setIpady (int ipady) {
        this.ipady = ipady;
    }

    public void setWeightx (double weightx) {
        this.weightx = weightx;
    }

    public void setWeighty (double weighty) {
        this.weighty = weighty;
    }
}
