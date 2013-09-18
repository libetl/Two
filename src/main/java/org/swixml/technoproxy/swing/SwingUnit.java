package org.swixml.technoproxy.swing;

import java.util.HashMap;
import java.util.Map;

import org.swixml.technoproxy.ProxyCode;
import org.swixml.technoproxy.TechnoUnit;

public class SwingUnit implements TechnoUnit {

	private final Map<String, Class<? extends ProxyCode<?>>>	proxyClasses	= new HashMap<String, Class<? extends ProxyCode<?>>> () {
		                                                                         /**
         * 
         */
		                                                                         private static final long	serialVersionUID	= -6051639057164290313L;

		                                                                         {
			                                                                         this.put (
			                                                                                 org.swixml.SwingEngine.class
			                                                                                         .getName (),
			                                                                                 SwingEngine.class);
			                                                                         this.put (
			                                                                                 org.swixml.Parser.class
			                                                                                         .getName (),
			                                                                                 Parser.class);
		                                                                         }
	                                                                         };

	private final TypeAnalyser	                             typeAnalyser	 = new TypeAnalyser ();

	@Override
	public Map<String, Class<? extends ProxyCode<?>>> getProxyClasses () {
		return this.proxyClasses;
	}

	@Override
	public org.swixml.technoproxy.TypeAnalyser getTypeAnalyser () {
		return this.typeAnalyser;

	}

}
