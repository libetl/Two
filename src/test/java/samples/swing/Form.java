package samples.swing;

import java.awt.Container;

import org.swixml.SwingEngine;

/**
 * The Form class shows how to do a simple JGoodies FormLayout
 */
public class Form extends SwingEngine {
	public static void main (String [] args) {
		new Form ();
	}

	/** Default ctor for a SwingEngine. */

	private Form () {
		try {
			((Container) this.render ("xml/form.xml")).setVisible (true);
		} catch (final Exception e) {
			e.printStackTrace ();
		}
	}
}