package org.twixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import org.twixml.TwiXML;

/**
 * The SwingTwiXML is the main class adapter for the SwingUnit. Usage of this
 * class is not mandatory.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.5 $
 */
public class SwingTwiXML
        extends
        TwiXML<Container, Component, ActionListener, JLabel, ButtonGroup, LayoutManager> {

    public SwingTwiXML () {
        super ();
    }

    public SwingTwiXML (final Object o) {
        super (o);
    }

    public SwingTwiXML (final String s) {
        super (s);
    }
}
