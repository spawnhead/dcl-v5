package net.sam.dcl.dao;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:18:34 PM
 */
public class DAOException extends Exception
{
  public DAOException()
  {
  }

  public DAOException(String message)
  {
    super(message);
  }

  public DAOException(Throwable cause)
  {
    super(cause);
  }

  public DAOException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
