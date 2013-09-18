package samples.swing;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.swixml.SwingEngine;

public class HelloWorldnoAction {
    /**
     * Makes the class bootable
     */
    public static void main (String [] args) throws Exception {
        new HelloWorldnoAction ();
    }

    /**
     * submit counter
     */
    private int       clicks;

    /**
     * JTextField member gets instantiated through Swixml (look for id="tf" in
     * xml descriptor)
     */
    public JTextField tf;

    /**
     * Jlabel to display number of button clicks
     */
    public JLabel     cnt;

    /**
     * Renders UI at construction
     */
    private HelloWorldnoAction () throws Exception {
        ((JComponent) new SwingEngine (this).render ("xml/helloworld.xml")).setVisible (true);
    }

    /**
     * bound, using an element's action attribute, which was set to submit.
     */
    public void submit () {

        this.tf.setText (this.tf.getText () + '#');
        this.cnt.setText (String.valueOf (++this.clicks));
    }
}
