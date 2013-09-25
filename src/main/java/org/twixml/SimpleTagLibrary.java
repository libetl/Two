/*--
 $Id: SwingTagLibrary.java,v 1.1 2004/03/01 07:56:07 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The SwingTagLibrary contains Factories for all Swing Objects that can be
 * instatiated by parsing an XML descriptor at runtime.
 * 
 * Date: Dec 9, 2002
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.TagLibrary
 */
public final class SimpleTagLibrary extends TagLibrary {

    private static SimpleTagLibrary INSTANCE = new SimpleTagLibrary ();

    public static SimpleTagLibrary getInstance () {
        return SimpleTagLibrary.INSTANCE;
    }

    /**
     * Constructs a Swing Library by registering swings widgets
     */
    private SimpleTagLibrary () {
        this.registerTags ();
    }

    /**
     * Registers the tags this TagLibrary is all about. Strategy method called
     * by the super"), allowing derived")es to change the registration
     * behaviour.
     */
    @Override
    protected void registerTags () {
        this.registerTag ("Applet", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Applet"));
        this.registerTag ("Button", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Button"));
        this.registerTag ("ButtonGroup", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("ButtonGroup"));
        this.registerTag ("HBox", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XHBox"));
        this.registerTag ("VBox", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XVBox"));
        this.registerTag ("Checkbox", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("CheckBox"));
        this.registerTag ("CheckBoxMenuItem", CustomCodeProxy
                .getTypeAnalyser ().getCompatibleClass ("CheckBoxMenuItem"));
        this.registerTag ("ComboBox", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("ComboBox"));
        this.registerTag ("Component", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Component"));
        this.registerTag ("DesktopPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("DesktopPane"));
        this.registerTag ("Dialog", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XDialog"));
        this.registerTag ("EditorPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("EditorPane"));
        this.registerTag ("FormattedTextField", CustomCodeProxy
                .getTypeAnalyser ().getCompatibleClass ("FormattedTextField"));
        this.registerTag ("Frame", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Frame"));
        this.registerTag ("Glue", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XGlue"));
        this.registerTag ("GridBagConstraints", CustomCodeProxy
                .getTypeAnalyser ().getCompatibleClass ("XGridBagConstraints"));
        this.registerTag ("InternalFrame", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("InternalFrame"));
        this.registerTag ("Label", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Label"));
        this.registerTag ("List", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("List"));
        this.registerTag ("Menu", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Menu"));
        this.registerTag ("Menubar", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("MenuBar"));
        this.registerTag ("Menuitem", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("MenuItem"));
        this.registerTag ("Panel", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Panel"));
        this.registerTag ("PasswordField", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("PasswordField"));
        this.registerTag ("PopupMenu", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("PopupMenu"));
        this.registerTag ("ProgressBar", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("ProgressBar"));
        this.registerTag ("RadioButton", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("RadioButton"));
        this.registerTag ("RadioButtonMenuItem", CustomCodeProxy
                .getTypeAnalyser ().getCompatibleClass ("RadioButtonMenuItem"));
        this.registerTag ("OptionPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("OptionPane"));
        this.registerTag ("ScrollPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XScrollPane"));
        this.registerTag ("Separator", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Separator"));
        this.registerTag ("Slider", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Slider"));
        this.registerTag ("Spinner", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Spinner"));
        this.registerTag ("SplitPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XSplitPane"));
        this.registerTag ("TabbedPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XTabbedPane"));
        this.registerTag ("Table", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Table"));
        this.registerTag ("TableHeader", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("TableHeader"));
        this.registerTag ("TextArea", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("TextArea"));
        this.registerTag ("TextField", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("TextField"));
        this.registerTag ("TextPane", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("TextPane"));
        this.registerTag ("TitledSeparator", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("XTitledSeparator"));
        this.registerTag ("ToggleButton", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("ToggleButton"));
        this.registerTag ("Tree", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("Tree"));
        this.registerTag ("Toolbar", CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("ToolBar"));
    }

}
