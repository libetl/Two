package org.swixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;

import org.swixml.AppConstants;
import org.swixml.Attribute;
import org.swixml.SwingEngine;
import org.swixml.technoproxy.CustomCodeProxy;
import org.swixml.technoproxy.ProxyCode;
import org.w3c.dom.Element;

public class Parser
        extends
        ProxyCode<org.swixml.Parser<Container, Component, ActionListener, JLabel, ButtonGroup, LayoutManager>> {

    public Parser (
            org.swixml.Parser<Container, Component, ActionListener, JLabel, ButtonGroup, LayoutManager> source1) {
        super (source1);
    }

    public Component addChild (Container parent, Component component,
            Object constrains) {
        //
        // Set a JMenuBar for JFrames, JDialogs, etc.
        //
        if (CustomCodeProxy.getTypeAnalyser ().isConvenient (
                component.getClass (), "MenuBar")) {

            try {
                final Method m = parent.getClass ().getMethod ("setJMenuBar",
                        JMenuBar.class);
                m.invoke (parent, component);
            } catch (final NoSuchMethodException e) {
                if (constrains == null) {
                    parent.add (component);
                } else {
                    parent.add (component, constrains);
                }
            } catch (final Exception e) {
                // intentionally empty
            }

        } else if (parent instanceof RootPaneContainer) {
            //
            // add component into RootContainr
            // All Swing top-level containers contain a JRootPane as their only
            // child.
            // The content pane provided by the root pane should contain all the
            // non-menu components.
            //
            final RootPaneContainer rpc = (RootPaneContainer) parent;
            if (component instanceof LayoutManager) {
                rpc.getContentPane ().setLayout ((LayoutManager) component);
            } else {
                rpc.getContentPane ().add (component, constrains);
            }
        } else if (parent instanceof JScrollPane) {
            //
            // add component into a ScrollPane
            //
            final JScrollPane scrollPane = (JScrollPane) parent;
            scrollPane.setViewportView (component);
        } else if (parent instanceof JSplitPane) {
            //
            // add component into a SplitPane
            //
            final JSplitPane splitPane = (JSplitPane) parent;
            if (splitPane.getOrientation () == JSplitPane.HORIZONTAL_SPLIT) {
                //
                // Horizontal SplitPane
                //
                if (splitPane.getTopComponent () == null) {
                    splitPane.setTopComponent (component);
                } else {
                    splitPane.setBottomComponent (component);
                }
            } else {
                //
                // Vertical SplitPane
                //
                if (splitPane.getLeftComponent () == null) {
                    splitPane.setLeftComponent (component);
                } else {
                    splitPane.setRightComponent (component);
                }
            }
        } else if ( (parent instanceof JMenuBar)
                && (component instanceof JMenu)) {
            //
            // add Menu into a MenuBar
            //
            final JMenuBar menuBar = (JMenuBar) parent;
            menuBar.add (component, constrains);
        } else if (JSeparator.class.isAssignableFrom (component.getClass ())) {
            //
            // add Separator to a Menu, Toolbar or PopupMenu
            //
            try {
                if (JToolBar.class.isAssignableFrom (parent.getClass ())) {
                    ((JToolBar) parent).addSeparator ();
                } else if (JPopupMenu.class.isAssignableFrom (parent
                        .getClass ())) {
                    ((JPopupMenu) parent).addSeparator ();
                } else if (JMenu.class.isAssignableFrom (parent.getClass ())) {
                    ((JMenu) parent).addSeparator ();
                } else if (constrains != null) {
                    parent.add (component, constrains);
                } else {
                    parent.add (component);
                }
            } catch (final ClassCastException e) {
                parent.add (component);
            }

        } else if (parent instanceof Container) {
            //
            // add compontent into container
            //
            if (constrains == null) {
                parent.add (component);
            } else {
                parent.add (component, constrains);
            }
        }
        return component;
    }

    /**
     * Recursively adds AbstractButtons into the given
     * 
     * @param obj
     *            <code>Object</code> should be an AbstractButton or JComponent
     *            containing AbstractButtons
     * @param grp
     *            <code>ButtonGroup</code>
     */
    private void putIntoBtnGrp (Object obj, ButtonGroup grp) {
        if (CustomCodeProxy.getTypeAnalyser ().isConvenient (obj.getClass (),
                "AbstractButton")) {
            grp.add ((AbstractButton) obj);
        } else if (CustomCodeProxy.getTypeAnalyser ().isConvenient (
                obj.getClass (), "Container")) {
            final JComponent jp = (JComponent) obj;
            for (int i = 0 ; i < jp.getComponentCount () ; i++) {
                this.putIntoBtnGrp (jp.getComponent (i), grp);
            }
        } // otherwise just do nothing ...
    }

    public boolean getSwingButtonGroup (
            Element element,
            Object obj,
            Element child,
            SwingEngine<Container, Component, ActionListener, JLabel, ButtonGroup, LayoutManager> engine)
            throws Exception {
        if ("buttongroup".equalsIgnoreCase (child.getNodeName ())) {

            int k = JMenu.class.isAssignableFrom (obj.getClass ()) ? ((JMenu) obj)
                    .getItemCount () : ((Container) obj).getComponentCount ();
            this.getSource ().getSwing (child, obj);
            final int n = JMenu.class.isAssignableFrom (obj.getClass ()) ? ((JMenu) obj)
                    .getItemCount () : ((Container) obj).getComponentCount ();
            //
            // add the recently add container entries into the btngroup
            //
            final ButtonGroup btnGroup = new ButtonGroup ();
            // incase the button group was given an id attr. it should also
            // be put into the idmap.
            if (null != Attribute.getAttributeValue (child,
                    org.swixml.Parser.ATTR_ID)) {
                engine.getIdMap ().put (
                        Attribute.getAttributeValue (child,
                                org.swixml.Parser.ATTR_ID), btnGroup);
            }
            while (k < n) {
                this.putIntoBtnGrp (JMenu.class.isAssignableFrom (obj
                        .getClass ()) ? ((JMenu) obj).getItem (k++)
                        : ((Container) obj).getComponent (k++), btnGroup);
            }
            return true;
        }
        return false;
    }

    public void getSwingSetLayout (Container obj, LayoutManager lm) {
        obj.setLayout (lm);
    }

    public LayoutManager getSwingGetLayout (Container obj) {
        if (obj instanceof RootPaneContainer && ((RootPaneContainer) obj).getRootPane () != null
                && ((RootPaneContainer) obj).getRootPane ().getContentPane () != null){
            return ((RootPaneContainer) obj).getRootPane ().getContentPane ().getLayout ();
        }
        return obj.getLayout ();
    }

    public void getSwingPutClientProperty (Component obj, Attribute attr) {
        if (obj != null && obj instanceof JComponent){
          ((JComponent) obj).putClientProperty (attr.getName (), attr.getValue ());
        }
    }

    public void linkLabelsSetLabelFor (JLabel jl, Component c) {
        jl.setLabelFor (c);
    }

    public void processCustomAttributesSetLookAndFeel (Element element,
            String plaf) throws Exception {
        UIManager.setLookAndFeel (plaf);
    }

    public Container applyAttributesGetContentPane (RootPaneContainer rpc) {
        return rpc.getContentPane ();
    }

    public Object applyAttributesSetAction (
            Class<?> paraType,
            SwingEngine<Container, Component, ActionListener, JLabel, ButtonGroup, LayoutManager> engine,
            Attribute attr) {
        Object para = null;
        if (Action.class.equals (paraType)) {
            try {
                para = engine.getClient ().getClass ()
                        .getField (attr.getValue ()).get (engine.getClient ());
            } catch (final NoSuchFieldException e) {
                //
                // At this point we know that a action attribute
                // was put into an XML tag but the client call
                // doesn't seem to have an Action member variable with a
                // matching name.
                // Now we look for a public method that could be
                // wrapped into an generated AbstrtactAction
                // instead
                //
                try {
                    para = new XAction (engine.getClient (), attr.getValue ());
                } catch (final NoSuchMethodException e1) {
                    para = null;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (SecurityException e) {
            }
        }
        return para;
    }

    public void getSwingMacAction (Object initParameter,
            List<Attribute> attributes, Map<String, Object> macMap) {
        if (Action.class.isInstance (initParameter)) {
            for (int i = 0, n = attributes.size () ; i < n ; i++) {
                final Attribute attrib = attributes.get (i);
                final String attribName = attrib.getName ();
                this.getSource ();
                if ( (attribName != null)
                        && attribName
                                .startsWith (org.swixml.Parser.ATTR_MACOS_PREFIX)) {
                    macMap.put (attribName, initParameter);
                }
            }
        }
    }

    /**
     * Link actions with the MacOS' system menu bar
     */
    public void parseSupportMacOs (Map<String, Object> macMap) {
        if (AppConstants.isMacOSXSupported () && AppConstants.isMacOSX ()) {
            try {
                MacApp.getInstance ().update (macMap);
            } catch (final Throwable t) {
                // intentionally empty
            }
        }
    }
}
