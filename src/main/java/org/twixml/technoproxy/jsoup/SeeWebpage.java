package org.twixml.technoproxy.jsoup;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SeeWebpage {

    public static void see (String pageHTML) throws IOException{
        File f = File.createTempFile("twixml", ".html");
        FileWriter fw = new FileWriter (f);
        fw.write (pageHTML);
        fw.close ();
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
           if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
               try {
                   desktop.browse(f.toURI ());
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
    }
}
