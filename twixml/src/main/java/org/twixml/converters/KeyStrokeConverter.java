/*--
 $Id: KeyStrokeConverter.java,v 1.1 2004/03/01 07:56:01 wolfpaulus Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */

package org.twixml.converters;

import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.Localizer;
import org.twixml.Parser;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * A Converter that turns a Strings in the form of a filename into an KeyStroke
 * objects.
 * 
 * <pre>
 *   Valild syntax includes:
 *   <ul>
 *   <li>accelerator="F7"
 *   <li>accelerator="control N"
 *   <li>accelerator="alt A"
 *   </ul>
 * </pre>
 * <hr>
 * 
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 * @author <a href="mailto:Eric.LAFARGUE@cec.eu.int">Eric Lafargue</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */
public class KeyStrokeConverter implements Converter {

    /**
     * Converts a String into an KeyStroke.
     * 
     * Parses a string and returns a <code>KeyStroke</code>. The string must
     * have the following syntax:
     * 
     * <pre>
     *    &lt;modifiers&gt;* (&lt;typedID&gt; | &lt;pressedReleasedID&gt;)
     * 
     *    modifiers := shift | control | ctrl | meta | alt | button1 | button2 | button3
     *    typedID := typed &lt;typedKey&gt;
     *    typedKey := string of length 1 giving Unicode character.
     *    pressedReleasedID := (pressed | released) key
     *    key := KeyEvent key code name, i.e. the name following "VK_".
     * </pre>
     * 
     * If typed, pressed or released is not specified, pressed is assumed. Here
     * are some examples:
     * 
     * <pre>
     *     "INSERT" => getKeyStroke(KeyEvent.VK_INSERT, 0);
     *     "control DELETE" => getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK);
     *     "alt shift X" => getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
     *     "alt shift released X" => getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK, true);
     *     "typed a" => getKeyStroke('a');
     * </pre>
     * 
     * In order to maintain backward-compatibility, specifying a null String, or
     * a String which is formatted incorrectly, returns null.
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> attribute provides the value to be
     *            converted
     * @param localizer
     *            <code>Localizer</code> allow the use of resource lookups
     * @return <code>Object</code> - a <code>KeyStroke</code> object for that
     *         String, or null if the specified String is null, or is formatted
     *         incorrectly
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            Localizer localizer) {
        Object keyStroke = null;
        if (attr != null) {
            if (Parser.LOCALIZED_ATTRIBUTES.contains (attr.getName ()
                    .toLowerCase ())) {
                attr.setValue (localizer.getString (attr.getValue ()));
            }
            keyStroke = CustomCodeProxy.doProxy (this, attr);
        }
        return keyStroke;
    }

    /**
     * A <code>Converters</code> conversTo method informs about the Class<?>
     * type the converter is returning when its <code>convert</code> method is
     * called
     * 
     * @return <code>Class</code> - the Class<?> the converter is returning when
     *         its convert method is called
     */
    @Override
    public Class<?> convertsTo () {
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass (
                "KeyStroke");
    }
}
