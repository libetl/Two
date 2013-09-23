package org.swixml.technoproxy.jsoup;

import java.util.EventListener;



public abstract class ActionListener implements EventListener {

    /**
     * Invoked when an action occurs.
     */
    public abstract void actionPerformed (ActionEvent e);
    
    public String toString () {
        StringBuffer bodySB = ActionListenerJSBodies.bodies.get (this.getClass ().getName ());
        String body = (bodySB != null ? bodySB.toString () : "");
        return "function " + this.getClass ().getName () + " (e) {" + body 
                + "}";
    }

}
