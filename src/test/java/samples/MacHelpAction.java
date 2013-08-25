package samples;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import com.apple.eio.FileManager;

// $Id: MacHelpAction.java,v 1.1 2004/10/05 21:32:34 tichy Exp $

/**
 * Externalized help action taken from {@link HelloMac}.
 * 
 * @author $Author: tichy $
 */
public class MacHelpAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -46501717446085283L;

	@Override
	public void actionPerformed (ActionEvent e) {
		try {
			FileManager.openURL ("http://www.swixml.org/apidocs/index.html");
		} catch (final IOException e1) {
			e1.printStackTrace ();
		}
	}
}
