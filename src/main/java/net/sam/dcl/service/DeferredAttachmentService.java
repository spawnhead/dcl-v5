package net.sam.dcl.service;

import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.finalizer.FinalizerReference;
import net.sam.dcl.finalizer.FinalizerThread;
import net.sam.dcl.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author DG
 *         Date: 11-Jul-2008
 *         Time: 19:07:29
 */
public class DeferredAttachmentService implements Cloneable
{
	protected static Log log = LogFactory.getLog(DeferredAttachmentService.class);

	static public final String BIND_ATTR = DeferredAttachmentService.class.getName() + "#BIND_ATTR";

	static public DeferredAttachmentService get(HttpSession session)
	{
		List<DeferredAttachmentService> services = (List<DeferredAttachmentService>) session.getAttribute(BIND_ATTR);
		return services.get(services.size() - 1);
	}

	static public void removeLast(HttpSession session)
	{
		List<DeferredAttachmentService> services = (List<DeferredAttachmentService>) session.getAttribute(BIND_ATTR);
		services.remove(services.size() - 1);
		session.setAttribute(BIND_ATTR, services);
	}

	static public void set(HttpSession session, DeferredAttachmentService attachmentService)
	{
		List<DeferredAttachmentService> services = (List<DeferredAttachmentService>) session.getAttribute(BIND_ATTR);
		if (null == services)
		{
			services = new ArrayList<DeferredAttachmentService>();
		}
		for (DeferredAttachmentService service : services)
		{
			if ((attachmentService.getBackFromUploadForward() == null && service.getBackFromUploadForward() == null) || (attachmentService.getBackFromUploadForward() != null && attachmentService.getBackFromUploadForward().equals(service.getBackFromUploadForward())))
			{
				services.remove(service);
				break;
			}
		}
		services.add(attachmentService);
		session.setAttribute(BIND_ATTR, services);
	}

	static public DeferredAttachmentService create(HttpSession session, String referencedTable, ActionForward backFromUploadForward,
	                                               ActionForward backFromListForward) throws Exception
	{
		return create(session, referencedTable, null, backFromUploadForward, backFromListForward);
	}

	static public DeferredAttachmentService create(HttpSession session, String referencedTable, Integer referencedID,
	                                               ActionForward backFromUploadForward, ActionForward backFromListForward) throws Exception
	{
		DeferredAttachmentService attachmentService = new DeferredAttachmentService(referencedTable, referencedID, backFromUploadForward, backFromListForward);
		set(session, attachmentService);
		return attachmentService;
	}

	String referencedTable;
	Integer referencedID;
	private List<DeferredAttachment> attachments = new ArrayList<DeferredAttachment>();
	ActionForward backFromUploadForward;
	ActionForward backFromListForward;
	AttachmentFileRenamer renamer = new NoRenamingAttachmentFileRenamer();


	DeferredAttachmentService(String referencedTable, Integer referencedID, ActionForward backFromUploadForward, ActionForward backFromListForward) throws Exception
	{
		this.referencedTable = referencedTable;
		this.referencedID = referencedID;
		this.backFromUploadForward = backFromUploadForward;
		this.backFromListForward = backFromListForward;
		AttachmentsService service = new AttachmentsService(HibernateUtil.getSessionFactory().getCurrentSession());
		if (referencedID != null)
		{
			List<DboAttachment> attachments = service.list(referencedTable, referencedID);
			for (DboAttachment attachment : attachments)
			{
				this.attachments.add(new DeferredAttachment(attachment, this));
			}
		}
	}

	public DeferredAttachmentService clone() throws CloneNotSupportedException
	{
		DeferredAttachmentService service = (DeferredAttachmentService) super.clone();
		service.attachments = new ArrayList<DeferredAttachment>(attachments.size());
		for (DeferredAttachment attachment : attachments)
		{
			DeferredAttachment clone = (DeferredAttachment) attachment.clone();
			clone.service = service;
			service.attachments.add(clone);
		}
		return service;
	}

	public void add(FormFile file, String localName)
	{
		DeferredAttachment attachment = new DeferredAttachment(referencedTable, referencedID, localName, file, this);
		attachments.add(attachment);
	}

	//add copy to originalAttachment
	public void addCopy(DboAttachment originalAttachment)
	{
		DeferredAttachment attachment = new DeferredAttachment(referencedTable, referencedID, originalAttachment, this);
		attachments.add(attachment);
	}

	public void update(Integer id, FormFile file, String localName)
	{
		assert id != null;
		DeferredAttachment attachment = attachments.get(id);
		attachment.setFileControl(file);
		attachment.setLocalFileName(localName);
	}

