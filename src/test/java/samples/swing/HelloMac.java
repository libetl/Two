package samples.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
public class HelloMac extends WindowAdapter {
    //
    // Make the class bootable
    //
    public static void main (String [] args) throws Exception {
        new HelloMac ();
    }

    private final TwiXML swix;

    public Action        actionAbout = new AbstractAction () {
                                         /**
         * 
         */
                                         private static final long serialVersionUID = 6954263354727979419L;

                                         @Override
                                         public void actionPerformed (
                                                 ActionEvent e) {
                                             JOptionPane
                                                     .showMessageDialog (
                                                             (Component) HelloMac.this.swix
                                                                     .getRootComponent (),
                                                             "This is the Mac OS X Example.");
                                         }
                                     };

    public Action        actionHelp  = new AbstractAction () {
                                         /**
         * 
         */
                                         private static final long serialVersionUID = -6068983298395296459L;

                                         @Override
                                         public void actionPerformed (
                                                 ActionEvent e) {
                                             try {
                                                 new java.net.URL (
                                                         "http://www.twixml.org/apidocs/index.html")
                                                         .getContent ();
                                             } catch (final IOException e1) {
                                                 e1.printStackTrace ();
                                             }
                                         }
                                     };

    public Action        actionExit  = new AbstractAction () {
                                         /**
         * 
         */
                                         private static final long serialVersionUID = 6132794727984088741L;

                                         @Override
                                         public void actionPerformed (
                                                 ActionEvent e) {
                                             JOptionPane
                                                     .showMessageDialog (
                                                             (Component) HelloMac.this.swix
                                                                     .getRootComponent (),
                                                             HelloMac.this.swix
                                                                     .getLocalizer ()
                                                                     .getString (
                                                                             "mis_Exit"));
                                             HelloMac.this.windowClosing (null);
                                         }
                                     };

    private HelloMac () throws Exception {
        this.swix = new TwiXML (this);
        this.swix.render ("samples/swing/xml/hellomac.xml");
        ((JFrame) this.swix.getRootComponent ()).setVisible (true);
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