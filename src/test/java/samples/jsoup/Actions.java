package samples.jsoup;


import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.swixml.AppConstants;
import org.swixml.SwingEngine;
import org.swixml.technoproxy.CustomCodeProxy;
import org.swixml.technoproxy.Platform;
import org.swixml.technoproxy.jsoup.SeeWebpage;

/**
 * Twixml is magic. Change only the Platform.NAME object to display the result in a JFrame or in a browser.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @since swixml #065
 */
public class Actions  {
	public static void main (String [] args) {
		AppConstants.DEBUG_MODE = true;
		Platform.NAME = "swing";
		new Actions (true);
	}

	private SwingEngine	swix;
	public JMenuItem	mi_exit, mi_save;

	public JPanel	    pnl_North;
	//
	// For every Actions, there needs to be a
	// public AbstractAction member variables with an anonymous inner class
	// instantiation
	//

	/** <code>Action</code> newAction handles the <em>new</em> action attribute */
	public Object	    newAction	= new Object (){

		                               /**
		 * 
		 */
		                               private static final long	serialVersionUID	= 9164625122840770636L;

		                               public void actionPerformed (
		                                       ActionEvent e) {
			                               System.out.println ("New"); // show
			                                                           // the
			                                                           // access
			                                                           // outer
			                                                           // class
			                                                           // member
			                                                           // ..
			                               //this.setEnabled (false); // disables
			                                                        // ALL
			                                                        // buttons
			                                                        // that are
			                                                        // tied to
			                                                        // this
			                                                        // action
		                               }
	                               };

	/**
	 * <code>Action</code> modifyAction handles the <em>modify</em> action
	 * attribute
	 */
	public Object	    openAction	= new Object () {
		                               /**
		 * 
		 */
		                               private static final long	serialVersionUID	= -7450982892262328242L;

		                               /** Invoked when an action occurs. */
		                               public void actionPerformed (
		                                       ActionEvent e) {
			                               System.out.println ("Open");
		                               }
	                               };

	/** <code>Action</code> petAction handles the <em>combobox</em> */
	public Object	    petAction	= new Object () {
		                               /**
		 * 
		 */
		                               private static final long	serialVersionUID	= 1901168394709054787L;

		                               public void actionPerformed (
		                                       ActionEvent e) {
			                               System.out
			                                       .println ( ((JComboBox<?>) e
			                                               .getSource ())
			                                               .getSelectedItem ()
			                                               .toString ());
		                               }
	                               };

	//
	// Implement ActionListener
	//

	/**
	 * Constructs a new Actions object, registering action handlers for
	 * center_panel components.
	 */
	private Actions (boolean start) {
		try 
		{
		    //Duplicate "this" to have a compliant ActionListener object
		      this.newAction = CustomCodeProxy.getTypeAnalyser ().makeFieldExtendsClass (this.newAction, 
		            CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Action"));

		      //Make all Actions implements the adequate superclass quietly
              this.openAction = CustomCodeProxy.getTypeAnalyser ().makeFieldExtendsClass (this.openAction, 
                    CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Action"));

              this.petAction = CustomCodeProxy.getTypeAnalyser ().makeFieldExtendsClass (this.petAction, 
                    CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Action"));
            
              Object newActionsObject = CustomCodeProxy.getTypeAnalyser ().makeFieldExtendsClass (this, 
                      CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Action"));
              newActionsObject.getClass ().getDeclaredField ("newAction").set (newActionsObject, this.newAction);
              newActionsObject.getClass ().getDeclaredField ("openAction").set (newActionsObject, this.openAction);
              newActionsObject.getClass ().getDeclaredField ("petAction").set (newActionsObject, this.petAction);
			this.swix = new SwingEngine (newActionsObject);
			Object element = this.swix.render ("samples/swing/xml/actions.xml");
			if ("Element".equals (element.getClass ().getSimpleName ())){
			    SeeWebpage.see (element.toString ());
			}
			// at this point all AbstractActions are linked with the button etc.
			// ActionCommands however need to be linked manually, see below ...

			// add this class as an action listener to all buttons inside the
			// panel with the id = center_panel
			this.swix.setActionListener (this.pnl_North, newActionsObject);
			// add this class as an action listener to MenuItem with the id =
			// mi_exit.
			//this.mi_exit.addActionListener (this);
			// add this class as an action listener to MenuItem with the id =
			// mi_save
			//this.mi_save.addActionListener (this);
			//
			// Note, the mi_about MenuItem was not linked at all so far.
			// Therefore, no action is performed when this
			// menu item gets requested.
			// The Toolbar button with the Actions="newAction" attribute is
			// covered twice,
			// during parsing the AbstactAction newAction is linked in and
			// later, the setActionListener() adds
			// this object's actionPerformed(). Therefore, when clicked, both
			// actionPerformed() methods are getting called
			//
			//((JFrame) this.swix.getRootComponent ()).setVisible (true);
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
	public void actionPerformed (ActionEvent e) {
		final String command = e.getActionCommand ();
		if ("AC_EXIT".equals (command)) {
			this.windowClosing (null);
		} else if ("AC_SAVE".equals (command)) {
			System.out.println ("Save");
		} else {
			System.out.println ("Click");
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
	public void windowClosing (WindowEvent e) {
		System.out.println ("Good Bye!");
		System.exit (0);
	}
}
