package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.DepartmentDAO;
import net.sam.dcl.dao.PurposeDAO;
import net.sam.dcl.dao.StuffCategoryDAO;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.ImportFileForm;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostImportAction extends ImportFileAction
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
    ProduceCost produceCost = (ProduceCost) StoreUtil.getSession(context.getRequest(), ProduceCost.class);
    int counter = 1;
    String badRows = "";
    while (iterator.hasNext())
    {
      HSSFRow row = (HSSFRow) iterator.next();
      try
      {
        counter++;
        addRow(row, context, produceCost);
      }
      catch (Exception e)
      {
        log.error("Error while processing row:" + String.valueOf(counter), e);
        badRows += String.valueOf(counter) + ",";
      }
    }
    if (!StringUtil.isEmpty(badRows))
    {
      badRows = badRows.substring(0, badRows.length() - 1);
      StrutsUtil.addError(context, "ProduceCostImport.skip.row", badRows, null);
    }

    return context.getMapping().findForward("back");
  }

  protected void addRow(HSSFRow row, IActionContext context, ProduceCost produceCost) throws Exception
  {
    ProduceCostProduce produceCostProduce = ProduceCost.getEmptyProduceCostProduce();

    String stuffCategoryName;

    HSSFCell cell;
    //"Производ итель (продукт)"
    cell = row.getCell((short) 0);
    if (cell != null)
    {
      StuffCategory stuffCategory = new StuffCategory(null, cell.getStringCellValue());
      if (!StuffCategoryDAO.loadByName(context, stuffCategory))
      {
        log.info("Can't find StuffCategory with name:" + stuffCategory.getName());
        stuffCategory = new StuffCategory();
      }
      produceCostProduce.setStuffCategory(stuffCategory);
    }
    else
    {
      throw new Exception();
    }

    //"№ по каталогу"
    cell = row.getCell((short) 1);
    String catalogNumberSell = getAsString(cell);
    if (cell != null && !StringUtil.isEmpty(catalogNumberSell))
    {
      List<DboProduce> listDboProduces = DboProduce.findByCatalogNumber(catalogNumberSell);
      if (listDboProduces.size() != 0)
      {
        produceCostProduce.setProduce(listDboProduces.get(0));
        DboCatalogNumber catalogNumber = produceCostProduce.getProduce().findCatalogNumberByNumber(catalogNumberSell);
        if (null != catalogNumber)
        {
          produceCostProduce.setStuffCategory(StuffCategoryDAO.load(context, catalogNumber.getStuffCategory().getId().toString()));
        }
      }
    }
    else
    {
      throw new Exception();
    }

    //"Кто заказал(Менеджер)(код)"
    cell = row.getCell((short) 2);
    if (cell != null)
    {
      User manager = new User();
      manager.setUsr_code(cell.getStringCellValue());
      if (!UserDAO.loadUserByCode(context, manager))
      {
        // process warning?
        log.info("Can't find Manager with code:" + manager.getUsr_code());
        manager = new User();
      }
      produceCostProduce.setManager(manager);
    }
    else
    {
      throw new Exception();
    }

    //Отдел
    cell = row.getCell((short) 3);
    if (cell != null)
    {
      Department department = new Department();
      department.setName(cell.getStringCellValue());
      if (!DepartmentDAO.loadByName(context, department))
      {
        // process warning?
        log.info("Can't find Department with name:" + department.getName());
        department = new Department();
      }
      produceCostProduce.setDepartment(department);
    }
    else
    {
      throw new Exception();
    }

    //Количество
    cell = row.getCell((short) 4);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_count(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    //Себестоимость за 1 ед., LTL
    cell = row.getCell((short) 5);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_cost_one_ltl(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    //Цена на складе за ед., BYN
    cell = row.getCell((short) 6);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_cost_one_by(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    //Цена по прейскуранту за ед., BYN
    cell = row.getCell((short) 7);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_price_list_by(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    //Вес, кг
    cell = row.getCell((short) 8);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_weight(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    //Стоимость, EUR
    cell = row.getCell((short) 9);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      produceCostProduce.setLpc_summ(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    String defaultLoadId = Config.getString(Constants.defaultPurposeProduce);
    Purpose defaultPurpose = new Purpose();
    defaultPurpose.setId(defaultLoadId);
    PurposeDAO.load(context, defaultPurpose);
    produceCostProduce.setPurpose(defaultPurpose);

    produceCost.insertProduceCostProduce(produceCostProduce);
  }
}
