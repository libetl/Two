/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml;

import org.w3c.dom.Element;

/**
 * <p>
 * The <code>LayoutConverter</code> interface defines a layout converter that
 * creates LayoutManager objects and constraints objects based on a provided XML
 * attributes or elements.
 * </p>
 * 
 * @author Karl Tauber
 * @see LayoutConverterLibrary
 */
public interface LayoutConverter<LayoutManager> {

    /**
     * <p>
     * Converts the given "constraints" attribute to a layout manager specific
     * constraints instance.
     * </p>
     * 
     * <p>
     * If the layout converter does not use the "constraints" attribute, this
     * method should return <code>null</code>.
     * </p>
     * 
     * <p>
     * <b>XML notation:</b>
     * </p>
     * 
     * <pre>
     * &lt;label constraints="value" /&gt;
     * </pre>
     */
    public Object convertConstraintsAttribute (final Attribute attr);

    /**
     * <p>
     * Converts the given "constraints" element to a layout manager specific
     * constraints instance. The element may have any layout manager specific
     * attributes or child elements.
     * </p>
     * 
     * <p>
     * If the layout converter does not use the "constraints" element, this
     * method should return <code>null</code>.
     * </p>
     * 
     * <p>
     * <b>XML notation:</b>
     * </p>
     * 
     * <pre>
     * &lt;label&gt;
     *   &lt;constraints attr1="value1" /&gt;
     * &lt;/label&gt;
     * </pre>
     */
    public Object convertConstraintsElement (final Element element);

    /**
     * <p>
     * Converts the given "layout" attribute to a <code>LayoutManager</code>
     * instance. The attribute value always starts with the layout identifier
     * (see {@link #getID()}) followed by optional parameters.
     * </p>
     * 
     * <p>
     * If the layout converter does not use the "layout" attribute, this method
     * should return <code>null</code>, but then {@link #convertLayoutElement}
     * must return a <code>LayoutManager</code> instance.
     * </p>
     * 
     * <p>
     * <b>XML notation:</b>
     * </p>
     * 
     * <pre>
     * &lt;panel layout="mylayout"/&gt;
     * </pre>
     */
    public LayoutManager convertLayoutAttribute (final Attribute attr);

    /**
     * <p>
     * Converts the given "layout" element to a <code>LayoutManager</code>
     * instance. The element may have any layout manager specific attributes or
     * child elements. The "type" attribute is used to choose the layout
     * converter.
     * </p>
     * 
     * <p>
     * If the layout converter does not use the "layout" element, this method
     * should return <code>null</code>, but then {@link #convertLayoutAttribute}
     * must return a <code>LayoutManager</code> instance.
     * </p>
     * 
     * <p>
     * <b>XML notation:</b>
     * </p>
     * 
     * <pre>
     * &lt;panel&gt;
     *   &lt;layout type="mylayout" attr1="value1"/&gt;
     * &lt;/panel&gt;
     * </pre>
     */
    public LayoutManager convertLayoutElement (final Element element);

    /**
     * Returns the unique identifier of the layout converter. E.g. "flowlayout".
     * This identifier is used in "layout" attributes and elements to specify
     * the layout manager.
     */
    public String getID ();
}
