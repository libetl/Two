package samples.swing;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.twixml.TwiXML;

/**
 * The <code>Cards</code> class shows an example for the usage of a CardLayout.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * 
 * @since swixml #109
 */
public class Cards {

    private static final String DESCRIPTOR = "samples/swing/xml/cards.xml";

    public static void main (String [] args) {
        try {
            new Cards ();
        } catch (final Exception e) {
            System.err.println (e.getMessage ());
        }
    }

    private final TwiXML swix       = new TwiXML (this);

    /** panel with a CardLayout */
    public JPanel        pnl;

    /** shows the next card */
    public Action        nextAction = new AbstractAction () {
                                        /**
         * 
         */
                                        private static final long serialVersionUID = 7726724582953555227L;

                                        @Override
                                        public void actionPerformed (
                                                ActionEvent e) {
                                            final CardLayout cl = (CardLayout) (Cards.this.pnl
                                                    .getLayout ());
                                            cl.next (Cards.this.pnl);
                                        }
                                    };

    /** shows the card with the id requested in the actioncommand */
    public Action        showAction = new AbstractAction () {
                                        /**
         * 
         */
                                        private static final long serialVersionUID = 4644320744775529825L;

                                        @Override
                                        public void actionPerformed (
                                                ActionEvent e) {
                                            // System.err.println(
                                            // "ActionCommand=" +
                                            // e.getActionCommand() );
                                            final CardLayout cl = (CardLayout) (Cards.this.pnl
                                                    .getLayout ());
                                            if (e != null) {
                                                cl.show (Cards.this.pnl,
                                                        e.getActionCommand ());
                                            }
                                        }
                                    };

    private Cards () throws Exception {
        ((JFrame) this.swix.render (Cards.DESCRIPTOR)).setVisible (true);
        this.showAction.actionPerformed (null);
    }

}
