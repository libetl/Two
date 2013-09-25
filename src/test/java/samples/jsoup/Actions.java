package samples.jsoup;

import java.lang.reflect.InvocationTargetException;

import org.twixml.AppConstants;
import org.twixml.TwiXML;
import org.twixml.technoproxy.jsoup.JSoupTwiXML;
import org.twixml.technoproxy.jsoup.SeeWebpage;

/**
 * Twixml is magic. Change only the Impl object to display the result in a
 * JFrame or in a browser.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml #065
 */
public class Actions {
    public static void main (final String [] args) {
        AppConstants.DEBUG_MODE = true;
        new Actions (true);
    }

    private TwiXML swix;
    public Object  mi_exit, mi_save;

    public Object  pnl_North;
    //
    // For every Actions, there needs to be a
    // public AbstractAction member variables with an anonymous inner class
    // instantiation
    //

    /** <code>Action</code> newAction handles the <em>new</em> action attribute */
    public Object  newAction  = new Object () {

                                  public void actionPerformed (final Object e) {
                                      System.out.println ("New"); // show
                                                                  // the
                                                                  // access
                                                                  // outer
                                                                  // class
                                                                  // member
                                                                  // ..
                                      try {
                                          final Object o = e.getClass ()
                                                  .getMethod ("getSource")
                                                  .invoke (e);
                                          o.getClass ()
                                                  .getMethod ("setEnabled",
                                                          boolean.class)
                                                  .invoke (o, false);
                                      } catch (IllegalAccessException
                                              | IllegalArgumentException
                                              | InvocationTargetException
                                              | NoSuchMethodException
                                              | SecurityException e1) {
                                      }
                                  }
                              };

    /**
     * <code>Action</code> modifyAction handles the <em>modify</em> action
     * attribute
     */
    public Object  openAction = new Object () {

                                  /** Invoked when an action occurs. */
                                  public void actionPerformed (final Object e) {
                                      System.out.println ("Open");
                                  }
                              };

    /** <code>Action</code> petAction handles the <em>combobox</em> */
    public Object  petAction  = new Object () {

                                  public void actionPerformed (final Object e) {
                                      try {
                                          final Object o = e.getClass ()
                                                  .getMethod ("getSource")
                                                  .invoke (e);
                                          if (o instanceof javax.swing.JComboBox<?>) {
                                              System.out
                                                      .println ( ((javax.swing.JComboBox<?>) o)
                                                              .getSelectedItem ()
                                                              .toString ());
                                          }
                                      } catch (IllegalAccessException
                                              | IllegalArgumentException
                                              | InvocationTargetException
                                              | NoSuchMethodException
                                              | SecurityException e1) {
                                          e1.printStackTrace ();
                                      }
                                  }
                              };

    //
    // Implement ActionListener
    //

    /**
     * Constructs a new Actions object, registering action handlers for
     * center_panel components.
     */
    private Actions (final boolean start) {
        try {
            this.swix = new JSoupTwiXML ();

            // Make all Actions implements the adequate superclass quietly
            this.newAction = this.swix.getTypeAnalyser ()
                    .addMethodDelegateSingleParameter (
                            this.newAction.getClass (),
                            "actionPerformed",
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "ActionEvent"));
            this.newAction = this.swix.getTypeAnalyser ()
                    .makeFieldExtendsClass (
                            this.newAction.getClass (),
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "Action"));

