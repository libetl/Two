package samples.swing;

import javax.swing.JFrame;

import org.twixml.TwiXML;

/**
 * The Form class shows how to do a simple JGoodies FormLayout
 */
public class Form extends TwiXML {
    public static void main (String [] args) {
        new Form ();
    }

    /** Default ctor for a SwingEngine. */

    private Form () {
        try {
            ((JFrame) this.render ("samples/swing/xml/form.xml"))
                    .setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}