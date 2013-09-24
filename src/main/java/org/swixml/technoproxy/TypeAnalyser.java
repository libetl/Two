package org.swixml.technoproxy;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;


public abstract class TypeAnalyser {

    public abstract <T> Class<T> getCompatibleClass (String string);

    public abstract boolean isConvenient (Object o, String string);

    public abstract <T> T instantiate (String clazz, Object... params);

	public abstract <T> Class<T> getMostSuperClass (String string);
	
	public <T> Object makeFieldExtendsClass (Object o, Class<T> c){
	    ClassPool cp = ClassPool.getDefault ();
	    try {
            CtClass cc = cp.get (o.getClass ().getName ());
            CtClass superc = cp.get (c.getName ());
            cc.setName (cc.getName ().replace ('$', 'A') + "Extends" + c.getSimpleName ());
            cc.setSuperclass (superc);
            CtConstructor ctc = new CtConstructor (new CtClass[0], cc);
            ctc.setBody ("super ();");
            try {
            cc.addConstructor (ctc);
            }catch (DuplicateMemberException dme){
                //This is not disturbing at all.
            }
            cc.setModifiers (cc.getModifiers () | javassist.Modifier.PUBLIC);
            Class<?> c2 = cc.toClass ();
            Object o2 = c2.newInstance ();
            cc.defrost ();
            cc.detach ();
            superc.defrost ();
            superc.detach ();
            return o2;
        } catch (NotFoundException e) {
            throw new ProxyCodeException (e);
        } catch (CannotCompileException e) {
            throw new ProxyCodeException (e);
        } catch (InstantiationException e) {
            throw new ProxyCodeException (e);
        } catch (IllegalAccessException e) {
            throw new ProxyCodeException (e);
        }
	}
	
}
