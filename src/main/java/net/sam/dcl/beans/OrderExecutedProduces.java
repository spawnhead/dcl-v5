package net.sam.dcl.beans;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderExecutedProduces extends BaseDispatchValidatorForm
{
  List<OrderExecutedDateLine> orderExecutedDate = new ArrayList<OrderExecutedDateLine>();
  List<OrderExecutedLine> orderExecuted = new ArrayList<OrderExecutedLine>();
  String disableEdit = "";

  public List<OrderExecutedDateLine> getOrderExecutedDate()
  {
    return orderExecutedDate;
  }

  public List<OrderExecutedLine> getOrderExecuted()
  {
    return orderExecuted;
  }

  public String getDisableEdit()
  {
    return disableEdit;
  }

  public void setDisableEdit(String disableEdit)
  {
    this.disableEdit = disableEdit;
  }

  public String getColspan()
  {
    return Integer.toString(getOrderExecutedDate().size() + 1);
  }

  //Добавляем в конец колонку с пустой строкой, при необходимости.
  public void checkAndFillLastColumn()
  {
    if (getDisableEdit().equals("true"))
      return;

    int size = getOrderExecutedDate().size();
    if ( (size > 0 && !StringUtil.isEmpty(getOrderExecutedDate().get(size - 1).getDate())) || size == 0 )
    {
      getOrderExecutedDate().add(new OrderExecutedDateLine("", true));
      for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
      {
        orderExecutedLine.getOrderExecutedByDate().add(StringUtil.double2appCurrencyString(0));
      }
    }
  }

  public String addNewExecutedDate(String newExecutedDate)
  {
    boolean foundDate = false;
    for (OrderExecutedDateLine executedDate : getOrderExecutedDate())
    {
      if (executedDate.getDate().equals(newExecutedDate))
      {
        foundDate = true;
        break;
      }
    }
    if (!foundDate)
    {
      int size = getOrderExecutedDate().size();
      getOrderExecutedDate().set(size - 1, new OrderExecutedDateLine(newExecutedDate, true));
    }
    else
    {
      return "OrderExecutedProduces.dateAlreadyExists";
    }

    return "";
  }

  public String setNewValueForDate(String forDate, String number, String newValue, Order order)
  {
    int index = -1;
    int i = 0;
    for (OrderExecutedDateLine executedDate : getOrderExecutedDate())
    {
      if (executedDate.getDate().equals(forDate))
      {
        index = i;
        break;
      }
      i++;
    }
    if (index >= 0)
    {
      for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
      {
        if (orderExecutedLine.getNumber().equals(number))
        {
          String oldValue = orderExecutedLine.getOrderExecutedByDate().get(index);
          orderExecutedLine.getOrderExecutedByDate().set(index, newValue);
          double allExecutedInLine = 0;
          for (String executedOnDate : orderExecutedLine.getOrderExecutedByDate())
          {
            allExecutedInLine += StringUtil.appCurrencyString2double(executedOnDate);
          }
          //Количество в строке превышено.
          if (allExecutedInLine > orderExecutedLine.getOpr_count())
          {
            //Возвращаем старое значение и возвращаем код ошибки.
            orderExecutedLine.getOrderExecutedByDate().set(index, oldValue);
            return "OrderExecutedProduces.countTooMuch";
          }
          //Нельзя, чтобы исполнено было меньше, чем уже в других доках.
          if (allExecutedInLine < order.getOprCountOccupiedByNumber(number))
          {
            //Возвращаем старое значение и возвращаем код ошибки.
            orderExecutedLine.getOrderExecutedByDate().set(index, oldValue);
            return "OrderExecutedProduces.incorrectRollback";
          }
          break;
        }
      }
    }

    recalcAllExecuted();
    setDeletedColumnFlag();

    return "";
  }

  //Пересчитывем количество исполнено по строкам.
  private void recalcAllExecuted()
  {
    for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
    {
      double allExecutedInLine = 0;
      for (String executedOnDate : orderExecutedLine.getOrderExecutedByDate())
      {
        allExecutedInLine += StringUtil.appCurrencyString2double(executedOnDate);
      }
      orderExecutedLine.setOpr_count_executed(allExecutedInLine);
    }
  }

  public void setDeletedColumnFlag()
  {
    for (int indDate = 0; indDate < getOrderExecutedDate().size(); indDate++)
    {
      OrderExecutedDateLine orderExecutedDateLine = getOrderExecutedDate().get(indDate);
      orderExecutedDateLine.setShowDeleteColumn(true);
      for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
      {
        if (StringUtil.appCurrencyString2double(orderExecutedLine.getOrderExecutedByDate().get(indDate)) > 0)
        {
          orderExecutedDateLine.setShowDeleteColumn(false);
          break;
        }
      }
    }
  }

  public void deleteColumn(int idx)
  {
    getOrderExecutedDate().remove(idx);
    for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
    {
      orderExecutedLine.getOrderExecutedByDate().remove(idx);
    }
  }
}