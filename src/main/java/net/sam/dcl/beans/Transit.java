package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Transit implements Serializable
{
  protected static Log log = LogFactory.getLog(Transit.class);

  String asm_id;
  String dlr_id;
  String spi_id;
  String trn_date;
  String trn_number;
  double trn_produce_count;

  boolean processed;

  public Transit()
  {
  }

  public Transit(Transit transit)
  {
    asm_id = transit.getAsm_id();
    dlr_id = transit.getDlr_id();
    spi_id = transit.getSpi_id();
    trn_date = transit.getTrn_date();
    trn_number = transit.getTrn_number();
    trn_produce_count = transit.getTrn_produce_count();
  }

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public String getSpi_id()
  {
    return spi_id;
  }

  public void setSpi_id(String spi_id)
  {
    this.spi_id = spi_id;
  }

  public String getTrn_name()
  {
    return calcName(asm_id, dlr_id, spi_id);
  }

  public static String calcName(String asmId, String dlrId, String spiId)
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (!StringUtil.isEmpty(asmId))
      {
        return StrutsUtil.getMessage(context, "transitDocName.Assemble");
      }
      if (!StringUtil.isEmpty(dlrId))
      {
        return StrutsUtil.getMessage(context, "transitDocName.DeliveryRequest");
      }
      if (!StringUtil.isEmpty(spiId))
      {
        return StrutsUtil.getMessage(context, "transitDocName.SpecificationImport");
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public String getTrn_date()
  {
    return trn_date;
  }

  public void setTrn_date(String trn_date)
  {
    this.trn_date = trn_date;
  }

  public String getTrn_number()
  {
    return trn_number;
  }

  public void setTrn_number(String trn_number)
  {
    this.trn_number = trn_number;
  }

  public double getTrn_produce_count()
  {
    return trn_produce_count;
  }

  public void setTrn_produce_count(double trn_produce_count)
  {
    this.trn_produce_count = trn_produce_count;
  }

  public boolean isProcessed()
  {
    return processed;
  }

  public void setProcessed(boolean processed)
  {
    this.processed = processed;
  }
}