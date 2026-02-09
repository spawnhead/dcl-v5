package net.sam.dcl.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sam.dcl.beans.ImportPosition;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboProduce;

import java.util.List;
import java.util.ArrayList;

public class ImportData
{
  protected static Log log = LogFactory.getLog(ImportData.class);
  List<LeftRecord> leftOriginal = null;
  List<LeftRecord> leftIntermediate = new ArrayList<LeftRecord>();
  List<RightRecord> rightIntermediate = new ArrayList<RightRecord>();

  public ImportData.RightRecord findRightRecordByDocId(String doc_id)
  {
    for (ImportData.RightRecord record : rightIntermediate)
    {
      if ( record.getPosition().isUseMainId() )
      {
        if (StringUtil.equal(doc_id, record.getPosition().getId()))
        {
          return record;
        }
      }
      else
      {
        if (StringUtil.equal(doc_id, record.getPosition().getDoc_id()))
        {
          return record;
        }
      }
    }
    return null;
  }

  public ImportData.RightRecord findRightRecordByDocIdIdx(String doc_id, int idx)
  {
    for (ImportData.RightRecord record : rightIntermediate)
    {
      if ( record.getPosition().isUseMainId() )
      {
        if (record.getId() == idx && StringUtil.equal(doc_id, record.getPosition().getId()))
        {
          return record;
        }
      }
      else
      {
        if (record.getId() == idx && StringUtil.equal(doc_id, record.getPosition().getDoc_id()))
        {
          return record;
        }
      }
    }
    return null;
  }

  public List<RightRecord> getRightIntermediate()
  {
    return rightIntermediate;
  }

  public void setRightIntermediate(List<RightRecord> rightIntermediate)
  {
    this.rightIntermediate = rightIntermediate;
  }

  public List<LeftRecord> getLeftIntermediate()
  {
    return leftIntermediate;
  }

  public void setLeftIntermediate(List<LeftRecord> leftIntermediate)
  {
    this.leftIntermediate = leftIntermediate;
  }

  public List<LeftRecord> getLeftOriginal()
  {
    return leftOriginal;
  }

  public void setLeftOriginal(List<LeftRecord> leftOriginal)
  {
    this.leftOriginal = leftOriginal;
  }

  public static class LeftRecord
  {
    String id;
    String number;
    String date;
    String contractor;
		String contract;
		String specification;
		String orderProduceContractor;
    String doc_id;
    DboProduce produce = new DboProduce();
    double count;
    double reserved;
    String shipped_count = "";
    String selected_id;

    String ctn_number;
    String lpc_1c_number;
    String seller;
    String guarantee;

    public LeftRecord()
    {
    }

    public LeftRecord(ImportData.LeftRecord leftRecord)
    {
      this.id = leftRecord.id;
      this.number = leftRecord.number;
      this.date = leftRecord.date;
      this.contractor = leftRecord.contractor;
      this.doc_id = leftRecord.doc_id;
      produce = leftRecord.produce;
      this.count = leftRecord.count;
      this.reserved = leftRecord.reserved;
      this.shipped_count = leftRecord.shipped_count;
      this.selected_id = leftRecord.selected_id;
      this.ctn_number = leftRecord.ctn_number;
      this.lpc_1c_number = leftRecord.lpc_1c_number;
      this.seller = leftRecord.seller;
      this.guarantee = leftRecord.guarantee;
			this.contract = leftRecord.contract;
			this.specification = leftRecord.specification;
			this.orderProduceContractor = leftRecord.orderProduceContractor;
    }

    public String getId()
    {
      return id;
    }

    public void setId(String id)
    {
      this.id = id;
    }

    public String getNumber()
    {
      return number;
    }

    public String getNumber_date()
    {
      String retStr = "";

      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "msg.common.from", number, StringUtil.dbDateString2appDateString(date));
        if ( !StringUtil.isEmpty(contractor) )
        {
          String ctr = contractor.substring(0, contractor.length() - 2);
          retStr = StrutsUtil.getMessage(context, "msg.common.contractor_for", retStr, ctr);
        }
        if ( !StringUtil.isEmpty(seller) )
        {
          retStr += " " + getSellerFormatted();
        }
        if ( !StringUtil.isEmpty(guarantee) )
        {
          retStr += " " + getGuaranteeFormatted();
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return retStr;
    }

    public void setNumber(String number)
    {
      this.number = number;
    }

    public String getDate()
    {
      return date;
    }

    public void setDate(String date)
    {
      this.date = date;
    }

    public String getContractor()
    {
      return contractor;
    }

    public void setContractor(String contractor)
    {
      this.contractor = contractor;
    }

