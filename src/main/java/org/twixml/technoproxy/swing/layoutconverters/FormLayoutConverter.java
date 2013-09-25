package org.twixml.technoproxy.swing.layoutconverters;

import org.twixml.technoproxy.ProxyCode;

import com.jgoodies.forms.layout.FormLayout;

public class FormLayoutConverter extends ProxyCode<org.twixml.layoutconverters.FormLayoutConverter<FormLayout>> {

    public FormLayoutConverter (
            org.twixml.layoutconverters.FormLayoutConverter<FormLayout> source1) {
        super (source1);
    }
    
    public void convertLayoutElement (FormLayout lm, int [][]columnGroupIndices, int [][] rowGroupIndices){
        if (columnGroupIndices != null) {
            lm.setColumnGroups (columnGroupIndices);
        }
        if (rowGroupIndices != null) {
            lm.setRowGroups (rowGroupIndices);
        }
    }

}
