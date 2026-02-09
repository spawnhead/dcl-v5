package net.sam.dcl.controller.processor;

import java.util.List;

/**
 * User: Dima
 * Date: Mar 20, 2005
 * Time: 1:39:08 PM
 */
public interface ActionProcessor {
  public static final String CONTROL = "ctrl";
  public static final String ACTION = "do";
  void addActionHandler(String action, ActionHandler handler);
  void addActionHandler(String control, String action, ActionHandler handler);
  void deleteActionHandler(String action);
  void deleteActionHandler(String control, String action);
  List findActionHandlers(String action);
  List findActionHandlers(String control, String action);
  boolean isProcessed();
}
