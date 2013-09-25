package org.twixml.technoproxy.jsoup.layoutconverters;

import org.twixml.technoproxy.ProxyCode;

public class FormLayoutConverter
        extends
        ProxyCode<org.twixml.layoutconverters.FormLayoutConverter<org.jsoup.nodes.Element>> {

    public FormLayoutConverter (
            org.twixml.layoutconverters.FormLayoutConverter<org.jsoup.nodes.Element> source1) {
        super (source1);
    }

    public void convertLayoutElement (org.jsoup.nodes.Element lm,
            int [][] columnGroupIndices, int [][] rowGroupIndices) {
        /*
         * if (columnGroupIndices != null) { lm.setColumnGroups
         * (columnGroupIndices); } if (rowGroupIndices != null) {
         * lm.setRowGroups (rowGroupIndices); }
         */
    }

}
