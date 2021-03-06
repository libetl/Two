package samples.swing;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.twixml.technoproxy.swing.SwingTwiXML;

public class HelloWorldnoAction {
    /**
     * Makes the class bootable
     */
    public static void main (final String [] args) throws Exception {
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
        ((JFrame) new SwingTwiXML (this)
                .render ("samples/swing/xml/helloworld.xml")).setVisible (true);
    }

    /**
     * bound, using an element's action attribute, which was set to submit.
     */
    public void submit () {

        this.tf.setText (this.tf.getText () + '#');
        this.cnt.setText (String.valueOf (++this.clicks));
    }
}
