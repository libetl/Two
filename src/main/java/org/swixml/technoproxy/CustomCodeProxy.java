package org.swixml.technoproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.swixml.technoproxy.swing.SwingUnit;

public class CustomCodeProxy {

    private static Map<String, TechnoUnit> units = new HashMap<String, TechnoUnit> (){/**
         * 
         */
        private static final long serialVersionUID = 3339561009429231514L;

    {
        this.put ("swing", new SwingUnit ());
    }};
   

    public static <T, R> R doProxy (T source, Object... params) {
        return CustomCodeProxy.doProxy (source, "", params);
    }

    @SuppressWarnings ("unchecked")
    public static <T, R> R doProxy (T source, String suffix, Object... params) {
        try {
            StackTraceElement ste = Thread.currentThread ().getStackTrace () [2];
            TechnoUnit unit = CustomCodeProxy.units.get (Techno.NAME);
            Class<?> c = unit.getProxyClasses ().get (
                    source.getClass ().getName ());

            String simpleClassName = ste.getClassName ().substring (
                    ste.getClassName ().lastIndexOf ('.') + 1);
            final String proxyClassPrefix = "org.swixml.technoproxy."
                    + Techno.NAME;
            final String proxyClassSubPackage = ste.getClassName ().substring (
                    "org.swixml.".length (),
                    ste.getClassName ().lastIndexOf ('.') + 1);
            String realClassName = proxyClassPrefix + proxyClassSubPackage
                    + simpleClassName;
            Method [] ms = c.getMethods ();
            Method m = null;
            int i = 0;
            String methodName = ste.getMethodName () + suffix;
            while (i < ms.length && m == null) {
                if (ms [i].getName ().equals (methodName)) {
                    m = ms [i];
                }
                i++;
            }
            Constructor<ProxyCode<T>> constr = (Constructor<ProxyCode<T>>) Class
                    .forName (realClassName).getConstructor (Object.class);
            ProxyCode<T> pc = constr.newInstance (source);
            return (R) m.invoke (pc, params);
        } catch (ClassNotFoundException e) {
            throw new ProxyCodeException (e);
        } catch (NoSuchMethodException e) {
            throw new ProxyCodeException (e);
        } catch (SecurityException e) {
            throw new ProxyCodeException (e);
        } catch (InstantiationException e) {
            throw new ProxyCodeException (e);
        } catch (IllegalAccessException e) {
            throw new ProxyCodeException (e);
        } catch (IllegalArgumentException e) {
            throw new ProxyCodeException (e);
        } catch (InvocationTargetException e) {
            throw new ProxyCodeException (e);
        }
    }

    public static TypeAnalyser getTypeAnalyser () {
        TechnoUnit unit = CustomCodeProxy.units.get (Techno.NAME);
        return unit.getTypeAnalyser ();
    }
}
