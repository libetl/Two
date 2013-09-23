package org.swixml.technoproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.swixml.technoproxy.jsoup.JSoupUnit;
import org.swixml.technoproxy.swing.SwingUnit;

public class CustomCodeProxy {

    private static Map<String, PlatformUnit> units = new HashMap<String, PlatformUnit> () {
                                                     /**
         * 
         */
                                                     private static final long serialVersionUID = 3339561009429231514L;

                                                     {
                                                         this.put (
                                                                 "swing",
                                                                 new SwingUnit ());
                                                         this.put (
                                                                 "html",
                                                                 new JSoupUnit ());
                                                     }
                                                 };

    public static <T, R> R doProxy (T source, Object... params) {
        return CustomCodeProxy.doProxy (source, "", params);
    }

    @SuppressWarnings ("unchecked")
    public static <T, R> R doProxy (T source, String suffix, Object... params) {
        try {
            final StackTraceElement ste = "doProxy".equals (Thread.currentThread ()
                    .getStackTrace () [2].getMethodName ()) ? Thread.currentThread ()
                            .getStackTrace () [3] : Thread.currentThread ()
                            .getStackTrace () [2];
            final PlatformUnit unit = CustomCodeProxy.units.get (Platform.NAME);
            final Class<?> c = unit.getProxyClasses ().get (
                    source.getClass ().getName ());

            final Method [] ms = c.getMethods ();
            Method m = null;
            int i = 0;
            final String methodName = ste.getMethodName () + suffix;
            while ( (i < ms.length) && (m == null)) {
                if (ms [i].getName ().equals (methodName)) {
                    m = ms [i];
                }
                i++;
            }
            final Constructor<ProxyCode<T>> constr = (Constructor<ProxyCode<T>>) c.getConstructors () [0];
            final ProxyCode<T> pc = constr.newInstance (source);
            return (R) m.invoke (pc, params);
        } catch (final SecurityException e) {
            throw new ProxyCodeException (e);
        } catch (final InstantiationException e) {
            throw new ProxyCodeException (e);
        } catch (final IllegalAccessException e) {
            throw new ProxyCodeException (e);
        } catch (final IllegalArgumentException e) {
            throw new ProxyCodeException (e);
        } catch (final InvocationTargetException e) {
            throw new ProxyCodeException (e);
        }
    }

    public static TypeAnalyser getTypeAnalyser () {
        final PlatformUnit unit = CustomCodeProxy.units.get (Platform.NAME);
        return unit.getTypeAnalyser ();
    }
}
