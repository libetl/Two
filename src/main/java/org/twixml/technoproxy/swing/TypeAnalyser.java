package org.twixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.MenuBar;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.twixml.technoproxy.ProxyCodeException;

public class TypeAnalyser extends org.twixml.technoproxy.TypeAnalyser {

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getCompatibleClass (String string) {
        Class<T> c = null;
        if ("CellConstraints".equals (string)) {
            return (Class<T>) com.jgoodies.forms.layout.CellConstraints.class;
        }
        if ("FormLayout".equals (string)) {
            return (Class<T>) com.jgoodies.forms.layout.FormLayout.class;
        }
        final List<String> possibilities = Arrays.asList (
                "org.twixml.technoproxy.swing.X" + string,
                "org.twixml.technoproxy.swing." + string, "javax.swing.table.J"
                        + string, "javax.swing.tree." + string,
                "javax.swing.border." + string, "javax.swing.J" + string,
                "javax.swing." + string, "java.awt.event." + string,
                "java.awt." + string);

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
    public boolean isConvenient (Object o, String test) {
        boolean result = false;
        if (o == null) {
            return result;
        }
        Class<?> c = o.getClass ();
        result |= "Frame".equals (test)
                && (JFrame.class.equals (c) || Frame.class.equals (c));
        result |= "Component".equals (test)
                && (Component.class.isAssignableFrom (c));
        result |= "MenuBar".equals (test)
                && (JMenuBar.class.equals (c) || MenuBar.class.equals (c));
        result |= "Container".equals (test)
                && (Container.class.isAssignableFrom (c));
        result |= "AbstractButton".equals (test)
                && (AbstractButton.class.isAssignableFrom (c));
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
                                .getClass ())
                                || c1.isPrimitive ()
                                || params [i].getClass ().isPrimitive ();
                    }
                }
                if (assignable) {
                    try {
                        result = constr.newInstance (params);
                    } catch (InstantiationException | IllegalAccessException
                            | IllegalArgumentException
                            | InvocationTargetException e) {
                        throw new ProxyCodeException (e);
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public <T> Class<T> getMostSuperClass (String string) {
        Class<T> clazz1 = this.getCompatibleClass (string);
        Class<T> clazz = clazz1;
        boolean foundSuper = false;
        do {
            foundSuper = false;
            if (clazz.getSuperclass () != null
                    && clazz.getSuperclass ().getSimpleName ()
                            .endsWith (string)) {
                clazz = (Class<T>) clazz.getSuperclass ();
                foundSuper = true;
            } else {
                int i = 0;
                while (i < clazz.getInterfaces ().length && !foundSuper) {
                    if (clazz.getInterfaces () [i] != null
                            && clazz.getInterfaces () [i].getSimpleName ()
                                    .endsWith (string)) {
                        clazz = (Class<T>) clazz.getInterfaces () [i];
                        foundSuper = true;
                    }
                    i++;
                }
            }
        } while (foundSuper);
        return clazz;
    }

}
