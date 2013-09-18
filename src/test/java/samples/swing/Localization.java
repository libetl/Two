package samples.swing;

/**
 * Localization, also shows localization for the MAC OS
 *
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 *
 * @since swixml (#129)
 */
import java.awt.Component;
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
		                              /**
         * 
         */
		                              private static final long	serialVersionUID	= 890920772898342595L;

		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              (Component) Localization.this.swix
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
		                              /**
         * 
         */
		                              private static final long	serialVersionUID	= 2740809806068831299L;

		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              (Component) Localization.this.swix
			                                                      .getRootComponent (),
			                                              "This is the Mac OS X Example.");
		                              }
	                              };

	public Action	actionHelp	  = new AbstractAction () {
		                              /**
         * 
         */
		                              private static final long	serialVersionUID	= 4207507042461976117L;

		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              (Component) Localization.this.swix
			                                                      .getRootComponent (),
			                                              "Help ....");
		                              }
	                              };

	public Action	actionExit	  = new AbstractAction () {
		                              /**
         * 
         */
		                              private static final long	serialVersionUID	= -5765311360570438534L;

		                              @Override
		                              public void actionPerformed (ActionEvent e) {
			                              JOptionPane
			                                      .showMessageDialog (
			                                              (Component) Localization.this.swix
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
		((Component) this.swix.render (Localization.DESCRIPTOR))
		        .setVisible (true);
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
