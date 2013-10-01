package org.twixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import org.twixml.TwiXML;
import org.twixml.technoproxy.PlatformUnit;

/**
 * The SwingTwiXML is the main class adapter for the SwingUnit. Use this class
 * instead of TwiXML.
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

    @Override
    protected PlatformUnit getUnitInstance () {
        return new SwingUnit ();
    }
}
