package org.twixml.technoproxy.jsoup.layoutconverters;

import org.twixml.Attribute;
import org.twixml.technoproxy.ProxyCode;
import org.twixml.technoproxy.jsoup.layout.BorderLayout;

public class BorderLayoutConverter
        extends
        ProxyCode<org.twixml.layoutconverters.BorderLayoutConverter<BorderLayout>> {
    public BorderLayoutConverter (
            final org.twixml.layoutconverters.BorderLayoutConverter<BorderLayout> source1) {
        super (source1);
    }

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
    public Object convertConstraintsAttribute (final Attribute attr) {
        return (attr.getValue ().indexOf ('.') != -1 ? attr.getValue ()
                .substring (attr.getValue ().lastIndexOf ('.') + 1) : attr
                .getValue ());
    }
}
