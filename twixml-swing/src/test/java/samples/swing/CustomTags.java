package samples.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.twixml.TwiXML;
import org.twixml.technoproxy.swing.SwingTwiXML;

import com.toedter.calendar.JCalendar;

public class CustomTags extends WindowAdapter {

    //
    // Make the class bootable
    //
    public static void main (final String [] args) throws Exception {
        new CustomTags ();
    }

    public CustomTags () throws Exception {
        final TwiXML swix = new SwingTwiXML (this);
        swix.getTaglib ().registerTag ("Calendar", JCalendar.class);
        ((JFrame) swix.render ("samples/swing/xml/customtags.xml"))
                .setVisible (true);
    }

    /**
     * Invoked when a window is in the process of being closed. The close
     * operation can be overridden at this point.
     */
    @Override
    public void windowClosing (final WindowEvent e) {
        super.windowClosing (e);
        System.exit (0);
    }
}
