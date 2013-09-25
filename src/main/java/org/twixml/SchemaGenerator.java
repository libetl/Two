/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Twixml XML Schema Generator
 * 
 * @author Wolf Paulus
 */
public class SchemaGenerator {
    protected static final String NAMESPACE_PREFIX = "xs";
    protected static final String NAMESPACE_URI    = "http://www.w3.org/2001/XMLSchema";

    /**
     * Loops through all the available setters in the factory and adds the
     * attributes.
     * 
     * @param factory
     *            <code>Factory</code.
     * @return <code>Element</code> - the complex type element, container for
     *         the attribute tags.
     */
    private static Element addSwixmlAttr (final Document schema,
            final Factory factory) {
        final Set<String> set = new HashSet<String> ();
        final Element elem = schema.createElementNS (
                SchemaGenerator.NAMESPACE_URI, "complexType");
        elem.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
        elem.setAttribute ("mixed", "true");
        final Element sequence = schema.createElementNS (
                SchemaGenerator.NAMESPACE_URI, "sequence");
        sequence.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
        final Element es = schema.createElementNS (
                SchemaGenerator.NAMESPACE_URI, "any");
        es.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
        sequence.appendChild (es);
        sequence.setAttribute ("minOccurs", "0");
        sequence.setAttribute ("maxOccurs", "unbounded");
        elem.appendChild (sequence);
        for (final Object obj : factory.getSetters ()) {
            final Element e = schema.createElementNS (
                    SchemaGenerator.NAMESPACE_URI, "attribute");
            e.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
            final Method m = (Method) obj;
            String s = m.getName ();
            if (s.startsWith (Factory.SETTER_ID)) {
                s = s.substring (Factory.SETTER_ID.length ()).toLowerCase ();
            }
            if (s.startsWith (Factory.ADDER_ID)) {
                s = s.substring (Factory.ADDER_ID.length ()).toLowerCase ();
            }
            final boolean b = boolean.class.equals (m.getParameterTypes () [0]);
            if (!set.contains (s)) {
                e.setAttribute ("name", s);
                e.setAttribute ("type", b ? SchemaGenerator.NAMESPACE_PREFIX
                        + ":boolean" : SchemaGenerator.NAMESPACE_PREFIX
                        + ":string");
                elem.appendChild (e);
                set.add (s);
            }
        }
        //
        // add custom twixml atributes
        //
        for (final Field field : Parser.class.getFields ()) {
            if (field.getName ().startsWith ("ATTR_")
                    && !field.getName ().endsWith ("PREFIX")
                    && Modifier.isFinal (field.getModifiers ())) {
                final Element e = schema.createElementNS (
                        SchemaGenerator.NAMESPACE_URI, "attribute");
                e.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
                try {
                    final String s = field.get (Parser.class).toString ()
                            .toLowerCase ();
                    if (!set.contains (s)) {
                        e.setAttribute ("name", s);
                        e.setAttribute ("type",
                                SchemaGenerator.NAMESPACE_PREFIX + ":string");
                        elem.appendChild (e);
                        set.add (s);
                    }
                } catch (final IllegalAccessException e1) {
                    e1.printStackTrace ();
                }
            }
        }
        return elem;
    }

    /**
     * Loops through all tags in Swixml's tag lib.
     * 
     * @param root
     *            <code>Element</code> - node tags will be inserted in
     * @return <code>Element</code> - passed in root.
     */
    private static Element addSwixmlTags (final Document schema,
            final Element root) {
        final TagLibrary taglib = SimpleTagLibrary.getInstance ();
        for (final Object name : new TreeSet<String> (taglib.getTagClasses ()
                .keySet ())) {
            final Element elem = schema.createElementNS (
                    SchemaGenerator.NAMESPACE_URI, "element");
            elem.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
            elem.setAttribute ("name", name.toString ());
            elem.appendChild (SchemaGenerator.addSwixmlAttr (schema, taglib
                    .getTagClasses ().get (name)));
            root.appendChild (elem);
        }
        return root;
    }

    /**
     * Writes teh schema into the given file. defaults to userhome/twixml.xsd
     * 
     * @param args
     *            <code>String[]<code> file name.
     */
    public static void main (final String [] args) {

        try {
            SchemaGenerator.print ();
        } catch (final Exception e) {
            e.printStackTrace ();
        }
        File file;
        try {
            if ( (args != null) && (args.length > 0) && (args [0] != null)
                    && (0 < args [0].length ())) {
                file = new File (args [0]);
                SchemaGenerator.print (new URI (
                        "http://www.swixml.org/2007/Swixml"), file);
            }
        } catch (final Exception e) {
            e.printStackTrace ();
        }
    }

    /*
     * private static Element addCustomTags(Element root) {
     * Map<String,LayoutConverter> lcs =
     * LayoutConverterLibrary.getInstance().getLayoutConverters(); for
     * (LayoutConverter lc : lcs.values()) { Element elem = new
     * Element("element", XSNS); elem.setAttribute("name", "layout");
     * elem.setAttribute("name", name.toString());
     * elem.setContent(addSwixmlAttr((Factory)
     * taglib.getTagClasses().get(name))); root.addContent(elem); } return root;
     * }
     */

    public static void print () throws Exception {
        final Document schema = DocumentBuilderFactory.newInstance ()
                .newDocumentBuilder ().newDocument ();

        final Element root = schema.createElementNS (
                SchemaGenerator.NAMESPACE_URI, "schema");
        root.setPrefix (SchemaGenerator.NAMESPACE_PREFIX);
        schema.appendChild (root);

        root.setAttribute ("targetNamespace", new URI (
                "http://www.swixml.org/2007/Swixml").toString ());
        root.setAttribute ("elementFormDefault", "qualified");

        SchemaGenerator.addSwixmlTags (schema, root);

        final Transformer tr = TransformerFactory.newInstance ()
                .newTransformer ();
        tr.setOutputProperty (OutputKeys.INDENT, "yes");
        tr.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "2");
        tr.transform (new DOMSource (schema), new StreamResult (System.out));
    }

    /**
     * Generate the Swixml XML Schema and writes it into a file.
     * 
     * @param uri
     *            <code> taget Namespace, e.g. http://www.swixml.org/2007/SwixmlTags
     * @param file
     *            <code>File</code> - target
     * @throws IOException
     *             - if schema cannot be printed into the given file.
     */
    public static void print (final URI uri, final File file) throws Exception {
        final Document schema = DocumentBuilderFactory.newInstance ()
                .newDocumentBuilder ().newDocument ();
        schema.setDocumentURI (SchemaGenerator.NAMESPACE_URI);

        final Element root = schema.createElement ("schema");
        schema.appendChild (root);

        root.setAttribute ("targetNamespace", uri.toString ());
        root.setAttribute ("elementFormDefault", "qualified");

        SchemaGenerator.addSwixmlTags (schema, root);

        final Transformer tr = TransformerFactory.newInstance ()
                .newTransformer ();
        tr.setOutputProperty (OutputKeys.INDENT, "yes");
        tr.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "2");
        tr.transform (new DOMSource (schema), new StreamResult (file));
    }
}
