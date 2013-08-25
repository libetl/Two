package org.swixml;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * Class for store attribute info.
 * 
 * @author <a href="mailto:alex73mail@gmail.com">Alex Buloichik</a>
 */
public class Attribute {
	/**
	 * Create class instance by name from element.
	 * 
	 * @param elem
	 *            element for find attribute
	 * @param attrName
	 *            attribute name
	 * @return object or null if attribute doesn't declared in element
	 */
	public static Attribute getAttribute (Element elem, String attrName) {
		final Attr attrNode = elem.getAttributeNode (attrName);
		return attrNode != null ? new Attribute (attrNode) : null;
	}

	/**
	 * Get attribute value from element.
	 * 
	 * @param elem
	 *            element for find attribute
	 * @param attrName
	 *            attribute name
	 * @return attribute value or null if attribute doesn't declared in element
	 */
	public static String getAttributeValue (Element elem, String attrName) {
		if (elem.getAttributeNode (attrName) != null) {
			return elem.getAttribute (attrName);
		} else {
			return null;
		}
	}

	/** Attribute name. */
	protected final String	name;

	/** Attribute value. */
	protected String	   value;

	/** Constructor. */
	private Attribute (Attr attr) {
		this.name = attr.getName ();
		this.value = attr.getValue ();
	}

	/** Constructor. */
	public Attribute (final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public String getName () {
		return this.name;
	}

	public String getValue () {
		return this.value;
	}

	public void setValue (String value) {
		this.value = value;
	}
}