	public void commit(Integer referencedId)
	{
		for (DeferredAttachment attachment : attachments)
		{
			attachment.setReferencedID(referencedId);
		}
		commit();
	}

	public void commit()
	{
		for (DeferredAttachment attachment : attachments)
		{
			assert attachment.getReferencedID() != null;
		}
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();

		int i = 0;
		try
		{
			for (; i < attachments.size(); i++)
			{
				DeferredAttachment attachment = attachments.get(i);
				if (!attachment.isPersisted())
				{
					DboAttachment original = attachment.getOriginal();
					if (original == null)
					{
						AttachmentsService.upload(attachment.getFileControl().getInputStream(), attachment);
					}
					hibSession.save(DboAttachment.class.getName(), attachment);
				}
			}

			for (i = 0; i < attachments.size(); i++)
			{
				DeferredAttachment attachment = attachments.get(i);
				if (attachment.isPersisted())
				{
					if (attachment.isDeleted())
					{
						try
						{
							hibSession.lock(DboAttachment.class.getName(), attachment, LockMode.UPGRADE);
							hibSession.delete(DboAttachment.class.getName(), attachment);
						}
						catch (StaleObjectStateException e)
						{
							log.warn("Attachment already deleted:" + attachment.getLocalFileName(), e);
						}
					}
				}
			}
			hibSession.flush();

			for (i = 0; i < attachments.size(); i++)
			{
				DeferredAttachment attachment = attachments.get(i);
				if (attachment.isPersisted())
				{
					if (attachment.isDeleted())
					{
						attachment.deleteFile();
					}
				}
			}
		}
		catch (Exception e)
		{
			abort(attachments.size() - 1);
			throw new RuntimeException(e);
		}
	}

	void abort(int lastEntry)
	{
		for (int i = 0; i <= lastEntry; i++)
		{
			DeferredAttachment attachment = attachments.get(i);
			if (!attachment.isPersisted())
			{
				attachment.deleteFile();
				attachment.setId(null);
			}
		}
	}

	public void rollback()
	{
		attachments = new ArrayList<DeferredAttachment>();
	}

	public List<DeferredAttachment> list() throws Exception
	{
		List<DeferredAttachment> result = new ArrayList<DeferredAttachment>();
		for (DeferredAttachment attachment : attachments)
		{
			if (!attachment.isDeleted())
			{
				result.add(attachment);
			}
		}
		return result;
	}

	public List<DeferredAttachment> listDeleted() throws Exception
	{
		List<DeferredAttachment> result = new ArrayList<DeferredAttachment>();
		for (DeferredAttachment attachment : attachments)
		{
			if (attachment.isDeleted())
			{
				result.add(attachment);
			}
		}
		return result;
	}

	public void fixLinksOnDeleted(DboAttachment deleted)
	{
		Iterator<DeferredAttachment> iterator = attachments.iterator();
		while (iterator.hasNext())
		{
			DeferredAttachment attachment = iterator.next();
			assert attachment.getReferencedID() != null;
			if (attachment.getOriginal() != null)
			{
				if (attachment.getOriginal().getId() == null)
				{
					if (attachment.getOriginal().equals(deleted))
					{
						iterator.remove();
					}
				}
				else
				{
					if (attachment.getOriginal().getId().equals(deleted.getId()))
					{
						iterator.remove();
					}

				}
			}
		}
	}

	public void deleteNotPersisted()
	{
		List<DeferredAttachment> notPersistedAttachments = new ArrayList<DeferredAttachment>();
		for (int i = 0; i < attachments.size(); i++)
		{
			if (!attachments.get(i).isPersisted())
			{
				notPersistedAttachments.add(attachments.get(i));
			}
		}

		for (DeferredAttachment attachment : notPersistedAttachments)
		{
			attachments.remove(attachment);
		}
	}

	public boolean alreadyExists(String name)
	{
		for (int i = 0; i < attachments.size(); i++)
		{
			if (attachments.get(i).getOriginalFileName().equals(name))
			{
				return true;
			}
		}

		return false;
	}

	public DeferredAttachment firstAttach() throws Exception
	{
		return find(0);
	}

	public void delete(String id) throws Exception
	{
		assert id != null;
		DeferredAttachment attachment = find(Integer.parseInt(id));
		delete(attachment);
	}

	public void delete(DeferredAttachment attachment)
	{
		if (attachment.isPersisted())
		{
			attachment.setDeleted(true);
		}
		else
		{
			attachments.remove(attachment);
		}
	}

