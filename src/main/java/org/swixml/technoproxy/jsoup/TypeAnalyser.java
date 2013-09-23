package org.swixml.technoproxy.jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class TypeAnalyser implements org.swixml.technoproxy.TypeAnalyser {

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getCompatibleClass (String string) {
        return (Class<T>) Element.class;
    }

    @Override
    public boolean isConvenient (Object o, String test) {
        boolean result = false;
        if (o == null || !(o instanceof Element)){
            return result;
        }
        Class<?> c = o.getClass ();
        Element e = (Element) o;
        result |= "LayoutManager".equals (test) && LayoutManager.class.equals (o.getClass ());
        result |= "Frame".equals (test)
                && (Element.class.isAssignableFrom (c) && e.hasClass ("frame"));
        result |= "Component".equals (test)
                && (Element.class.isAssignableFrom (c) && e.hasClass ("component"));
        result |= "MenuBar".equals (test)
                && (Element.class.equals (c) && e.hasClass ("menubar"));
        result |= "Container".equals (test)
                && (Element.class.isAssignableFrom (c) && e.hasClass ("container"));
        result |= "AbstractButton".equals (test)
                && (Element.class.isAssignableFrom (c) && e.hasClass ("abstractbutton"));
        return result;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public <T> T instantiate (String clazz, Object... params) {
        Element e = new Element (Tag.valueOf ("div"), null);
        e.addClass (clazz);
        return (T) e;
    }

	@SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getMostSuperClass (String string) {
	    return (Class<T>) Element.class;
    }

}
