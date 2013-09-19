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

package org.swixml.layoutconverters;

import java.lang.reflect.Field;
import java.util.StringTokenizer;

import org.swixml.AppConstants;
import org.swixml.Attribute;
import org.swixml.LayoutConverter;
import org.swixml.converters.Util;
import org.swixml.technoproxy.CustomCodeProxy;
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
