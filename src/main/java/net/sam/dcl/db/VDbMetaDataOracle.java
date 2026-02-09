package net.sam.dcl.db;

public class VDbMetaDataOracle extends VDbMetaData {
	static private int ERROR__NOT_HAVE_JOIN_WITH_CONNECT_BY = 1437;

	public VDbMetaDataOracle() {
	}

	/**
	 * Indicates whether the driver supports join with CONNECT BY.
	 *
	 * @return true if the driver supports join with CONNECT BY ; false otherwise
	 */
	public boolean supportsJoinWithConnectBy() throws VDbException {
		VDbConnection conn = null;
		String sql = "select 1 from dual a , dual b where a.dummy = b.dummy " +
								 "start with a.dummy = 'X' connect by 'xxxx' = prior a.dummy";

		try {
			conn = VDbConnectionManager.getVDbConnection();
			conn.execute(VDbConnectionManager.USERS__DEFAULT_SYS_USER, sql);
		}
		catch (VDbException e) {
			if (e.getErrorCode() == ERROR__NOT_HAVE_JOIN_WITH_CONNECT_BY) return false;

			throw e;
		}
		finally {
			if (conn != null) conn.close();
		}

		return true;
	}
}
