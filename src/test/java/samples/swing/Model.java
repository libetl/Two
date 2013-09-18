package samples.swing;

import javax.swing.DefaultListModel;

public class Model extends DefaultListModel<String> {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8207372369194673809L;

	/**
	 * Constructs a DefaultComboBoxModel object.
	 */
	public Model () {
		for (final String s : new String [] { "Bird", "Cat", "Dog", "Rabbit",
		        "Pig" }) {
			this.addElement (s);
		}
	}
}