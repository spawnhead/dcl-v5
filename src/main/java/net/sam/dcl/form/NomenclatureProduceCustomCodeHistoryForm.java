package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * Created by A.Shkrobova.
 * Date: 5/23/2015.
 */
public class NomenclatureProduceCustomCodeHistoryForm extends BaseForm {
	private String produceDescription;
	private HolderImplUsingList grid = new HolderImplUsingList();
	private String prd_id;

	public String getProduceDescription() {
		return produceDescription;
	}

	public void setProduceDescription(String produceDescription) {
		this.produceDescription = produceDescription;
	}

	public HolderImplUsingList getGrid() {
		return grid;
	}

	public void setGrid(HolderImplUsingList grid) {
		this.grid = grid;
	}

	static public class CustomCode {
		String id;
		String cus_code;
		String date_created;

		public String getCus_code() {
			return cus_code;
		}

		public void setCus_code(String cus_code) {
			this.cus_code = cus_code;
		}

		public String getDate_created() {
			return StringUtil.dbTimestampString2appDateString(date_created);
		}

		public void setDate_created(String date_created) {
			this.date_created = date_created;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}

	public String getPrd_id() {
		return prd_id;
	}

	public void setPrd_id(String prd_id) {
		this.prd_id = prd_id;
	}
}
