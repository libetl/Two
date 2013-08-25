package samples;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.swixml.SwingEngine;

/**
 * The Accelerator shows in the usage of accelerators.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml (#101)
 */
public class Accelerator {
	private static final String	DESCRIPTOR	= "xml/accelerator.xml";

	public static void main (String [] args) {
		try {
			new Accelerator ();
		} catch (final Exception e) {
			System.err.println (e.getMessage ());
		}
	}

	SwingEngine	  swix	        = new SwingEngine (this);

	public Action	newAction	= new AbstractAction () {
		                            @Override
		                            public void actionPerformed (ActionEvent e) {
			                            JOptionPane.showMessageDialog (
			                                    Accelerator.this.swix
			                                            .getRootComponent (),
			                                    "Sorry, not implemented yet.");
		                            }
	                            };

	public Action	aboutAction	= new AbstractAction () {
		                            @Override
		                            public void actionPerformed (ActionEvent e) {
			                            JOptionPane
			                                    .showMessageDialog (
			                                            Accelerator.this.swix
			                                                    .getRootComponent (),
			                                            "This is the Accelerator Example.");
		                            }
	                            };

	public Accelerator () throws Exception {
		this.swix.render (Accelerator.DESCRIPTOR).setVisible (true);
	}

}
