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
 * A layout converter for <code>CardLayout</code>.
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <pre>
 * &lt;panel layout="CardLayout"&gt;
 *   &lt;panel constraints="card1" /&gt;
 *   &lt;panel constraints="card2" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel layout="CardLayout(10,20)"&gt;
 *   &lt;panel constraints="firstCard" /&gt;
 *   &lt;panel constraints="secondCard" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="CardLayout" hgap="10" vgap="20"/&gt;
 *   &lt;panel constraints="firstCard" /&gt;
 *   &lt;panel constraints="secondCard" /&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <p>
 * Here is how to access the card layout manager of a component installed by
 * TwixML
 * <code>(CardLayout)((Container)swingEngine.find("id_of_my_CLed_comp")).getLayout()</code>
 * </p>
 * 
 * @author Karl Tauber
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class CardLayoutConverter<CardLayout> implements
        LayoutConverter<CardLayout> {

    /**
     * Converts CardLayout constraints. The attribute value is used as card
     * name.
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>constraints="cardname"</code></li>
     * </ul>
     */
    @Override
    public Object convertConstraintsAttribute (final Attribute attr) {
        //
        // CardLayout accepts only constraints of type String
        //
        return attr.getValue ();
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
     * Creates a CardLayout instance.
     * </p>
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>layout="CardLayout"</code></li>
     * <li><code>layout="CardLayout(int hgap, int vgap)"</code></li>
     * </ul>
     */
    @Override
    public CardLayout convertLayoutAttribute (final Attribute attr) {
        final StringTokenizer st = new StringTokenizer (attr.getValue (), "(,)");
        st.nextToken (); // skip layout type

        final int [] para = Util.ia (st);
        if (para.length < 2) {
            return CustomCodeProxy.getTypeAnalyser ()
                    .instantiate ("CardLayout");
        } else {
            return CustomCodeProxy.getTypeAnalyser ().instantiate (
                    "CardLayout", para [0], para [1]);
        }
    }

    /**
     * <p>
     * Creates a CardLayout instance.
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
     * <li><code>&lt;layout type="CardLayout"/&gt;</code></li>
     * <li><code>&lt;layout type="CardLayout" hgap="10" vgap="20"/&gt;</code></li>
     * </ul>
     */
    @Override
    public CardLayout convertLayoutElement (final Element element) {
        final int hgap = Util.getInteger (element, "hgap", 0);
        final int vgap = Util.getInteger (element, "vgap", 0);
        return CustomCodeProxy.getTypeAnalyser ().instantiate ("CardLayout",
                hgap, vgap);
    }

    /**
     * Returns "cardlayout".
     */
    @Override
    public String getID () {
        return "cardlayout";
    }
}
