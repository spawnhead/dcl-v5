package net.sam.dcl.db;

import net.sam.dcl.db.*;
import net.sam.dcl.db.StringValueHolder;

import java.util.Vector;
import java.sql.Types;

/**
 * the class represents a set of parameters which being used
 * in SQL queries
 */

public class VParameter {

	private Vector mParamVector;
	private boolean mNamedParam = false;

	public VParameter() {
		mParamVector = new Vector();
	}

	public VParameter(String paramVal) {
		mParamVector = new Vector();
		add(paramVal);
	}

	public VParameter(String paramVal, int paramType) {
		mParamVector = new Vector();
		add(paramVal, paramType);
	}

	public VParameter(String paramName, String paramVal, int paramType) {
		mParamVector = new Vector();
		add(paramName, paramVal, paramType);
	}

	/**
	 * Adds a praameter to the inner list.
	 *
	 * @param paramType the type of param from java.sql.Types.
	 */
	public void add(String paramVal, int paramType) {
		mParamVector.add(new VParameter.Element(null, paramVal, paramType));
	}

	public void add(String paramVal) {
		add(paramVal, Types.VARCHAR);
	}

	/**
	 * Adds a praameter to the inner list.
	 *
	 * @param paramName the name of parameter
	 * @param paramType the type of param from java.sql.Types.
	 */
	public void add(String paramName, String paramVal, int paramType) {
		mParamVector.add(new VParameter.Element(paramName, paramVal, paramType));
		mNamedParam = true;
	}

	/**
	 * Registers an out parameters
	 *
	 * @param paramType the type of param from java.sql.Types.
	 */
	public void addOut(net.sam.dcl.db.StringValueHolder paramVal, int paramType) {
		mParamVector.add(new VParameter.Element(null, paramVal, paramType, true));
	}

	/**
	 * Registers an out parameters
	 *
	 * @param name			String
	 * @param paramVal	StringValueHolder
	 * @param paramType int
	 */
	public void addOut(String paramName, StringValueHolder paramVal, int paramType) {
		mParamVector.add(new VParameter.Element(paramName, paramVal, paramType, true));
		mNamedParam = true;
	}


	/**
	 * Adds a praameter to the inner list.
	 */
	public void add(VParameter param) {
		VParameter.Element paramElements[] = param.getParamElements();
		//x.1) adds  params
		for (int i = 0; i < paramElements.length; i++) {
			this.add(paramElements[i]);
		}
	}

	/**
	 * Adds a praameter to the inner list.
	 */
	public void add(VParameter.Element element) {
		mParamVector.add(element);
		if (element.mParamName != null) mNamedParam = true;
	}

	/**
	 * Returns an array of parameters.
	 */

	public VParameter.Element[] getParamElements() {
		int length = mParamVector.size();
		VParameter.Element[] elemArr = new VParameter.Element[length];
		for (int i = 0; i < length; i++) {
			elemArr[i] = (VParameter.Element) (mParamVector.elementAt(i));
		}

		return elemArr;

	}

	/**
	 * Resets a paremeter vector.
	 */
	public void reset() {
		mParamVector = new Vector();
		mNamedParam = false;
	}

	public boolean isNamed() {
		return mNamedParam;
	}

	public VParameter copy() {
		VParameter copy = new VParameter();
		copy.mNamedParam = mNamedParam;
		copy.mParamVector = (Vector) mParamVector.clone();
		return copy;
	}

	/**
	 * The inner class represents a parameter.
	 */
	public class Element {
		public String mParamName;
		public String mParamVal;
		public int mParamType;

		public Object mOutParamVal;
		public boolean mParamOut;

		/**
		 * Constructs a parameter
		 */
		public Element(String paramName, String paramVal, int paramType) {
			mParamName = paramName;
			mParamVal = paramVal == null ? null : (paramVal.equals("") ? null : paramVal);
			mParamType = paramType;
			mOutParamVal = null;
			mParamOut = false;
		}

		/**
		 * Constructs an out parameter.
		 */
		public Element(String paramName, Object outParamVal, int paramType, boolean paramOut) {
			mParamName = paramName;
			mParamVal = null;
			mParamType = paramType;
			mOutParamVal = outParamVal;
			mParamOut = paramOut;
		}
	}

	public String toString() {
		VParameter.Element[] elements = getParamElements();
		StringBuffer res = new StringBuffer();
		res.append("{");
		for (int i = 0; i < elements.length; i++) {
			VParameter.Element element = elements[i];
			if (element.mParamName != null) {
				res.append(element.mParamName).append("=");
			}
			res.append(element.mParamVal);
			if (i != elements.length - 1) {
				res.append(",");
			}
		}
		res.append("}");
		return res.toString();
	}
}
