package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ImportFileForm;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.StuffCategoryDAO;
import net.sam.dcl.dao.CustomCodeDAO;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboCatalogNumber;
import org.apache.struts.action.ActionForward;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposalImportAction extends ImportFileAction
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
    CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
    int counter = 1;
    String badRows = "";
    List<CommercialProposalProduce> produces = new ArrayList<CommercialProposalProduce>();
    while (iterator.hasNext())
    {
      HSSFRow row = (HSSFRow) iterator.next();
      try
      {
        counter++;
        addRow(row, context, commercialProposal, produces);
      }
      catch (Exception e)
      {
        log.error("Error while processing row:" + String.valueOf(counter), e);
        badRows += String.valueOf(counter) + ",";
      }
    }
    if (StringUtil.isEmpty(badRows))
    {
      for (CommercialProposalProduce commercialProposalProduce : produces)
      {
        commercialProposal.insertProduce("", commercialProposalProduce);
      }
    }
    else
    {
      StrutsUtil.addError(context, "CommercialProposalImport.error.msg", null);
    }
    return context.getMapping().findForward("back");
  }

  protected void addRow(HSSFRow row, IActionContext context, CommercialProposal commercialProposal, List<CommercialProposalProduce> produces) throws Exception
  {
    CommercialProposalProduce commercialProposalProduce = CommercialProposal.getEmptyProduce();
    commercialProposalProduce.setCpr_date(commercialProposal.getCpr_date());

    HSSFCell cell;

    cell = row.getCell((short) 0);
    if (cell != null)
    {
      commercialProposalProduce.setLpr_comment(getAsString(cell));
    }

    cell = row.getCell((short) 1);
    String catalogNumberCell = getAsString(cell);
    if (cell != null && !StringUtil.isEmpty(catalogNumberCell))
    {
      List<DboProduce> listDboProduces = DboProduce.findByCatalogNumber(catalogNumberCell);
      if (listDboProduces.size() != 0)
      {
        commercialProposalProduce.setProduce(listDboProduces.get(0));
        if ( !StringUtil.isEmpty(commercialProposal.getCpr_old_version()) )
        {
          commercialProposalProduce.setLpr_produce_name(commercialProposalProduce.getProduce().getFullName());  
        }
        DboCatalogNumber catalogNumber = commercialProposalProduce.getProduce().findCatalogNumberByNumber(catalogNumberCell);
        if (null != catalogNumber)
        {
          commercialProposalProduce.setStuffCategory(StuffCategoryDAO.load(context, catalogNumber.getStuffCategory().getId().toString()));
          if ( !StringUtil.isEmpty(commercialProposal.getCpr_old_version()) )
          {
            commercialProposalProduce.setLpr_catalog_num(catalogNumber.getNumber());  
          }
        }
      }
    }

    cell = row.getCell((short) 2);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      commercialProposalProduce.setLpr_price_netto(cell.getNumericCellValue());
    }
    else
    {
      log.info("Can't parse price");
      throw new Exception();
    }

    cell = row.getCell((short) 3);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      commercialProposalProduce.setLpr_count(cell.getNumericCellValue());
    }
    else
    {
      log.info("Can't parse count");
      throw new Exception();
    }

    cell = row.getCell((short) 4);
    catalogNumberCell = getAsString(cell);
    if (cell != null && !StringUtil.isEmpty(catalogNumberCell))
    {
      CustomCode customCode = new CustomCode();
      customCode.setCode(getAsString(cell));
      customCode.setDate(StringUtil.appDateString2dbTimestampString(StringUtil.date2appDateString(StringUtil.getCurrentDateTime())));
      if (CustomCodeDAO.loadByCode(context, customCode))
      {
        commercialProposalProduce.setCustomCode(customCode);
      }
      else
      {
        log.info("Can't find CustomCode with co:" + customCode.getCode());
      }
    }

    cell = row.getCell((short) 5);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      commercialProposalProduce.setLpr_coeficient(cell.getNumericCellValue());
    }
    else
    {
      log.info("Can't parse coefficient");
      throw new Exception();
    }
    produces.add(commercialProposalProduce);
  }
}
