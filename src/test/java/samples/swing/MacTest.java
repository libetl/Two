package samples.swing;

import java.awt.Container;
import java.awt.event.WindowAdapter;

import org.twixml.TwiXML;

/**
 * The HelloMac class shows a couple of the Mac specifics exposed
 * <code>HeeloMac</code> renders the GUI, which is described in hellomac.xml
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 1.1
 */
public class MacTest extends WindowAdapter {
	private static TwiXML	swix;

	/**
	 * @return
	 */
	public static TwiXML getSwix () {
		// TODO Auto-generated method stub
		return MacTest.swix;
	}

	//
	// Make the class bootable
	//
	public static void main (String [] args) throws Exception {
		new MacTest ();
	}

	private MacTest () throws Exception {
		MacTest.swix = new TwiXML (this);
		MacTest.swix.render ("samples/swing/xml/mactester.xml");
		((Container) MacTest.swix.getRootComponent ()).setVisible (true);
	}

}