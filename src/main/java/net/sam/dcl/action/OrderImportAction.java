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
import net.sam.dcl.beans.*;
import net.sam.dcl.dbo.DboProduce;

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
public class OrderImportAction extends ImportFileAction
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
    Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
    int counter = 1;
    String badRows = "";
    List<OrderProduce> produces = new ArrayList<OrderProduce>();
    while (iterator.hasNext())
    {
      HSSFRow row = (HSSFRow) iterator.next();
      try
      {
        counter++;
        addRow(row, order, produces);
      }
      catch (Exception e)
      {
        log.error("Error while processing row:" + String.valueOf(counter), e);
        badRows += String.valueOf(counter) + ",";
      }
    }
    if (StringUtil.isEmpty(badRows))
    {
      for (OrderProduce produce : produces)
      {
        order.insertProduce(produce);
      }
    }
    else
    {
      StrutsUtil.addError(context, "OrderImport.error.msg", null);
    }
    return context.getMapping().findForward("back");
  }

  protected void addRow(HSSFRow row, Order order, List<OrderProduce> produces) throws Exception
  {
    OrderProduce orderProduce = Order.getEmptyProduce();

    HSSFCell cell;
    cell = row.getCell((short) 0);
    String tmp = getAsString(cell);
    if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
    {
      orderProduce.setOpr_produce_name(tmp);
    }
    else
    {
      throw new Exception();
    }

    cell = row.getCell((short) 1);
    tmp = getAsString(cell);
    if (cell != null && !StringUtil.isEmpty(tmp))
    {
      List<DboProduce> listDboProduces = DboProduce.findByCatalogNumberAndStuffCategory(tmp, new Integer(order.getStuffCategory().getId()));
      if (listDboProduces.size() != 0)
      {
        orderProduce.setProduce(listDboProduces.get(0));
        orderProduce.setStf_id(order.getStuffCategory().getId());
      }
    }

    cell = row.getCell((short) 2);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      orderProduce.setOpr_price_netto(cell.getNumericCellValue());
    }
    else
    {
      throw new Exception();
    }

    cell = row.getCell((short) 3);
    if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
    {
      orderProduce.setOpr_count(cell.getNumericCellValue());
      orderProduce.setOpr_count_executed(0.0);
      orderProduce.setOpr_count_occupied(0.0);
    }
    else
    {
      throw new Exception();
    }

    produces.add(orderProduce);
  }

}
