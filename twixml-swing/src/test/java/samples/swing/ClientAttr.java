package samples.swing;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.twixml.TwiXML;
import org.twixml.technoproxy.swing.SwingTwiXML;

/**
 * The ClientAttr shows in the usage of client attributes in twixml tags.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml 0.98
 */
public class ClientAttr extends WindowAdapter {
    public static void main (final String [] args) {
        new ClientAttr ();
    }

    private final TwiXML swix = new SwingTwiXML (this);
    public JButton       btn;
    public JTextArea     ta;

    public Action        show = new AbstractAction () {
                                  /**
         * 
         */
                                  private static final long serialVersionUID = -4764666452966921115L;

                                  @Override
                                  public void actionPerformed (
                                          final ActionEvent e) {
                                      ClientAttr.this.ta.setText ("X:"
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
            ((JFrame) this.swix.render ("samples/swing/xml/clientattr.xml"))
                    .setVisible (true);
            this.swix.deleteViewId ("x");
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }
}