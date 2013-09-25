package org.twixml.technoproxy.jsoup;

import java.util.Collection;
import java.util.List;

import org.twixml.technoproxy.ProxyCode;

public class TwiXML
        extends
        ProxyCode<org.twixml.TwiXML<Object, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, Object, Object>> {

    public TwiXML (
            org.twixml.TwiXML<Object, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, Object, Object> source1) {
        super (source1);
    }

    public void cleanup (Object obj, List<String> zombies, String key) {
        if ( (obj instanceof org.jsoup.nodes.Element)
                && ! ((org.jsoup.nodes.Element) obj).hasClass ("visible")) {
            zombies.add (key);
        }
    }

    /**
     * Recursively Sets an ActionListener
     * <p/>
     * 
     * <pre>
     *  Backtracking algorithm: if all was set for a child org.jsoup.nodes.Element, its not being set for its parent
     * </pre>.
     * 
     * @param c
     *            <code>org.jsoup.nodes.Element</code> start
     *            org.jsoup.nodes.Element
     * @param al
     *            <code>ActionListener</code>
     * @return <code>boolean</code> true, if ActionListener was set.
     */
    public boolean setActionListener (final org.jsoup.nodes.Element c,
            final ActionListener al) {
        boolean b = false;
        if (c != null) {
            if (c.hasClass ("container")) {
                final org.jsoup.select.Elements s = c
                        .getAllElements ();
                for (final org.jsoup.nodes.Element value : s) {
                    b = b | this.setActionListener (value, al);
                }
            }
            if (!b) {
                if (c.hasClass ("menu")) {
                    final int k = c.childNodeSize ();
                    for (int i = 0 ; i < k ; i++) {
                        b = b | this.setActionListener (c.child (i), al);
                    }
                } else if (c.hasClass ("button")) {
                    c.attr ("onclick", "javascript:"
                            + al.getClass ().getSimpleName () + " (e)");
                    b = true;
                }
            }

        }
        return b;
    }

    public void traverse (final org.jsoup.nodes.Element c,
            Collection<org.jsoup.nodes.Element> collection) {
        if (c.hasClass ("menu")) {
            final int k = c.childNodeSize ();
            for (int i = 0 ; i < k ; i++) {
                this.getSource ().traverse (c.child (i), collection);
            }
        } else if (c.hasClass ("container")) {
            final org.jsoup.select.Elements s = c.getAllElements ();
            for (final org.jsoup.nodes.Element value : s) {
                this.traverse (value, collection);
            }
        }
    }
}
