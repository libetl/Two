package org.twixml.technoproxy.jsoup.layout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BorderLayout extends LayoutManager {

    @Override
    public void addConstraintedElement (final Element parent,
            final Element component, final String constraint) {
        final Elements e = parent.getElementsByClass (constraint.toString ());
        Element candidate = null;
        final Iterator<Element> it = e.iterator ();
        while ( (candidate == null) && it.hasNext ()) {
            final Element tmp = it.next ();
            if ( (tmp.parent () == parent)
                    || ( (tmp.parent () != null)
                            && (tmp.parent ().className () != null)
                            && tmp.parent ().className ()
                                    .contains ("BORDERLAYOUTLANE") && (tmp
                            .parent ().parent () == parent))) {
                candidate = tmp;
            }
        }
        if (candidate != null) {
            component.addClass (candidate.className ());
            candidate.replaceWith (component);
            if (Arrays.asList ("WEST", "CENTER", "EAST").contains (constraint)) {
                component.parent ().attr ("style", "min-height:50px");
            }
        } else {
            parent.appendChild (component);
        }
    }

    @Override
    public void apply (final Element e, final Element leaf) {
        super.apply (e, leaf);
        final List<String> regions = Arrays.<String> asList ("NORTH", "WEST",
                "CENTER", "EAST", "SOUTH");
        final List<String> aligns = Arrays.<String> asList ("text-center",
                "text-left", "text-center", "text-right", "text-center");
        final List<String> sizes = Arrays.<String> asList ("col-xs-12",
                "col-xs-4", "col-xs-4", "col-xs-4", "col-xs-12");
        leaf.append ("<div class=\"" + aligns.get (0) + " " + sizes.get (0)
                + " " + regions.get (0) + "\"></div>");
        leaf.append ("<div class=\"col-xs-12 BORDERLAYOUTLANE\"></div>");
        leaf.append ("<div class=\"" + aligns.get (4) + " " + sizes.get (4)
                + " " + regions.get (4) + "\"></div>");
        for (int i = 1 ; i < 4 ; i++) {
            leaf.child (1).append (
                    "<div class=\"" + aligns.get (i) + " " + sizes.get (i)
                            + " " + regions.get (i) + "\"></div>");

        }
    }

}
