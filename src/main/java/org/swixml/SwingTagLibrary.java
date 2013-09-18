/*--
 $Id: SwingTagLibrary.java,v 1.1 2004/03/01 07:56:07 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials provided
 with the distribution.

 3. The end-user documentation included with the redistribution,
 if any, must include the following acknowledgment:
        "This product includes software developed by the
         SWIXML Project (http://www.swixml.org/)."
 Alternately, this acknowledgment may appear in the software itself,
 if and wherever such third-party acknowledgments normally appear.

 4. The name "Swixml" must not be used to endorse or promote products
 derived from this software without prior written permission. For
 written permission, please contact <info_AT_swixml_DOT_org>

 5. Products derived from this software may not be called "Swixml",
 nor may "Swixml" appear in their name, without prior written
 permission from the Swixml Project Management.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE SWIXML PROJECT OR ITS
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 individuals on behalf of the Swixml Project and was originally
 created by Wolf Paulus <wolf_AT_swixml_DOT_org>. For more information
 on the Swixml Project, please see <http://www.swixml.org/>.
 */
package org.swixml;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.table.JTableHeader;

import org.swixml.technoproxy.swing.XDialog;
import org.swixml.technoproxy.swing.XGlue;
import org.swixml.technoproxy.swing.XGridBagConstraints;
import org.swixml.technoproxy.swing.XHBox;
import org.swixml.technoproxy.swing.XScrollPane;
import org.swixml.technoproxy.swing.XSplitPane;
import org.swixml.technoproxy.swing.XTabbedPane;
import org.swixml.technoproxy.swing.XTitledSeparator;
import org.swixml.technoproxy.swing.XVBox;

/**
 * The SwingTagLibrary contains Factories for all Swing Objects that can be
 * instatiated by parsing an XML descriptor at runtime.
 * 
 * Date: Dec 9, 2002
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.swixml.TagLibrary
 */
public final class SwingTagLibrary extends TagLibrary {

    private static SwingTagLibrary INSTANCE = new SwingTagLibrary ();

    public static SwingTagLibrary getInstance () {
        return SwingTagLibrary.INSTANCE;
    }

    /**
     * Constructs a Swing Library by registering swings widgets
     */
    private SwingTagLibrary () {
        this.registerTags ();
    }

    /**
     * Registers the tags this TagLibrary is all about. Strategy method called
     * by the super class, allowing derived classes to change the registration
     * behaviour.
     */
    @Override
    protected void registerTags () {
        this.registerTag ("Applet", JApplet.class);
        this.registerTag ("Button", JButton.class);
        this.registerTag ("ButtonGroup", ButtonGroup.class);
        this.registerTag ("HBox", XHBox.class);
        this.registerTag ("VBox", XVBox.class);
        this.registerTag ("Checkbox", JCheckBox.class);
        this.registerTag ("CheckBoxMenuItem", JCheckBoxMenuItem.class);
        this.registerTag ("ComboBox", JComboBox.class);
        this.registerTag ("Component", JComponent.class);
        this.registerTag ("DesktopPane", JDesktopPane.class);
        this.registerTag ("Dialog", XDialog.class);
        this.registerTag ("EditorPane", JEditorPane.class);
        this.registerTag ("FormattedTextField", JFormattedTextField.class);
        this.registerTag ("Frame", JFrame.class);
        this.registerTag ("Glue", XGlue.class);
        this.registerTag ("GridBagConstraints", XGridBagConstraints.class);
        this.registerTag ("InternalFrame", JInternalFrame.class);
        this.registerTag ("Label", JLabel.class);
        this.registerTag ("List", JList.class);
        this.registerTag ("Menu", JMenu.class);
        this.registerTag ("Menubar", JMenuBar.class);
        this.registerTag ("Menuitem", JMenuItem.class);
        this.registerTag ("Panel", JPanel.class);
        this.registerTag ("PasswordField", JPasswordField.class);
        this.registerTag ("PopupMenu", JPopupMenu.class);
        this.registerTag ("ProgressBar", JProgressBar.class);
        this.registerTag ("RadioButton", JRadioButton.class);
        this.registerTag ("RadioButtonMenuItem", JRadioButtonMenuItem.class);
        this.registerTag ("OptionPane", JOptionPane.class);
        this.registerTag ("ScrollPane", XScrollPane.class);
        this.registerTag ("Separator", JSeparator.class);
        this.registerTag ("Slider", JSlider.class);
        this.registerTag ("Spinner", JSpinner.class);
        this.registerTag ("SplitPane", XSplitPane.class);
        this.registerTag ("TabbedPane", XTabbedPane.class);
        this.registerTag ("Table", JTable.class);
        this.registerTag ("TableHeader", JTableHeader.class);
        this.registerTag ("TextArea", JTextArea.class);
        this.registerTag ("TextField", JTextField.class);
        this.registerTag ("TextPane", JTextPane.class);
        this.registerTag ("TitledSeparator", XTitledSeparator.class);
        this.registerTag ("ToggleButton", JToggleButton.class);
        this.registerTag ("Tree", JTree.class);
        this.registerTag ("Toolbar", JToolBar.class);
    }
}
