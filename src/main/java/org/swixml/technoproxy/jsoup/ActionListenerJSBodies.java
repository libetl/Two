package org.swixml.technoproxy.jsoup;

import java.util.HashMap;
import java.util.Map;



public final class ActionListenerJSBodies {

    public static final Map<String, StringBuffer> bodies = new HashMap<String, StringBuffer> ();
    
    public static void clearALBody (Class<? extends ActionListener> clazz){
        StringBuffer sb = bodies.get (clazz.getName ());
        if (sb != null){
            sb.setLength (0);
        }
    }

    public static void appendToALJS (Object... params){
        StackTraceElement steinvok = Thread.currentThread ().getStackTrace () [2];
        StackTraceElement stecall = Thread.currentThread ().getStackTrace () [3];
        StringBuffer sb = bodies.get (stecall.getClassName ());
        if (sb == null){
            bodies.put (stecall.getClassName (), new StringBuffer ());
            sb = bodies.get (stecall.getClassName ());
        }
        sb.append (steinvok.getClassName () + "." + steinvok.getMethodName ());
        sb.append (" (");
        for (int i = 0 ; i < params.length ; i++){
            sb.append (params [i]);
            if (i + 1 < params.length){
                sb.append (", ");
            }
        }
        sb.append (");\n");
    }
}
