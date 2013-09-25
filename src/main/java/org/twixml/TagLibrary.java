/*--
 $Id: TagLibrary.java,v 1.1 2004/03/01 07:56:07 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.twixml.technoproxy.CustomCodeProxy;

/**
 * A skeletal impementation of a TagLibrary<br>
 * A TagLibrary has a collection of Factories. Every Tag is mapped to a Factory
 * which is used to build the java object during document parsing. Date: Dec 9,
 * 2002
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public abstract class TagLibrary {

    private final Map<String, Factory> tags = new HashMap<String, Factory> ();

    /**
     * Constructs a new TagLibrary and regisiters all factories.
     */
    public TagLibrary () {
        this.registerTags ();
    }

    /**
     * Returns the Factory that is currently registered for the given Tag name
     * 
     * @param template
     *            <code>Class</code>
     * @return <code>Factory</code> - regsitered for the given tag name
     */
    public Factory getFactory (Class<?> template) {
        Factory factory = null;
        final Iterator<Factory> it = this.tags.values ().iterator ();
        while ( (it != null) && it.hasNext ()) {
            final Factory f = it.next ();
            if (f.getTemplate ().equals (template)) {
                factory = f;
                break;
            }
        }
        return factory;
    }

    /**
     * Returns the Factory that is currently registered for the given Tag name
     * 
     * @param name
     *            <code>String</code>
     * @return <code>Factory</code> - regsitered for the given tag name
     */
    public Factory getFactory (String name) {
        return this.tags.get (name.toLowerCase ());
    }

    /**
     * Returns a setter method by name for a specified template class
     * 
     * @param template
     *            <code>Class</code> template class
     * @param name
     *            <code>Sting</code> method name
     * @return <code>Method</code> - a setter method for the given class.
     * @see #guessSetter(Class, String)
     * @see org.twixml.Factory#getSetter(String)
     */
    protected Method getSetter (Class<?> template, String name) {
        Method method = null;
        final Factory factory = this.getFactory (template.getName ());
        if (factory != null) {
            method = factory.getSetter (name);
        }
        return method;
    }

    /**
     * @return <code>Map</code> - all registered tags.
     * 
     *         <pre>
     * Use athe tag names to get to the factories
     * </pre>
     */
    public Map<String, Factory> getTagClasses () {
        return this.tags;
    }

    /**
     * Returns a setter method by name for a specified template class
     * 
     * @param template
     *            <code>Class</code> template class
     * @param name
     *            <code>Sting</code> attribute name
     * @return <code>Method</code> - a setter method for the given class, able
     *         to modify the property.
     * @see #getSetter(Class, String)
     * @see org.twixml.Factory#guessSetter(String)
     */
    protected Method guessSetter (Class<?> template, String name) {
        Method method = null;
        final Factory factory = this.getFactory (template.getName ());
        if (factory != null) {
            method = factory.guessSetter (name);
        }
        return method;
    }

    /**
     * Registers a class for the given tag name
     * 
     * @param name
     *            <code>String</code> the tag's name
     * @param template
     *            <code>Class</code> the java class that represents the tag
     */
    public void registerTag (String name, Class<?> template) {
        CustomCodeProxy.doProxy (this, template, name);
    }

    /**
     * Registers a factory for the given tag name
     * 
     * @param name
     *            <code>String</code> the tag's name
     * @param factory
     *            <code>FactoryFactory</code> factory to create an Instance of
     *            the tag
     */
    public void registerTag (String name, Factory factory) {
        this.tags.put (name.toLowerCase (), factory);
    }

    /**
     * Registers all factories for the TagLibrary.
     */
    abstract protected void registerTags ();

    /**
     * Un-registers (removes) a registered tag.
     * 
     * @param name
     *            <code>String</code> the tag's name
     * @return <code>boolean</code> true if tag was registered befoire and now
     *         successfuly removed.
     */
    public boolean unregisterTag (String name) {
        return (null != this.tags.remove (name));
    }

}
