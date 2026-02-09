package net.sam.dcl.dao;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:19:52 PM
 */
public class LoadException extends DAOException
{
  public LoadException()
  {
  }

  public LoadException(Object obj, String id)
  {
    super(obj.getClass().getName() + ":" + id);
  }

  public LoadException(String message)
  {
    super(message);
  }

  public LoadException(Throwable cause)
  {
    super(cause);
  }

  public LoadException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
