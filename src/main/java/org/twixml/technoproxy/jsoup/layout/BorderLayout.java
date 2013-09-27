package org.twixml.technoproxy.jsoup.layout;

import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;

public class BorderLayout extends LayoutManager {

    @Override
    public void apply (final Element e, final Element leaf) {
        super.apply (e, leaf);
        final List<String> regions = Arrays.<String> asList ("NORTH", "WEST",
                "CENTER", "EAST", "SOUTH");
        final List<String> aligns = Arrays.<String> asList ("text-center",
                "text-left", "text-center", "text-right", "text-center");
        final List<String> sizes = Arrays.<String> asList ("col-xs-12",
                "col-xs-4", "col-xs-4", "col-xs-4", "col-xs-12");
        for (int i = 0 ; i < regions.size () ; i++) {
            leaf.append ("<div class=\"" + aligns.get (i) + " " + sizes.get (i)
                    + " " + regions.get (i) + "\"></div>");
        }
    }
}
