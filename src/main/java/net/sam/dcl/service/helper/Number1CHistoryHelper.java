package net.sam.dcl.service.helper;

import net.sam.dcl.beans.ProduceCostProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.sql.Types;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shprotova
 * Date: 10/14/13
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Number1CHistoryHelper {

  static public String getLast1CNumberFromHistory(IActionContext context, String productId) throws Exception {

    VResultSet resultSet = getAll1CNumbersFromHistory(context, productId);
    try {
      resultSet.next();
      String number1C = resultSet.getData("NUMBER_1C");
      return number1C;
    } catch (VDbException e) {
      return null;
    }
  }

  static private VResultSet getAll1CNumbersFromHistory(IActionContext context, String productId) throws Exception {
    VDbConnection connection = context.getConnection();
    String sql = context.getSqlResource().get("select_from_1C_number_history");
    VParameter parameter = new VParameter();
    parameter.add("PRD_ID", productId, Types.INTEGER);
    return DAOUtils.executeQuery(connection, sql, null, parameter);
  }

  static public String getLastActual1CNumberFromHistory(IActionContext context, String id, String dateCreated) throws Exception {
    VResultSet resultSet = getAll1CNumbersFromHistory(context, id);
    Date actualDate = StringUtil.appDateString2Date(dateCreated);
    String tempNumber1C = null;
    String number1C=null;
    while (resultSet.next()) {
      Date date = StringUtil.dbDateString2Date(resultSet.getData("DATE_CREATED"));
      tempNumber1C = resultSet.getData("NUMBER_1C");
      if (date.after(actualDate)) continue;
      else {
        number1C = tempNumber1C;
        break;
      }
    }
    return number1C;
  }

  static public void insert(IActionContext context, String produceId, String number1C, String dateCreated) throws Exception {
    String sql = context.getSqlResource().get("insert_into_1C_number_history");
    VParameter parameter = new VParameter();
    parameter.add("PRD_ID", produceId, Types.INTEGER);
    parameter.add("NUMBER_1C", number1C, Types.VARCHAR);
    parameter.add("DATE_CREATED", StringUtil.appDateString2dbTimestampString(dateCreated), Types.VARCHAR);
    DAOUtils.executeQuery(context.getConnection(), sql, null, parameter);
  }

  static public void update(IActionContext context, String id, String number1C, String dateCreated) {
    VParameter parameter = new VParameter();
    parameter.add("ID", id, Types.INTEGER);
    parameter.add("NUMBER_1C", number1C, Types.VARCHAR);
    parameter.add("DATE_CREATED", StringUtil.appDateString2dbTimestampString(dateCreated), Types.VARCHAR);
    try {
      DAOUtils.executeQuery(context.getConnection(), context.getSqlResource().get("update_dcl_1c_number_history"), null, parameter);
    } catch (Exception e) {
    }
  }

  static public boolean isNumber1CExist(IActionContext context, ProduceCostProduce produce) throws Exception {
    VDbConnection connection = context.getConnection();
    String sql = context.getSqlResource().get("select_from_1C_number_history_by_prc_id_and_number_1_C");
    VParameter parameter = new VParameter();
    parameter.add("PRD_ID", String.valueOf(produce.getProduce().getId()), Types.INTEGER);
    parameter.add("NUMBER_1C", produce.getLpc_1c_number(), Types.VARCHAR);
    List<String> produces = DAOUtils.resultSet2StringList(DAOUtils.executeQuery(connection, sql, null, parameter));
    if (produces.size() > 0) return true;
    return false;
  }
}
