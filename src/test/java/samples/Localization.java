package samples;

/**
 * Localization, also shows localization for the MAC OS
 *
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 *
 * @since swixml (#129)
 */
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.swixml.SwingEngine;

public class Localization extends WindowAdapter {

	private static final String	DESCRIPTOR	= "xml/localization.xml";

	public static void main (String [] args) {
		try {
			new Localization ();
		} catch (final Exception e) {
			System.err.println (e.getMessage ());
		}
	}

	SwingEngine	  swix	          = new SwingEngine (this);

	public Action	actionOptions	= new AbstractAction () {
		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              Localization.this.swix
			                                                      .getRootComponent (),
			                                              "Sorry, "
			                                                      + Localization.this.swix
			                                                              .getLocalizer ()
			                                                              .getString (
			                                                                      "mis_Options")
			                                                      + " not implemented yet.");
		                              }
	                              };

	public Action	actionAbout	  = new AbstractAction () {
		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              Localization.this.swix
			                                                      .getRootComponent (),
			                                              "This is the Mac OS X Example.");
		                              }
	                              };

	public Action	actionHelp	  = new AbstractAction () {
		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane.showMessageDialog (
			                                      Localization.this.swix
			                                              .getRootComponent (),
			                                      "Help ....");
		                              }
	                              };

	public Action	actionExit	  = new AbstractAction () {
		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane.showMessageDialog (
			                                      Localization.this.swix
			                                              .getRootComponent (),
			                                      Localization.this.swix
			                                              .getLocalizer ()
			                                              .getString (
			                                                      "mis_Exit"));
			                              Localization.this
			                                      .windowClosing (null);
		                              }
	                              };

	public Localization () throws Exception {
		this.swix.render (Localization.DESCRIPTOR).setVisible (true);
	}

	/**
	 * Invoked when a window is in the process of being closed. The close
	 * operation can be overridden at this point.
	 */
	@Override
	public void windowClosing (WindowEvent e) {
		super.windowClosing (e);
		System.exit (0);
	}

}
