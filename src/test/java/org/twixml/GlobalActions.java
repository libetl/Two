/*--
 $Id: ExtMappingTest.java,v 1.1 2005/06/01 00:02:43 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 * Interface containg action implementation for broader use, e.g. in multiple
 * frames, dialog.
 * 
 * @author Wolf Paulus
 */
public interface GlobalActions {
	Action	quitAction	= new AbstractAction () {
		                    /**
		 * 
		 */
		                    private static final long	serialVersionUID	= -838048201909219582L;

		                    @Override
		                    public void actionPerformed (ActionEvent arg0) {
			                    // Quit the program...
			                    System.exit (0);
		                    }
	                    };

	Action	aboutAction	= new AbstractAction () {
		                    /**
         * 
         */
		                    private static final long	serialVersionUID	= -4358489161049932137L;

		                    @Override
		                    public void actionPerformed (ActionEvent arg0) {
			                    // show 'About Application' dialog
			                    JOptionPane.showMessageDialog (null,
			                            "Test Case");
		                    }
	                    };
}
