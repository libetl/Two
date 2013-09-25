/*--
 $Id: Util.java,v 1.1 2004/03/01 07:56:03 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import java.util.StringTokenizer;

import org.twixml.Attribute;
import org.w3c.dom.Element;

/**
 * Util. Class<?> with static helper methods
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public final class Util {
    /**
     * Return a capitalized version of the specified property name.
     * 
     * @param s
     *            <code>String</code> The property name
     * @return <code>String</code> given String with 1st letter capitalized
     */
    public static final String capitalize (final String s) {
        String cs = null;
        if ( (s != null) && (0 < s.length ())) {
            final char [] chars = s.toCharArray ();
            chars [0] = Character.toUpperCase (chars [0]);
            cs = new String (chars);
        }
        return cs;
    }

    /**
     * Returns the remaining tokens of a StringTokenizer in a double-Array
     * 
     * @param st
     *            <code>StringTokenizer</code>
     * @return <code>double[]</code> array containing the remaining tokens
     *         converted into double(s)
     */
    public static double [] da (StringTokenizer st) {
        final int size = st != null ? st.countTokens () : 0;
        final double [] a = new double [size];
        int i = 0;
        while ( (st != null) && st.hasMoreTokens ()) {
            try {
                a [i] = Double.parseDouble (st.nextToken ().trim ());
                i++;
            } catch (final NumberFormatException e) {
                // no exceptiion handling required
            }
        }
        final double [] b = new double [i];
        System.arraycopy (a, 0, b, 0, i);
        return b;
    }

    /**
     * Returns the integer value of the given XML attribute; or the default
     * value.
     */
    public static final int getInteger (final Element element,
            final String attr, int def) {
        final String value = Attribute.getAttributeValue (element, attr);
        if (value == null) {
            return def;
        }

        try {
            return Integer.parseInt (value.trim ());
        } catch (final NumberFormatException e) {
            // no exception handling required
            return def;
        }
    }

    /**
     * Returns the remaining tokens of a StringTokenizer in an int-Array
     * 
     * @param st
     *            <code>StringTokenizer</code>
     * @return <code>int[]</code> array containing the remaining tokens
     *         converted into int(s)
     */
    public static int [] ia (StringTokenizer st) {
        final int size = st != null ? st.countTokens () : 0;
        final int [] a = new int [size];
        int i = 0;
        while ( (st != null) && st.hasMoreTokens ()) {
            try {
                a [i] = Integer.parseInt (st.nextToken ().trim ());
                i++;
            } catch (final NumberFormatException e) {
                // no exceptiion handling required
            }
        }
        final int [] b = new int [i];
        System.arraycopy (a, 0, b, 0, i);
        return b;
    }
}
