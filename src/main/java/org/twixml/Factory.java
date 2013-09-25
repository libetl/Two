/*--
 $Id: Factory.java,v 1.1 2004/03/01 07:56:05 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * An interface to represent a generic factory
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 */
public interface Factory {
    /** Specifies the prefix string for all setter methods */
    static final String SETTER_ID = "set";
    static final String ADDER_ID  = "add";

    /**
     * Eventually, the obj generated itself a tree. The leaf has to be
     * considered instead of the parent.
     * 
     * @param obj
     * @return
     */
    Object getLeaf (Object obj);

    /**
     * Returns a setter method, which accepts a parameter of the given type
     * 
     * @param template
     *            <code>Class</code> type of the setter method's parameter
     * @return <code>Method</code> setter method which maybe invoked on an
     *         object of the template class
     */
    Method getSetter (Class<?> template);

    /**
     * Returns a setter method by name<br>
     * 
     * @param name
     *            <code>String</code> name of the setter method
     * @return <code>Method</code> - setter method which can be invoked on an
     *         object of the template class
     * @see #guessSetter <pre>
     * <b>Typical Use:</b>
     * <p>Method method = factory.getSetter(&quot;set&quot; + Parser.capitalize(attr.getName()));</p>
     * </pre>
     */
    Method getSetter (String name);

    /**
     * @return <code>Collection</code> - containing all available setter methods
     */
    Collection<Method> getSetters ();

    /**
     * @return class - <code>Class</code> the backing class template
     */
    Class<?> getTemplate ();

    /**
     * Returns a setter method by a Attribute name. Differently to the
     * <code>getSetter</code> method, here the attibute name can be used
     * directly and case doesn't matter.<br>
     * 
     * @param name
     *            <code>String</code> name of the setter method
     * @return <code>Method</code> - setter method which can invoked on an
     *         object of the template class
     * @see #getSetter <pre>
     * <b>Typical Use:</b>
     * <p>Method method = factory.getSetter( attr.getName() );</p>
     * </pre>
     */
    Method guessSetter (String name);

    /**
     * Create a new component instance
     * 
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    Object newInstance (String id) throws Exception;

    /**
     * Creates a new Object which class is {@link #getTemplate()}
     * 
     * @param parameter
     *            <code>Object</code>, parameter used during construction or
     *            initialization.
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    Object newInstance (String id, Object parameter) throws Exception;

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
     */
    Object newInstance (String id, Object [] parameter)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException;

}
