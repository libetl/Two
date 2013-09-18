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


import org.swixml.technoproxy.CustomCodeProxy;

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
     * by the super"), allowing derived")es to change the registration
     * behaviour.
     */
    @Override
    protected void registerTags () {
        this.registerTag ("Applet", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JApplet"));
        this.registerTag ("Button", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JButton"));
        this.registerTag ("ButtonGroup", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("ButtonGroup"));
        this.registerTag ("HBox", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XHBox"));
        this.registerTag ("VBox", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XVBox"));
        this.registerTag ("Checkbox", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JCheckBox"));
        this.registerTag ("CheckBoxMenuItem", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JCheckBoxMenuItem"));
        this.registerTag ("ComboBox", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JComboBox"));
        this.registerTag ("Component", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JComponent"));
        this.registerTag ("DesktopPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JDesktopPane"));
        this.registerTag ("Dialog", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XDialog"));
        this.registerTag ("EditorPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JEditorPane"));
        this.registerTag ("FormattedTextField", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JFormattedTextField"));
        this.registerTag ("Frame", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JFrame"));
        this.registerTag ("Glue", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XGlue"));
        this.registerTag ("GridBagConstraints", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XGridBagConstraints"));
        this.registerTag ("InternalFrame", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JInternalFrame"));
        this.registerTag ("Label", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JLabel"));
        this.registerTag ("List", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JList"));
        this.registerTag ("Menu", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JMenu"));
        this.registerTag ("Menubar", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JMenuBar"));
        this.registerTag ("Menuitem", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JMenuItem"));
        this.registerTag ("Panel", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JPanel"));
        this.registerTag ("PasswordField", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JPasswordField"));
        this.registerTag ("PopupMenu", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JPopupMenu"));
        this.registerTag ("ProgressBar", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JProgressBar"));
        this.registerTag ("RadioButton", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JRadioButton"));
        this.registerTag ("RadioButtonMenuItem", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JRadioButtonMenuItem"));
        this.registerTag ("OptionPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JOptionPane"));
        this.registerTag ("ScrollPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XScrollPane"));
        this.registerTag ("Separator", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JSeparator"));
        this.registerTag ("Slider", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JSlider"));
        this.registerTag ("Spinner", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JSpinner"));
        this.registerTag ("SplitPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XSplitPane"));
        this.registerTag ("TabbedPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XTabbedPane"));
        this.registerTag ("Table", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTable"));
        this.registerTag ("TableHeader", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTableHeader"));
        this.registerTag ("TextArea", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTextArea"));
        this.registerTag ("TextField", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTextField"));
        this.registerTag ("TextPane", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTextPane"));
        this.registerTag ("TitledSeparator", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("XTitledSeparator"));
        this.registerTag ("ToggleButton", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JToggleButton"));
        this.registerTag ("Tree", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JTree"));
        this.registerTag ("Toolbar", CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("JToolBar"));
    }
}
