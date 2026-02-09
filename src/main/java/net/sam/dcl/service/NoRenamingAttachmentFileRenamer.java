package net.sam.dcl.service;

/**
 * @author DG
 *         Date: 30.10.2008
 *         Time: 13:13:34
 */
public class NoRenamingAttachmentFileRenamer implements AttachmentFileRenamer{

	public String rename(String original) {
		return original;
	}
}
