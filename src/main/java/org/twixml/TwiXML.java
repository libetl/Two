/*--
 $Id: SwingEngine.java,v 1.5 2005/06/29 08:02:37 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.twixml.technoproxy.CustomCodeProxy;
import org.twixml.technoproxy.TypeAnalyser;
import org.w3c.dom.Document;

/**
 * The TwiXML class is the rendering engine able to convert an XML descriptor
 * into another form
 * <p/>
 * <img src="doc-files/swixml_1_0.png" ALIGN="center">
 * </p>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @version $Revision: 1.5 $
 * @SuppressWarnings ("UnusedDeclaration")
 */
public class TwiXML<Container, Component, ActionListener, Label, ButtonGroup, LayoutManager> {
    //
    // Static Constants
    //
    /**
     * Mac OSX identifier in System.getProperty(os.name)
     */
    public static final String                                                                    MAC_OSX_OS_NAME        = "mac os x";

    /**
     * Mac OSX locale variant to localize strings like quit etc.
     */
    public static final String                                                                    MAC_OSX_LOCALE_VARIANT = "mac";

    /**
     * XML Error
     */
    private static final String                                                                   XML_ERROR_MSG          = "Invalid TwiXML Descriptor.";
    /**
     * IO Error Message.
     */
    private static final String                                                                   IO_ERROR_MSG           = "Resource could not be found ";
    /**
     * Mapping Error Message.
     */
    private static final String                                                                   MAPPING_ERROR_MSG      = " could not be mapped to any Object and remained un-initialized.";

    //
    // Static Member Variables
    //

    /**
     * main frame
     */
    private Container                                                                             appFrame;

    //
    // Member Variables
    //
    /**
     * Swixml Parser.
     */
    private final Parser<Container, Component, ActionListener, Label, ButtonGroup, LayoutManager> parser                 = new Parser<Container, Component, ActionListener, Label, ButtonGroup, LayoutManager> (
                                                                                                                                 this);

    /**
     * Client object hosting the swingengine, alternative to extending the
     * SwinEngine Class
     */
    private Object                                                                                client;

    /**
     * Root Component for the rendered swing UI.
     */
    private Container                                                                             root;

    /**
     * Swing object map, contains only those object that were given an id
     * attribute.
     */
    private final Map<String, Object>                                                             idmap                  = new HashMap<String, Object> ();

    /**
     * Flattened Swing object tree, contains all object, even the ones without
     * an id.
     */
    private Collection<Component>                                                                 components             = null;

    /**
     * access to taglib to let overwriting class add and remove tags.
     */
    private final Localizer                                                                       localizer              = new Localizer ();

    //
    // Private Constants
    //
    /**
     * Classload to load resources
     */
    private final TagLibrary                                                                      taglib                 = SimpleTagLibrary
                                                                                                                                 .getInstance ();

    /**
     * Localizer, setup by parameters found in the xml descriptor.
     */
    protected ClassLoader                                                                         cl                     = this.getClass ()
                                                                                                                                 .getClassLoader ();

    /**
     * Default ctor for a SwingEngine.
     */
    public TwiXML () {
        this.client = this;
        this.setLocale (AppConstants.getDefault_locale ());
        this.getLocalizer ().setResourceBundle (
                AppConstants.getDefault_resource_bundle_name ());

        try {
            if (AppConstants.isMacOSXSupported () && AppConstants.isMacOSX ()) {
                // Use apple's ScreenMenuBar instead of the MS-Window style
                // application's own menu bar
                System.setProperty ("com.apple.macos.useScreenMenuBar", "true");
                System.setProperty ("apple.laf.useScreenMenuBar", "true");

                // Don't let the growbox intrude other widgets
                System.setProperty ("apple.awt.showGrowBox", "true");
                System.setProperty (
                        "com.apple.mrj.application.growbox.intrudes", "false");
            }
        } catch (final AccessControlException e) {
            // intentionally empty
        }
    }

