package samples;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextArea;

import org.swixml.SwingEngine;

/**
 * The ClientAttr shows in the usage of client attributes in swixml tags.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 0.98
 */
public class ClientAttr extends WindowAdapter {
    public static void main (String [] args) {
        new ClientAttr ();
    }

    private final SwingEngine swix = new SwingEngine (this);
    public JButton            btn;
    public JTextArea          ta;

    public Action             show = new AbstractAction () {
                                       /**
         * 
         */
                                       private static final long serialVersionUID = -4764666452966921115L;

                                       @Override
                                       public void actionPerformed (
                                               ActionEvent e) {
                                           ClientAttr.this.ta
                                                   .setText ("X:"
                                                           + ClientAttr.this.btn
                                                                   .getClientProperty ("X")
                                                           + "\n"
                                                           + "Y:"
                                                           + ClientAttr.this.btn
                                                                   .getClientProperty ("Y"));
                                       }
                                   };

    private ClientAttr () {
        try {
            this.swix.render ("xml/clientattr.xml").setVisible (true);
            this.swix.forget ("x");
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}