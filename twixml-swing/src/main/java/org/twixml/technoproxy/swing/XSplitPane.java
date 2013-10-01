/*--
 $Id: XSplitPane.java,v 1.1 2004/03/01 07:56:09 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.technoproxy.swing;

import javax.swing.JSplitPane;

/**
 * XSplitPane simply extends JSplitPane to clear components during the
 * construction process
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */

public class XSplitPane extends JSplitPane {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2815017707349201336L;

    public XSplitPane () {
        super (JSplitPane.HORIZONTAL_SPLIT, null, null);
        this.setTopComponent (null);
        this.setBottomComponent (null);
    }
}
