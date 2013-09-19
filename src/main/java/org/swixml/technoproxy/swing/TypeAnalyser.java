package org.swixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.MenuBar;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class TypeAnalyser implements org.swixml.technoproxy.TypeAnalyser {

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getCompatibleClass (String string) {
        Class<T> c = null;
        if ("CellConstraints".equals (string)){
            return (Class<T>) com.jgoodies.forms.layout.CellConstraints.class;
        }
        if ("FormLayout".equals (string)){
            return (Class<T>)com.jgoodies.forms.layout.FormLayout.class;
        }
        final List<String> possibilities = Arrays.asList ("javax.swing.tree." + string,
                "javax.swing.border." + string, "javax.swing.J" + string, 
                "javax.swing." + string, "java.awt." + string);

        for (final String possibility : possibilities) {
            if (c == null) {
                try {
                    c = (Class<T>) Class.forName (possibility);
                } catch (final ClassNotFoundException e) {

                }
            }
        }
        return c;
    }

    @Override
    public boolean isConvenient (Class<?> c, String test) {
        boolean result = false;
        result |= "Frame".equals (test)
                && (JFrame.class.equals (c) || Frame.class.equals (c));
        result |= "Component".equals (test)
                && (Component.class.isAssignableFrom (c));
        result |= "MenuBar".equals (test)
                && (JMenuBar.class.equals (c) || MenuBar.class.equals (c));
        result |= "Container".equals (test)
                && (Container.class.isAssignableFrom (c));
        return result;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public <T> T instantiate (String clazz, Object... params) {
        Class<T> c = this.getCompatibleClass (clazz);
        Constructor<T> [] constrs = (Constructor<T> []) c.getConstructors ();
        T result = null;
        for (Constructor<T> constr : constrs) {
            if (result == null
                    && constr.getParameterTypes ().length == params.length) {
                boolean assignable = true;
                for (int i = 0 ; i < constr.getParameterTypes ().length ; i++) {
                    Class<?> c1 = constr.getParameterTypes () [i];
                    if (params [i] != null) {
                        assignable &= c1.isAssignableFrom (params [i]
                                .getClass ());
                    }
                }
                if (assignable) {
                    try {
                        result = constr.newInstance (params);
                    } catch (InstantiationException | IllegalAccessException
                            | IllegalArgumentException
                            | InvocationTargetException e) {
                    }
                }
            }
        }
        return result;
    }

}
