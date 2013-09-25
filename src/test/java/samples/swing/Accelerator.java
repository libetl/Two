package samples.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import org.twixml.TwiXML;

/**
 * The Accelerator shows in the usage of accelerators.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml (#101)
 */
public class Accelerator {
	private static final String	DESCRIPTOR	= "samples/swing/xml/accelerator.xml";

	public static void main (String [] args) {
		try {
			new Accelerator ();
		} catch (final Exception e) {
			System.err.println (e.getMessage ());
		}
	}

	TwiXML	  swix	        = new TwiXML (this);

	public Action	newAction	= new AbstractAction () {
		                            /**
         * 
         */
		                            private static final long	serialVersionUID	= -6521606410982495882L;

		                            @Override
		                            public void actionPerformed (ActionEvent e) {
			                            JOptionPane
			                                    .showMessageDialog (
			                                            (Component) Accelerator.this.swix
			                                                    .getRootComponent (),
			                                            "Sorry, not implemented yet.");
		                            }
	                            };

	public Action	aboutAction	= new AbstractAction () {
		                            /**
         * 
         */
		                            private static final long	serialVersionUID	= 4882366063782590844L;

		                            @Override
		                            public void actionPerformed (ActionEvent e) {
			                            JOptionPane
			                                    .showMessageDialog (
			                                            (Component) Accelerator.this.swix
			                                                    .getRootComponent (),
			                                            "This is the Accelerator Example.");
		                            }
	                            };

	public Accelerator () throws Exception {
		((Component) this.swix.render (Accelerator.DESCRIPTOR))
		        .setVisible (true);
	}

}
