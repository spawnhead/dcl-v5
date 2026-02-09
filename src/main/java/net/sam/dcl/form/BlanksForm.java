package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.ReportDelimiterConsts;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class BlanksForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(BlanksForm.class);

  String type;
  HolderImplUsingList gridBlanks = new HolderImplUsingList();

  String bln_id;
  String printExample;
  boolean needPrintExample;
  String printAction;

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public HolderImplUsingList getGridBlanks()
  {
    return gridBlanks;
  }

  public void setGridBlanks(HolderImplUsingList gridBlanks)
  {
    this.gridBlanks = gridBlanks;
  }

  public String getBln_id()
  {
    return bln_id;
  }

  public void setBln_id(String bln_id)
  {
    this.bln_id = bln_id;
  }

  public String getPrintExample()
  {
    return printExample;
  }

  public void setPrintExample(String printExample)
  {
    this.printExample = printExample;
  }

  public boolean isNeedPrintExample()
  {
    return needPrintExample;
  }

  public void setNeedPrintExample(boolean needPrintExample)
  {
    this.needPrintExample = needPrintExample;
  }

  public String getPrintAction()
  {
    return printAction;
  }

  public void setPrintAction(String printAction)
  {
    this.printAction = printAction;
  }

  static public class Blank
  {
    String bln_id;
    String bln_type;
    String bln_name;
    String sln_name;
    String bln_images;
    String bln_charset;
    String bln_language;
    String bln_preamble;
    String bln_note;
    String bln_usage;

    public String getBln_id()
    {
      return bln_id;
    }

    public void setBln_id(String bln_id)
    {
      this.bln_id = bln_id;
    }

    public String getBln_type()
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        if ( Constants.commercialProposalBlankType.equals(bln_type) )
        {
          return StrutsUtil.getMessage(context, "blank_types_list.commercial_proposal_blank_name");
        }
        if ( Constants.orderBlankType.equals(bln_type) )
        {
          return StrutsUtil.getMessage(context, "blank_types_list.order_blank_name");
        }
        if ( Constants.commonBlankType.equals(bln_type) )
        {
          return StrutsUtil.getMessage(context, "blank_types_list.common_blank_name");
        }
        if ( Constants.commonLightBlankType.equals(bln_type) )
        {
          return StrutsUtil.getMessage(context, "blank_types_list.common_light_blank_name");
        }
        if ( Constants.letterRequestBlankType.equals(bln_type) )
        {
          return StrutsUtil.getMessage(context, "blank_types_list.letter_request_blank_name");
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return "";
    }

    public void setBln_type(String bln_type)
    {
      this.bln_type = bln_type;
    }

    public String getBln_name()
    {
      return bln_name;
    }

    public void setBln_name(String bln_name)
    {
      this.bln_name = bln_name;
    }

	  public String getSln_name()
	  {
		  return sln_name;
	  }

	  public void setSln_name(String sln_name)
	  {
		  this.sln_name = sln_name;
	  }

	  public String getBln_images()
    {
      return bln_images;
    }

    public void setBln_images(String bln_images)
    {
      this.bln_images = bln_images;
    }

    public String getBln_charset()
    {
      return bln_charset;
    }

    public void setBln_charset(String bln_charset)
    {
      this.bln_charset = bln_charset;
    }

    public String getBln_language()
    {
      return bln_language;
    }

    public void setBln_language(String bln_language)
    {
      this.bln_language = bln_language;
    }

    public String getBln_preamble()
    {
      return bln_preamble;
    }

    public void setBln_preamble(String bln_preamble)
    {
      this.bln_preamble = bln_preamble;
    }

    public String getBln_note()
    {
      return bln_note;
    }

    public void setBln_note(String bln_note)
    {
      this.bln_note = bln_note;
    }

    public String getBln_usage()
    {
      return bln_usage;
    }

    public String getBln_usage_formatted()
    {
      String retStr = getBln_usage();
      if (!StringUtil.isEmpty(retStr))
      {
        retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
      }

      return retStr;
    }

    public void setBln_usage(String bln_usage)
    {
      this.bln_usage = bln_usage;
    }

    public String getPrintExample()
    {
      if ( StringUtil.isEmpty(bln_id) )
      {
        return "";
      }

      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "Blanks.printExample");
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return "";
    }
  }
}