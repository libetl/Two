package org.twixml.technoproxy.jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.twixml.technoproxy.jsoup.layout.BorderLayout;
import org.twixml.technoproxy.jsoup.layout.LayoutManager;

public class TypeAnalyser extends org.twixml.technoproxy.TypeAnalyser {

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getCompatibleClass (final String string) {
        if ("BorderLayout".equalsIgnoreCase (string)) {
            return (Class<T>) BorderLayout.class;
        }
        if ( (string != null) && string.toLowerCase ().endsWith ("layout")) {
            return (Class<T>) LayoutManager.class;
        }
        if ("WindowAdapter".equalsIgnoreCase (string)) {
            return (Class<T>) WindowAdapter.class;
        }
        if ("WindowEvent".equalsIgnoreCase (string)) {
            return (Class<T>) WindowEvent.class;
        }
        if ("ActionEvent".equalsIgnoreCase (string)) {
            return (Class<T>) ActionEvent.class;
        }
        if ("Action".equalsIgnoreCase (string)) {
            return (Class<T>) XAction.class;
        }
        return (Class<T>) Element.class;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getMostSuperClass (final String string) {
        return (Class<T>) Element.class;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public <T> T instantiate (final String clazz, final Object... params) {
        LayoutManager result = null;
        if ("BorderLayout".equalsIgnoreCase (clazz)) {
            result = new BorderLayout ();
            result.setParams (params);
        } else if ( (clazz != null) && clazz.toLowerCase ().endsWith ("layout")) {
            result = new LayoutManager ();
            result.setParams (params);
        }
        if (result != null) {
            return (T) result;
        }
        final Element e = new Element (Tag.valueOf ("div"), "");
        e.addClass (clazz);
        return (T) e;
    }

    @Override
    public boolean isConvenient (final Object o, final String test) {
        boolean result = false;
        if ( (o == null) || ! (o instanceof Element)) {
            return result;
        }
        final Class<?> c = o.getClass ();
        final Element e = (Element) o;
        final String s = e.className () + " ";
        result |= "LayoutManager".equals (test) && (o != null)
                && (o instanceof LayoutManager);
        result |= "Frame".equals (test)
                && (Element.class.isAssignableFrom (c) && s
                        .contains (" panel "));
        result |= "Component".equals (test)
                && (Element.class.isAssignableFrom (c) && e
                        .hasClass ("component"));
        result |= "MenuBar".equals (test)
                && (Element.class.equals (c) && s.contains (" navbar "));
        result |= "Container".equals (test)
                && (Element.class.isAssignableFrom (c) && s
                        .contains (" panel "));
        result |= "AbstractButton".equals (test)
                && (Element.class.isAssignableFrom (c) && s.contains (" btn "));
        return result;
    }

}
