package org.twixml;

import java.util.Locale;

public class AppConstants {

    /**
     * static resource bundle
     */
    private static String  default_resource_bundle_name = null;
    /**
     * static locale
     */
    private static Locale  default_locale               = Locale.getDefault ();
    /**
     * Check is currently running on a Mac
     */
    private static boolean MAC_OSX                      = false;
    /**
     * static Mac OS X Support, set to true to support Mac UI specialties
     */
    private static boolean MAC_OSX_SUPPORTED            = true;
    /**
     * Debug / Release Mode
     */
    public static boolean  DEBUG_MODE                   = false;

    //
    // Static Initializer
    //
    /** display the swing release version to system out. */
    static {
        if (AppConstants.DEBUG_MODE) {
            System.out.println ("TwixML @version@");
        }
        try {
            AppConstants.MAC_OSX = System.getProperty ("os.name")
                    .toLowerCase ().startsWith (TwiXML.MAC_OSX_OS_NAME);
        } catch (final Exception e) {
            AppConstants.MAC_OSX = false;
        }
    }

    public static Locale getDefault_locale () {
        return AppConstants.default_locale;
    }

    public static String getDefault_resource_bundle_name () {
        return AppConstants.default_resource_bundle_name;
    }

    /**
     * Indicates if currently running on Mac OS X
     * 
     * @return <code>boolean</code>- indicating if currently running on a MAC
     */
    public static boolean isMacOSX () {
        return AppConstants.MAC_OSX;
    }

    /**
     * Indicates state of Mac OS X support (default is true = ON).
     * 
     * @return <code>boolean</code>- indicating MacOS support is enabled
     */
    public static boolean isMacOSXSupported () {
        return AppConstants.MAC_OSX_SUPPORTED;
    }

    public static void setDefault_locale (final Locale default_locale) {
        AppConstants.default_locale = default_locale;
    }

    public static void setDefault_resource_bundle_name (
            final String default_resource_bundle_name) {
        AppConstants.default_resource_bundle_name = default_resource_bundle_name;
    }

    /**
     * Sets the SwingEngine's global locale, to be used by all SwingEngine
     * instances. This locale can be overwritten however for a single instance,
     * if a <code>locale</code> attribute is places in the root tag of an XML
     * descriptor.
     * 
     * @param locale
     *            <code>Locale</code>
     */
    public static void setDefaultLocale (final Locale locale) {
        AppConstants.setDefault_locale (locale);
    }

    /**
     * Enables or disables support of Mac OS X GUIs
     * 
     * @param osx
     *            <code>boolean</code>
     */
    public static void setMacOSXSuport (final boolean osx) {
        AppConstants.MAC_OSX_SUPPORTED = osx;
    }

    /**
     * Sets the SwingEngine's global resource bundle name, to be used by all
     * SwingEngine instances. This name can be overwritten however for a single
     * instance, if a <code>bundle</code> attribute is places in the root tag of
     * an XML descriptor.
     * 
     * @param bundlename
     *            <code>String</code> the resource bundle name.
     */
    public static void setResourceBundleName (final String bundlename) {
        AppConstants.setDefault_resource_bundle_name (bundlename);
    }
}
