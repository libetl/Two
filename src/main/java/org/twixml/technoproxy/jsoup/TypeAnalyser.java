package org.twixml.technoproxy.jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class TypeAnalyser extends org.twixml.technoproxy.TypeAnalyser {

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getCompatibleClass (final String string) {
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
        result |= "LayoutManager".equals (test)
                && LayoutManager.class.equals (o.getClass ());
        result |= "Frame".equals (test)
                && (Element.class.isAssignableFrom (c) && e.hasClass ("frame"));
        result |= "Component".equals (test)
                && (Element.class.isAssignableFrom (c) && e
                        .hasClass ("component"));
        result |= "MenuBar".equals (test)
                && (Element.class.equals (c) && e.hasClass ("navbar"));
        result |= "Container".equals (test)
                && (Element.class.isAssignableFrom (c) && e
                        .hasClass ("container"));
        result |= "AbstractButton".equals (test)
                && (Element.class.isAssignableFrom (c) && e
                        .hasClass ("abstractbutton"));
        return result;
    }

}
