package samples.swing;

import java.awt.Container;
import java.awt.event.WindowAdapter;

import org.twixml.TwiXML;
import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * The XInclude class shows in simple way how to use xml includes.
 * <code>XInclude</code> extends the WindowAdapter and uses a SwingEngine to
 * renders the GUI.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 0.95
 */
public class XInclude extends WindowAdapter {
    public static void main (final String [] args) {
        new XInclude ();
    }

    private final TwiXML swix = new SwingTwiXML (this);

    private XInclude () {
        try {
            ((Container) this.swix.render ("samples/swing/xml/xinclude.xml"))
                    .setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}