	public void deleteToLast()
	{
		for (int i = 0; i < attachments.size() - 1; i++)
		{
			delete(attachments.get(i));
		}
	}

	public DeferredAttachment find(Integer id)
	{
		assert id != null;
		int index = id.intValue();
		return attachments.get(index);
	}

	public void download(String id, HttpServletRequest request, HttpServletResponse response)
					throws Exception, AttachmentException
	{
		log.info("downloading file with id:" + id);
		assert id != null;

		DeferredAttachment attachment = find(Integer.parseInt(id));
		if (attachment.getFileControl() == null)
		{
			try
			{
				AttachmentsService.download(attachment, request, response);
			}
			catch (FileNotFoundException e)
			{
				throw new AttachmentException("Attachments.file.deleted");
			}
		}
		else
		{
			AttachmentsService.downloadStream(request, response, attachment.getFileControl().getInputStream(), attachment.getOriginalFileName());
		}

	}

	public ActionForward getBackFromUploadForward()
	{
		return backFromUploadForward;
	}

	public void setBackFromUploadForward(ActionForward backFromUploadForward)
	{
		this.backFromUploadForward = backFromUploadForward;
	}

	public ActionForward getBackFromListForward()
	{
		return backFromListForward;
	}

	public void setBackFromListForward(ActionForward backFromListForward)
	{
		this.backFromListForward = backFromListForward;
	}

	public AttachmentFileRenamer getRenamer()
	{
		return renamer;
	}

	public void setRenamer(AttachmentFileRenamer renamer)
	{
		this.renamer = renamer;
	}

	/**
	 * @author DG
	 *         Date: 14-Jul-2008
	 *         Time: 13:48:12
	 */
	static public class DeferredAttachment extends DboAttachment implements Cloneable
	{
		FormFile fileControl;
		boolean deleted = false;
		boolean persisted = false;
		DeferredAttachmentService service;

		DeferredAttachment(String referencedTable, Integer referencedID, DboAttachment original, DeferredAttachmentService service)
		{
			super(referencedTable, referencedID);
			assert original != null;
			setOriginal(original);
			this.fileControl = null;
			this.service = service;
			FinalizerThread.get().add(new FinalizerRef(this));
		}

		DeferredAttachment(String referencedTable, Integer referencedID, String localFileName, FormFile fileControl,
		                   DeferredAttachmentService service)
		{
			super(referencedTable, referencedID, fileControl.getFileName(), localFileName);
			this.fileControl = fileControl;
			this.service = service;
			FinalizerThread.get().add(new FinalizerRef(this));
		}

		DeferredAttachment(DboAttachment attachment, DeferredAttachmentService service)
		{
			super(attachment.getId(), attachment.getReferencedTable(), attachment.getReferencedID(), attachment.getOriginalFileName(), attachment.getLocalFileName());
			this.persisted = true;
			setOriginal(attachment.getOriginal());
			this.service = service;
			FinalizerThread.get().add(new FinalizerRef(this));
		}

		public boolean isPersisted()
		{
			return persisted;
		}

		public FormFile getFileControl()
		{
			return getOriginal() instanceof DeferredAttachment ? ((DeferredAttachment) getOriginal()).getFileControl() : fileControl;
		}

		public boolean isDeleted()
		{
			return deleted;
		}

		public void setDeleted(boolean deleted)
		{
			this.deleted = deleted;
		}

		public int getIdx()
		{
			return service.attachments.indexOf(this);
		}

		public void setFileControl(FormFile fileControl)
		{
			assert getOriginal() == null;
			this.fileControl = fileControl;
		}

		public byte[] getContent()
		{
			if (fileControl == null)
			{
				return super.getContent();
			}
			else
			{
				try
				{
					return fileControl.getFileData();
				}
				catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			}
		}

		public Object clone() throws CloneNotSupportedException
		{
			return super.clone();
		}
	}

	static public class FinalizerRef extends FinalizerReference<DeferredAttachment>
	{
		FormFile fileControl;
		String referencedTable;
		Integer referencedID;

		public FinalizerRef(DeferredAttachment referent)
		{
			super(referent);
			fileControl = referent.fileControl;
			referencedTable = referent.getReferencedTable();
			referencedID = referent.getReferencedID();
		}

		public void destroy()
		{
			if (fileControl != null)
			{
				fileControl.destroy();
				fileControl = null;
			}
		}

		public String toString()
		{
			return "DeferredAttachment{referencedTable:" + referencedTable + "," +
							"referencedID:" + referencedID + "," +
							(fileControl != null ? ",fileControl:" + fileControl : "") +
							"}";
		}

	}

}
