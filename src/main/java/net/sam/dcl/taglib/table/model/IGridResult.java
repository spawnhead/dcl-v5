package net.sam.dcl.taglib.table.model;

/**
 * @author: DG
 * Date: 17.02.2006
 * Time: 12:29:17
 */
public interface IGridResult<T> {
  T checkRow(String pk) throws Exception;
  T getRow(String pk) throws Exception;
  void setRow(String pk,T value) throws Exception;
}
