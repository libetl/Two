package org.swixml.technoproxy;

import java.util.Map;

public interface TechnoUnit {

    public Map<String, Class<ProxyCode<?>>> getProxyClasses ();
    
    public TypeAnalyser getTypeAnalyser ();
}
