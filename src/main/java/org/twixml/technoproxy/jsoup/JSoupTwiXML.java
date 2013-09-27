package org.twixml.technoproxy.jsoup;

import org.twixml.TwiXML;
import org.twixml.technoproxy.jsoup.layout.LayoutManager;

/**
 * The SwingTwiXML is the main class adapter for the JsoupUnit. Usage of this
 * class is not mandatory.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.5 $
 */
public class JSoupTwiXML
        extends
        TwiXML<org.jsoup.nodes.Document, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, org.jsoup.nodes.Element, LayoutManager> {

    public JSoupTwiXML () {
        super ();
    }

    public JSoupTwiXML (final Object o) {
        super (o);
    }

    public JSoupTwiXML (final String s) {
        super (s);
    }
}