    /**
     * Constructs a new SwingEngine, rendering the provided XML into an UI
     * 
     * @param resource
     *            <code>String</code>
     * @deprecated
     */
    @Deprecated
    public TwiXML (final ClassLoader cl, final String resource) {
        this ();
        this.setClassLoader (cl);
        final InputStream in = cl.getResourceAsStream (resource);
        try {
            if (in == null) {
                throw new IOException (TwiXML.IO_ERROR_MSG + resource);
            }
            this.render (in, resource);
        } catch (final Exception e) {
            if (AppConstants.DEBUG_MODE) {
                System.err.println (e);
            }
        } finally {
            try {
                if (in != null) {
                    in.close ();
                }
            } catch (final Exception e) {
                // intentionally empty
            }
        }
    }

    /**
     * Constructor<?> to be used if the SwingEngine is not extend but used
     * through object composition.
     * 
     * @param client
     *            <code>Object</code> owner of this instance
     */
    public TwiXML (final Object client) {
        this ();
        this.client = client;
    }

    /**
     * Constructs a new SwingEngine, rendering the provided XML into an UI
     * 
     * @param resource
     *            <code>String</code>
     * @SuppressWarnings ("deprecation")
     */
    public TwiXML (final String resource) {
        this (TwiXML.class.getClassLoader (), resource);
    }

    /**
     * Removes all un-displayable compontents from the id map and deletes the
     * components collection (for recreation at the next request).
     * <p/>
     * 
     * <pre>
     *  A component is made undisplayable either when it is removed from a displayable containment hierarchy or when its
     * containment hierarchy is made undisplayable. A containment hierarchy is made undisplayable when its ancestor
     * window
     * is disposed.
     * </pre>
     * 
     * @return <code>int</code> number of removed componentes.
     */
    public int cleanup () {
        final List<String> zombies = new ArrayList<String> ();
        final Iterator<String> it = this.idmap.keySet ().iterator ();
        while ( (it != null) && it.hasNext ()) {
            final String key = it.next ();
            final Object obj = this.idmap.get (key);
            CustomCodeProxy.doProxy (this, obj, zombies, key);
        }
        for (int i = 0 ; i < zombies.size () ; i++) {
            this.idmap.remove (zombies.get (i));
        }
        this.components = null;
        return zombies.size ();
    }

    /**
     * Returns the UI component with the given name or null.
     * 
     * @param id
     *            <code>String</code> assigned name
     * @return <code>Component</code>- the GUI component with the given name or
     *         null if not found.
     */
    @SuppressWarnings ("unchecked")
    public Component find (final String id) {
        Object obj = this.idmap.get (id);
        if ( (obj != null)
                && ! (CustomCodeProxy.getTypeAnalyser ().isConvenient (
                        this.root, "Component"))) {
            obj = null;
        }
        return (Component) obj;
    }

    /**
     * Removes the id from the internal from the id map, to make the given id
     * available for re-use.
     * 
     * @param id
     *            <code>String</code> assigned name
     */
    public void forget (final String id) {
        this.idmap.remove (id);
    }

    /**
     * Returns an Iterator for all parsed GUI components.
     * 
     * @return <code>Iterator</code> GUI components iterator
     */
    @SuppressWarnings ("unchecked")
    public Iterator<Component> getAllComponentIterator () {
        if (this.components == null) {
            this.traverse ((Component) this.root,
                    this.components = new ArrayList<Component> ());
        }
        return this.components.iterator ();
    }

    /**
     * @return <code>Window</code> a parent for all dialogs.
     */
    public Container getAppFrame () {
        return this.appFrame;
    }

    /**
     * @return <code>ClassLoader</code>- the Classloader used for all <i>
     *         getResourse..()</i> and <i>loadClass()</i> calls.
     */
    public ClassLoader getClassLoader () {
        return this.cl;
    }

    /**
     * Returns the object which instantiated this SwingEngine.
     * 
     * @return <code>Object</code> SwingEngine client object
     *         <p/>
     *         <p>
     *         <b>Note:</b><br>
     *         This is the object used through introspection the actions and
     *         fileds are set.
     *         </p>
     */
    public Object getClient () {
        return this.client;
    }

    /**
     * Walks the whole tree to add all components into the
     * <code>components<code> collection.
     * 
     * @param c
     *            <code> Component</code> recursive start component.
     *            <p/>
     *            Note:There is another collection available that only tracks
     *            those object that were provided with an <em>id</em>attribute,
     *            which hold an unique id
     *            </p>
     * @return <code>Iterator</code> to walk all components, not just the id
     *         components.
     */
    public Iterator<Component> getDescendants (final Component c) {
        final List<Component> list = new ArrayList<Component> (12);
        this.traverse (c, list);
        return list.iterator ();
    }

