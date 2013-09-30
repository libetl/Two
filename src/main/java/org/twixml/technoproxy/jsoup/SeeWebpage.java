package org.twixml.technoproxy.jsoup;

//Here is the only reference of the package jsoup to an awt object
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SeeWebpage {

    public static void see (final String pageHTML) throws IOException {
        final File f = File.createTempFile ("twixml", ".html");
        final FileWriter fw = new FileWriter (f);
        fw.write (pageHTML);
        fw.close ();
        final Desktop desktop = Desktop.isDesktopSupported () ? Desktop
                .getDesktop () : null;
        if ( (desktop != null) && desktop.isSupported (Desktop.Action.BROWSE)) {
            try {
                desktop.browse (f.toURI ());
            } catch (final Exception e) {
                e.printStackTrace ();
            }
        }
    }
}
