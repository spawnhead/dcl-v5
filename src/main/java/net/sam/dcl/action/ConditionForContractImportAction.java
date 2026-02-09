package net.sam.dcl.action;

import org.apache.struts.action.ActionForward;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ImportFileForm;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.ConditionForContract;
import net.sam.dcl.beans.ConditionForContractProduce;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dao.StuffCategoryDAO;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ConditionForContractImportAction extends ImportFileAction
{
  public ActionForward process(IActionContext context) throws Exception
  {
    ImportFileForm form = (ImportFileForm) context.getForm();
    HSSFWorkbook wb;
    try
    {
      POIFSFileSystem fs = new POIFSFileSystem(form.getFile().getInputStream());
      wb = new HSSFWorkbook(fs);
    }
    catch (IOException e)
    {
      StrutsUtil.addError(context, "error.wrong.exce.file.format", e);
      return input(context);
    }
    HSSFSheet sheet = wb.getSheetAt(0);
    Iterator iterator = sheet.rowIterator();
    if (iterator.hasNext())
    {
      // skip first row
      iterator.next();
    }
    ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
    int counter = 1;
    String badRows = "";
    List<ConditionForContractProduce> produces = new ArrayList<ConditionForContractProduce>();
    while (iterator.hasNext())
    {
      HSSFRow row = (HSSFRow) iterator.next();
      try
      {
        counter++;
        addRow(row, context, produces);
      }
      catch (Exception e)
      {
        log.error("Error while processing row:" + String.valueOf(counter), e);
        badRows += String.valueOf(counter) + ",";
      }
    }
    if (StringUtil.isEmpty(badRows))
    {
      for (ConditionForContractProduce produce : produces)
      {
        conditionForContract.insertConditionForContractProduce(produce);
      }
    }
    else
    {
      StrutsUtil.addError(context, "ConditionForContractImport.error.msg", null);
    }
    return context.getMapping().findForward("back");
  }

  protected void addRow(HSSFRow row, IActionContext context, List<ConditionForContractProduce> produces) throws Exception
  {
    ConditionForContractProduce conditionForContractProduce = ConditionForContract.getEmptyConditionForContractProduce();

    HSSFCell cell;
    cell = row.getCell((short) 0);
    String tmp = getAsString(cell);
    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
    {
      List<DboProduce> listDboProduces = DboProduce.findByCatalogNumber(tmp);
      if (listDboProduces.size() != 0)
      {
        conditionForContractProduce.setProduce(listDboProduces.get(0));
        Iterator<DboCatalogNumber> iter = conditionForContractProduce.getProduce().getCatalogNumbers().values().iterator();
        if (iter.hasNext())
        {
          DboCatalogNumber catalogNumber = iter.next();
          conditionForContractProduce.setStuffCategory(StuffCategoryDAO.load(context, catalogNumber.getStuffCategory().getId().toString()));
        }
      }
    }
    else
    {
      throw new Exception();
    }

    cell = row.getCell((short) 1);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      conditionForContractProduce.setCcp_price(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    cell = row.getCell((short) 2);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      conditionForContractProduce.setCcp_count(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    produces.add(conditionForContractProduce);
  }

}
