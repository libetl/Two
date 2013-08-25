package samples;

import javax.swing.DefaultComboBoxModel;

/**
 * Combobox Model used in the InitClass sample.
 */
public class ComboModel extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6296702257529721075L;

	/**
	 * Constructs a DefaultComboBoxModel object.
	 */
	public ComboModel () {
		super (new Object [] { "Bird", "Cat", "Dog", "Rabbit", "Pig" });
	}
}
