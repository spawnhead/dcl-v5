package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import org.apache.struts.upload.FormFile;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ImportFileForm extends BaseDispatchValidatorForm {
  FormFile file;

  public FormFile getFile() {
    return file;
  }
  public void setFile(FormFile file) {
    this.file = file;
  }
}
