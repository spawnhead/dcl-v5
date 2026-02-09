package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ImportFileAction extends DBTransactionAction implements IDispatchable
{

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    throw new NotImplementedException();
  }

  static protected String getAsString(HSSFCell cell)
  {
    if (cell == null)
    {
      return "";
    }
    return cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK ?
            cell.getStringCellValue() :
            StringUtil.double2appCurrencyStringByMask(cell.getNumericCellValue(), "0.##");
  }

  static protected boolean isRowEmpty(HSSFRow row)
  {
    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
    {
      if (!StringUtil.isEmpty(getAsString(row.getCell((short) i))))
      {
        return false;
      }
    }
    return true;
  }
}
