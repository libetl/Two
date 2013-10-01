/*--
 $Id: XGlue.java,v 1.1 2004/03/01 07:56:08 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import java.awt.Dimension;

import javax.swing.Box;

/**
 * Creates a Glue for BoxLayout.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public class XGlue extends Box.Filler {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5643904287609410375L;

    public XGlue () {
        super (new Dimension (0, 0), new Dimension (0, 0), new Dimension (
                Short.MAX_VALUE, Short.MAX_VALUE));
    }
}
