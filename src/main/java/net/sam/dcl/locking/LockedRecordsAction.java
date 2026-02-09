package net.sam.dcl.locking;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import org.apache.struts.action.ActionForward;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 2:30:43 PM
 */
public class LockedRecordsAction extends DBTransactionAction implements IDispatchable
{
	public ActionForward list(IActionContext context) throws Exception
	{
		final List list = new ArrayList();
		LockedRecords.getLockedRecords().iterate(new LockedRecordsProcessor()
		{
			public void process(SyncObject syncObject)
			{
				list.add(new LockedRecordsForm.LockedRecord(syncObject));
			}
		});
		LockedRecordsForm form = (LockedRecordsForm) context.getForm();
		form.getGrid().setDataList(list);
		return context.getMapping().getInputForward();
	}

	public ActionForward unlock(IActionContext context) throws Exception
	{
		LockedRecordsForm form = (LockedRecordsForm) context.getForm();
		SyncObject syncObject = new SyncObject(form.getName(), form.getId(), null);
		SyncObject found = LockedRecords.getLockedRecords().findLock(syncObject);
		if (found != null)
		{
			LockedRecords.getLockedRecords().unlock(found);
		}
		return list(context);
	}
}
