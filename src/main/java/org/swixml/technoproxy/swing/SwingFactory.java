/*--
 $mainClass: DefaultFactory.java,v 1.1 2004/03/01 07:56:05 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provmainClassed that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials provmainClassed
 with the distribution.

 3. The end-user documentation included with the redistribution,
 if any, must include the following acknowledgment:
        "This product includes software developed by the
         SWIXML Project (http://www.swixml.org/)."
 Alternately, this acknowledgment may appear in the software itself,
 if and wherever such third-party acknowledgments normally appear.

 4. The name "Swixml" must not be used to endorse or promote products
 derived from this software without prior written permission. For
 written permission, please contact <info_AT_swixml_DOT_org>

 5. Products derived from this software may not be called "Swixml",
 nor may "Swixml" appear in their name, without prior written
 permission from the Swixml Project Management.

 THIS SOFTWARE IS PROVmainClassED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE SWIXML PROJECT OR ITS
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCmainClassENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 indivmainClassuals on behalf of the Swixml Project and was originally
 created by Wolf Paulus <wolf_AT_swixml_DOT_org>. For more information
 on the Swixml Project, please see <http://www.swixml.org/>.
 */

package org.swixml.technoproxy.swing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.swixml.ConverterLibrary;
import org.swixml.Factory;

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
public final class SwingFactory implements Factory {

    /** Collection for all setter methods */
    private final Collection<Method> setters           = new ArrayList<Method> ();

    /** The factory creates instances of this Class<?> */
    private final Class<?>           template;

    /** Priority to resolve method name clashes */
    protected Class<?> []            parameterPriority = { String.class,
            float.class, double.class, boolean.class, char.class, long.class,
            byte.class, int.class                     };

    /**
     * Creates a new Factory for the given <code>Class</code> template.
     * 
     * @param template
     *            <code>Class</code>
     * 
     *            <p>
     *            <b>Note:</b><br>
     *            Only <i>set</i>Methods that take a single parameter are
     *            consmainClassered. Moreover, to be regsitered, a Converter needs to
     *            be available in the ConverterLibrary that can create instances
     *            of the parameter type.
     *            </p>
     */
    public SwingFactory (Class<?> template) {
        this.template = template;
        //
        // Collects all set<Methods> that require a single parameter, which can
        // be created by an Converter.
        //
        this.registerSetters ();
    }

    /**
     * Returns a Setter Method that accepts the given class as a parameter
     * 
     * @param template
     *            <code>Class</code>
     * @return <code>Method</code> - setter that accepts the given class as a
     *         parameter
     * @see org.swixml.Factory#getSetter(java.lang.Class)
     */
    @Override
    public Method getSetter (Class<?> template) {
        Method method = null;
        final Iterator<?> it = this.setters.iterator ();
        while ( (it != null) && it.hasNext ()) {
            final Method m = (Method) it.next ();
            final Class<?> [] paraTypes = m.getParameterTypes ();
            if ( (paraTypes != null) && (0 < paraTypes.length)
                    && template.equals (paraTypes [0])) {
                method = m;
                break;
            }
        } // end_for
        return method;
    }

