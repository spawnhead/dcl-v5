package net.sam.dcl.controller.processor;

import net.sam.dcl.controller.IActionContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import java.util.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Abstract class for all controllers.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.33 $
 */
public class ActionProcessorImpl  implements ActionProcessor{
  final static public String ACTION_NOT_PROCESSED = "ACTION_SHOW_FORM";

  protected static Log log = LogFactory.getLog(ActionProcessorImpl.class);

  protected Map controls = new HashMap();
  protected boolean isProcessed = false;
  public ActionForward execute(IActionContext context) throws Exception {
    String control = context.getRequest().getParameter(CONTROL);
    String action = context.getRequest().getParameter(ACTION);

    ActionForward actionForward =  processAction(context, control, action);
    if (isProcessed){
      if (actionForward == null){
        actionForward = context.getMapping().getInputForward();
      }
    }else{
      processAction(context, null,ACTION_NOT_PROCESSED);
      actionForward = null;
    }
    return actionForward;
  }
  protected ActionForward processAction(IActionContext context, String control, String action) throws Exception {
    isProcessed = false;
    ActionForward actionForward = null;
    Map actions = (Map) controls.get(control);
    if (actions!= null){
      List handlers = (List) actions.get(action);
      if (handlers!=null){
        for (int i = 0; i < handlers.size(); i++) {
          isProcessed = true;
          ActionHandler actionHandler = (ActionHandler) handlers.get(i);
          actionForward = actionHandler.process(context);
          if (actionForward != null){
            return actionForward;
          }
        }
      }
    }
    return actionForward;
  }

	public void doRegisterControls(IActionContext context) throws Exception {
		if (context.getForm() == null) return;
//		Map props = BeanUtils.describe(context.getForm());
		PropertyDescriptor descriptors[] =
				PropertyUtils.getPropertyDescriptors(context.getForm());
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if (Registerable.class.isAssignableFrom(descriptors[i].getPropertyType())) {
				Object obj = PropertyUtils.getProperty(context.getForm(), name);
				((Registerable) obj).register(this, name);
			}
		}
	}

	public void addActionHandler(String action, ActionHandler handler){
      addActionHandler(null,action, handler);
  }
  public void addActionHandler(String control, String action, ActionHandler handler){
    Map actions = (Map) controls.get(control);
    if (actions== null){
      actions = new HashMap();
      controls.put(control,actions);
    }
    List handlers = (List) actions.get(action);
    if (handlers==null){
      handlers = new ArrayList();
      actions.put(action,handlers);
    }
    handlers.add(0,handler);
  }
  public void deleteActionHandler(String action) {
    deleteActionHandler(null,action);
  }
  public void deleteActionHandler(String control, String action) {
    Map actions = (Map) controls.get(control);
    if (actions== null){
      return;
    }
    actions.remove(action);
  }
  public List findActionHandlers(String action) {
    return findActionHandlers(null,action);
  }
  public List findActionHandlers(String control, String action) {
    Map actions = (Map) controls.get(control);
    if (actions== null){
      return null;
    }
    return (List) actions.get(action);
  }
  public boolean isProcessed() {
    return isProcessed;
  }
}
