package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.form.BlankForm;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.BlankImage;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.dao.LanguageDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class BlankAction extends DBAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    BlankForm form = (BlankForm) context.getForm();

    Blank blank = (Blank) StoreUtil.getSession(context.getRequest(), Blank.class);

    blank.setBln_id(form.getBln_id());
    blank.setBlankType(form.getBlankType());
    blank.setSeller(form.getSeller());
    blank.setBln_name(form.getBln_name());
    blank.setBln_charset(form.getBln_charset());
    blank.setBln_preamble(form.getBln_preamble());
    blank.setBln_note(form.getBln_note());
    blank.setBln_usage(form.getBln_usage());
    try
    {
      if (!StringUtil.isEmpty(form.getLanguage().getId()))
      {
        blank.setLanguage(LanguageDAO.load(context, form.getLanguage().getId()));
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }

    StoreUtil.putSession(context.getRequest(), blank);
  }

  private void getCurrentFormFromBean(IActionContext context, Blank blankIn)
  {
    BlankForm form = (BlankForm) context.getForm();
    Blank blank;
    if (null != blankIn)
    {
      blank = blankIn;
    }
    else
    {
      blank = (Blank) StoreUtil.getSession(context.getRequest(), Blank.class);
    }

    if (null != blank)
    {
      form.setBln_id(blank.getBln_id());
      form.setBlankType(blank.getBlankType());
      form.setSeller(blank.getSeller());
      form.setBln_name(blank.getBln_name());
      form.setBln_charset(blank.getBln_charset());
      form.setBln_preamble(blank.getBln_preamble());
      form.setBln_note(blank.getBln_note());
      form.setBln_usage(blank.getBln_usage());
      form.setLanguage(blank.getLanguage());

      form.getGridImages().setDataList(blank.getImages());
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    BlankForm form = (BlankForm) context.getForm();

    if ( Constants.commercialProposalBlankType.equals(form.getBlankType().getId()) )
    {
      form.getBlankType().setName(StrutsUtil.getMessage(context, "blank_types_list.commercial_proposal_blank_name"));
      if ( form.getGridImages().getDataList().size() == 0 ) //новый док
      {
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_top")));
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_right")));
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_bottom")));
      }
    }
    if ( Constants.orderBlankType.equals(form.getBlankType().getId()) )
    {
      form.getBlankType().setName(StrutsUtil.getMessage(context, "blank_types_list.order_blank_name"));
      if ( form.getGridImages().getDataList().size() == 0 ) //новый док
      {
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_top")));
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_right")));
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_bottom")));
      }
    }
    if ( Constants.commonBlankType.equals(form.getBlankType().getId()) )
    {
      form.getBlankType().setName(StrutsUtil.getMessage(context, "blank_types_list.common_blank_name"));
      if ( form.getGridImages().getDataList().size() == 0 ) //новый док
      {
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_top")));
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_right")));
      }
    }
    if ( Constants.commonLightBlankType.equals(form.getBlankType().getId()) )
    {
      form.getBlankType().setName(StrutsUtil.getMessage(context, "blank_types_list.common_light_blank_name"));
      if ( form.getGridImages().getDataList().size() == 0 ) //новый док
      {
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_top")));
      }
    }
    if ( Constants.letterRequestBlankType.equals(form.getBlankType().getId()) )
    {
      form.getBlankType().setName(StrutsUtil.getMessage(context, "blank_types_list.letter_request_blank_name"));
      if ( form.getGridImages().getDataList().size() == 0 ) //новый док
      {
        form.getGridImages().getDataList().add(new BlankImage(StrutsUtil.getMessage(context, "Blank.img_left")));
      }
    }
    if ( !Constants.orderBlankType.equals(form.getBlankType().getId()) )
    {
      form.setBln_charset(StrutsUtil.getMessage(context, "Blank.default_charset"));
      form.setLanguage(LanguageDAO.loadByLngLetterId(context, null, StrutsUtil.getMessage(context, "Blank.default_lng")));
    }

    for ( int i = 0; i < form.getGridImages().getDataList().size(); i++ )
    {
      BlankImage blankImage = (BlankImage)form.getGridImages().getDataList().get(i);
      blankImage.setNumber(Integer.toString(i + 1));
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    Blank blank = new Blank();

    StoreUtil.putSession(context.getRequest(), blank);
    getCurrentFormFromBean(context, blank);
    
    return input(context);
  }

  public ActionForward reload(IActionContext context) throws Exception
  {
    BlankForm form = (BlankForm) context.getForm();
    form.getGridImages().getDataList().clear();
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    BlankForm form = (BlankForm) context.getForm();
    Blank blank = BlankDAO.load(context, form.getBln_id());

    StoreUtil.putSession(context.getRequest(), blank);
    getCurrentFormFromBean(context, blank);

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    BlankForm form = (BlankForm) context.getForm();

    String errMsg = "";
    if ( Constants.orderBlankType.equals(form.getBlankType().getId()) && StringUtil.isEmpty(form.getBln_charset()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.blank.empty_charset");
    }
    if ( Constants.orderBlankType.equals(form.getBlankType().getId()) && !StringUtil.isEmpty(form.getBln_charset()) && form.getBln_charset().length() > 20 )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.blank.length_charset");
    }
    if ( Constants.orderBlankType.equals(form.getBlankType().getId()) && StringUtil.isEmpty(form.getLanguage().getId()) )
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.blank.empty_language");
    }
    for ( int i = 0; i < form.getGridImages().getDataList().size(); i++ )
    {
      BlankImage blankImage = (BlankImage)form.getGridImages().getDataList().get(i);
      if ( StringUtil.isEmpty(blankImage.getBim_image()) )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.blank.empty_file_name", blankImage.getBim_name());
      }
      if ( !StringUtil.isEmpty(blankImage.getBim_image()) && blankImage.getBim_image().length() > 32 )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.blank.incorrect_file_name_length", blankImage.getBim_name());
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    saveCurrentFormToBean(context);

    Blank blank = (Blank) StoreUtil.getSession(context.getRequest(), Blank.class);
    if (StringUtil.isEmpty(form.getBln_id()))
    {
      BlankDAO.insert(context, blank);
    }
    else
    {
      BlankDAO.save(context, blank);
    }
    return context.getMapping().findForward("back");
  }

}