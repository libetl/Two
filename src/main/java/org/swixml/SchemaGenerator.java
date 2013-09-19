/*--
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
 * Swixml XML Schema Generator
 * 
 * @author Wolf Paulus
 */
public class SchemaGenerator {
    protected static final String NAMESPACE_PREFIX = "xs";
    protected static final String NAMESPACE_URI    = "http://www.w3.org/2001/XMLSchema";

    /**
     * Loops through all the available setters in the facktory and adds the
     * attributes.
     * 
     * @param factory
     *            <code>Factory</code.
     * @return <code>Element</code> - the complex type element, container for
     *         the attribute tags.
     */
    private static Element addSwixmlAttr (Document schema, Factory factory) {
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
        // add custom swixml atributes
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
    private static Element addSwixmlTags (Document schema, Element root) {
        final TagLibrary taglib = new SwingEngine<Object, Object, Object, Object, Object, Object> ().getTaglib ();
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
     * Writes teh schema into the given file. defaults to userhome/swixml.xsd
     * 
     * @param args
     *            <code>String[]<code> file name.
     */
    public static void main (String [] args) {

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
    public static void print (URI uri, File file) throws Exception {
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
