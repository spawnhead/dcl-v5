/**
 * Created by IntelliJ IDEA.
 * User: paa
 * Date: Mar 23, 2005
 * Time: 2:13:59 PM
 * To change this template use Options | File Templates.
 */
package net.sam.dcl.controller;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import javax.servlet.http.HttpServletRequest;

import net.sam.dcl.util.StringUtil;

public class BaseDispatchValidatorForm extends ValidatorActionForm    
{
  public static String mainPanel = "mainPanel";

  String activePanelName = mainPanel;

  public String getValidationKey(ActionMapping mapping, HttpServletRequest request)
  {
    String key = super.getValidationKey(mapping, request);
    String dispatchName = DispatchActionHelper.getDispatchMethodName(mapping, request);
    if (dispatchName != null)
    {
      key += ":" + dispatchName;
      if ("process".equals(dispatchName))
      {
        setActivePanelName(mainPanel);  
      }
    }
    return key;
  }

  public String getActivePanelName()
  {
    return activePanelName;
  }

  public void setActivePanelName(String activePanelName)
  {
    this.activePanelName = activePanelName;
  }

  public boolean isFillActivePanel()
  {
    return !StringUtil.isEmpty(activePanelName);
  }
}
