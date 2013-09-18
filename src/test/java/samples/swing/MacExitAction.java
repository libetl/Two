package samples.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

// $Id: MacExitAction.java,v 1.1 2004/10/05 21:32:34 tichy Exp $

/**
 * Externalized exit action taken from {@link HelloMac}.
 * 
 * @author $Author: tichy $
 */
public class MacExitAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4901187209372755308L;

	@Override
	public void actionPerformed (ActionEvent e) {
		JOptionPane.showMessageDialog ((Component) MacTest.getSwix ()
		        .getRootComponent (), MacTest.getSwix ().getLocalizer ()
		        .getString ("mis_Exit"));
		System.exit (0);
	}
}
