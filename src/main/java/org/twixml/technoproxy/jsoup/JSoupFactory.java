/*--
 $Id: DefaultFactory.java,v 1.1 2004/03/01 07:56:05 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.technoproxy.jsoup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.twixml.Factory;

/**
 * The <code>DefaultFactory</code> is a default implementation of the
 * <code>Factory</code> Interface.
 * <p>
 * The DefaultFactory registers all setter methods that take a single producable
 * paramter with a class template
 * </p>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public final class JSoupFactory implements Factory {
    private static final Map<String, String> TAG               = new HashMap<String, String> () {
                                                                   /**
         * 
         */
                                                                   private static final long serialVersionUID = -4838706878848755022L;

                                                                   {
                                                                       this.put (
                                                                               "menubar",
                                                                               "nav");
                                                                       this.put (
                                                                               "menuitem",
                                                                               "li");
                                                                   }
                                                               };
    private static final Map<String, String> MATCHING          = new HashMap<String, String> () {
                                                                   private static final long serialVersionUID = -4838706878848755022L;
                                                                   {
                                                                       this.put (
                                                                               "menubar",
                                                                               "navbar navbar-nav");
                                                                       this.put (
                                                                               "menu",
                                                                               "dropdown");
                                                                       this.put (
                                                                               "button",
                                                                               "btn btn-default");
                                                                       this.put (
                                                                               "toolbar",
                                                                               "btn-toolbar");
                                                                       this.put (
                                                                               "menuitem",
                                                                               "");
                                                                   }
                                                               };

    /** Collection for all setter methods */
    private final Collection<Method>         setters           = new ArrayList<Method> ();

    /** The factory creates instances of this Class<?> */
    private final Class<?>                   template;

    /** Priority to resolve method name clashes */
    protected Class<?> []                    parameterPriority = {
            String.class, float.class, double.class, boolean.class, char.class,
            long.class, byte.class, int.class                 };

    /**
     * Creates a new Factory for the given <code>Class</code> template.
     * 
     * @param template
     *            <code>Class</code>
     * 
     *            <p>
     *            <b>Note:</b><br>
     *            Only <i>set</i>Methods that take a single parameter are
     *            considered. Moreover, to be regsitered, a Converter needs to
     *            be available in the ConverterLibrary that can create instances
     *            of the parameter type.
     *            </p>
     */
    public JSoupFactory (final Class<?> template) {
        this.template = template;
        //
        // Collects all set<Methods> that require a single parameter, which can
        // be created by an Converter.
        //
        this.registerSetters ();
    }

    @Override
    public Object getLeaf (final Object obj) {
        if (! (obj instanceof org.jsoup.nodes.Element)) {
            return obj;
        }
        Element element = (org.jsoup.nodes.Element) obj;
        while (element.children ().size () > 0) {
            element = element.child (element.children ().size () - 1);
        }
        return element;
    }

    /**
     * Returns a Setter Method that accepts the given class as a parameter
     * 
     * @param template
     *            <code>Class</code>
     * @return <code>Method</code> - setter that accepts the given class as a
     *         parameter
     * @see org.twixml.Factory#getSetter(java.lang.Class)
     */
    @Override
    public Method getSetter (final Class<?> template) {
        try {
            return org.jsoup.nodes.Element.class.getDeclaredMethod ("attr",
                    String.class, String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    /**
     * Returns a setter method by name<br>
     * 
     * @param name
     *            <code>String</code> name of the setter method
     * @return <code>Method</code> - setter method which can be invoked on an
     *         object of the template class
     * @see #guessSetter
     * @see org.twixml.Factory <pre>
     * <b>Typical Use:</b>
     * <p>Method method = factory.getSetter(&quot;set&quot; + Parser.capitalize(attr.getName()));</p>
     * </pre>
     */
    @Override
    public Method getSetter (final String name) {
        try {
            return org.jsoup.nodes.Element.class.getDeclaredMethod ("attr",
                    String.class, String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    /**
     * @return <code>Collection</code> containing all available setter methods
     */
    @Override
    public Collection<Method> getSetters () {
        return this.setters;
    }

    /**
     * @return class - <code>Class</code> the backing class template
     */
    @Override
    public Class<?> getTemplate () {
        return this.template;
    }

    /**
     * Returns a setter method by a Attribute name. Differently to the
     * <code>getSetter</code> method, here the attibute name can be used
     * directly and case doesn't matter.<br>
     * 
     * @param name
     *            <code>String</code> name of the setter method
     * 
     * @return <code>Method</code> - setter method which can invoked on an
     *         object of the template class
     * 
     * @see #getSetter <pre>
     * <b>Typical Use:</b>
     *      <p>Method method = factory.getSetter( attr.getName() );</p>
     * </pre>
     */
    @Override
    public Method guessSetter (final String name) {
        try {
            return org.jsoup.nodes.Element.class.getDeclaredMethod ("attr",
                    String.class, String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    /**
     * Create a new component instance
     * 
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    @Override
    public Object newInstance (final String mainClass) throws Exception {
        final String tagName = JSoupFactory.TAG.get (mainClass) != null ? JSoupFactory.TAG
                .get (mainClass) : "div";
        final String className = JSoupFactory.MATCHING.get (mainClass) != null ? JSoupFactory.MATCHING
                .get (mainClass) : mainClass;
        org.jsoup.nodes.Element e = new org.jsoup.nodes.Element (
                Tag.valueOf (tagName), "");
        e.addClass (className);
        e = this.specialCases (e, mainClass, new Object [0]);
        return e;
    }

    /**
     * Creates a new Object which class is {@link #getTemplate()}. A default
     * constructor is only used if no constructor is available, accepting the
     * provided parameter
     * 
     * @param parameter
     *            <code>Object</code>, parameter used during construction or
     *            initialization.
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    @Override
    public Object newInstance (final String mainClass, final Object parameter)
            throws Exception {
        final String tagName = JSoupFactory.TAG.get (mainClass) != null ? JSoupFactory.TAG
                .get (mainClass) : "div";
        // parameter
        final String className = JSoupFactory.MATCHING.get (mainClass) != null ? JSoupFactory.MATCHING
                .get (mainClass) : mainClass;
        org.jsoup.nodes.Element result = new org.jsoup.nodes.Element (
                Tag.valueOf (tagName), "");
        result.attr ("name", className);
        result.addClass (JSoupFactory.MATCHING.get (mainClass));
        result = this.specialCases (result, mainClass,
                new Object [] { parameter });
        return result;
    }

    /**
     * Creates a new Object which class is {@link #getTemplate()} and the
     * constructor parameter are <code>parameter</code>.
     * 
     * @param parameter
     *            <code>Object[]</code> the parameter array to be passed into
     *            the constructor
     * @return <code>Object</object> - the created object, an instance of the template class
     * @throws InstantiationException
     *             if the creation of the object failed
     * @throws IllegalAccessException
     *             if the constructor is either private or protected.
     * @throws InvocationTargetException
     *             if the constructor invoked throws an exception
     * 
     *             idea suggested by Frank Meissner <f.meissner@web.de>
     * 
     */
    @Override
    public Object newInstance (final String mainClass, final Object [] parameter)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        final String tagName = JSoupFactory.TAG.get (mainClass) != null ? JSoupFactory.TAG
                .get (mainClass) : "div";
        final String className = JSoupFactory.MATCHING.get (mainClass) != null ? JSoupFactory.MATCHING
                .get (mainClass) : mainClass;
        org.jsoup.nodes.Element e = new org.jsoup.nodes.Element (
                Tag.valueOf (tagName), "");
        e.addClass (className);
        e = this.specialCases (e, mainClass, parameter);
        return e;
    }

    /**
     * Returns a priority ID of the given type based on a priority arrray
     * 
     * @param type
     *            <code>Class</code>
     * @return <code>int</code> parameter type priority
     */
    protected int priority (final Class<?> type) {
        for (int i = 0 ; i < this.parameterPriority.length ; i++) {
            if (type.isAssignableFrom (this.parameterPriority [i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Registers all available setter methods meeting these rules:
     * <ul>
     * <li>Method name needs to start with <i>set</i></li>
     * <li>Method signature specifies exactly one parameter</li>
     * <li>if methods have the same name then super class methods are ignored</li>
     * <li>if methods have the same name and are implemented in the same class,
     * then only the method which parameter type has the highest priority is
     * registered</li>
     * </ul>
     */
    protected void registerSetters () {
        // Useless method since we are using the Element.class
    }

    /**
     * Remove the given method form the collection of supported setters.
     * 
     * @param method
     *            <code>Method</code>
     */
    public void removeSetter (final Method method) {
        this.setters.remove (method);
    }

    private Element specialCases (Element e, final String mainClass,
            final Object [] parameter) {
        if ("label".equals (mainClass) && (parameter.length > 0)) {
            e.appendChild (new TextNode (parameter [0].toString (), ""));
        } else if ("combobox".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("select"), "");
            @SuppressWarnings ("unchecked")
            final Iterable<String> iterable = (Iterable<String>) parameter [0];
            if ( (parameter.length > 0) && (parameter [0] instanceof Iterable)) {
                for (final String string : iterable) {
                    e.append ("<option value=\"" + string + "\">" + string
                            + "</option>");
                }
            }
        } else if ("textfield".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("input"), "");
            e.attr ("type", "text");
        } else if ("toolbar".equals (mainClass)) {
            e.append ("<div class=\"btn-group btn-group-lg\"></div>");
        } else if ("menubar".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("div"), "");
            e.addClass ("collapse navbar-collapse navbar-ex1-collapse");
            e.append ("<ul class=\"nav navbar-nav\"></ul>");
        } else if ("menu".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("li"), "");
            e.addClass ("dropdown");
            e.append ("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">[text]<b class=\"caret\"></b></a>");
            e.append ("<ul class=\"dropdown-menu\"></ul>");
        } else if ("menuitem".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("li"), "");
            e.append ("<a href=\"#\">[text]</a>");
        } else if ("separator".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("li"), "");
            e.addClass ("divider");
        } else if ("frame".equals (mainClass)) {
            e = new org.jsoup.nodes.Element (Tag.valueOf ("div"), "");
            e.addClass ("panel panel-default");
            e.append ("<div class=\"panel-heading\"><h3 class=\"panel-title\">Panel title</h3></div>");
            e.append ("<div class=\"panel-body\"></div>");
        } else if ("menubar".equals (mainClass)) {
            e.attr ("role", "navigation");
        }
        return e;
    }
}