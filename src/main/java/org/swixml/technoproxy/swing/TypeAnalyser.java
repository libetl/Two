package org.swixml.technoproxy.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.MenuBar;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class TypeAnalyser implements org.swixml.technoproxy.TypeAnalyser {

	@Override
	public Class<?> getCompatibleClass (String string) {
		Class<?> c = null;
		final List<String> possibilities = Arrays.asList ("javax.swing.J"
		        + string, "javax.swing." + string, "java.awt." + string);

		for (final String possibility : possibilities) {
			if (c == null) {
				try {
					c = Class.forName (possibility);
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


}
