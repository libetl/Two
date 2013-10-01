/*--
 $Id: XVBox.java,v 1.1 2004/03/01 07:56:09 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * XVBox simply extends the Box container but ceates a vertical Box.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public class XVBox extends Box {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6346497698578934314L;

    public XVBox () {
        super (BoxLayout.Y_AXIS);
    }
}
