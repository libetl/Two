/*--
 $Id: XDialog.java,v 1.2 2004/08/20 05:59:30 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * XDialog simply extends JDialog to allow instantiation with a parent frame
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.2 $
 */
public class XDialog extends JDialog {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4891442502294317642L;

    /**
     * Creates a non-modal dialog without a title and without a specified
     * <code>Frame</code> owner. A shared, hidden frame will be set as the owner
     * of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * </p>
     * <p>
     * setLocationRelativeTo is called for the instanced dialog if a parent
     * could be provided by the SwingEngine.
     * </p>
     * 
     * @exception HeadlessException
     *                if GraphicsEnvironment.isHeadless() returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @see Window#setLocationRelativeTo
     */
    public XDialog () throws HeadlessException {

        super (
                (Frame.getFrames ().length > 0)
                        && Frame.getFrames () [0].isDisplayable () ? Frame
                        .getFrames () [0] : null);
    }

    /**
     * Overwrites the <code>createRootPane</code> method to install Escape key
     * handling.
     * 
     * <pre>
     *  When using the JDialog window through a JOptionPane, you do not have to install the Escape key handling,
     *  as the basic look-and-feel class for the option pane (BasicOptionPaneUI) already does this for you.
     * </pre>
     * 
     * @return <code>JRootPane</code> - the rootpane with some keyboard actions
     *         registered.
     * 
     */
    @Override
    protected JRootPane createRootPane () {
        final ActionListener actionListener = new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent actionEvent) {
                XDialog.this.setVisible (false);
            }
        };
        final JRootPane rootPane = new JRootPane ();
        final KeyStroke stroke = KeyStroke.getKeyStroke (KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction (actionListener, stroke,
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
    }

    /**
     * Sets the application frame system icon.
     * 
     * <pre>
     * <b>Note:</b><br>
     * The provided icon is only applied if an enclosing frame doesn't really exists yet or does not have an icon set.
     * </pre>
     * 
     * @param image
     *            <code>Image</code> the image to become the app's system icon.
     */
    @Override
    public synchronized void setIconImage (Image image) {
        final Frame f = JOptionPane.getFrameForComponent (this);
        if ( (f != null) && (f.getIconImage () == null)) {
            f.setIconImage (image);
        }
    }

    /**
     * Makes the Dialog visible. If the dialog and/or its owner are not yet
     * displayable, both are made displayable. The dialog will be validated
     * prior to being made visible. If the dialog is already visible, this will
     * bring the dialog to the front.
     * <p>
     * If the dialog is modal and is not already visible, this call will not
     * return until the dialog is hidden by calling <code>hide</code> or
     * <code>dispose</code>. It is permissible to show modal dialogs from the
     * event dispatching thread because the toolkit will ensure that another
     * event pump runs while the one which invoked this method is blocked.
     * 
     * @see Component#hide
     * @see Component#isDisplayable
     * @see Component#validate
     * @see Dialog#isModal
     */
    @Override
    @Deprecated
    public void show () {
        this.setLocationRelativeTo (SwingUtilities.windowForComponent (this));
        super.show ();
    }
}
