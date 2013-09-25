/*--
 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.jsoup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * XAction, Action Wrapper to generate Actions on the fly.
 * 
 * @author <a href="mailto:wolf@wolfpaulus.com">Wolf Paulus</a>
 */

public class XAction extends ActionListener {
    Method method;
    Object client;

    public XAction (Object client, String methodName)
            throws NoSuchMethodException {
        this.client = client;
        if (client != null) {
            this.method = client.getClass ().getMethod (methodName);
        }

    }

    public XAction () {

    }

    @Override
    public void actionPerformed (ActionEvent e) {
        try {
            this.method.invoke (this.client);
        } catch (final IllegalAccessException e1) {
            e1.printStackTrace ();
        } catch (final InvocationTargetException e1) {
            e1.printStackTrace ();
        }
    }
}
