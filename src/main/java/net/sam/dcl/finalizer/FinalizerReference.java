package net.sam.dcl.finalizer;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author DG
 *         Date: 25.10.2008
 *         Time: 20:36:33
 */
 abstract public class FinalizerReference<T> extends PhantomReference<T>{
	public FinalizerReference(T referent) {
		super(referent, FinalizerThread.get().queue);
	}
	abstract public void destroy();

}
