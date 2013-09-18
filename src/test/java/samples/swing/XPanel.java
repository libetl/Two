package samples.swing;

import javax.swing.JPanel;

import org.swixml.SwingEngine;

/**
 * This file contains proprietary information of CarlsbadCubes Copying or
 * reproduction without prior written approval is prohibited. Copyright (c)
 * 2002-2003
 * 
 * 
 * 
 * Date: Feb 28, 2003
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since
 */

public class XPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1503710571522599188L;
    private final SwingEngine swix             = new SwingEngine (this);

    public void setXml (String resource) {
        try {
            this.swix.insert ("xml/" + resource, this);
        } catch (final Exception e) {
            System.err.println (e.getMessage ());
        }
    }
}
