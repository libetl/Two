package org.swixml.technoproxy;

public interface TypeAnalyser {

	public Class<?> getCompatibleClass (String string);

	public boolean isConvenient (Class<?> c, String string);

}
