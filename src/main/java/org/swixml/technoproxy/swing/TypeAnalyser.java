package org.swixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;


public class TypeAnalyser implements org.swixml.technoproxy.TypeAnalyser {

    @Override
    public boolean isConvenient (Class<?> c, String test) {
        boolean result = false;
        result |= "Frame".equals (test) && 
                (JFrame.class.equals (c) || Frame.class.equals (c));
        result |= "Component".equals (test) && 
                (Component.class.isAssignableFrom (c));
        result |= "MenuBar".equals (test) &&
                (JMenuBar.class.equals (c) || MenuBar.class.equals (c));
        result |= "Container".equals (test) && 
                (Container.class.isAssignableFrom (c));
        return result;
    }

}
