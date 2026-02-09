package net.sam.dcl.message;

import org.apache.struts.action.ActionMessage;

/**
 * User: Dima
 * Date: Mar 11, 2005
 * Time: 5:21:28 PM
 */
public class EMessage extends ActionMessage {
  private Throwable exception = null;
  public EMessage(Throwable exception) {
    super("errors.common",exception.getMessage());
    this.exception = exception;
  }
  public EMessage(String key, Throwable exception) {
    super(key);
    this.exception = exception;
  }
  public EMessage(String key, Object value0, Throwable exception) {
    super(key, value0);
    this.exception = exception;
  }
  public EMessage(String key, Object value0, Object value1, Throwable exception) {
    super(key, value0, value1);
    this.exception = exception;
  }
  public EMessage(String key, Object value0, Object value1, Object value2, Throwable exception) {
    super(key, value0, value1, value2);
    this.exception = exception;
  }
  public EMessage(String key, Object value0, Object value1, Object value2, Object value3, Throwable exception) {
    super(key, value0, value1, value2, value3);
    this.exception = exception;
  }
  public EMessage(String key, Object[] values, Throwable exception) {
    super(key, values);
    this.exception = exception;
  }
  public Throwable getException() {
    return exception;
  }
  public void setException(Throwable exception) {
    this.exception = exception;
  }
}
