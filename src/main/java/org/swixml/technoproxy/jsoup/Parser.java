package org.swixml.technoproxy.jsoup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.parser.Tag;
import org.swixml.Attribute;
import org.swixml.SwingEngine;
import org.swixml.technoproxy.CustomCodeProxy;
import org.swixml.technoproxy.ProxyCode;
import org.swixml.technoproxy.ProxyCodeException;

public class Parser
        extends
        ProxyCode<org.swixml.Parser<Object, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, Object, Object>> {

    public Parser (
            org.swixml.Parser<Object, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, Object, Object> source1) {
        super (source1);
    }

    public org.jsoup.nodes.Element addChild (org.jsoup.nodes.Element parent, org.jsoup.nodes.Element component,
            Object constrains) {
 
        parent.appendChild (component);
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
    public void putIntoBtnGrp (Object obj, Object grp) {
        if (CustomCodeProxy.getTypeAnalyser ().isConvenient (obj,
                "AbstractButton") && grp instanceof org.jsoup.nodes.Element) {
            ((org.jsoup.nodes.Element)grp).appendChild ((org.jsoup.nodes.Element)obj);
        } else if (CustomCodeProxy.getTypeAnalyser ().isConvenient (
                obj, "Container")) {
            for (int i = 0 ; i < ((org.jsoup.nodes.Element)obj).childNodeSize () ; i++) {
                this.putIntoBtnGrp (((org.jsoup.nodes.Element)obj).child (i), grp);
            }
        } // otherwise just do nothing ...
    }

    public boolean getSwingButtonGroup (
            org.w3c.dom.Element element,
            Object obj,
            org.w3c.dom.Element child,
            SwingEngine<Object, org.jsoup.nodes.Element, ActionListener, org.jsoup.nodes.TextNode, Object, Object> engine)
            throws Exception {
        if ("buttongroup".equalsIgnoreCase (child.getNodeName ())) {

            int k = obj instanceof org.jsoup.nodes.Element ? ((org.jsoup.nodes.Element) obj).childNodeSize () : 0;
            this.getSource ().getSwing (child, obj);
            final int n = obj instanceof org.jsoup.nodes.Element ? ((org.jsoup.nodes.Element) obj).childNodeSize () : 0;
            
            //
            // add the recently add container entries into the btngroup
            //
            final org.jsoup.nodes.Element btnGroup = new org.jsoup.nodes.Element (Tag.valueOf ("div"), "");
            btnGroup.addClass ("buttongroup");
            // incase the button group was given an id attr. it should also
            // be put into the idmap.
            if (null != Attribute.getAttributeValue (child,
                    org.swixml.Parser.ATTR_ID)) {
                engine.getIdMap ().put (
                        Attribute.getAttributeValue (child,
                                org.swixml.Parser.ATTR_ID), btnGroup);
            }
            while (k < n) {
                this.putIntoBtnGrp (((org.jsoup.nodes.Element) obj).child (k++), btnGroup);
            }
            return true;
        }
        return false;
    }

    public void getSwingSetLayout (Object obj, Object lm) {
        if (obj != null && obj instanceof org.jsoup.nodes.Element){
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    lm.toString () + ";" + ((org.jsoup.nodes.Element)obj).attr ("style"));
        }
    }

    public Object getSwingGetLayout (Object obj) {
        return new LayoutManager (((org.jsoup.nodes.Element)obj).attr ("style"));
    }

    public void getSwingPutClientProperty (org.jsoup.nodes.Element obj, Attribute attr) {
        if (obj != null){
          obj.attr (attr.getName (), attr.getValue ());
        }
    }

    public void linkLabelsSetLabelFor (org.jsoup.nodes.TextNode jl, org.jsoup.nodes.Element c) {
        String uuid = UUID.randomUUID ().toString ();
        c.attr ("name", uuid);
        org.jsoup.nodes.Element label = new org.jsoup.nodes.Element (Tag.valueOf ("label"), "");
        label.attr ("for", uuid);
        c.parent ().insertChildren (c.parent ().siblingIndex (), Arrays.asList (label));
    }

    public void processCustomAttributesSetLookAndFeel (org.jsoup.nodes.Element element,
            String plaf) throws Exception {
        element.addClass ("UI" + plaf);
    }

    public Object applyAttributesGetContentPane (Object rpc) {
        return rpc;
    }

    public Object applyAttributesSetAction (
            Class<?> paraType,
            SwingEngine<Object, org.jsoup.nodes.Element, ActionListener, 
            org.jsoup.nodes.TextNode, Object, Object> engine,
            Attribute attr) {
        Object para = null;
        if ("action".equalsIgnoreCase (attr.getName ())) {
            try {
                para = engine.getClient ().getClass ()
                        .getField (attr.getValue ()).get (engine.getClient ());
            } catch (final NoSuchFieldException e) {
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
    
    public void applyAttributesMethodInvoke (
            Method method, Object obj, Attribute attr, Object para) {
      try {
        if ("text".equalsIgnoreCase (attr.getName ())){
          ((org.jsoup.nodes.Element)obj).appendChild (new org.jsoup.nodes.TextNode (attr.getValue (), ""));  
        }else if ("layout".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).addClass (attr.getValue ());
        }else if ("constraints".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).addClass (attr.getValue ().replace ('.', 'A'));
        }else if ("icon".equalsIgnoreCase (attr.getName ())){
            Class<?> mainClass;
            try {
                mainClass = Class.forName (Thread.currentThread ().getStackTrace () [Thread.currentThread ().getStackTrace ().length - 1].getClassName ());
                String folder = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
                ((org.jsoup.nodes.Element)obj).attr ("style",
                        ((org.jsoup.nodes.Element)obj).attr ("style") + ";background-image:url('" + folder +
                        attr.getValue () +"')");
            } catch (ClassNotFoundException e) {
            }
        }else if ("tooltiptext".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("title", attr.getValue ()) ;
        }else if ("enabled".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    ((org.jsoup.nodes.Element)obj).attr ("style") + ";enabled:" + attr.getValue () +"");
        }else if ("borderpainted".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    ((org.jsoup.nodes.Element)obj).attr ("style") + ";border: solid 1px #000");
        }else if ("visible".equalsIgnoreCase (attr.getName ())){
            if ("true".equalsIgnoreCase (attr.getValue ())){
                ((org.jsoup.nodes.Element)obj).attr ("style",
                        ((org.jsoup.nodes.Element)obj).attr ("style") + ";display:block");
            }else if ("false".equalsIgnoreCase (attr.getValue ())){
                ((org.jsoup.nodes.Element)obj).attr ("style",
                        ((org.jsoup.nodes.Element)obj).attr ("style") + ";display:none");                
            }
        }else if ("size".equalsIgnoreCase (attr.getName ())){
            int width = Integer.parseInt (attr.getValue ().split (",") [0]);
            int height = Integer.parseInt (attr.getValue ().split (",") [1]);
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    ((org.jsoup.nodes.Element)obj).attr ("style") + ";width:" + width +"px;height:" + height + "px");
        }else if ("foreground".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    ((org.jsoup.nodes.Element)obj).attr ("style") + ";font-color:" + attr.getValue ());
        }else if ("font".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("style",
                    ((org.jsoup.nodes.Element)obj).attr ("style") + ";font:" + attr.getValue ());
        }else if ("action".equalsIgnoreCase (attr.getName ())){
            ((org.jsoup.nodes.Element)obj).attr ("onclick",
                    "javascript:" + para.getClass ().getName () + "." + "actionPerformed (e);");
        }else{
          method.invoke (obj, attr.getName (), para.toString ());
        }
    } catch (IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
        throw new ProxyCodeException (e);
    } // ATTR SET
    }

    public void getSwingMacAction (Object initParameter,
            List<Attribute> attributes, Map<String, Object> macMap) {
    }

    /**
     * Link actions with the MacOS' system menu bar
     */
    public void parseSupportMacOs (Map<String, Object> macMap) {
    }
    
    public Object parseSurroundObj (org.w3c.dom.Document jdoc, org.jsoup.nodes.Element obj, String name){
        org.jsoup.nodes.Document d = new org.jsoup.nodes.Document ("");
        org.jsoup.nodes.Element link = new org.jsoup.nodes.Element (Tag.valueOf ("link"), "");
        org.jsoup.nodes.Element link1 = new org.jsoup.nodes.Element (Tag.valueOf ("link"), "");
        org.jsoup.nodes.Element link2 = new org.jsoup.nodes.Element (Tag.valueOf ("link"), "");
        org.jsoup.nodes.Element script = new org.jsoup.nodes.Element (Tag.valueOf ("script"), "");
        Class<?> mainClass = SwingEngine.class;
        String folder = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        link.attr ("rel", "stylesheet");
        link.attr ("type", "text/css");
        link.attr ("href", folder + "org/swixml/technoproxy/jsoup/metalUIHtml.css");
        link1.attr ("rel", "stylesheet");
        link1.attr ("type", "text/css");
        link1.attr ("href", "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css");
        link2.attr ("rel", "stylesheet");
        link2.attr ("type", "text/css");
        link2.attr ("href", "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css");
        script.attr ("type", "text/javascript");
        script.attr ("src", "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js");
        d.appendChild (new org.jsoup.nodes.DocumentType ("html", "", "", ""));
        d.appendChild (new org.jsoup.nodes.Element (Tag.valueOf ("html"), ""));
        d.child (0).appendChild (new org.jsoup.nodes.Element (Tag.valueOf ("head"), ""));
        d.child (0).child (0).appendChild (link);
        d.child (0).child (0).appendChild (link1);
        d.child (0).child (0).appendChild (link2);
        d.child (0).child (0).appendChild (script);
        d.child (0).child (0).appendChild (new org.jsoup.nodes.Element (Tag.valueOf ("title"), ""));
        d.child (0).child (0).child (4).appendChild (new org.jsoup.nodes.TextNode (name, ""));
        d.child (0).appendChild (new org.jsoup.nodes.Element (Tag.valueOf ("body"), ""));
        d.getElementsByTag ("body").iterator ().next ().appendChild (obj);
        return d;
    }
}