    /**
     * Returns an Iterator for id-ed parsed GUI components.
     * 
     * @return <code>Iterator</code> GUI components iterator
     */
    public Iterator<Object> getIdComponentIterator () {
        return this.idmap.values ().iterator ();
    }

    /**
     * Returns the id map, containing all id-ed parsed GUI components.
     * 
     * @return <code>Map</code> GUI components map
     */
    public Map<String, Object> getIdMap () {
        return this.idmap;
    }

    /**
     * @return <code>Localizer</code>- the Localizer, which is used for
     *         localization.
     */
    public Localizer getLocalizer () {
        return this.localizer;
    }

    /**
     * Returns the root component of the generated Swing UI.
     * 
     * @return <code>Component</code>- the root component of the UI
     */
    public Container getRootComponent () {
        return this.root;
    }

    /**
     * @return <code>TagLibrary</code>- the Taglibray to insert custom tags.
     *         <p/>
     * 
     *         <pre>
     * <b>Note:</b>ConverterLibrary and TagLibray need to be set up before rendering is called.
     * </pre>
     */
    public TagLibrary getTaglib () {
        return this.taglib;
    }

    @SuppressWarnings ({ "unchecked" })
    public TypeAnalyser getTypeAnalyser () {
        return CustomCodeProxy
                .getTypeAnalyser ((Class<TwiXML<?, ?, ?, ?, ?, ?>>) this
                        .getClass ());
    }

    /**
     * Inserts swing objects rendered from an XML document into the given
     * container.
     * <p/>
     * 
     * <pre>
     *  Differently to the parse methods, insert does NOT consider the root node of the XML document.
     * </pre>
     * 
     * <pre>
     *  <b>NOTE:</b><br>insert() does NOT clear() the idmap before rendering.
     * Therefore, if this SwingEngine's parser was used before, the idmap still
     * contains (key/value) pairs (id, JComponent obj. references).<br>
     * If insert() is NOT
     * used to insert in a previously (with this very SwingEngine) rendered UI,
     * it is highly recommended to clear the idmap:
     * <br>
     *  <div>
     *    <code>mySwingEngine.getIdMap().clear()</code>
     *  </div>
     * </pre>
     * 
     * @param jdoc
     *            <code>Document</code> xml-doc path info
     * @param container
     *            <code>Container</code> target, the swing obj, are added to
     * @throws Exception
     *             <code>Exception</code> exception thrown by the parser
     */
    public void insert (final Document jdoc, final Container container)
            throws Exception {
        this.root = container;
        try {
            this.parser.parse (jdoc, container);
        } catch (final Exception e) {
            if (AppConstants.DEBUG_MODE) {
                System.err.println (e);
            }
            throw (e);
        }
        // reset components collection
        this.components = null;
        // initialize all client fields with UI components by their id
        this.mapMembers (this.client);
    }

    /**
     * Inserts swing objects rendered from an XML reader into the given
     * container.
     * <p/>
     * 
     * <pre>
     *  Differently to the render methods, insert does NOT consider the root node of the XML document.
     * </pre>
     * 
     * <pre>
     *  <b>NOTE:</b><br>insert() does NOT clear() the idmap before rendering.
     * Therefore, if this SwingEngine's parser was used before, the idmap still
     * contains (key/value) pairs (id, JComponent obj. references).<br>If insert() is NOT
     * used to insert in a previously (with this very SwingEngine) rendered UI, it is highly
     * recommended to clear the idmap:
     * <br>
     *  <div>
     *    <code>mySwingEngine.getIdMap().clear()</code>
     *  </div>
     * </pre>
     * 
     * @param reader
     *            <code>Reader</code> xml-file path info
     * @param container
     *            <code>Container</code> target, the swing obj, are added to.
     * @throws Exception
     */
    public void insert (final InputStream reader, final Container container)
            throws Exception {
        if (reader == null) {
            throw new IOException ();
        }
        final DocumentBuilder builder = DocumentBuilderFactory.newInstance ()
                .newDocumentBuilder ();
        this.insert (builder.parse (reader), container);
    }

