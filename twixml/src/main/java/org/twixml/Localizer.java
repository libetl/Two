/*--
 $Id: Localizer.java,v 1.2 2004/08/20 05:59:57 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The <code>Localizer</code> class provides consumers with a simple
 * localization tools: <code>getString(key)</code>. Locale and ResourceBundle
 * need to be set to use it. Since some setters accept comma separated lists of
 * Strings, e.g. a TabbedPane's setTitles methods, the Localizer will try to
 * split a given key by <i>commas</i> if the key doesn't resolve, i.e. a
 * <code>MissingResourceException</code> is thrown. <br />
 * For example, if the resource bundle contains strings for the following single
 * keys: <br />
 * a = Alpha <br />
 * b = Bravo <br />
 * c = Charlie<br />
 * then calling getString(&quot;a,b,c&quot;) will result in a String containing
 * the comma sepearted values, like &quot;Alpha,Brave,Charlie&quot; <br />
 * Look at the provided testcase for more details.
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.2 $
 */
public class Localizer {
    private static String SEPARATOR = ",";

    /**
     * Sets the regular expression used to split a key, that could not be found
     * in the resource bundle.
     * 
     * @param regExp
     *            <code>String</code>
     * @see String#split
     * @see #getString
     */
    public static void setSeparator (final String regExp) {
        Localizer.SEPARATOR = regExp;
    }

    private Locale         locale = Locale.getDefault ();
    private String         bundleName;
    private ResourceBundle bundle;

    private ClassLoader    cl     = Localizer.class.getClassLoader ();

    /**
     * @return <code>ClassLoader</code> returns the classloader attribute, which
     *         has probably been set by the Twixml
     */
    public ClassLoader getClassLoader () {
        return this.cl;
    }

    /**
     * Returns the localized String baseed on the given key. If the key cannot
     * be found, the key is returned insstead.
     * 
     * @param key
     *            <code>String</code>
     * @return <code>String</code> - localized String , or key , if no
     *         lacalization is found.
     */
    public String getString (final String key) {
        if (!this.isUsable ()) {
            return key;
        }
        String s = "";
        try {
            s = this.bundle.getString (key);
        } catch (final MissingResourceException e) {
            final String [] keys = key.split (Localizer.SEPARATOR);
            if (2 <= keys.length) {
                for (int i = 0 ; i < keys.length ; i++) {
                    s += (i == 0) ? this.getString (keys [i]) : ","
                            + this.getString (keys [i]);
                }
            } else {
                s = key;
            }
        } catch (final Exception e) {
            s = key; // key not found, return key
        }
        return s;
    }

    /**
     * Informs about the usablility of this Localizer.
     * 
     * @return <code>boolean</code> - true if Localizer is setup with Locale and
     *         ResourceBundle.
     */
    public boolean isUsable () {
        return ( (this.locale != null) && (this.bundle != null));
    }

    /**
     * Sets the ClassLoader attribute. The Localizer does not use the provided
     * classloader directly but return it when asked for.
     * 
     * @param cl
     *            <code>ClassLoader</code> - custom classloader
     */
    void setClassLoader (final ClassLoader cl) {
        this.cl = cl;
    }

    /**
     * Sets this Localizer's locale.
     * 
     * @param locale
     *            <code>Locale</code>
     */
    public void setLocale (final Locale locale) {
        if (locale == null) {
            this.locale = null;
            this.bundle = null;
            this.bundleName = null;
        } else if (this.locale != locale) {
            this.locale = locale;
            this.setResourceBundle (this.bundleName);
        }
    }

    /**
     * Sets this Localizer's ResourceBundle.
     * 
     * @param bundleName
     *            <code>String</code>ResourceBundle file / class name
     * @throws java.util.MissingResourceException
     *             - if no resource bundle for the specified base name can be
     *             found
     */
    public void setResourceBundle (final String bundleName)
            throws MissingResourceException {
        this.bundleName = bundleName;
        if (this.locale == null) {
            this.locale = Locale.getDefault ();
        }
        if (bundleName != null) {
            this.bundle = ResourceBundle.getBundle (bundleName, this.locale,
                    this.cl);
        } else {
            this.bundle = null;
        }
    }
}
