package org.twixml.technoproxy;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;


public abstract class TypeAnalyser {

    public abstract <T> Class<T> getCompatibleClass (String string);

    public abstract boolean isConvenient (Object o, String string);

    public abstract <T> T instantiate (String clazz, Object... params);

	public abstract <T> Class<T> getMostSuperClass (String string);
	
	private void makeNewEmptyConstructor (CtClass cc){

        try {
        CtConstructor ctc = new CtConstructor (new CtClass[0], cc);
            ctc.setBody ("super ();");
        
        cc.addConstructor (ctc);
        }catch (DuplicateMemberException dme){
            //This is not disturbing at all.
        } catch (CannotCompileException e) {
        }
	}
	
	public <T> Object makeFieldExtendsClass (Class<?> source, Class<T> c){
	    ClassPool cp = ClassPool.getDefault ();
	    try {
            CtClass cc = cp.get (source.getName ());
            CtClass superc = cp.get (c.getName ());
            cc.setName (cc.getName ().replace ('$', 'A') + "Extends" + c.getSimpleName ());
            cc.setSuperclass (superc);
            Class<?> c2 = cc.toClass ();
            Object o2 = c2.newInstance ();
            cc.defrost ();
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

    public Object addMethodDelegateSingleParameter (Class<? extends Object> class1,
            String methodName, Class<Object> compatibleClass) {
        ClassPool cp = ClassPool.getDefault ();
        try {
            CtClass cc = cp.get (class1.getName ());
            CtMethod method = new CtMethod (cp.get (void.class.getName ()), methodName, 
                    new CtClass [] {cp.get (compatibleClass.getName ())}, cc);
            method.setBody ("$0." + methodName + " ((Object) $1);");
            method.setModifiers (cc.getModifiers () | javassist.Modifier.PUBLIC);
            cc.setName (cc.getName ().replace ('$', 'A') + "Delegate" + methodName);
            cc.setModifiers (cc.getModifiers () | javassist.Modifier.PUBLIC);
            cc.addMethod (method);
            this.makeNewEmptyConstructor (cc);
            Class<?> c2 = cc.toClass ();
            Object o2 = c2.newInstance ();
            cc.defrost ();
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
