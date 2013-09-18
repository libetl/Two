package samples;

import java.util.TimeZone;

import org.swixml.ConverterLibrary;
import org.swixml.SwingEngine;

/**
 * Extend the TagLib with a new Class<?> and a new Converter
 */
public class NewTag extends SwingEngine {

    public static void main (String [] args) {
        new NewTag ();
    }

    private NewTag () {
        //
        // Register a new new Converter,
        // Generally, Converters should be regsitered before Tags
        //
        ConverterLibrary.getInstance ().register (TimeZone.class,
                new TimeZoneConverter ());
        //
        // Register a Tag that uses a SwingEngine itself ...
        //
        this.getTaglib ().registerTag ("xpanel", XPanel.class);
        try {
            this.getTaglib ().registerTag ("redlabel", RedLabel.class);
        } catch (final Exception e) {
            System.err.println (e.getMessage ());
        }

        try {
            this.render ("xml/newtag.xml").setVisible (true);
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}