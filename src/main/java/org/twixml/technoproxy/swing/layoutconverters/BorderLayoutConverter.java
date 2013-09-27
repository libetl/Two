package org.twixml.technoproxy.swing.layoutconverters;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import org.twixml.Attribute;
import org.twixml.technoproxy.CustomCodeProxy;
import org.twixml.technoproxy.ProxyCode;

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
}
