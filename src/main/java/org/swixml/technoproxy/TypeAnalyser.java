package org.swixml.technoproxy;

public interface TypeAnalyser {

    public <T> Class<T> getCompatibleClass (String string);

    public boolean isConvenient (Class<?> c, String string);

    public <T> T instantiate (String clazz, Object... params);

}
