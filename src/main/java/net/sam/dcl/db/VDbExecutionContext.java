package net.sam.dcl.db;

import net.sam.dcl.db.VParameter;

/**
 * @author: DG
 * Date: Nov 23, 2005
 * Time: 11:54:03 AM
 */
public class VDbExecutionContext {
	private VParameter originalParam;
	private String originalSql;
	private VParameter param;
	private String sql;

	public VDbExecutionContext(String sql) {
		originalSql = sql;
		this.sql = sql;
		param = null;
		originalParam = null;
	}

	public VDbExecutionContext(VParameter vParameter, String sql) {
		originalParam = vParameter;
		originalSql = sql;
		this.param = vParameter.copy();
		this.sql = sql;
	}

	public VParameter getOriginalParam() {
		return originalParam;
	}

	public String getOriginalSql() {
		return originalSql;
	}

	public VParameter getParam() {
		return param;
	}

	public void setParam(VParameter vParameter) {
		this.param = vParameter;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("[").append(getOriginalSql()).append("]");
		if (originalParam != null) {
			res.append(originalParam.toString());
		}
		if (!getOriginalSql().equals(getSql())) {
			res.append("[").append(getSql()).append("]");
			if (param != null) {
				res.append(param.toString());
			}
		}
		return res.toString();
	}

}
