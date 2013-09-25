/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.layoutconverters;

import java.lang.reflect.Field;
import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.twixml.LayoutConverter;
import org.twixml.converters.Util;
import org.twixml.technoproxy.CustomCodeProxy;
import org.w3c.dom.Element;

/**
 * A layout converter for a <code>BorderLayout</code> of any platform.
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <pre>
 * &lt;panel layout="BorderLayout"&gt;
 *   &lt;panel constraints="BorderLayout.NORTH" /&gt;
 *   &lt;panel constraints="BorderLayout.CENTER" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="BorderLayout(10,20)"&gt;
 *   &lt;panel constraints="NORTH" /&gt;
 *   &lt;panel constraints="CENTER" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="BorderLayout" hgap="10" vgap="20"/&gt;
 *   &lt;panel constraints="NORTH" /&gt;
 *   &lt;panel constraints="CENTER" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * @author Karl Tauber
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class BorderLayoutConverter<BorderLayout> implements
        LayoutConverter<BorderLayout> {

    BorderLayout b;

    /**
     * Converts BorderLayout constraints.
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>constraints="BorderLayout.CENTER"</code></li>
     * <li><code>constraints="BorderLayout.NORTH"</code></li>
     * <li><code>constraints="EAST"</code></li>
     * </ul>
     */
    @Override
    public Object convertConstraintsAttribute (final Attribute attr) {
        final String value = attr.getValue ();
        final Field [] fields = CustomCodeProxy.getTypeAnalyser ()
                .getCompatibleClass ("BorderLayout").getFields ();
        for (final Field field : fields) {
            if (value.endsWith (field.getName ())) {
                try {
                    return field.get (CustomCodeProxy.getTypeAnalyser ()
                            .getCompatibleClass ("BorderLayout"));
                } catch (final Exception e) {
                }
                break;
            }
        }
        return null;
    }

    /**
     * Returns always <code>null</code>.
     */
    @Override
    public Object convertConstraintsElement (final Element element) {
        return null;
    }

    /**
     * <p>
     * Creates a BorderLayout instance.
     * </p>
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>layout="BorderLayout"</code></li>
     * <li><code>layout="BorderLayout(int hgap, int vgap)"</code></li>
     * </ul>
     */
    @Override
    public BorderLayout convertLayoutAttribute (final Attribute attr) {
        final StringTokenizer st = new StringTokenizer (attr.getValue (), "(,)");
        st.nextToken (); // skip layout type

        final int [] para = Util.ia (st);
        if (para.length < 2) {
            return CustomCodeProxy.getTypeAnalyser ().instantiate (
                    "BorderLayout");
        } else {
            return CustomCodeProxy.getTypeAnalyser ().instantiate (
                    "BorderLayout", para [0], para [1]);
        }
    }

    /**
     * <p>
     * Creates a BorderLayout instance.
     * </p>
     * 
     * <p>
     * <b>Attributes:</b>
     * </p>
     * <ul>
     * <li><code>hgap</code> (optional): The horizontal gap.</li>
     * <li><code>vgap</code> (optional): The vertical gap.</li>
     * </ul>
     * 
     * <p>
     * <b>Examples for Valid XML element notations:</b>
     * </p>
     * <ul>
     * <li><code>&lt;layout type="BorderLayout"/&gt;</code></li>
     * <li><code>&lt;layout type="BorderLayout" hgap="10" vgap="20"/&gt;</code></li>
     * </ul>
     */
    @Override
    public BorderLayout convertLayoutElement (final Element element) {
        final int hgap = Util.getInteger (element, "hgap", 0);
        final int vgap = Util.getInteger (element, "vgap", 0);
        return CustomCodeProxy.getTypeAnalyser ().instantiate ("BorderLayout",
                hgap, vgap);
    }

    /**
     * Returns "borderlayout".
     */
    @Override
    public String getID () {
        return "borderlayout";
    }
}
