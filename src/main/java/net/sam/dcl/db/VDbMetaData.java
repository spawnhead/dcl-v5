package net.sam.dcl.db;

public abstract class VDbMetaData {

	public VDbMetaData() {
	}

	/**
	 * Indicates whether the driver supports join with CONNECT BY.
	 *
	 * @return true if the driver supports join with CONNECT BY ; false otherwise
	 */
	abstract public boolean supportsJoinWithConnectBy() throws VDbException;
}
