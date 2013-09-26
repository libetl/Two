package samples.jsoup;

import org.twixml.technoproxy.jsoup.JSoupTwiXML;
import org.twixml.technoproxy.jsoup.SeeWebpage;

/**
 * The Layout class shows the use of layout managers
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml (#136)
 */
public class Layout {
    private static final String DESCRIPTOR = "samples/swing/xml/funlayout.xml";

    public static void main (final String [] args) {
        try {
            new Layout ();
        } catch (final Exception e) {
            System.err.println (e.getMessage ());
        }
    }

    private Layout () throws Exception {
        SeeWebpage.see (new JSoupTwiXML (this).render (Layout.DESCRIPTOR)
                .toString ());
    }
}