            this.openAction = this.swix.getTypeAnalyser ()
                    .addMethodDelegateSingleParameter (
                            this.openAction.getClass (),
                            "actionPerformed",
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "ActionEvent"));
            this.openAction = this.swix.getTypeAnalyser ()
                    .makeFieldExtendsClass (
                            this.openAction.getClass (),
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "Action"));

            this.petAction = this.swix.getTypeAnalyser ()
                    .addMethodDelegateSingleParameter (
                            this.petAction.getClass (),
                            "actionPerformed",
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "ActionEvent"));
            this.petAction = this.swix.getTypeAnalyser ()
                    .makeFieldExtendsClass (
                            this.petAction.getClass (),
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "Action"));

            // Duplicate "this" to have a compliant ActionListener object
            Object newActionsObject = this.swix.getTypeAnalyser ()
                    .addMethodDelegateSingleParameter (
                            this.getClass (),
                            "actionPerformed",
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "ActionEvent"));
            newActionsObject = this.swix.getTypeAnalyser ()
                    .makeFieldExtendsClass (
                            newActionsObject.getClass (),
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "Action"));

            Object newActionsObject2 = this.swix.getTypeAnalyser ()
                    .addMethodDelegateSingleParameter (
                            this.getClass (),
                            "windowClosing",
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "WindowEvent"));
            newActionsObject2 = this.swix.getTypeAnalyser ()
                    .makeFieldExtendsClass (
                            newActionsObject2.getClass (),
                            this.swix.getTypeAnalyser ().getCompatibleClass (
                                    "WindowAdapter"));

            newActionsObject.getClass ().getDeclaredField ("newAction")
                    .set (newActionsObject, this.newAction);
            newActionsObject.getClass ().getDeclaredField ("openAction")
                    .set (newActionsObject, this.openAction);
            newActionsObject.getClass ().getDeclaredField ("petAction")
                    .set (newActionsObject, this.petAction);
            this.swix.setClient (newActionsObject);
            final Object element = this.swix
                    .render ("samples/swing/xml/actions.xml");
            if ("Document".equals (element.getClass ().getSimpleName ())) {
                SeeWebpage.see (element.toString ());
            } else {
                // at this point all AbstractActions are linked with the button
                // etc.
                // ActionCommands however need to be linked manually, see below
                // ...

                // add this class as an action listener to all buttons inside
                // the
                // panel with the id = center_panel
                this.swix.setActionListener (newActionsObject.getClass ()
                        .getDeclaredField ("pnl_North").get (newActionsObject),
                        newActionsObject);
                // add this class as an action listener to MenuItem with the id
                // =
                // mi_exit.
                final javax.swing.JMenuItem mi_exit = (javax.swing.JMenuItem) newActionsObject
                        .getClass ().getDeclaredField ("mi_exit")
                        .get (newActionsObject);
                mi_exit.addActionListener ((java.awt.event.ActionListener) newActionsObject);
                // add this class as an action listener to MenuItem with the id
                // =
                // mi_save
                final javax.swing.JMenuItem mi_save = (javax.swing.JMenuItem) newActionsObject
                        .getClass ().getDeclaredField ("mi_save")
                        .get (newActionsObject);
                mi_save.addActionListener ((java.awt.event.ActionListener) newActionsObject);
                //
                // Note, the mi_about MenuItem was not linked at all so far.
                // Therefore, no action is performed when this
                // menu item gets requested.
                // The Toolbar button with the Actions="newAction" attribute is
                // covered twice,
                // during parsing the AbstactAction newAction is linked in and
                // later, the setActionListener() adds
                // this object's actionPerformed(). Therefore, when clicked,
                // both
                // actionPerformed() methods are getting called
                //
            }
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }

    //
    // Overwrite Superclass implementation
    //

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed (final Object e) {
        String command;
        try {
            command = (String) e.getClass ().getMethod ("getActionCommand")
                    .invoke (e);
            if ("AC_EXIT".equals (command)) {
                this.windowClosing (null);
            } else if ("AC_SAVE".equals (command)) {
                System.out.println ("Save");
            } else {
                System.out.println ("Click");
            }
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e1) {
            e1.printStackTrace ();
        }
    }

    //
    // Make the class bootable
    //

    /**
     * Invoked when the user attempts to close the window from the window's
     * system menu. If the program does not explicitly hide or dispose the
     * window while processing this event, the window close operation will be
     * cancelled.
     */
    public void windowClosing (final Object e) {
        System.out.println ("Good Bye!");
        System.exit (0);
    }
}
