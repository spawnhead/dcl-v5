package net.sam.dcl.controller;

import org.apache.struts.action.ActionForward;

/**
 * User: Dima
 * Date: Feb 18, 2005
 * Time: 11:35:59 AM
 */
public interface IProcessBefore {
  ActionForward processBefore(IActionContext context) throws Exception;
}