    /**
     * Returns a setter method by name<br>
     * 
     * @param name
     *            <code>String</code> name of the setter method
     * @return <code>Method</code> - setter method which can be invoked on an
     *         object of the template class
     * @see #guessSetter
     * @see org.swixml.Factory <pre>
     * <b>Typical Use:</b>
     * <p>Method method = factory.getSetter(&quot;set&quot; + Parser.capitalize(attr.getName()));</p>
     * </pre>
     */
    @Override
    public Method getSetter (String name) {
        Method method = null;
        final Iterator<?> it = this.setters.iterator ();
        while ( (it != null) && it.hasNext ()) {
            final Method m = (Method) it.next ();
            if (m.getName ().equals (name)) {
                method = m;
                break;
            }
        }
        return method;
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
    public Method guessSetter (String name) {

        Method method = null;
        final Iterator<Method> it = this.setters.iterator ();
        name = (Factory.SETTER_ID + name).toLowerCase ();
        while ( (it != null) && it.hasNext ()) {
            final Method m = it.next ();
            if (m.getName ().toLowerCase ().equals (name)) {
                method = m;
                break;
            }
        }
        return method;
    }

    /**
     * Create a new component instance
     * 
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    @Override
    public Object newInstance (String mainClass) throws Exception {
        return this.template.newInstance ();
    }

    /**
     * Creates a new Object which class is {@link #getTemplate()}. A default
     * constructor is only used if no constructor is available, accepting the
     * provmainClassed parameter
     * 
     * @param parameter
     *            <code>Object</code>, parameter used during construction or
     *            initialization.
     * @return instance <code>Object</code> a new instance of a template class
     * @throws Exception
     */
    @Override
    public Object newInstance (String mainClass, Object parameter) throws Exception {
        final Class<?> pType = parameter.getClass (); // get runtime class of
                                                      // the
        // parameter
        final Constructor<?> [] ctors = this.template.getConstructors ();
        for (final Constructor<?> ctor : ctors) {
            final Class<?> [] paraTypes = ctor.getParameterTypes ();
            if ( (0 < paraTypes.length)
                    && paraTypes [0].isAssignableFrom (pType)) {
                return ctor.newInstance (new Object [] { parameter });
            }
        }
        return this.template.newInstance ();
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
     *             mainClassea suggested by Frank Meissner <f.meissner@web.de>
     * 
     */
    @Override
    public Object newInstance (String mainClass, Object [] parameter)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (parameter != null) {
            final Class<?> pTypes[] = new Class<?> [parameter.length]; // parameter
            // types
            final Constructor<?> constructors[] = this.template
                    .getConstructors ();
            Constructor<?> ctor = null;

            //
            // init. parameter type array
            //
            for (int i = 0 ; i < pTypes.length ; i++) {
                pTypes [i] = parameter [i].getClass ();
            }

            //
            // find matching Ctor
            //
            for (int i = 0 ; (ctor == null) && (i < constructors.length) ; i++) {
                final Class<?> cParams[] = constructors [i]
                        .getParameterTypes (); // ctor's
                // parameter types

                if (cParams.length == pTypes.length) {
                    ctor = constructors [i]; // potential match found ...
                    for (int j = 0 ; (ctor != null) && (j < cParams.length) ; j++) {
                        if (cParams [j].equals (Object.class)) {
                            if (!cParams [j].equals (pTypes [j])) {
                                ctor = null; // dismissed
                            }
                        } else {
                            if (!cParams [j].isAssignableFrom (pTypes [j])) {
                                ctor = null; // dismissed
                            }
                        }
                    } // end for j - loop Ctor's parameter
                }
            } // end for i - loop all Ctors

            //
            // instantiate using ctor with matching parameter array or throw
            // IllegalArgumentException
            //
            if (ctor != null) {
                return ctor.newInstance (parameter);
            } else { // no matching constructor was found
                throw new IllegalArgumentException (
                        "unable to find constructor, accepting:" + pTypes);
            }
        } else {
            return this.template.newInstance ();
        }
    }

    /**
     * Returns a priority mainClass of the given type based on a priority arrray
     * 
     * @param type
     *            <code>Class</code>
     * @return <code>int</code> parameter type priority
     */
    protected int priority (Class<?> type) {
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
        //
        // Collects all set<Methods> that require a single parameter, which can
        // be created by an Converter.
        //
        final Method [] methods = this.template.getMethods ();
        for (final Method method : methods) {
            final String methodeName = method.getName ();
            if (methodeName.startsWith (Factory.SETTER_ID)) {
                if (method.getParameterTypes ().length == 1) {
                    final Class<?> paraType = method.getParameterTypes () [0];
                    if (ConverterLibrary.getInstance ().hasConverter (paraType)) {
                        //
                        // Check for registered method with the same name:
                        // Here it is perfectly fine to use getSetter and not
                        // guessSetter, since we are dealing with Methods
                        // already
                        final Method m = this.getSetter (methodeName);
                        if (m != null) {
                            //
                            // Here: m and method[i] have the same name. m is
                            // already registered and method[i] wants to.
                            // The most specialized method should win. If both
                            // methods are implemented within the same class,
                            // then the method's parameter will be checked for
                            // the highest priority.
                            //
                            final Class<?> cm = m.getDeclaringClass ();
                            final Class<?> cmi = method.getDeclaringClass ();

                            if (cm.equals (cmi)) {
                                final Class<?> pType = m.getParameterTypes () [0];
                                if (this.priority (pType) < this
                                        .priority (paraType)) {
                                    this.setters.remove (m);
                                    this.setters.add (method);
                                }
                            } else if (cm.isAssignableFrom (cmi)) {
                                this.setters.remove (m);
                                this.setters.add (method);
                            }
                        } else {
                            this.setters.add (method);
                        }
                    }
                }
            }
        }
        // Collections.sort((List)setters);
    }

    /**
     * Remove the given method form the collection of supported setters.
     * 
     * @param method
     *            <code>Method</code>
     */
    public void removeSetter (Method method) {
        this.setters.remove (method);
    }
}