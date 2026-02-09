package net.sam.dcl.test;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 7:40:05 PM
 */
public class TestMapAction extends ListAction {
  public Object listExecute(IActionContext context) throws Exception {
    Map map = new HashMap();
    map.put("1","Test 1");
    map.put("2","Test 2");
    return map;
  }
}
