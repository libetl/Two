package org.swixml.technoproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomCodeProxy {

    public static <T, R> R doProxy (T source, Object... params) {
        return CustomCodeProxy.doProxy (source, 0, params);
    }

    @SuppressWarnings ("unchecked")
    public static <T, R> R doProxy (T source, int n, Object... params) {
        try {
            StackTraceElement ste = Thread.currentThread ().getStackTrace () [2];
            if (ste.getClassName ().indexOf ('.') != -1) {
                String simpleClassName = ste.getClassName ().substring (
                        ste.getClassName ().lastIndexOf ('.') + 1);
                final String proxyClassPrefix = "org.swixml.technoproxy."
                        + System.getProperty ("swixml.platform");
                final String proxyClassSubPackage = ste.getClassName ()
                        .substring ("org.swixml.".length (),
                                ste.getClassName ().lastIndexOf ('.') + 1);
                String realClassName = proxyClassPrefix + proxyClassSubPackage
                        + simpleClassName;
                Method [] ms = Class.forName (realClassName).getMethods ();
                Method m = null;
                int i = 0;
                String methodName = ste.getMethodName ();
                if (n > 0){
                    methodName += n;
                }
                while (i < ms.length && m == null){
                    if (ms [i].getName ().equals (methodName)){
                        m = ms [i];
                    }
                    i++;
                }
                Constructor<ProxyCode<T>> constr = 
                        (Constructor<ProxyCode<T>>) 
                        Class.forName (realClassName).getConstructor (Object.class);
                ProxyCode<T> pc = constr.newInstance (source);
                return (R) m.invoke (pc, params);
            }
            return null;
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
}
