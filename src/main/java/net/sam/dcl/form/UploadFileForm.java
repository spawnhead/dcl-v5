package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import org.apache.struts.upload.FormFile;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UploadFileForm extends BaseDispatchValidatorForm {
  FormFile file;
	String referencedTable;
	Integer referencedID;

	public String getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}

	public Integer getReferencedID() {
		return referencedID;
	}

	public void setReferencedID(Integer referencedID) {
		this.referencedID = referencedID;
	}

	public FormFile getFile() {
    return file;
  }
  public void setFile(FormFile file) {
    this.file = file;
  }
}