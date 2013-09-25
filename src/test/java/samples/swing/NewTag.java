package samples.swing;

import java.util.TimeZone;

import javax.swing.JFrame;

import org.twixml.ConverterLibrary;
import org.twixml.TwiXML;
import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * Extend the TagLib with a new Class<?> and a new Converter
 */
public class NewTag {

    public static void main (final String [] args) {
        new NewTag ();
    }

    private final TwiXML twix;

    private NewTag () {
        this.twix = new SwingTwiXML ();
        //
        // Register a new new Converter,
        // Generally, Converters should be registered before Tags
        //
        ConverterLibrary.getInstance ().register (TimeZone.class,
                new TimeZoneConverter ());
        //
        // Register a Tag that uses a SwingEngine itself ...
        //
        this.twix.getTaglib ().registerTag ("xpanel", XPanel.class);
        try {
            this.twix.getTaglib ().registerTag ("redlabel", RedLabel.class);
        } catch (final Exception e) {
            System.err.println (e.getMessage ());
        }

        try {
            ((JFrame) this.twix.render ("samples/swing/xml/newtag.xml"))
                    .setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}
