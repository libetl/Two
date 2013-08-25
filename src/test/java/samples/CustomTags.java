package samples;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.swixml.SwingEngine;

import com.toedter.calendar.JCalendar;

public class CustomTags extends WindowAdapter {

	//
	// Make the class bootable
	//
	public static void main (String [] args) throws Exception {
		new CustomTags ();
	}

	public CustomTags () throws Exception {
		final SwingEngine swix = new SwingEngine (this);
		swix.getTaglib ().registerTag ("Calendar", JCalendar.class);
		swix.render ("xml/customtags.xml").setVisible (true);
	}

	/**
	 * Invoked when a window is in the process of being closed. The close
	 * operation can be overridden at this point.
	 */
	@Override
	public void windowClosing (WindowEvent e) {
		super.windowClosing (e);
		System.exit (0);
	}
}
