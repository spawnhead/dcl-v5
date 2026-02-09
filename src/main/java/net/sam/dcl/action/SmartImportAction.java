package net.sam.dcl.action;

import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ImportFileForm;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.beans.Constants;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SmartImportAction extends ImportFileAction {
	public ActionForward process(IActionContext context) throws Exception {
		ImportFileForm form = (ImportFileForm) context.getForm();
		HSSFWorkbook wb = null;
		FormFile fFile = form.getFile();
		try {
			POIFSFileSystem fs = new POIFSFileSystem(form.getFile().getInputStream());
			wb = new HSSFWorkbook(fs);
		}
		catch (IOException e) {
			StrutsUtil.addError(context, "error.wrong.exce.file.format", e);
			return input(context);
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		Iterator iterator = sheet.rowIterator();
		if (iterator.hasNext()) {
			// skip first row
			iterator.next();
		}

		afterLoad(context);

		int counter = 2;
		List badRows = new ArrayList();
		List rows = new ArrayList();
		HSSFRow row;
		while (iterator.hasNext()) {
			row = (HSSFRow) iterator.next();
			rows.add(row);
			if (isRowEmpty(row)){
				continue;
			}
			try {
				processRow(row, context, badRows);
				counter++;
			}
			catch (Exception e) {
				log.error("Error while processing row:" + String.valueOf(counter), e);
				badRows.add(row);
			}
		}
//		is.close();
		afterProcess();

		if (badRows.size() != 0) {
			rows.removeAll(badRows);
			for (int i = 0; i < rows.size(); i++) {
				HSSFRow r = (HSSFRow) rows.get(i);
				sheet.removeRow(r);
			}
			File origName = new File(fFile.getFileName());
			int idx = origName.getName().indexOf(".");
			String resName = origName.getName().substring(0,idx)+"-copy"+origName.getName().substring(idx,origName.getName().length());
			File copy = new File(Config.getString(Constants.importResultsDir), resName);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(copy));
			wb.write(os);
			os.close();

			StrutsUtil.addError(context, "SmartImport.error.msg", copy.getAbsolutePath(), null);
		}
		return context.getMapping().findForward("back");
	}

	public void afterProcess() {
	}

	protected void processRow(HSSFRow row, IActionContext context, List badRows) throws Exception {
	}

	public void afterLoad(IActionContext context) {

	}
}