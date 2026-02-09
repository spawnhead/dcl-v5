package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.form.SpecificationImportForm;
import net.sam.dcl.form.SpecificationImportPositionsForm;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.util.*;
import net.sam.dcl.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * SpecificationImportAction: pww
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SpecificationImportAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(SpecificationImportAction.class);
	public static final String ORDER_POSITIONS_LOCK_NAME = "OrderPositions";
	public static final String DELIVERY_REQUEST_POSITIONS_LOCK_NAME = "DeliveryRequestPositions";
	public static final String POSITIONS_LOCK_ID = "FULL TABLE";

	private void saveCurrentFormToBean(IActionContext context)
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();

		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);

		specificationImport.setIs_new_doc(form.getIs_new_doc());

		specificationImport.setSpi_id(form.getSpi_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				specificationImport.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				specificationImport.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		specificationImport.setUsr_date_create(form.getUsr_date_create());
		specificationImport.setUsr_date_edit(form.getUsr_date_edit());
		specificationImport.setSpi_number(form.getSpi_number());
		specificationImport.setSpi_date(form.getSpi_date());
		specificationImport.setSpi_comment(form.getSpi_comment());
		specificationImport.setSpi_course(StringUtil.appCurrencyString2double(form.getSpi_course()));
		specificationImport.setSpi_koeff(StringUtil.appCurrencyString2double(form.getSpi_koeff()));
		specificationImport.setSpi_arrive(form.getSpi_arrive());
		specificationImport.setSpi_arrive_from(form.getSpi_arrive_from());

		StoreUtil.putSession(context.getRequest(), specificationImport);
	}

	private void getCurrentFormFromBean(IActionContext context, SpecificationImport specificationImportIn)
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		SpecificationImport specificationImport;
		if (null != specificationImportIn)
		{
			specificationImport = specificationImportIn;
		}
		else
		{
			specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		}

		if (null != specificationImport)
		{
			form.setIs_new_doc(specificationImport.getIs_new_doc());

			form.setSpi_id(specificationImport.getSpi_id());
			form.setCreateUser(specificationImport.getCreateUser());
			form.setEditUser(specificationImport.getEditUser());
			form.setUsr_date_create(specificationImport.getUsr_date_create());
			form.setUsr_date_edit(specificationImport.getUsr_date_edit());
			form.setSpi_number(specificationImport.getSpi_number());
			form.setSpi_date(specificationImport.getSpi_date());
			form.setSpi_comment(specificationImport.getSpi_comment());
			form.setSpi_course(specificationImport.getSpiCourseFormatted());
			form.setSpi_koeff(specificationImport.getSpi_koeff_formatted());
			form.setSpi_arrive(specificationImport.getSpi_arrive());
			form.setSpi_arrive_from(specificationImport.getSpi_arrive_from());

			form.getGridSpec().setDataList(specificationImport.getProduces());
		}
	}

	public ActionForward deleteProduce(IActionContext context) throws Exception
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		saveCurrentFormToBean(context);
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		specificationImport.deleteProduce(form.getNumber());
		StoreUtil.putSession(context.getRequest(), specificationImport);

		getCurrentFormFromBean(context, null);
		return show(context);
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		form.setSpi_arrive(form.getSpi_arrive_from());
		saveCurrentFormToBean(context);

		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);

		if (StringUtil.isEmpty(form.getSpi_id()))
		{
			SpecificationImportDAO.insert(context, specificationImport);
		}
		else
		{
			SpecificationImportDAO.save(context, specificationImport);
		}

		return true;
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		User currentUser = UserUtil.getCurrentUser(context.getRequest());

		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		specificationImport.calculate(currentUser.isEconomist());
		form.getGridSpec().setDataList(specificationImport.getProduces());
		StoreUtil.putSession(context.getRequest(), specificationImport);

    /*
      Создают и редактируют экономист, декларант
      После блокировки:
      read-only могут админ, экономист, декларант
    */
		if ("1".equals(specificationImport.getSpi_arrive()))
		{
			form.setFormReadOnly(true);
		}
		else
		{
			form.setFormReadOnly(false);
		}
		if (currentUser.isOnlyManager())
		{
			form.setFormReadOnly(true);
		}

		context.getRequest().setAttribute("show-checker", new IShowChecker()
		{
			int size = form.getGridSpec().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return context.getTable().getRecordCounter() < size;
			}
		}
		);

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				SpecificationImportProduce specificationImportProduce = (SpecificationImportProduce) form.getGridSpec().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (specificationImportProduce.isItog())
				{
					return "bold-cell";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				SpecificationImportProduce specificationImportProduce = (SpecificationImportProduce) form.getGridSpec().getDataList().get(context.getTable().getRecordCounter() - 1);
				return !(StringUtil.isEmpty(specificationImportProduce.getSip_occupied()) && !form.isFormReadOnly());
			}
		});


		return context.getMapping().findForward("form");
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward recalcPrices(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		specificationImport.setNeedRecalcPrices(true);

		return show(context);
	}

	public ActionForward backFromSelect(IActionContext context) throws Exception
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()))
		{
			LockedRecords.getLockedRecords().unlockWithTheSame(ORDER_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
		}
		if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()))
		{
			LockedRecords.getLockedRecords().unlockWithTheSame(DELIVERY_REQUEST_POSITIONS_LOCK_NAME, context.getRequest().getSession().getId());
		}
		ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);

		if (specificationImport.isHaveItog())
		{
			specificationImport.getProduces().remove(specificationImport.getProduces().size() - 1);
			specificationImport.setHaveItog(false);
		}
		for (int i = 0; i < specificationImport.getProduces().size(); i++)
		{
			SpecificationImportProduce specificationImportProduce = specificationImport.getProduces().get(i);
			if (!StringUtil.isEmpty(specificationImportProduce.getSip_occupied()))
			{
				continue;
			}

			if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()) && (StringUtil.isEmpty(specificationImportProduce.getOpr_id())))
			{
				continue;
			}
			if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()) && StringUtil.isEmpty(specificationImportProduce.getDrp_id()))
			{
				continue;
			}

			ImportData.RightRecord rightRecord = null;
			if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()))
			{
				rightRecord = data.findRightRecordByDocId(specificationImportProduce.getOpr_id());
			}
			if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()))
			{
				rightRecord = data.findRightRecordByDocId(specificationImportProduce.getDrp_id());
			}
			if (null != rightRecord) //нашли - значит эту запись отредактировали
			{
				rightRecord.setModified(true);
				specificationImportProduce.setSip_count(rightRecord.getPosition().getCount());
			}
			else //не нашли - значит эту запись удалили - удаляем из списка
			{
				specificationImport.getProduces().remove(i);
				i--;
			}
		}

		for (int i = 0; i < data.getRightIntermediate().size(); i++)
		{
			ImportData.RightRecord record = data.getRightIntermediate().get(i);
			if (!record.isModified()) //не измененная, а новая запись - добавляем в список товаров
			{
				SpecificationImportProduce specificationImportProduce = new SpecificationImportProduce();
				specificationImportProduce.setSpi_id(specificationImport.getSpi_id());
				specificationImportProduce.setSip_count(record.getPosition().getCount());

				specificationImportProduce.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

				if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()))
				{
					specificationImportProduce.setOpr_id(record.getPosition().getDoc_id());
					specificationImportProduce.setDrp_id(null);
					OrderProduce orderProduce = OrderProduceDAO.load(context, specificationImportProduce.getOpr_id());
					specificationImportProduce.setDrp_price(orderProduce.getDrp_price());
					specificationImportProduce.setDrp_max_extra(orderProduce.getDrp_max_extra());
					specificationImportProduce.setHave_depend(orderProduce.getOpr_have_depend());
					specificationImportProduce.setSip_price_calculated(false);

					Order order = OrderDAO.loadWithoutProduces(context, orderProduce.getOrd_id());
					specificationImportProduce.setOrd_number(order.getOrd_number());
					specificationImportProduce.setOrd_date(StringUtil.appDateString2dbDateString(order.getOrd_date()));
					specificationImportProduce.setStuffCategory(order.getStuffCategory());
					if (order.isAllIncludeInSpec() && !StringUtil.isEmpty(order.getOrd_by_guaranty()))
					{
						specificationImportProduce.setDrp_price(0.0);
					}
					if (!StringUtil.isEmpty(order.getOrd_in_one_spec())) //клиент для всего Заказа - грузим из Заказа
					{
						specificationImportProduce.setCustomer(order.getContractor_for());
						OrderDAO.loadContract(context, order);
						specificationImportProduce.setSpc_number(order.getSpecification().getSpc_number());
						specificationImportProduce.setSpc_date(order.getSpecification().getSpc_date());
						specificationImportProduce.setSpc_delivery_date(order.getSpecification().getSpc_delivery_date_formatted());
						specificationImportProduce.setCon_number(order.getContract().getCon_number());
						specificationImportProduce.setCon_date(order.getContract().getCon_date_formatted());
						specificationImportProduce.setCurrency(order.getContract().getCurrency());
					}
					else //клиент для отдельной строки - грузим из строки Заказа
					{
						specificationImportProduce.setCustomer(orderProduce.getContractor());
						specificationImportProduce.setSpc_number(orderProduce.getSpecification().getSpc_number());
						specificationImportProduce.setSpc_date(orderProduce.getSpecification().getSpc_date());
						specificationImportProduce.setSpc_delivery_date(orderProduce.getSpecification().getSpc_delivery_date_formatted());
						specificationImportProduce.setCon_number(orderProduce.getContract().getCon_number());
						specificationImportProduce.setCon_date(orderProduce.getContract().getCon_date_formatted());
						specificationImportProduce.setCurrency(orderProduce.getContract().getCurrency());
					}
					//только после получения Spc_delivery_date - смотри чуть выше.
					specificationImportProduce.calculateCountDay(specificationImport.getSpi_date());
					Purpose purpose = new Purpose();
					purpose.setId(Config.getString(Constants.defaultPurposeProduce));
					PurposeDAO.load(context, purpose);
					specificationImportProduce.setPurpose(purpose);
				}

				if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()))
				{
					specificationImportProduce.setOpr_id(null);
					specificationImportProduce.setDrp_id(record.getPosition().getDoc_id());
					DeliveryRequestProduce deliveryRequestProduce = DeliveryRequestProduceDAO.load(context, specificationImportProduce.getDrp_id());
					specificationImportProduce.setDrp_price(deliveryRequestProduce.getDrp_price());
					specificationImportProduce.setDrp_max_extra(deliveryRequestProduce.getDrp_max_extra());
					specificationImportProduce.setHave_depend(deliveryRequestProduce.getDrp_have_depend());
					specificationImportProduce.setSip_price_calculated(false);
					specificationImportProduce.setOrd_date(deliveryRequestProduce.getOrd_date());
					specificationImportProduce.setOrd_number(deliveryRequestProduce.getOrd_number());
					specificationImportProduce.setStuffCategory(deliveryRequestProduce.getStuffCategory());
					specificationImportProduce.setCustomer(deliveryRequestProduce.getCustomer());
					specificationImportProduce.setSpc_number(deliveryRequestProduce.getSpecification().getSpc_number());
					specificationImportProduce.setSpc_date(deliveryRequestProduce.getSpecification().getSpc_date());
					specificationImportProduce.setSpc_delivery_date(deliveryRequestProduce.getSpecification().getSpc_delivery_date_formatted());
					specificationImportProduce.setCon_number(deliveryRequestProduce.getContract().getCon_number());
					specificationImportProduce.setCon_date(deliveryRequestProduce.getContract().getCon_date_formatted());
					specificationImportProduce.setCurrency(deliveryRequestProduce.getContract().getCurrency());
					if (!StringUtil.isEmpty(deliveryRequestProduce.getOpr_id()))
					{
						//только после получения Spc_delivery_date - смотри чуть выше.
						specificationImportProduce.calculateCountDay(specificationImport.getSpi_date());
					}
					specificationImportProduce.setPurpose(deliveryRequestProduce.getPurpose());
					specificationImportProduce.setDlr_ord_not_form(deliveryRequestProduce.getDlr_ord_not_form());
				}

				specificationImportProduce.setSip_occupied("");

				specificationImportProduce.setNumber(Integer.toString(specificationImport.getProduces().size()));
				specificationImport.getProduces().add(specificationImportProduce);
			}
		}
		StoreUtil.putSession(context.getRequest(), specificationImport);

		getCurrentFormFromBean(context, null);
		return show(context);
	}

	public ActionForward selectDocsForSpecImport(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		SyncObject syncObjectForLock = null;
		if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()))
		{
			syncObjectForLock = new SyncObject(ORDER_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
		}
		if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()))
		{
			syncObjectForLock = new SyncObject(DELIVERY_REQUEST_POSITIONS_LOCK_NAME, POSITIONS_LOCK_ID, context.getRequest().getSession().getId());
		}
		SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
		if (!syncObjectCurrent.equals(syncObjectForLock))
		{
			StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
			StrutsUtil.addError(context, "error.record.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
			return show(context);
		}

		ImportData importData = new ImportData();
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		for (int i = 0; i < specificationImport.getProduces().size() - 1; i++)
		{
			SpecificationImportProduce specificationImportProduce = specificationImport.getProduces().get(i);
			if (SpecificationImportPositionsForm.ORDER_TARGET.equals(form.getTarget()))
			{
				//только те, что импортированы из заказа с флагом Все включено
				if (!StringUtil.isEmpty(specificationImportProduce.getOpr_id()))
				{
					if (StringUtil.isEmpty(specificationImportProduce.getSip_occupied()))
					{
						ImportPosition position = new ImportPosition();
						position.setDoc_id(specificationImportProduce.getOpr_id());
						position.setCount(specificationImportProduce.getSip_count());
						position.setProduce(specificationImportProduce.getProduce());
						ImportData.RightRecord rightRecord = new ImportData.RightRecord();
						rightRecord.setId(i);
						rightRecord.setPosition(new ImportPosition(position));
						importData.getRightIntermediate().add(rightRecord);
					}
				}
			}

			if (SpecificationImportPositionsForm.DELIVERY_REQUEST_TARGET.equals(form.getTarget()))
			{
				if (!StringUtil.isEmpty(specificationImportProduce.getDrp_id())) //только те, что импортированы из заявки
				{
					if (StringUtil.isEmpty(specificationImportProduce.getSip_occupied()))
					{
						ImportPosition position = new ImportPosition();
						position.setDoc_id(specificationImportProduce.getDrp_id());
						position.setCount(specificationImportProduce.getSip_count());
						position.setProduce(specificationImportProduce.getProduce());
						ImportData.RightRecord rightRecord = new ImportData.RightRecord();
						rightRecord.setId(i);
						rightRecord.setPosition(new ImportPosition(position));
						importData.getRightIntermediate().add(rightRecord);
					}
				}
			}

		}
		StoreUtil.putSession(context.getRequest(), importData);

		return context.getMapping().findForward("selectDocsForSpecImport");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();

		SpecificationImport specificationImport = new SpecificationImport();
		StoreUtil.putSession(context.getRequest(), specificationImport);
		//обнуляем поля формы
		getCurrentFormFromBean(context, specificationImport);

		form.setIs_new_doc("true");
		form.setSpi_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setSpi_arrive("");
		form.setSpi_arrive_from("");
		form.setSpi_koeff(StrutsUtil.getMessage(context, "SpecificationImport.spi_koeff_default"));

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();

		SpecificationImport specificationImport = SpecificationImportDAO.load(context, form.getSpi_id());
		specificationImport.setSpi_arrive_from(specificationImport.getSpi_arrive());
		StoreUtil.putSession(context.getRequest(), specificationImport);
		getCurrentFormFromBean(context, null);

		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context);

		if (retFromSave)
		{
			return context.getMapping().findForward("back");
		}
		else
		{
			return show(context);
		}
	}

	public ActionForward produceView(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("produceView");
	}

	public ActionForward generateExcel(IActionContext context) throws Exception
	{
		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		specificationImport.calculate(currentUser.isEconomist());

		Grid2Excel grid2Excel = new Grid2Excel("SpecificationImport Report");
		grid2Excel.doGrid2Excel(context, toExcelGrid(context, specificationImport.getProduces(), grid2Excel.getWb()));
		return null;
	}

	List toExcelGrid(IActionContext context, List produces, HSSFWorkbook wb)
	{
		List<Object> rows = new ArrayList<Object>();

		List<Object> header = new ArrayList<Object>();
		try
		{
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.fullName"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.unit"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.ctn_number"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.sip_price"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.sip_count"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.sip_cost"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.custom_code"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.stuffCategory"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.order"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.drp_price"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.currency"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.customer"));
			header.add(StrutsUtil.getMessage(context, "SpecificationImportProduces.custom_percent"));
			rows.add(header);

			for (int i = 0; i < produces.size() - 1; i++)
			{
				SpecificationImportProduce specificationImportProduce = (SpecificationImportProduce) produces.get(i);
				List<Object> record = new ArrayList<Object>();
				record.add(specificationImportProduce.getProduce().getFullName());
				if (specificationImportProduce.getProduce().getUnit() != null)
				{
					record.add(specificationImportProduce.getProduce().getUnit().getName());
				}
				record.add(specificationImportProduce.getCatalogNumberForStuffCategory());
				record.add(specificationImportProduce.getSip_price());
				record.add(specificationImportProduce.getSip_count());
				record.add(specificationImportProduce.getSip_cost());
				record.add(specificationImportProduce.getCustomCode());
				record.add(specificationImportProduce.getStuffCategory().getName());
				record.add(specificationImportProduce.getOrd_number_date());
				if (!StringUtil.isEmpty(specificationImportProduce.getDrp_max_extra()))
				{
					record.add(new Grid2Excel.CellFormat(wb, specificationImportProduce.getDrp_max_extra_formatted(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
				}
				else
				{
					record.add(specificationImportProduce.getDrp_price());
				}
				record.add(specificationImportProduce.getCurrency().getName());
				record.add(specificationImportProduce.getCustomer().getName());
				record.add(specificationImportProduce.getCustom_percent_formatted());
				rows.add(record);

				if (!StringUtil.isEmpty(specificationImportProduce.getHave_depend()))
				{
					List lst = DAOUtils.fillList(context, "select-sip_depended_lines", specificationImportProduce, SpecificationImportProduce.class, null, null);
					for (int j = 0; j < lst.size(); j++)
					{
						SpecificationImportProduce specificationImportProduceDepend = (SpecificationImportProduce) lst.get(j);
						if (null != specificationImportProduceDepend.getProduce().getId())
						{
							specificationImportProduceDepend.setProduce(ProduceDAO.loadProduce(specificationImportProduceDepend.getProduce().getId()));
							//сборка
							if ("-1".equals(specificationImportProduceDepend.getId()))
							{
								specificationImportProduceDepend.setSip_count(specificationImportProduceDepend.getSip_count() * specificationImportProduce.getSip_count());
							}

							List<Object> recordInner = new ArrayList<Object>();
							recordInner.add(new Grid2Excel.CellFormat(wb, "        " + Integer.toString(j + 1) + ") " + specificationImportProduceDepend.getProduce().getFullName(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							if (specificationImportProduceDepend.getProduce().getUnit() != null)
							{
								recordInner.add(new Grid2Excel.CellFormat(wb, specificationImportProduceDepend.getProduce().getUnit().getName(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							}
							recordInner.add(new Grid2Excel.CellFormat(wb, specificationImportProduceDepend.getCatalogNumberForStuffCategory(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, 0.0, HorizontalAlignment.RIGHT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, specificationImportProduceDepend.getSip_count(), HorizontalAlignment.RIGHT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, 0.0, HorizontalAlignment.RIGHT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, "", HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, specificationImportProduceDepend.getStuffCategory().getName(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							recordInner.add(new Grid2Excel.CellFormat(wb, specificationImportProduceDepend.getOrd_number_date(), HorizontalAlignment.LEFT, HSSFFont.COLOR_RED, true));
							rows.add(recordInner);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return rows;
	}

	public ActionForward recalcPriceByCustomPercent(IActionContext context) throws Exception
	{
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		if (!StringUtil.isEmpty(form.getRecordNumberForRecalc()))
		{
			double percent = 0.0;
			if (!StringUtil.isEmpty(form.getValueForRecalc()))
			{
				percent = StringUtil.appCurrencyString2double(form.getValueForRecalc());
			}
			for (int i = 0; i < form.getGridSpec().getDataList().size(); i++)
			{
				SpecificationImportProduce specificationImportProduce = (SpecificationImportProduce) form.getGridSpec().getDataList().get(i);
				if (form.getRecordNumberForRecalc().equals(specificationImportProduce.getNumber()))
				{
					specificationImportProduce.calculateSipCost(percent, specificationImport.getSpi_course(), specificationImport.getSpi_koeff());
					form.setRecordNumberForRecalc(String.valueOf(i));
					break;
				}
			}
		}
		specificationImport.calculateSpiCost();
		return context.getMapping().findForward("SpecificationImportRecalcPriceByCustomPercent");
	}

	public ActionForward recalcCostByPrice(IActionContext context) throws Exception
	{
		SpecificationImport specificationImport = (SpecificationImport) StoreUtil.getSession(context.getRequest(), SpecificationImport.class);
		SpecificationImportForm form = (SpecificationImportForm) context.getForm();
		if (!StringUtil.isEmpty(form.getRecordNumberForRecalc()))
		{
			double sipPrice = 0.0;
			if (!StringUtil.isEmpty(form.getValueForRecalc()))
			{
				sipPrice = StringUtil.appCurrencyString2double(form.getValueForRecalc());
			}
			for (int i = 0; i < form.getGridSpec().getDataList().size(); i++)
			{
				SpecificationImportProduce specificationImportProduce = (SpecificationImportProduce) form.getGridSpec().getDataList().get(i);
				if (form.getRecordNumberForRecalc().equals(specificationImportProduce.getNumber()))
				{
					sipPrice = StringUtil.roundN(sipPrice, 2);
					specificationImportProduce.setSip_price(sipPrice);
					double sip_cost = sipPrice * specificationImportProduce.getSip_count();
					sip_cost = StringUtil.roundN(sip_cost, 2);
					specificationImportProduce.setSip_cost(sip_cost);
					form.setRecordNumberForRecalc(String.valueOf(i));
					break;
				}
			}
		}
		specificationImport.calculateSpiCost();
		return context.getMapping().findForward("SpecificationImportRecalcCostByPrice");
	}

}
