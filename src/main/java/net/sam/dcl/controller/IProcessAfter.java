package net.sam.dcl.controller;

import org.apache.struts.action.ActionForward;

/**
 * User: Dima
 * Date: Feb 18, 2005
 * Time: 11:36:51 AM
 */
public interface IProcessAfter {
  ActionForward processAfter(IActionContext context,ActionForward forward) throws Exception;
}
