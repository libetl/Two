package samples.swing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

/**
 * Combobox Model used in the InitClass<?> sample.
 */
public class ComboModel extends DefaultComboBoxModel<String> implements
        Iterable<String> {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6296702257529721075L;

	/**
	 * Constructs a DefaultComboBoxModel object.
	 */
	public ComboModel () {
		super (new String [] { "Bird", "Cat", "Dog", "Rabbit", "Pig" });
	}

	@Override
	public Iterator<String> iterator () {
		final List<String> tmp = new LinkedList<String> ();
		for (int i = 0; i < this.getSize (); i++) {
			tmp.add (this.getElementAt (i));
		}
		return tmp.iterator ();
	}
}