    public String getDoc_id()
    {
      return doc_id;
    }

    public void setDoc_id(String doc_id)
    {
      this.doc_id = doc_id;
    }

    public DboProduce getProduce()
    {
      return produce;
    }

    public void setProduce(DboProduce produce)
    {
      this.produce = produce;
    }

    public double getCount()
    {
      return count;
    }

    public void setCount(double count)
    {
      this.count = count;
    }

    public double getReserved()
    {
      return reserved;
    }

    public void setReserved(double reserved)
    {
      this.reserved = reserved;
    }

    public void setCount_formatted(String count)
    {
      this.count = StringUtil.appCurrencyString2double(count);
    }

    public String getCount_formatted()
    {
      return StringUtil.double2appCurrencyString(count);
    }

    public String getCountReservedFormatted()
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ProducesForAssembleMinsk.reservedLink",
                                     StringUtil.double2appCurrencyString(getCount()),
                                     getDoc_id(), 
                                     StringUtil.double2appCurrencyString(getReserved()),
                                     getShipped_count_formatted());
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return "";
    }

    public double getShipped_count()
    {
      return StringUtil.appCurrencyString2double(shipped_count);
    }

    public void setShipped_count(double shipped_count)
    {
      this.shipped_count = StringUtil.double2appCurrencyString(shipped_count);
    }

    public void setShipped_count_formatted(String shipped_count)
    {
      this.shipped_count = shipped_count;
    }

    public String getShipped_count_formatted()
    {
      return shipped_count;
    }

    public String getSelected_id()
    {
      return selected_id;
    }

    public void setSelected_id(String selected_id)
    {
      this.selected_id = selected_id;
    }

    public String getCtn_number()
    {
      return ctn_number;
    }

    public void setCtn_number(String ctn_number)
    {
      this.ctn_number = ctn_number;
    }

    public String getLpc_1c_number()
    {
      return lpc_1c_number;
    }

    public void setLpc_1c_number(String lpc_1c_number)
    {
      this.lpc_1c_number = lpc_1c_number;
    }

    public String getSeller()
    {
      return seller;
    }

    public String getSellerFormatted()
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        if ( !StringUtil.isEmpty(seller) )
        {
          if ( seller.equals("0") )
          {
            return " " + StrutsUtil.getMessage(context, "Order.import0");
          }
          if ( seller.equals("1") )
          {
            return " " + StrutsUtil.getMessage(context, "Order.import1");
          }
          if ( seller.equals("3") )
          {
            return " " + StrutsUtil.getMessage(context, "Order.import0_1");
          }
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }
      
      return seller;
    }

    public void setSeller(String seller)
    {
      this.seller = seller;
    }

    public String getGuarantee()
    {
      return guarantee;
    }

    public String getGuaranteeFormatted()
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "DeliveryRequest.guarantee");
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return seller;
    }

    public void setGuarantee(String guarantee)
    {
      this.guarantee = guarantee;
    }

		public String getContract() {
			return contract;
		}

		public void setContract(String contract) {
			this.contract = contract;
		}

		public String getSpecification() {
			return specification;
		}

		public void setSpecification(String specification) {
			this.specification = specification;
		}

		public String getOrderProduceContractor() {
			return orderProduceContractor;
		}

		public void setOrderProduceContractor(String orderProduceContractor) {
			this.orderProduceContractor = orderProduceContractor;
		}

		public String getOrderProduceInfoForGrid(){
			String orderProduceInfo = "";
			if(!StringUtil.isEmpty(orderProduceContractor)){
				orderProduceInfo = orderProduceInfo.concat(String.format("Для %s", orderProduceContractor));
			}

			if(!StringUtil.isEmpty(contract)){
				orderProduceInfo = orderProduceInfo.concat(String.format(" дог. %s", contract));
			}

			if(!StringUtil.isEmpty(specification)){
				orderProduceInfo = orderProduceInfo.concat(String.format(" спец. %s", specification));
			}

			return orderProduceInfo;
		}
	}

  public static class RightRecord
  {
    int id = 0;
    ImportPosition position = new ImportPosition();
    String selected_id;
    boolean modified = false;

    public int getId()
    {
      return id;
    }

    public void setId(int id)
    {
      this.id = id;
    }

    public ImportPosition getPosition()
    {
      return position;
    }

    public void setPosition(ImportPosition position)
    {
      this.position = position;
    }

    public String getSelected_id()
    {
      return selected_id;
    }

    public void setSelected_id(String selected_id)
    {
      this.selected_id = selected_id;
    }

    public boolean isModified()
    {
      return modified;
    }

    public void setModified(boolean modified)
    {
      this.modified = modified;
    }
  }
}
