package org.swixml.technoproxy.swing;

import java.util.HashMap;
import java.util.Map;

import org.swixml.technoproxy.ProxyCode;
import org.swixml.technoproxy.TechnoUnit;
import org.swixml.technoproxy.swing.TypeAnalyser;

public class SwingUnit implements TechnoUnit {

    private final Map<String, Class<ProxyCode<?>>> proxyClasses = new HashMap<String, Class<ProxyCode<?>>>(){/**
         * 
         */
        private static final long serialVersionUID = -6051639057164290313L;

    {
        
    }};
    
    private final TypeAnalyser typeAnalyser = new TypeAnalyser ();
    
    @Override
    public Map<String, Class<ProxyCode<?>>> getProxyClasses () {
        return this.proxyClasses;
    }

    @Override
    public org.swixml.technoproxy.TypeAnalyser getTypeAnalyser () {
        return this.typeAnalyser;
            
    }

}
