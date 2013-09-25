package samples.swing;

import javax.swing.JFrame;

import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * The Form class shows how to do a simple JGoodies FormLayout. The previous
 * "extends SwingEngine" triggers a NullPointerException and is discouraged (it
 * is not a good Java practice).
 */
public class Form {
    public static void main (final String [] args) {
        new Form ();
    }

    /** Default ctor for a SwingEngine. */

    private Form () {
        super ();
        try {
            ((JFrame) new SwingTwiXML ().render ("samples/swing/xml/form.xml"))
                    .setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}