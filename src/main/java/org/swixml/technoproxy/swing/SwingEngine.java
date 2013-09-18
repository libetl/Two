package org.swixml.technoproxy.swing;

import java.awt.Container;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.AbstractButton;

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
    
    /**
     * Recursively Sets an ActionListener
     * <p/>
     * 
     * <pre>
     *  Backtracking algorithm: if al was set for a child component, its not being set for its parent
     * </pre>.
     * 
     * @param c
     *            <code>Component</code> start component
     * @param al
     *            <code>ActionListener</code>
     * @return <code>boolean</code> true, if ActionListener was set.
     */
    public boolean setActionListener (final Component c, final ActionListener al) {
        boolean b = false;
        if (c != null) {
            if (Container.class.isAssignableFrom (c.getClass ())) {
                final Component [] s = ((Container) c).getComponents ();
                for (final Component value : s) {
                    b = b | this.setActionListener (value, al);
                }
            }
            if (!b) {
                if (JMenu.class.isAssignableFrom (c.getClass ())) {
                    final JMenu m = (JMenu) c;
                    final int k = m.getItemCount ();
                    for (int i = 0 ; i < k ; i++) {
                        b = b | this.setActionListener (m.getItem (i), al);
                    }
                } else if (AbstractButton.class
                        .isAssignableFrom (c.getClass ())) {
                    ((AbstractButton) c).addActionListener (al);
                    b = true;
                }
            }

        }
        return b;
    }
}
