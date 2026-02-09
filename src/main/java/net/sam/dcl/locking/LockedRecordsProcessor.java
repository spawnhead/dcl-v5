package net.sam.dcl.locking;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 11:42:52 AM
 */
public interface LockedRecordsProcessor
{
	void process(SyncObject syncObject) throws Exception;
}