    /**
     * Inserts swing objects rendered from an XML reader into the given
     * container.
     * <p/>
     * 
     * <pre>
     *  Differently to the render methods, insert does NOT consider the root node of the XML document.
     * </pre>
     * 
     * <pre>
     *  <b>NOTE:</b><br>insert() does NOT clear() the idmap before rendering.
     * Therefore, if this SwingEngine's parser was used before, the idmap still
     * contains (key/value) pairs (id, JComponent obj. references).<br>
     * If insert() is NOT used to insert in a previously (with this very SwingEngine)
     * rendered UI, it is highly recommended to clear the idmap:
     * <br>
     *  <div>
     *    <code>mySwingEngine.getIdMap().clear()</code>
     *  </div>
     * </pre>
     * 
     * @param resource
     *            <code>String</code> xml-file path info
     * @param container
     *            <code>Container</code> target, the swing obj, are added to.
     * @throws Exception
     */
    public void insert (final String resource, final Container container)
            throws Exception {
        final InputStream in = this.cl.getResourceAsStream (resource);
        if (in == null) {
            throw new IOException (TwiXML.IO_ERROR_MSG + resource);
        }
        try {
            this.insert (in, container);
        } finally {
            try {
                in.close ();
            } catch (final Exception ex) {
                // intentionally empty
            }
        }
    }

    /**
     * Inserts swing object rendered from an XML document into the given
     * container.
     * <p/>
     * 
     * <pre>
     *  Differently to the render methods, insert does NOT consider the root node of the XML document.
     * </pre>
     * 
     * <pre>
     *  <b>NOTE:</b><br>insert() does NOT clear() the idmap before rendering.
     * Therefore, if this SwingEngine's parser was used before, the idmap still
     * contains (key/value) pairs (id, JComponent obj. references).<br>If insert() is NOT
     * used to insert in a previously (with this very SwingEngine) rendered UI,
     * it is highly recommended to clear the idmap:
     * <br>
     *  <div>
     *    <code>mySwingEngine.getIdMap().clear()</code>
     *  </div>
     * </pre>
     * 
     * @param url
     *            <code>URL</code> url pointing to an XML descriptor *
     * @param container
     *            <code>Container</code> target, the swing obj, are added to.
     * @throws Exception
     */
    public void insert (final URL url, final Container container)
            throws Exception {
        final InputStream in = url.openStream ();
        if (in == null) {
            throw new IOException (TwiXML.IO_ERROR_MSG + url.toString ());
        }
        try {
            this.insert (in, container);
        } finally {
            try {
                in.close ();
            } catch (final Exception ex) {
                // intentionally empty
            }
        }
    }

    /**
     * Introspects the given object's class and initializes its non-transient
     * fields with objects that have been instanced during parsing. Mapping
     * happens based on type and field name: the fields name has to be equal to
     * the tag id, specified in the XML descriptor. The fields class has to be
     * assignable (equals or super class..) from the class that was used to
     * instance the tag.
     * 
     * @param obj
     *            <code>Object</code> target object to be mapped with instanced
     *            tags
     */
    protected void mapMembers (final Object obj) {
        if (obj != null) {
            this.mapMembers (obj, obj.getClass ());
        }
    }

