package org.swixml.technoproxy;

public interface TypeAnalyser {

    public <T> Class<T> getCompatibleClass (String string);

    public boolean isConvenient (Object o, String string);

    public <T> T instantiate (String clazz, Object... params);

	public <T> Class<T> getMostSuperClass (String string);

}
