/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.layoutconverters;

import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.twixml.LayoutConverter;
import org.twixml.converters.PrimitiveConverter;
import org.twixml.converters.Util;
import org.twixml.technoproxy.CustomCodeProxy;
import org.w3c.dom.Element;

/**
 * A layout converter for <code>FlowLayout</code>.
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <pre>
 * &lt;panel layout="FlowLayout"&gt;
 *   &lt;panel ... /&gt;
 *   &lt;panel ... /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="FlowLayout(FlowLayout.RIGHT)"&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="FlowLayout(FlowLayout.LEFT, 1, 2)"&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="FlowLayout" alignment="FlowLayout.RIGHT"/&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="FlowLayout" alignment="FlowLayout.LEFT" hgap="10" vgap="20"/&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * @author Karl Tauber
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class FlowLayoutConverter<FlowLayout> implements LayoutConverter<FlowLayout> {

    /**
     * Returns always <code>null</code>.
     */
    @Override
    public FlowLayout convertConstraintsAttribute (final Attribute attr) {
        return null;
    }

    /**
     * Returns always <code>null</code>.
     */
    @Override
    public FlowLayout convertConstraintsElement (final Element element) {
        return null;
    }

    /**
     * <p>
     * Creates a FlowLayout instance.
     * </p>
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>layout="FlowLayout"</code></li>
     * <li><code>layout="FlowLayout(int align)"</code></li>
     * <li><code>layout="FlowLayout(int align, int hgap, int vgap)"</code></li>
     * </ul>
     */
    @Override
    public FlowLayout convertLayoutAttribute (final Attribute attr) {
        final StringTokenizer st = new StringTokenizer (attr.getValue (), "(,)");
        st.nextToken (); // skip layout type

        try {
            if (st.hasMoreTokens ()) {
                //
                // First FlowLayout parameter might be a pre-defined constant's
                // name
                //
                final Object o = new PrimitiveConverter().convert (null, new Attribute (
                        "NA", st.nextToken ()), null);
                final int [] para = Util.ia (st);
                //
                // Remaining paramters should be integer values
                //
                if (para.length < 2) {
                    return CustomCodeProxy.getTypeAnalyser ().instantiate ("FlowLayout",
                            Integer.valueOf (o.toString ()).intValue ());
                } else {
                    return CustomCodeProxy.getTypeAnalyser ().instantiate ("FlowLayout",
                            Integer.valueOf (o.toString ())
                            .intValue (), para [0], para [1]);
                }
            }
        } catch (final Exception e) {
        }
        return CustomCodeProxy.getTypeAnalyser ().instantiate ("FlowLayout");
    }

    /**
     * <p>
     * Creates a FlowLayout instance.
     * </p>
     * 
     * <p>
     * <b>Attributes:</b>
     * </p>
     * <ul>
     * <li><code>alignment</code> (optional): The horizontal alignment.</li>
     * <li><code>hgap</code> (optional): The horizontal gap.</li>
     * <li><code>vgap</code> (optional): The vertical gap.</li>
     * </ul>
     * 
     * <p>
     * <b>Examples for Valid XML element notations:</b>
     * </p>
     * <ul>
     * <li><code>&lt;layout type="FlowLayout"/&gt;</code></li>
     * <li>
     * <code>&lt;layout type="FlowLayout" alignment="FlowLayout.LEFT"/&gt;</code>
     * </li>
     * <li>
     * <code>&lt;layout type="FlowLayout" alignment="FlowLayout.LEFT" hgap="10" vgap="20"/&gt;</code>
     * </li>
     * </ul>
     */
    @Override
    public FlowLayout convertLayoutElement (final Element element) {
        int align = 0;
        final String value = Attribute.getAttributeValue (element, "alignment");
        if (value != null) {
            try {
                final Object o = new PrimitiveConverter().convert (null, new Attribute (
                        "NA", value), null);
                align = Integer.valueOf (o.toString ()).intValue ();
            } catch (final Exception ex) {
            }
        }
        final int hgap = Util.getInteger (element, "hgap", 5);
        final int vgap = Util.getInteger (element, "vgap", 5);
        return CustomCodeProxy.getTypeAnalyser ().instantiate ("FlowLayout", align, hgap, vgap);
    }

    /**
     * Returns "flowlayout".
     */
    @Override
    public String getID () {
        return "flowlayout";
    }

}
