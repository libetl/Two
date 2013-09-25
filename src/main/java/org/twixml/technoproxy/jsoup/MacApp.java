/*--
 $Id: MacApp.java,v 1.2 2004/10/05 21:32:35 tichy Exp $

 Copyright (C) 2003-2007 Wolf Paulus.
 All rights reserved.


 */
package org.twixml.technoproxy.jsoup;

import java.util.Map;

/**
 * The MapApp in jsoup is useless
 */
public class MacApp  {

    private static MacApp INSTANCE = null;

    public synchronized static MacApp getInstance () {
        if (MacApp.INSTANCE == null) {
            MacApp.INSTANCE = new MacApp ();
        }
        return MacApp.INSTANCE;
    }

    // ////////////////////////////////////////////////



    private MacApp () {
        //stub constructor
    }

    public void update (Map<String, Object> action_map) {

        //stub method
    }

}
