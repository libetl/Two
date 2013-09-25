/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.layoutconverters;

import java.lang.reflect.Field;
import java.util.StringTokenizer;

import org.twixml.AppConstants;
import org.twixml.Attribute;
import org.twixml.LayoutConverter;
import org.twixml.converters.Util;
import org.twixml.technoproxy.CustomCodeProxy;
import org.w3c.dom.Element;

/**
 * A layout converter for <code>GridBagLayout</code>.
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <pre>
 * &lt;panel layout="GridBagLayout"&gt;
 *   &lt;button&gt;
 *     &lt;gridbagconstraints
 *         gridx="1" gridy="2" gridwidth="3" gridheight="4" weightx="0.1" weighty="1"
 *         anchor="GridBagConstraints.NORTH" fill="GridBagConstraints.HORIZONTAL"
 *         insets="1,2,3,4" ipadx="5" ipady="6"/&gt;
 *   &lt;/button&gt;
 *   &lt;button&gt;
 *     &lt;gridbagconstraints gridx="2" gridy="3"/&gt;
 *   &lt;/button&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * <pre>
 * &lt;panel&gt;
 *   &lt;layout type="GridBagLayout" columnWidths="200, 400, 150"&gt;
 *   &lt;button&gt;
 *     &lt;gridbagconstraints gridx="1" gridy="2"/&gt;
 *   &lt;/button&gt;
 *   &lt;button&gt;
 *     &lt;gridbagconstraints gridx="2" gridy="3"/&gt;
 *   &lt;/button&gt;
 * &lt;/panel&gt;
 * </pre>
 * 
 * @author Karl Tauber
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */
public class GridBagLayoutConverter<GridBagLayout> implements LayoutConverter<GridBagLayout> {

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
     * Creates a GridBagLayout instance.
     * </p>
     * 
     * <p>
     * <b>Examples for Valid XML attribute notations:</b>
     * </p>
     * <ul>
     * <li><code>layout="GridBagLayout"</code></li>
     * <li><code>layout="GridBagLayout(rowWeights(0,0,1.0,0))"</code></li>
     * <li>
     * <code>layout="GridBagLayout(columnWeights(0.5, 0.5, 1.0, 99.9))"</code></li>
     * <li><code>layout="GridBagLayout(columnWidths(5, 5, 10, 33))"</code></li>
     * <li><code>layout="GridBagLayout(rowHeights(5, 5, 10, 33))"</code></li>
     * </ul>
     */
    @Override
    public GridBagLayout convertLayoutAttribute (final Attribute attr) {
        final StringTokenizer st = new StringTokenizer (attr.getValue (), "(,)");
        st.nextToken (); // skip layout type

        //
        // Gridbag Layouts have some public arrays, accept one but only one.
        // public double[] rowWeights
        // public double[] colWeights
        //
        final GridBagLayout lm = CustomCodeProxy.getTypeAnalyser ().instantiate ("GridBagLayout");

        if (st.hasMoreTokens ()) {
            try {
                final String fieldname = st.nextToken ();
                final Field field = lm.getClass ().getField (fieldname);
                if (field != null) {
                    final Class<?> fieldtype = field.getType ();

                    if (int [].class.equals (fieldtype)) {
                        field.set (lm, Util.ia (st));
                    } else if (double [].class.equals (fieldtype)) {
                        field.set (lm, Util.da (st));
                    }

                }
            } catch (final NoSuchFieldException e) {
                if (AppConstants.DEBUG_MODE) {
                    System.err.println (e.getMessage ());
                }
            } catch (final SecurityException e) {
                if (AppConstants.DEBUG_MODE) {
                    System.err.println (e.getMessage ());
                }
            } catch (final IllegalArgumentException e) {
                if (AppConstants.DEBUG_MODE) {
                    System.err.println (e.getMessage ());
                }
            } catch (final IllegalAccessException e) {
                if (AppConstants.DEBUG_MODE) {
                    System.err.println (e.getMessage ());
                }
            }
        }
        return lm;
    }

    /**
     * <p>
     * Creates a GridBagLayout instance.
     * </p>
     * 
     * <p>
     * <b>Attributes:</b>
     * </p>
     * <ul>
     * <li><code>columnWidths</code> (optional): The minimum column widths.</li>
     * <li><code>rowHeights</code> (optional): The minimum row heights.</li>
     * <li><code>columnWeights</code> (optional): The column weights.</li>
     * <li><code>rowWeights</code> (optional): The row weights.</li>
     * </ul>
     * 
     * <p>
     * <b>Examples for Valid XML element notations:</b>
     * </p>
     * <ul>
     * <li><code>&lt;layout type="GridBagLayout"/&gt;</code></li>
     * <li>
     * <code>&lt;layout type="GridBagLayout" columnWidths="5, 5, 10, 33" rowWeights="0,0,1.0,0"/&gt;</code>
     * </li>
     * </ul>
     */
    @Override
    public GridBagLayout convertLayoutElement (final Element element) {
        final String columnWidths = Attribute.getAttributeValue (element,
                "columnWidths");
        final String rowHeights = Attribute.getAttributeValue (element,
                "rowHeights");
        final String columnWeights = Attribute.getAttributeValue (element,
                "columnWeights");
        final String rowWeights = Attribute.getAttributeValue (element,
                "rowWeights");

        final GridBagLayout lm = CustomCodeProxy.getTypeAnalyser ().instantiate ("GridBagLayout");


        CustomCodeProxy.doProxy (this, lm, columnWidths, rowHeights, columnWeights, rowWeights);
        
        return lm;
    }

    /**
     * Returns "gridbaglayout".
     */
    @Override
    public String getID () {
        return "gridbaglayout";
    }
}
