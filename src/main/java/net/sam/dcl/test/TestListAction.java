package net.sam.dcl.test;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;

import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 7:40:05 PM
 */
public class TestListAction extends ListAction {
  public Object listExecute(IActionContext context) throws Exception {
    ArrayList list = new ArrayList();
    list.add("Test 1");
    list.add("Test 2");
    return list;
  }
}
