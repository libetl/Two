package org.swixml.technoproxy.swing.layoutconverters;

import org.swixml.technoproxy.ProxyCode;

import com.jgoodies.forms.layout.FormLayout;

public class FormLayoutConverter extends ProxyCode<org.swixml.layoutconverters.FormLayoutConverter<FormLayout>> {

    public FormLayoutConverter (
            org.swixml.layoutconverters.FormLayoutConverter<FormLayout> source1) {
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
