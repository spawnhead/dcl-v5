package net.sam.dcl.util;

import org.hibernate.context.ThreadLocalSessionContext;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author DG
 *         Date: 21-Jun-2007
 *         Time: 21:05:27
 */
public class ThreadLocalSessionContextNoClose extends ThreadLocalSessionContext {

	public ThreadLocalSessionContextNoClose(SessionFactoryImplementor factory) {
		super(factory);
	}

	protected boolean isAutoCloseEnabled() {
		return false;
	}

}