    private void mapMembers (final Object obj, final Class<?> cls) {
        boolean fullaccess = true;

        if ( (obj != null) && (cls != null) && !Object.class.equals (cls)) {
            Field [] flds = null;
            try {
                flds = cls.getDeclaredFields ();
            } catch (final AccessControlException e) {
                fullaccess = false; // applet or otherwise restricted
                                    // environment
                flds = cls.getFields ();
            }
            //
            // loops through class' declared fields and try to find a matching
            // widget.
            //
            for (int i = 0 ; i < flds.length ; i++) {
                final Object widget = this.idmap.get (flds [i].getName ());
                if (widget != null) {
                    // field and object type need to be compatible and field
                    // must not be declared Transient
                    if (flds [i].getType ().isAssignableFrom (
                            widget.getClass ())
                            && !Modifier.isTransient (flds [i].getModifiers ())) {
                        try {

                            final boolean accessible = flds [i].isAccessible ();
                            flds [i].setAccessible (true);
                            flds [i].set (obj, widget);
                            flds [i].setAccessible (accessible);
                        } catch (final AccessControlException e) {
                            try {
                                fullaccess = false;
                                flds [i].set (obj, widget);
                            } catch (final IllegalAccessException e1) {
                            }

                        } catch (final IllegalArgumentException e) {
                            // intentionally empty
                        } catch (final IllegalAccessException e) {
                            // intentionally empty
                        }
                    }
                }

                //
                // If an intended mapping didn't work out the objects member
                // would remain un-initialized.
                // To prevent this, we try to instantiate with a default ctor.
                //
                if (flds [i] == null) {
                    if (!AppConstants.DEBUG_MODE) {
                        try {
                            flds [i].set (obj, flds [i].getType ()
                                    .newInstance ());
                        } catch (final IllegalArgumentException e) {
                            // intentionally empty
                        } catch (final IllegalAccessException e) {
                            // intentionally empty
                        } catch (final InstantiationException e) {
                            // intentionally empty
                        }
                    } else { // SwingEngine.DEBUG_MODE)
                        System.err.println (flds [i].getType () + " : "
                                + flds [i].getName ()
                                + TwiXML.MAPPING_ERROR_MSG);
                    }
                }
            }

            // Since getDeclaredFields() only works on the class itself, not the
            // super class,
            // we need to make this recursive down to the object.class
            if (fullaccess) {
                // only if we have access to the declared fields do we need to
                // visit the whole tree.
                this.mapMembers (obj, cls.getSuperclass ());
            }
        }
    }

    /**
     * Gets the parsing of the XML file started.
     * 
     * @param jdoc
     *            <code>Document</code> xml gui descritptor
     * @return <code>Object</code>- instanced swing object tree root
     */
    @SuppressWarnings ("unchecked")
    public Container render (final Document jdoc, final String name)
            throws Exception {
        this.idmap.clear ();
        try {
            this.root = (Container) this.parser.parse (jdoc, name);
        } catch (final Exception e) {
            if (AppConstants.DEBUG_MODE) {
                System.err.println (e);
            }
            throw (e);
        }
        // reset components collection
        this.components = null;
        // initialize all client fields with UI components by their id
        this.mapMembers (this.client);
        if ( (this.root == null)
                || CustomCodeProxy.getTypeAnalyser (
                        (Class<TwiXML<?, ?, ?, ?, ?, ?>>) this.getClass ())
                        .isConvenient (this.root, "Frame")) {
            this.setAppFrame (this.root);
        }
        return this.root;
    }

    /**
     * Gets the parsing of the XML file started.
     * 
     * @param xml_file
     *            <code>File</code> xml-file
     * @return <code>Object</code>- instanced swing object tree root
     */
    public Container render (final File xml_file) throws Exception {
        if (xml_file == null) {
            throw new IOException ();
        }
        final String simpleName = xml_file.getName ();
        final FileInputStream in = new FileInputStream (xml_file);
        try {
            return this.render (in, simpleName);
        } finally {
            try {
                in.close ();
            } catch (final Exception ex) {
                // intentionally empty
            }
        }
    }

    /**
     * Gets the parsing of the XML file started.
     * 
     * @param xml_reader
     *            <code>Reader</code> xml-file path info
     * @return <code>Object</code>- instanced swing object tree root
     */
    public Container render (final InputStream xml_reader,
            final String simpleName) throws Exception {
        if (xml_reader == null) {
            throw new IOException ();
        }
        try {
            final DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance ().newDocumentBuilder ();
            return this.render (builder.parse (xml_reader), simpleName);
        } catch (final Exception e) {
            System.err.println (e);
        }
        throw new Exception (TwiXML.XML_ERROR_MSG);
    }

    /**
     * Gets the parsing of the XML file started.
     * 
     * @param resource
     *            <code>String</code> xml-file path info
     * @return <code>Object</code>- instanced swing object tree root
     */
    public Container render (final String resource) throws Exception {
        Container obj = null;
        final String simpleName = (resource.indexOf ('/') == -1 ? resource
                : resource.substring (resource.lastIndexOf ('/')));
        final InputStream in = this.cl.getResourceAsStream (resource);
        if (in == null) {
            throw new IOException (TwiXML.IO_ERROR_MSG + resource);
        }
        try {
            obj = this.render (in, simpleName);
        } finally {
            try {
                in.close ();
            } catch (final Exception ex) {
                // intentionally empty
            }
        }
        return obj;
    }

