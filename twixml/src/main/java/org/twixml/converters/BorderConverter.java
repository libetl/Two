/*--
 $Id: BorderConverter.java,v 1.1 2004/03/01 07:55:58 wolfpaulus Exp $


 */

package org.twixml.converters;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.twixml.AppConstants;
import org.twixml.Attribute;
import org.twixml.Converter;
import org.twixml.ConverterLibrary;
import org.twixml.Localizer;
import org.twixml.technoproxy.CustomCodeProxy;

/**
 * The <code>BorderConverter</code> class defines a converter that creates
 * Border objects based on a provided String. The BorderConverter internally
 * uses the <code>BorderFactory</code> and its static <i>create</i>.. methods to
 * instantiate different kinds of borders, based on the given String.<br>
 * Additional parameters to create a border need to be comma separated and
 * enclosed in parentheses.<br>
 * <br>
 * Parameter types get converted through the <code>ConverterLibrary</code>.
 * Therefore, only parameter classes are supported that have registered
 * converters in the ConverLibrary. <h3>Examples for Valid XML attribute
 * notations:</h3>
 * 
 * <pre>
 * <ul>
 * <li>border="MatteBorder(4,4,4,4,red)"</li>
 * <li>border="EmptyBorder(5,5,5,5)"</li>
 * <li>border="TitledBorder(My Title)"</li>
 * <li>border="RaisedBevelBorder"</li>
 * <li>border="TitledBorder(TitledBorder, myTitle, TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, VERDANA-BOLD-18, blue)"</li>
 * <li>border="TitledBorder(LineBorder(red,2), myTitle, TitledBorder.CENTER, TitledBorder.ABOVE_TOP)"</li>
 * <li>border="CompoundBorder(LineBorder(blue,5), CompoundBorder(EmptyBorder(2,2,2,2), RaisedBevelBorder))"</li>
 * </ul>
 * </pre>
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 * @version $Revision: 1.1 $
 * @see org.twixml.ConverterLibrary
 */
public class BorderConverter implements Converter {

    /**
     * all methods the BorderFactory provides
     */
    private static final Method [] METHODS = CustomCodeProxy
                                                   .getTypeAnalyser ()
                                                   .getCompatibleClass (
                                                           "BorderFactory")
                                                   .getMethods ();

    /**
     * Returns a <code>Border</code>
     * 
     * @param type
     *            <code>Class</code> not used
     * @param attr
     *            <code>Attribute</code> value needs to provide Border type name
     *            and optional parameter
     * @return <code>Object</code> runtime type is subclass of
     *         <code>AbstractBorder</code>
     */
    @Override
    public Object convert (final Class<?> type, final Attribute attr,
            final Localizer localizer) {
        Object border = null;
        final List<String> params = this.parse (attr.getValue ()); // border
                                                                   // type +
        // parameters
        int n = params.size () - 1; // number of parameter to create a border
        final String borderType = params.remove (0).trim ();
        Method method = null;
        final ConverterLibrary cvtlib = ConverterLibrary.getInstance ();
        //
        // Special case for single parameter construction, give priority to
        // String Type
        //
        if (n == 0) {
            try {
                method = CustomCodeProxy.getTypeAnalyser ()
                        .getCompatibleClass ("BorderFactory")
                        .getMethod ("create" + borderType);

            } catch (final NoSuchMethodException e) {
                // intent. empty
            }
            if (method == null) { // try with empty string
                n = 1;
                params.add (" ");
            }
        }
        if (n == 1) {
            try {
                method = CustomCodeProxy
                        .getTypeAnalyser ()
                        .getCompatibleClass ("BorderFactory")
                        .getMethod ("create" + borderType,
                                new Class<?> [] { String.class });
            } catch (final NoSuchMethodException e) {
                // no need to do anything here.
            }
        }
        for (int i = 0 ; (method == null)
                && (i < BorderConverter.METHODS.length) ; i++) {
            if ( (BorderConverter.METHODS [i].getParameterTypes ().length == n)
                    && BorderConverter.METHODS [i].getName ().endsWith (
                            borderType)) {
                method = BorderConverter.METHODS [i];

                for (int j = 0 ; j < method.getParameterTypes ().length ; j++) {
                    if (String.class.equals (method.getParameterTypes () [j])) {
                        continue;
                    }
                    if (null == cvtlib.getConverter (method
                            .getParameterTypes () [j])) {
                        method = null;
                        break;
                    }
                }
            }
        }
        try {
            final Object [] args = new Object [n];
            for (int i = 0 ; i < n ; i++) { // fill argument array
                final Converter converter = cvtlib.getConverter (method
                        .getParameterTypes () [i]);
                Class<?> convertsTo = Void.class;
                if (converter != null) {
                    convertsTo = converter.convertsTo ();
                }
                final Attribute attrib = new Attribute (
                        String.class.equals (convertsTo) ? "title" : "NA",
                        params.remove (0).trim ());
                if (converter != null) {
                    args [i] = converter.convert (
                            method.getParameterTypes () [i], attrib, localizer);
                } else {
                    args [i] = attrib.getValue ();
                }
            }
            border = method.invoke (null, args);
        } catch (final Exception e) {
            if (AppConstants.DEBUG_MODE) {
                System.err.println ("Couldn't create border, "
                        + attr.getValue () + "\n" + e.getMessage ());
            }
        }
        return border;
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
        return CustomCodeProxy.getTypeAnalyser ().getCompatibleClass ("Border");
    }

    /**
     * Parse a border attribute value into the border type and parameters, where
     * the parameters may specify additional <code>Border</code> constructors.
     * For example, the following string <br>
     * "CompoundBorder(LineBorder(blue,5), CompoundBorder(EmptyBorder(2,2,2,2), RaisedBevelBorder))"
     * <br>
     * would be parsed into three strings: <br>
     * CompoundBorder <br>
     * LineBorder(blue,5) <br>
     * CompoundBorder(EmptyBorder(2,2,2,2), RaisedBevelBorder) <br>
     * 
     * @param input
     *            <code>String</code> containing the value to parse
     * @return <code>List&lt;String&gt;</code> containing the values parsed
     */
    private List<String> parse (final String input) {
        final ArrayList<String> tokens = new ArrayList<String> ();
        int index = input.indexOf ('(');
        if (index < 0) {
            tokens.add (input);
        } else {
            tokens.add (input.substring (0, index));
            while (++index < input.length ()) {
                int depth = 0;
                final int beginIndex = index;
                while ( (depth >= 0) && (++index < input.length ())) {
                    if ( (input.charAt (index) == ',') && (depth == 0)) {
                        break;
                    }
                    if (input.charAt (index) == '(') {
                        ++depth;
                    } else if (input.charAt (index) == ')') {
                        --depth;
                    }
                }
                if (index < input.length ()) {
                    tokens.add (input.substring (beginIndex, index));
                }
            }
        }
        return tokens;
    }

}
