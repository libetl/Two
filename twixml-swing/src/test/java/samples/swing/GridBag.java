package samples.swing;

import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * The GridBag class shows how to do a simple GridBag layout
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 0.5
 */
public class GridBag {
    public static void main (final String [] args) {
        new GridBag ();
    }

    /** Default ctor for a SwingEngine. */

    private GridBag () {
        try {
            new SwingTwiXML ().render ("samples/swing/xml/gridbag.xml")
                    .setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}