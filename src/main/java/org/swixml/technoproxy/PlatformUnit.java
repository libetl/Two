package org.swixml.technoproxy;

import java.util.Map;

public interface PlatformUnit {

    public Map<String, Class<? extends ProxyCode<?>>> getProxyClasses ();

    public TypeAnalyser getTypeAnalyser ();
}
