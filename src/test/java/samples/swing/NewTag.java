package samples.swing;

import java.util.TimeZone;

import javax.swing.JFrame;

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
		// Generally, Converters should be registered before Tags
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
			((JFrame) this.render ("samples/swing/xml/newtag.xml")).setVisible (true);
		} catch (final Exception e) {
			e.printStackTrace ();
		}
	}
}
