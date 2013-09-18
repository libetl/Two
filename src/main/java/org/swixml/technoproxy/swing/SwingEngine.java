package org.swixml.technoproxy.swing;

import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JMenu;

import org.swixml.technoproxy.ProxyCode;

public class SwingEngine extends ProxyCode<org.swixml.SwingEngine<Container, Component, ActionListener>> {

    public SwingEngine (org.swixml.SwingEngine<Container, Component, ActionListener> source1) {
        super (source1);
    }

    public void traverse (final Component c,
            Collection<Component> collection) {
        if (c instanceof JMenu) {
            final JMenu m = (JMenu) c;
            final int k = m.getItemCount ();
            for (int i = 0 ; i < k ; i++) {
                this.getSource ().traverse (m.getItem (i), collection);
            }
        } else if (c instanceof Container) {
            final Component [] s = ((Container) c).getComponents ();
            for (final Component value : s) {
                this.traverse (value, collection);
            }
        }
    }
    
    public void cleanup (Object obj, List<String> zombies, String key) {
        if ( (obj instanceof Component)
                && ! ((Component) obj).isDisplayable ()) {
            zombies.add (key);
        }
    }
}
