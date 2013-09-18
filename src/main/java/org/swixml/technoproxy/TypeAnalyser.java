package org.swixml.technoproxy;

public interface TypeAnalyser {

    public boolean isConvenient (Class<?> c, String string);

	public Class<?> getCompatibleClass (String string);

}
