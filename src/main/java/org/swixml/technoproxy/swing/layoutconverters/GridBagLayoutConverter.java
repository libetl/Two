package org.swixml.technoproxy.swing.layoutconverters;

import java.awt.GridBagLayout;
import java.util.StringTokenizer;

import org.swixml.converters.Util;
import org.swixml.technoproxy.ProxyCode;

public class GridBagLayoutConverter extends ProxyCode<org.swixml.layoutconverters.GridBagLayoutConverter<GridBagLayout>> {

    public GridBagLayoutConverter (
            org.swixml.layoutconverters.GridBagLayoutConverter<GridBagLayout> source1) {
        super (source1);
    }
    
    public void convertLayoutElement (GridBagLayout lm, String columnWidths, String rowHeights, String columnWeights, String rowWeights){

        if (columnWidths != null) {
            lm.columnWidths = Util.ia (new StringTokenizer (columnWidths, ","));
        }
        if (rowHeights != null) {
            lm.rowHeights = Util.ia (new StringTokenizer (rowHeights, ","));
        }
        if (columnWeights != null) {
            lm.columnWeights = Util
                    .da (new StringTokenizer (columnWeights, ","));
        }
        if (rowWeights != null) {
            lm.rowWeights = Util.da (new StringTokenizer (rowWeights, ","));
        }
    }

}
