package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.config.Config;

import java.util.*;
import java.io.File;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class LogoImgsListAction extends ListAction {
  public Object listExecute(IActionContext context) throws Exception {
    File dir = new File(Config.getString("img-logo.dir"));
    File files[] = dir.listFiles();
    List result = new ArrayList();
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      result.add(file.getName());
    }
    return result;
  }
}
