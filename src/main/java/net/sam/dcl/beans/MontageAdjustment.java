package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MontageAdjustment implements Serializable
{
  protected static Log log = LogFactory.getLog(MontageAdjustment.class);

  String mad_id;
  String mad_machine_type;
  ComplexityCategory complexityCategory = new ComplexityCategory();
  String mad_annul;

  public MontageAdjustment()
  {
  }

  public String getMad_id()
  {
    return mad_id;
  }

  public void setMad_id(String mad_id)
  {
    this.mad_id = mad_id;
  }

  public String getMad_machine_type()
  {
    return mad_machine_type;
  }

  public String getMad_machine_type_for_list()
  {
    String retStr = mad_machine_type;
    if ( !StringUtil.isEmpty(mad_annul) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr += StrutsUtil.getMessage(context, "MontageAdjustment.mad_annul_in_list");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }
    return retStr;
  }

  public void setMad_machine_type(String mad_machine_type)
  {
    this.mad_machine_type = mad_machine_type;
  }

  public ComplexityCategory getComplexityCategory()
  {
    return complexityCategory;
  }

  public void setComplexityCategory(ComplexityCategory complexityCategory)
  {
    this.complexityCategory = complexityCategory;
  }

  public String getMad_annul()
  {
    return mad_annul;
  }

  public void setMad_annul(String mad_annul)
  {
    this.mad_annul = mad_annul;
  }
}
