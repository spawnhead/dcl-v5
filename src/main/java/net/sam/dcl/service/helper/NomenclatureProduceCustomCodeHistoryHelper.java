package net.sam.dcl.service.helper;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.NomenclatureProduceCustomCodeHistoryForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.sql.Types;
import java.util.List;

/**
 * Created by A.Shkrobova.
 * Date: 5/23/2015.
 */
public class NomenclatureProduceCustomCodeHistoryHelper {
	private static IActionContext context = ActionContext.threadInstance();

	public static List<NomenclatureProduceCustomCodeHistoryForm.CustomCode> getAllCustomCodesFromHistory(String productId) {
		try {
			VParameter parameter = new VParameter();
			parameter.add("PRD_ID", productId, Types.INTEGER);
			context.setConnection(VDbConnectionManager.getVDbConnection());
			List<NomenclatureProduceCustomCodeHistoryForm.CustomCode> customCodes = DAOUtils.fillList(context, "select_all_custom_codes_for_product", null, NomenclatureProduceCustomCodeHistoryForm.CustomCode.class, null, parameter);
			return customCodes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void insert(String produceId, String customCode, String createdDate) {
		try {
			String sql = context.getSqlResource().get("insert_into_produce_custom_code_history");
			VParameter parameter = new VParameter();
			parameter.add("PRD_ID", produceId, Types.INTEGER);
			parameter.add("CUS_CODE", customCode, Types.VARCHAR);
			parameter.add("DATE_CREATED", StringUtil.appDateString2dbTimestampString(createdDate), Types.VARCHAR);
			DAOUtils.executeQuery(VDbConnectionManager.getVDbConnection(), sql, null, parameter);
		} catch (Exception e) {
			if (!(e instanceof VDbException && e.getMessage().equals("No resultset for sql"))) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void update(String id, String customCode, String dateCreated) {
		try {
			VParameter parameter = new VParameter();
			parameter.add("ID", id, Types.INTEGER);
			parameter.add("CUS_CODE", customCode, Types.VARCHAR);
			parameter.add("DATE_CREATED", StringUtil.appDateString2dbTimestampString(dateCreated), Types.VARCHAR);
			DAOUtils.executeQuery(VDbConnectionManager.getVDbConnection(), context.getSqlResource().get("update_produce_custom_code_history"), null, parameter);
		} catch (Exception e) {
			if (!(e instanceof VDbException && e.getMessage().equals("No resultset for sql"))) {
				throw new RuntimeException(e);
			}
		}
	}

	public static NomenclatureProduceCustomCodeHistoryForm.CustomCode loadCustomCodeForHistoryById(String id) {
		try {
			VParameter parameter = new VParameter("ID", id, Types.INTEGER);
			VResultSet resultSet = DAOUtils.executeQuery(VDbConnectionManager.getVDbConnection(), context.getSqlResource().get("select_custom_code_by_id_from_dcl_custom_code_history"), null, parameter);
			resultSet.next();
			NomenclatureProduceCustomCodeHistoryForm.CustomCode customCode = new NomenclatureProduceCustomCodeHistoryForm.CustomCode();
			customCode.setId(id);
			customCode.setCus_code(resultSet.getData("CUS_CODE"));
			customCode.setDate_created(resultSet.getData("DATE_CREATED"));
			return customCode;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String loadLastCustomCodeByDateAndProduce(String date, Integer produceId) {
		try {
			VParameter vParameter = new VParameter();
			vParameter.add("prd_id", String.valueOf(produceId), Types.INTEGER);
			vParameter.add("date_created", date, Types.VARCHAR);
			VResultSet resultSet = DAOUtils.executeQuery(VDbConnectionManager.getVDbConnection(), context.getSqlResource().get("select_custom_code_by_prd_and_date_from_dcl_custom_code_history"), null, vParameter);
      if (resultSet.next()) {
        return resultSet.getData("cus_code");
      }
    } catch (Exception e) {
			if (!(e instanceof VDbException && e.getMessage().equals("No resultset for sql"))) {
				throw new RuntimeException(e);
			}
		}

		return "";
	}
}
