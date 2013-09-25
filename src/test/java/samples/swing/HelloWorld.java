package samples.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.twixml.TwiXML;

public class HelloWorld {
	/** Makes the class bootable */
	public static void main (String [] args) throws Exception {
		new HelloWorld ();
	}

	/** submit counter */
	private int	      clicks;

	/**
	 * JTextField member gets instantiated through Swixml (look for id="tf" in
	 * xml descriptor)
	 */
	public JTextField	tf;

	/** Jlabel to display number of button clicks */
	public JLabel	  cnt;

	/** Action appends a '#' to the textfields content. */
	public Action	  submit	= new AbstractAction () {
		                         /**
		 * 
		 */
		                         private static final long	serialVersionUID	= 8700061266817868946L;

		                         @Override
		                         public void actionPerformed (ActionEvent e) {
			                         HelloWorld.this.tf
			                                 .setText (HelloWorld.this.tf
			                                         .getText () + '#');
			                         HelloWorld.this.cnt
			                                 .setText (String
			                                         .valueOf (++HelloWorld.this.clicks));
		                         }
	                         };

	/** Renders UI at construction */
	private HelloWorld () throws Exception {
		((JFrame) new TwiXML (this).render ("samples/swing/xml/helloworld.xml"))
		        .setVisible (true);
	}
}