    /**
     * Gets the parsing of the XML started.
     * 
     * @param url
     *            <code>URL</code> url pointing to an XML descriptor
     * @return <code>Object</code>- instanced swing object tree root
     * @throws Exception
     */
    public Container render (final URL url) throws Exception {
        Container obj = null;
        if (url == null) {
            throw new IllegalArgumentException (
                    "SwingEngine.render called with a null URL");
        }
        final String simpleName = (url.getFile ().indexOf ('/') == -1 ? url
                .getFile () : url.getFile ().substring (
                url.getFile ().lastIndexOf ('/')));
        final InputStream in = url.openStream ();
        if (in == null) {
            throw new IOException (TwiXML.IO_ERROR_MSG + url.toString ());
        }
        try {
            obj = this.render (in, simpleName);
        } finally {
            try {
                in.close ();
            } catch (final Exception ex) {
                // intentionally empty
            }
        }
        return obj;
    }

    /**
     * Recursively Sets an ActionListener
     * <p/>
     * 
     * <pre>
     *  Backtracking algorithm: if al was set for a child component, its not being set for its parent
     * </pre>.
     * 
     * @param c
     *            <code>Component</code> start component
     * @param al
     *            <code>ActionListener</code>
     * @return <code>boolean</code> true, if ActionListener was set.
     */
    public boolean setActionListener (final Component c, final ActionListener al) {
        return CustomCodeProxy.<TwiXML<?, ?, ?, ?, ?, ?>, Boolean> doProxy (
                this.getClass ().getSimpleName (), this, c, al);
    }

    /**
     * Sets the SwingEngine's global application frame variable, to be used as a
     * parent for all child dialogs.
     * 
     * @param frame
     *            <code>Window</code> the parent for all future dialogs.
     */
    public void setAppFrame (final Container frame) {
        if (frame != null) {
            if (this.appFrame == null) {
                this.appFrame = frame;
            }
        }
    }

    /**
     * Sets a classloader to be used for all <i>getResourse..()</i> and <i>
     * loadClass()</i> calls. If no class loader is set, the SwingEngine's
     * loader is used.
     * 
     * @param cl
     *            <code>ClassLoader</code>
     * @see ClassLoader#loadClass
     * @see ClassLoader#getResource
     */
    public void setClassLoader (final ClassLoader cl) {
        this.cl = cl;
        this.localizer.setClassLoader (cl);
    }

    public void setClient (final Object client)
            throws AlreadyRenderedContainerException {
        if (this.root != null) {
            throw new AlreadyRenderedContainerException ();
        }
        this.client = client;
    }

    /**
     * Sets the locale to be used during parsing / String conversion
     * 
     * @param l
     *            <code>Locale</code>
     */
    public void setLocale (Locale l) {
        if (AppConstants.isMacOSXSupported () && AppConstants.isMacOSX ()) {
            l = new Locale (l.getLanguage (), l.getCountry (),
                    TwiXML.MAC_OSX_LOCALE_VARIANT);
        }
        this.localizer.setLocale (l);
    }

    /**
     * Sets the ResourceBundle to be used during parsing / String conversion
     * 
     * @param bundlename
     *            <code>String</code>
     */
    public void setResourceBundle (final String bundlename) {
        this.localizer.setResourceBundle (bundlename);
    }

    /**
     * Walks the whole tree to add all components into the
     * <code>components<code> collection.
     * 
     * @param c
     *            <code>Component</code> recursive start component.
     * @param collection
     *            <code>Collection</code> target collection.
     *            <p/>
     *            Note:There is another collection available that only tracks
     *            those object that were provided with an <em>id</em>attribute,
     *            which hold an unique id
     *            </p>
     */
    public void traverse (final Component c,
            final Collection<Component> collection) {
        if (c != null) {
            collection.add (c);
            CustomCodeProxy.doProxy (this, c, collection);
        }
    }
}
