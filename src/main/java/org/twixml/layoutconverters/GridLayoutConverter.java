/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.layoutconverters;

import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.twixml.LayoutConverter;
import org.twixml.converters.Util;
import org.twixml.technoproxy.CustomCodeProxy;
import org.w3c.dom.Element;

/**
 * A layout converter for <code>GridLayout</code>.
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <pre>
 * &lt;panel layout="GridLayout"&gt;
 *   &lt;panel ... /&gt;
 *   &lt;panel ... /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="GridLayout(2,4)"&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="GridLayout(2,4,10,20)"&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="GridLayout" rows="2" columns="4"/&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="GridLayout" rows="2" columns="4" hgap="10" vgap="20"/&gt;
 *   ...
 * &lt;/panel&gt;
 * </pre>
 * 
 * @author Karl Tauber
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class GridLayoutConverter<GridLayout> implements LayoutConverter<GridLayout> {

    /**
     * Returns always <code>null</code>.
     */
    @Override
    public Object convertConstraintsAttribute (final Attribute attr) {
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
     * Creates a GridLayout instance.
     * </p>
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>layout="GridLayout"</code></li>
     * <li><code>layout="GridLayout(int rows, int cols)"</code></li>
     * <li>
     * <code>layout="GridLayout(int rows, int cols, int hgap, int vgap)"</code></li>
     * </ul>
     */
    @Override
    public GridLayout convertLayoutAttribute (final Attribute attr) {
        final StringTokenizer st = new StringTokenizer (attr.getValue (), "(,)");
        st.nextToken (); // skip layout type

        final int [] para = Util.ia (st);
        if (4 <= para.length) {
            return CustomCodeProxy.getTypeAnalyser ().instantiate ("GridLayout", para [0], para [1], para [2], para [3]);
        } else if (2 <= para.length) {
            return CustomCodeProxy.getTypeAnalyser ().instantiate ("GridLayout", para [0], para [1]);
        } else {
            return CustomCodeProxy.getTypeAnalyser ().instantiate ("GridLayout");
        }
    }

    /**
     * <p>
     * Creates a GridLayout instance.
     * </p>
     * 
     * <p>
     * <b>Attributes:</b>
     * </p>
     * <ul>
     * <li><code>rows</code> (optional): The number of rows.</li>
     * <li><code>columns</code> (optional): The number of columns.</li>
     * <li><code>hgap</code> (optional): The horizontal gap.</li>
     * <li><code>vgap</code> (optional): The vertical gap.</li>
     * </ul>
     * 
     * <p>
     * <b>Examples for Valid XML element notations:</b>
     * </p>
     * <ul>
     * <li><code>&lt;layout type="GridLayout"/&gt;</code></li>
     * <li><code>&lt;layout type="GridLayout" rows="4" columns="5"/&gt;</code></li>
     * <li>
     * <code>&lt;layout type="GridLayout" rows="2" columns="4" hgap="10" vgap="20"/&gt;</code>
     * </li>
     * </ul>
     */
    @Override
    public GridLayout convertLayoutElement (final Element element) {
        final int rows = Util.getInteger (element, "rows", 1);
        final int cols = Util.getInteger (element, "columns", 0);
        final int hgap = Util.getInteger (element, "hgap", 0);
        final int vgap = Util.getInteger (element, "vgap", 0);
        return CustomCodeProxy.getTypeAnalyser ().instantiate ("GridLayout", rows, cols, hgap, vgap);
    }

    /**
     * Returns "gridlayout".
     */
    @Override
    public String getID () {
        return "gridlayout";
    }
}
