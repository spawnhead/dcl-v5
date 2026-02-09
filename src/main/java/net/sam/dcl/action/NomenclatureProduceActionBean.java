package net.sam.dcl.action;

import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.User;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.controller.ValidationException;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dao.BlankDAO;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dbo.*;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.service.helper.Number1CHistoryHelper;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class NomenclatureProduceActionBean extends ActionBean
{
	protected static Log log = LogFactory.getLog(NomenclatureProduceActionBean.class);

	DboProduce produce;
	String id;
	String cat_id;
	HolderImplUsingList gridCN;
	GridResult<String> catalogNumbersSelectedIds;
	DboUnit unit = new DboUnit();
	DboCustomCode customCode = new DboCustomCode();
	String number1C;
	List<LangRow> languageTranslations;
	List<CatalogNumberRow> catalogNumberRows;
	HolderImplUsingList gridLT;
	boolean produceUsed = false;

	boolean allReadOnly = false;

	boolean formReadOnly = true;
	boolean number1CButtonReadOnly = true;
	boolean blockReadOnly = true;
	boolean infoFieldsReadOnly = true;
	boolean blankReadOnly = true;
	boolean deleteSelectedBtnReadonly = false;
	boolean stuffCategoryReadonly = false;
	boolean montageAdjustmentReadonly = false;
	boolean saveReadOnly = false;
	boolean customCodeHistoryButtonReadonly = false;

	String warning;
	String warningCyrillicChars;
	String warningDigitChars;
	String warningIncorrectFirstWord;
	int warningLinesCount = 0;

	int timeOutForNoForProduceError = 0;

	boolean printTo = false;
	Blank blank = new Blank();
	Integer selectedImage;
	String doPrintTo;
	boolean canEditCustomCodeForSpcImport = false;

	DeferredAttachmentService attachmentService = null;

	String printScale = "100";

	private List<String> fillAdjectiveTerminations()
	{
		List<String> terminations = new ArrayList<String>();
		try
		{
			String terminationsStr = StrutsUtil.getMessage(this, "NomenclatureProduce.adjectiveTerminations");
			String[] terminationsArr = terminationsStr.split(" ");
			Collections.addAll(terminations, terminationsArr);
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return terminations;
	}

	public void initForCreate() throws Exception
	{
		produce = new DboProduce();
		attachmentService = DeferredAttachmentService.create(request.getSession(), referencedTable, mapping.findForward("backFromAttach"), null);
		initForEdit();
	}

	public void initForEdit() throws Exception
	{
		id = null;
		gridCN = new HolderImplUsingList();
		gridLT = new HolderImplUsingList();
		resetCatalogNumbersSelectedIds();
		if (produce.getUnit() != null)
		{
			unit = new DboUnit(produce.getUnit());
		}
		else
		{
			unit = (DboUnit) hibSession.load(DboUnit.class, Integer.parseInt(Config.getString(Constants.defaultNomenclatureUnit)));
		}
		customCode = produce.getLastCustomCode();

		if (customCode == null)
		{
			customCode = new DboCustomCode();
		}
		number1C = Number1CHistoryHelper.getLast1CNumberFromHistory(this, String.valueOf(produce.getId()));
		this.getRequest().getSession().setAttribute("produce", produce);
		catalogNumberRows = new ArrayList<CatalogNumberRow>();

		blank = BlankDAO.load(this, Config.getString(Constants.defaultNomenclatureBlank));
		canEditCustomCodeForSpcImport = ProduceDAO.GetCanEditCustomCodeForSpcImport(this, produce.getId());

		doPrintTo = "0";
		printTo = false;
		selectedImage = null;
		if (produce.getId() != null)
		{
			attachmentService = DeferredAttachmentService.create(request.getSession(), referencedTable, produce.getId(), mapping.findForward("backFromAttach"), null);
		}
		Hibernate.initialize(produce.getCreateUser());
		Hibernate.initialize(produce.getEditUser());
		Hibernate.initialize(produce.getBlockUser());
	}


	public ActionForward show() throws Exception
	{
		warning = null;
		warningCyrillicChars = null;
		warningDigitChars = null;
		warningIncorrectFirstWord = null;
		return internalShow();
	}

	private ActionForward internalShow() throws Exception
	{
		User user = UserUtil.getCurrentUser(request);
		if (user.isAdmin() || user.isEconomist()) number1CButtonReadOnly = false;
		if (user.isUserInLithuania())
		{
			infoFieldsReadOnly = formReadOnly = infoFieldsReadOnly =
							montageAdjustmentReadonly = stuffCategoryReadonly = deleteSelectedBtnReadonly = saveReadOnly = true;
			blankReadOnly = false;
		}
		else
		{
			infoFieldsReadOnly = !(user.isAdmin() || user.isManager() || user.isEconomist());
			blankReadOnly = !(user.isAdmin() || user.isManager() || user.isDeclarant() || user.isEconomist());

			if (produceUsed)
			{
				stuffCategoryReadonly = deleteSelectedBtnReadonly = true;
				montageAdjustmentReadonly = !(user.isAdmin() || user.isEconomist());
				blockReadOnly = !(user.isAdmin() || user.isDeclarant());
				formReadOnly = !user.isAdmin();
			}
			else
			{

				if (user.isAdmin() || user.isManager() || user.isEconomist())
				{
					formReadOnly = false;
					montageAdjustmentReadonly = stuffCategoryReadonly = deleteSelectedBtnReadonly = false;
				}
				else
				{
					montageAdjustmentReadonly = stuffCategoryReadonly = deleteSelectedBtnReadonly = true;
				}
				if (user.isAdmin())
				{
					blockReadOnly = false;
				}
			}
			saveReadOnly = blockReadOnly && produce.isBBlock();
		}

		if (isAllReadOnly())
		{
			setFormReadOnly(true);
			setNumber1CButtonReadOnly(true);
			setBlockReadOnly(true);
			setInfoFieldsReadOnly(true);
			setBlankReadOnly(true);
			setDeleteSelectedBtnReadonly(true);
			setStuffCategoryReadonly(true);
			setMontageAdjustmentReadonly(true);
			setSaveReadOnly(true);
			setCustomCodeHistoryButtonReadonly(true);
		}

		attachmentsGrid = new HolderImplUsingList();
		attachmentsGrid.setDataList(attachmentService.list());
		request.setAttribute("attachRadioChecker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				DboAttachment attachment = (DboAttachment) context.getBean();
				String ext = attachment.getOriginalFileExtention();
				return (".GIF".equals(ext) || ".JPG".equals(ext));
			}
		});

		setTimeOutForNoForProduceError(Config.getNumber(Constants.timeOutForNoForProduceError, 90));

		return mapping.findForward("form");
	}

	public ActionForward input() throws Exception
	{
		initForCreate();
		customCodeHistoryButtonReadonly = !UserUtil.getCurrentUser(request).isAdmin();
		produceUsed = false;
		DboCategory category = (DboCategory) hibSession.load(DboCategory.class, getCat_id_integer());
		produce.setCategory(category);
		languageTranslations = hibSession
						.getNamedQuery("languages-except-one")
						.setString("code", Config.getString(Constants.nomenclatureProduceMainLang))
						.setResultTransformer(new AliasToBeanResultTransformer(NomenclatureProduceActionBean.LangRow.class))
						.list();
		addRussionLangToTranslations();

		return show();
	}

	public ActionForward copy() throws Exception
	{
		SelectFromGridAction.interruptSelectMode(this);
		editInternal();
		produceUsed = false;
		customCode = new DboCustomCode();
		DboProduce produceCopy = new DboProduce(null,
						null,
						null,
						null,
						produce.getName(),
						produce.getType(),
						produce.getParams(),
						produce.getAddParams(),
						produce.getMaterial(),
						produce.getPurpose(),
						produce.getSpecification(),
						produce.getPrinciple());

		initForCreate();
		customCodeHistoryButtonReadonly = !UserUtil.getCurrentUser(request).isAdmin();
		produceUsed = false;
		DboCategory category = (DboCategory) hibSession.load(DboCategory.class, getCat_id_integer());
		produce.setCategory(category);

		produce.setName(produceCopy.getName());
		produce.setType(produceCopy.getType());
		produce.setParams(produceCopy.getParams());
		produce.setAddParams(produceCopy.getAddParams());
		produce.setMaterial(produceCopy.getMaterial());
		produce.setPurpose(produceCopy.getPurpose());
		produce.setSpecification(produceCopy.getSpecification());
		produce.setPrinciple(produceCopy.getPrinciple());

		return show();
	}

	public ActionForward edit() throws Exception
	{
		if (id != null && !id.equals("null"))
		{
			User currentUser = UserUtil.getCurrentUser(request);
			customCodeHistoryButtonReadonly = !(currentUser.isAdmin() || currentUser.isDeclarant());
			editInternal();
			produceUsed = isProduceUsedSomewhere(produce);
		}

		return show();
	}

	private void editInternal() throws Exception
	{
		produce = (DboProduce) hibSession.get(DboProduce.class, new Integer(id));
		initForEdit();
		for (DboCatalogNumber catalogNumber : produce.getCatalogNumbers().values())
		{
			CatalogNumberRow row = new CatalogNumberRow(catalogNumber);
			catalogNumberRows.add(row);
		}

		languageTranslations = hibSession
						.getNamedQuery("languages-for-produce-except-one")
						.setString("code", Config.getString(Constants.nomenclatureProduceMainLang))
						.setInteger("produce_id", produce.getId())
						.setResultTransformer(new AliasToBeanResultTransformer(LangRow.class))
						.list();

		addRussionLangToTranslations();
		Hibernate.initialize(produce.getProduceLanguages());
		hibSession.evict(produce);
	}

	private void addRussionLangToTranslations()
	{
		DboLanguage lang = DboLanguage.DAO.loadByLangCode(Config.getString(Constants.nomenclatureProduceMainLang));
		languageTranslations.add(0, new LangRow(lang.getCode(), lang.getName(), produce.getName()));
	}

	private boolean isProduceUsedSomewhere(DboProduce produce)
	{
		Session session = hibSession;
		Long usageCount = (Long) session
						.getNamedQuery("find-produce-usage-count-dlr-list-produce")
						.setEntity("produce", produce)
						.uniqueResult();
		if (usageCount == 0)
		{
			usageCount = (Long) session
							.getNamedQuery("find-produce-usage-count-prc-list-produce")
							.setEntity("produce", produce)
							.uniqueResult();
			if (usageCount == 0)
			{
				usageCount = (Long) session
								.getNamedQuery("find-produce-usage-count-order")
								.setEntity("produce", produce)
								.uniqueResult();
				if (usageCount == 0)
				{
					usageCount = (Long) session
									.getNamedQuery("find-produce-usage-count-spi-list-produce")
									.setEntity("produce", produce)
									.uniqueResult();
					return usageCount > 0;
				}
			}
		}
		return true;
	}

	public ActionForward back() throws Exception
	{
		attachmentService.rollback();
		attachmentService = null;
		DeferredAttachmentService.removeLast(request.getSession());
		return mapping.findForward("back");
	}

	public ActionForward ajaxCNGrid() throws Exception
	{
		request.setAttribute("grid-checker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CatalogNumberRow row = (CatalogNumberRow) ctx.getBean();
				return row.getCatalogNumber().getId() >= 0 && formReadOnly;
			}
		});
		request.setAttribute("grid-checker-for-stuffCategory", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CatalogNumberRow row = (CatalogNumberRow) ctx.getBean();
				return row.getCatalogNumber().getId() >= 0 && stuffCategoryReadonly;
			}
		});
		request.setAttribute("grid-checker-for-montageAdjustment", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CatalogNumberRow row = (CatalogNumberRow) ctx.getBean();
				return row.getCatalogNumber().getId() >= 0 && montageAdjustmentReadonly;
			}
		});

		gridCN.setDataList(catalogNumberRows);
		return mapping.findForward("ajaxCNGrid");
	}

	public ActionForward ajaxAddRowInCNGrid() throws Exception
	{
		int generatedFakeId = 0 - (catalogNumberRows.size() + 1);
		catalogNumberRows.add(
						new CatalogNumberRow(
										new DboCatalogNumber(generatedFakeId, produce, new DboStuffCategory(), null, null)));
		return ajaxCNGrid();
	}

	public ActionForward ajaxDeleteRowFromCNGrid() throws Exception
	{
		for (String id : catalogNumbersSelectedIds.getRecordList())
		{
			CatalogNumberRow row = findCatalogNumberRow(id);
			if (row != null)
			{
				catalogNumberRows.remove(row);
			}
		}
		resetCatalogNumbersSelectedIds();

		return ajaxCNGrid();
	}

	public ActionForward ajaxLTGrid() throws Exception
	{
		request.setAttribute("langChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				User currentUser = UserUtil.getCurrentUser(request);
				LangRow row = (LangRow) ctx.getBean();
				if (produceUsed && Config.getString(Constants.nomenclatureProduceMainLang).equalsIgnoreCase(row.getLangCode()))
				{
					return !(currentUser.isAdmin());
				}
				else if (produceUsed)
				{
					return !(currentUser.isAdmin() || currentUser.isManager());
				}
				else
				{
					return !(currentUser.isAdmin() || currentUser.isManager() || currentUser.isEconomist());
				}
			}
		});

		gridLT.setDataList(languageTranslations);
		return mapping.findForward("ajaxLTGrid");
	}

	public ActionForward process() throws Exception
	{
		validate();
		if (hasWarnings())
		{
			return internalShow();
		}
		return save();
	}

	private boolean isCatalogNumbersContainsCyrilicChars(Map<Integer, DboCatalogNumber> catalogNumbers)
	{
		for (DboCatalogNumber catalogNumber : catalogNumbers.values())
		{
			if (StringUtil.isContainsCyrilicChars(catalogNumber.getNumber()))
			{
				return true;
			}
		}

		return false;
	}

	private boolean hasWarnings() throws Exception
	{
		warningLinesCount = 0;
		StringBuilder warningBuffer = new StringBuilder();
		warning = "";
		for (CatalogNumberRow row : catalogNumberRows)
		{
			DboCatalogNumber catalogNumber = row.getCatalogNumber();
			List<DboProduce> produces = getSameCatalogNumbers(catalogNumber);
			if (produces.size() > 0)
			{
				warningBuffer.append(StrutsUtil.getMessage(this, "error.catalog-number.already-exist", catalogNumber.getNumber()));
				warningLinesCount++;
				for (DboProduce pr : produces)
				{
					warningBuffer.append(StrutsUtil.getMessage(this, "error.catalog-number.already-exist.position", pr.getName(), pr.getType(), pr.getParams(), pr.getAddParams()));
					warningLinesCount++;
				}
			}
		}
		if (warningBuffer.length() != 0)
		{
			warningBuffer.append(StrutsUtil.getMessage(this, "error.catalog-number.already-exist.question"));
			warningLinesCount++;
			warning = warningBuffer.toString();
		}
		warningCyrillicChars = "";
		boolean containsCyrillicCharacters = false;
		if (StringUtil.isContainsCyrilicChars(produce.getType()) ||
						StringUtil.isContainsCyrilicChars(produce.getParams()) ||
						StringUtil.isContainsCyrilicChars(produce.getAddParams()) ||
						isCatalogNumbersContainsCyrilicChars(produce.getCatalogNumbers()))
		{
			containsCyrillicCharacters = true;
		}
		else
		{
			for (int i = 1; i < languageTranslations.size(); i++)
			{
				NomenclatureProduceActionBean.LangRow langRow = languageTranslations.get(i);
				if (StringUtil.isContainsCyrilicChars(langRow.getText()))
				{
					containsCyrillicCharacters = true;
					break;
				}
			}
		}
		if (containsCyrillicCharacters)
		{
			warningCyrillicChars = StrutsUtil.getMessage(this, "error.NomenclatureProduce.contains-cyrillic-chars");
		}

		warningDigitChars = "";
		boolean containsDigitCharacters = false;
		if (!StringUtil.isEmpty(languageTranslations.get(0).getText()) && StringUtil.isContainsDigitChars(languageTranslations.get(0).getText()))
		{
			containsDigitCharacters = true;
		}
		if (containsDigitCharacters)
		{
			warningDigitChars = StrutsUtil.getMessage(this, "error.NomenclatureProduce.contains-digit-chars");
		}

		warningIncorrectFirstWord = "";
		boolean containsIncorrectFirstWord = false;
		for (String termination : fillAdjectiveTerminations())
		{
			if (!StringUtil.isEmpty(languageTranslations.get(0).getText()))
			{
				String firstWord = languageTranslations.get(0).getText().split(" ")[0];
				if (!StringUtil.isEmpty(firstWord) && firstWord.endsWith(termination))
				{
					containsIncorrectFirstWord = true;
					break;
				}
			}
		}
		if (containsIncorrectFirstWord)
		{
			warningIncorrectFirstWord = StrutsUtil.getMessage(this, "error.NomenclatureProduce.incorrect-first-word");
		}

		return warningLinesCount != 0 || containsCyrillicCharacters || containsDigitCharacters || containsIncorrectFirstWord;
	}

	private void validate() throws ValidationException
	{
		boolean throwError = false;
		if (StringUtil.isEmpty(languageTranslations.get(0).getText()))
		{
			StrutsUtil.addError(this, "error.NomenclatureProduce.name.ru.empty", null);
			throwError = true;
		}
		if (catalogNumberRows.size() < 1)
		{
			StrutsUtil.addError(this, "error.catalog-number.should-be", null);
			throwError = true;
		}
		for (CatalogNumberRow row : catalogNumberRows)
		{
			DboCatalogNumber catalogNumber = row.getCatalogNumber();
			if (!validate("NomenclatureProduce.CatalogNumber", catalogNumber))
			{
				throwError = true;
			}
		}
		if (throwError)
		{
			throw new ValidationException();
		}
	}

	private List<DboProduce> getSameCatalogNumbers(DboCatalogNumber catalogNumber)
	{
		if (StringUtil.isEmpty(catalogNumber.getNumber()))
		{
			return new ArrayList<DboProduce>();
		}
		if (produce.getId() == null)
		{
			return HibernateUtil.getSessionFactory().getCurrentSession()
							.getNamedQuery("produces-has-same-catalog-number")
							.setString("number", catalogNumber.getNumber())
							.list();

		}
		else
		{
			return HibernateUtil.getSessionFactory().getCurrentSession()
							.getNamedQuery("produces-has-same-catalog-number-except-me")
							.setString("number", catalogNumber.getNumber())
							.setEntity("produce", produce)
							.list();
		}
	}

	public ActionForward save()
	{
		produce.setName((languageTranslations.get(0)).getText());

		produce.setUnit((DboUnit) hibSession.get(DboUnit.class, unit.getId()));
		if (customCode.getId() != null)
		{
			produce.setCustomCode(customCode);
		}
		Map<Integer, DboCatalogNumber> catalogNumbers = produce.getCatalogNumbers();
		for (Integer id : new HashSet<Integer>(catalogNumbers.keySet()))
		{
			if (!isExistCatalogNumberRow(catalogNumbers.get(id).getId().toString()))
			{
				catalogNumbers.remove(id);
			}
		}
		for (CatalogNumberRow row : catalogNumberRows)
		{
			if (!produce.existCatalogNumber(row.getCatalogNumber().getId()))
			{
				catalogNumbers.put(row.getCatalogNumber().getStuffCategory().getId(), row.copyOfCatalogNumber(produce));
			}
		}
		for (Integer id : catalogNumbers.keySet())
		{
			if (catalogNumbers.get(id).getMontageAdjustment().getId() == null)
			{
				catalogNumbers.get(id).setMontageAdjustment(null);
			}
		}
		boolean newProduce = produce.getId() == null;
		DboUser curUser = (DboUser) hibSession.get(DboUser.class, new Integer(UserUtil.getCurrentUser(request).getUsr_id()));
		if (newProduce)
		{
			produce.setCreateUser(curUser);
			produce.setCreateDate(StringUtil.getCurrentDateTime());
			hibSession.saveOrUpdate(produce);
			if (produce.isBBlock())
			{
				produce.setBlockUser(curUser);
				produce.setBlockDate(StringUtil.getCurrentDateTime());
			}
		}
		for (int i = 1; i < languageTranslations.size(); i++)
		{
			LangRow langRow = languageTranslations.get(i);
			DboProduceLanguage pLanguage = produce.getProduceLanguages().get(langRow.getLangCode());
			if (pLanguage != null)
			{
				if (StringUtil.isEmpty(langRow.getText()))
				{
					produce.getProduceLanguages().remove(langRow.getLangCode());
				}
				else
				{
					pLanguage.setName(langRow.getText());
				}
			}
			else
			{
				if (!StringUtil.isEmpty(langRow.getText()))
				{
					pLanguage = new DboProduceLanguage(produce, DboLanguage.DAO.loadByLangCode(langRow.getLangCode()));
					pLanguage.setName(langRow.getText());
					produce.addProduceLanguage(pLanguage);
				}
			}
		}
		boolean changed;
		boolean changedBlock;
		if (!newProduce)
		{
			DboProduce oldProduce = (DboProduce) hibSession.load(DboProduce.class, produce.getId());
			changed = isProduceChanged(oldProduce);
			changedBlock = isBlockChanged(oldProduce);
			if (changed)
			{
				produce.setEditUser(curUser);
				produce.setEditDate(StringUtil.getCurrentDateTime());
			}
			if (changedBlock)
			{
				produce.setBlockUser(curUser);
				produce.setBlockDate(StringUtil.getCurrentDateTime());
			}
			hibSession.evict(oldProduce);
			if (changed || changedBlock)
			{
				hibSession.saveOrUpdate(produce);
			}
		}
		hibSession.flush();
		attachmentService.commit(produce.getId());
		DeferredAttachmentService.removeLast(request.getSession());
		return mapping.findForward("back");
	}

	HolderImplUsingList attachmentsGrid;
	public final static String referencedTable = "DCL_PRODUCE";

	String attachmentId;

	public ActionForward attach() throws Exception
	{
		return mapping.findForward("deferredAttach");
	}

	public ActionForward deleteAttachment() throws Exception
	{
		attachmentService.delete(attachmentId);
		return show();
	}

	public ActionForward downloadAttachment() throws Exception
	{
		try
		{
			attachmentService.download(attachmentId, request, response);
			return null;
		}
		catch (AttachmentException e)
		{
			StrutsUtil.addError(this, e.getMessage(), e);
			return show();
		}
	}

	public ActionForward print() throws Exception
	{
		StoreUtil.putSession(request, this);
		printTo = true;
		return show();
	}

	public DboProduce getProduce()
	{
		return produce;
	}

	public void setProduce(DboProduce produce)
	{
		this.produce = produce;
	}

	public HolderImplUsingList getGridCN()
	{
		return gridCN;
	}

	public void setGridCN(HolderImplUsingList gridCN)
	{
		this.gridCN = gridCN;
	}


	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Integer getCat_id_integer()
	{
		return StringUtil.isEmpty(cat_id) ? null : Integer.parseInt(cat_id);
	}

	public String getCat_id()
	{
		return cat_id;
	}

	public void setCat_id(String cat_id)
	{
		this.cat_id = cat_id;
	}

	public void resetCatalogNumbersSelectedIds()
	{
		catalogNumbersSelectedIds = new GridResult<String>(String.class);
	}

	public GridResult<String> getCatalogNumbersSelectedIds()
	{
		return catalogNumbersSelectedIds;
	}

	public void setCatalogNumbersSelectedIds(GridResult<String> catalogNumbersSelectedIds)
	{
		this.catalogNumbersSelectedIds = catalogNumbersSelectedIds;
	}

	public DboUnit getUnit()
	{
		return unit;
	}

	public void setUnit(DboUnit unit)
	{
		this.unit = unit;
	}

	public DboCustomCode getCustomCode()
	{
		return customCode;
	}

	public void setCustomCode(DboCustomCode customCode)
	{
		this.customCode = customCode;
	}

	public String getNumber1C()
	{
		return number1C;
	}

	public void setNumber1C(String number1C)
	{
		this.number1C = number1C;
	}

	public boolean isNumber1CButtonReadOnly()
	{
		return number1CButtonReadOnly;
	}

	public void setNumber1CButtonReadOnly(boolean number1CButtonReadOnly)
	{
		this.number1CButtonReadOnly = number1CButtonReadOnly;
	}

	public List<LangRow> getLanguageTranslations()
	{
		return languageTranslations;
	}

	public void setLanguageTranslations(List<LangRow> languageTranslations)
	{
		this.languageTranslations = languageTranslations;
	}

	public List<CatalogNumberRow> getCatalogNumberRows()
	{
		return catalogNumberRows;
	}

	public void setCatalogNumberRows(List<CatalogNumberRow> catalogNumberRows)
	{
		this.catalogNumberRows = catalogNumberRows;
	}

	public HolderImplUsingList getGridLT()
	{
		return gridLT;
	}

	public void setGridLT(HolderImplUsingList gridLT)
	{
		this.gridLT = gridLT;
	}

	public boolean isAllReadOnly()
	{
		return allReadOnly;
	}

	public void setAllReadOnly(boolean allReadOnly)
	{
		this.allReadOnly = allReadOnly;
	}

	public boolean isFormReadOnly()
	{
		return formReadOnly;
	}

	public void setFormReadOnly(boolean formReadOnly)
	{
		this.formReadOnly = formReadOnly;
	}

	public String getWarning()
	{
		return warning;
	}

	public String getWarningCyrillicChars()
	{
		return warningCyrillicChars;
	}

	public String getWarningDigitChars()
	{
		return warningDigitChars;
	}

	public String getWarningIncorrectFirstWord()
	{
		return warningIncorrectFirstWord;
	}

	public void setWarning(String warning)
	{
		this.warning = warning;
	}

	public int getWarningLinesCount()
	{
		return warningLinesCount;
	}

	public void setWarningLinesCount(int warningLinesCount)
	{
		this.warningLinesCount = warningLinesCount;
	}

	public int getTimeOutForNoForProduceError()
	{
		return timeOutForNoForProduceError;
	}

	public void setTimeOutForNoForProduceError(int timeOutForNoForProduceError)
	{
		this.timeOutForNoForProduceError = timeOutForNoForProduceError;
	}

	public boolean isPrintTo()
	{
		return printTo;
	}

	public void setPrintTo(boolean printTo)
	{
		this.printTo = printTo;
	}

	public boolean isDeleteSelectedBtnReadonly()
	{
		return deleteSelectedBtnReadonly;
	}

	public void setDeleteSelectedBtnReadonly(boolean deleteSelectedBtnReadonly)
	{
		this.deleteSelectedBtnReadonly = deleteSelectedBtnReadonly;
	}

	public void setStuffCategoryReadonly(boolean stuffCategoryReadonly)
	{
		this.stuffCategoryReadonly = stuffCategoryReadonly;
	}

	public void setMontageAdjustmentReadonly(boolean montageAdjustmentReadonly)
	{
		this.montageAdjustmentReadonly = montageAdjustmentReadonly;
	}

	public boolean isStuffCategoryReadonly()
	{
		return stuffCategoryReadonly;
	}

	private CatalogNumberRow findCatalogNumberRow(String id)
	{
		for (CatalogNumberRow row : catalogNumberRows)
		{
			if (id.equals(row.getCatalogNumber().getId().toString()))
			{
				return row;
			}
		}
		return null;
	}

	private boolean isExistCatalogNumberRow(String id)
	{
		return findCatalogNumberRow(id) != null;
	}

	public boolean isProduceUsed()
	{
		return produceUsed;
	}

	public void setProduceUsed(boolean produceUsed)
	{
		this.produceUsed = produceUsed;
	}

	public boolean isBlockReadOnly()
	{
		return blockReadOnly;
	}

	public void setBlockReadOnly(boolean blockReadOnly)
	{
		this.blockReadOnly = blockReadOnly;
	}

	public void setBlankReadOnly(boolean blankReadOnly)
	{
		this.blankReadOnly = blankReadOnly;
	}

	public void setSaveReadOnly(boolean saveReadOnly)
	{
		this.saveReadOnly = saveReadOnly;
	}

	public boolean isInfoFieldsReadOnly()
	{
		return infoFieldsReadOnly;
	}

	public void setInfoFieldsReadOnly(boolean infoFieldsReadOnly)
	{
		this.infoFieldsReadOnly = infoFieldsReadOnly;
	}

	public String getAttachmentId()
	{
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId)
	{
		this.attachmentId = attachmentId;
	}

	public String getReferencedTable()
	{
		return referencedTable;
	}


	public HolderImplUsingList getAttachmentsGrid()
	{
		return attachmentsGrid;
	}

	public void setAttachmentsGrid(HolderImplUsingList attachmentsGrid)
	{
		this.attachmentsGrid = attachmentsGrid;
	}

	public Blank getBlank()
	{
		return blank;
	}

	public void setBlank(Blank blank)
	{
		this.blank = blank;
	}

	public Integer getSelectedImage()
	{
		return selectedImage;
	}

	public void setSelectedImage(Integer selectedImage)
	{
		this.selectedImage = selectedImage;
	}

	public String getDoPrintTo()
	{
		return doPrintTo;
	}

	public void setDoPrintTo(String doPrintTo)
	{
		this.doPrintTo = doPrintTo;
	}

	public boolean isBlankReadOnly()
	{
		return blankReadOnly;
	}

	public String getPrintScale()
	{
		return printScale;
	}

	public void setPrintScale(String printScale)
	{
		this.printScale = printScale;
	}

	public boolean isProduceChanged(DboProduce oldProduce)
	{
		if (StringUtil.equal(produce.getName(), oldProduce.getName()) &&
						StringUtil.equal(produce.getType(), oldProduce.getType()) &&
						StringUtil.equal(produce.getParams(), oldProduce.getParams()) &&
						StringUtil.equal(produce.getAddParams(), oldProduce.getAddParams()) &&
						StringUtil.equal(produce.getType(), oldProduce.getType()) &&
						StringUtil.equal(produce.getCusCode(), oldProduce.getCusCode()) &&
						StringUtil.equal(produce.getMaterial(), oldProduce.getMaterial()) &&
						StringUtil.equal(produce.getPurpose(), oldProduce.getPurpose()) &&
						StringUtil.equal(produce.getSpecification(), oldProduce.getSpecification()) &&
						StringUtil.equal(produce.getPrinciple(), oldProduce.getPrinciple()) &&
						((produce.getUnit() == null && oldProduce.getUnit() == null) || produce.getUnit().getId().equals(oldProduce.getUnit().getId())) &&
						produce.getCatalogNumbers().size() == oldProduce.getCatalogNumbers().size() &&
						produce.getProduceLanguages().size() == oldProduce.getProduceLanguages().size())
		{
			Iterator iterator = produce.getCatalogNumbers().values().iterator();
			Iterator oldIterator = oldProduce.getCatalogNumbers().values().iterator();
			while (iterator.hasNext() && oldIterator.hasNext())
			{
				DboCatalogNumber number = (DboCatalogNumber) iterator.next();
				DboCatalogNumber oldNumber = (DboCatalogNumber) oldIterator.next();
				if (!StringUtil.equal(number.getNumber(), oldNumber.getNumber()) ||
								!((number.getStuffCategory() == null && oldNumber.getStuffCategory() == null) || number.getStuffCategory().getId().equals(oldNumber.getStuffCategory().getId())) ||
								!((number.getMontageAdjustment() == null && oldNumber.getMontageAdjustment() == null) ||
												(number.getMontageAdjustment() != null && oldNumber.getMontageAdjustment() != null &&
																number.getMontageAdjustment().getId().equals(oldNumber.getMontageAdjustment().getId()))))
				{
					return true;
				}
			}
			iterator = produce.getProduceLanguages().values().iterator();
			oldIterator = oldProduce.getProduceLanguages().values().iterator();
			while (iterator.hasNext() && oldIterator.hasNext())
			{
				DboProduceLanguage language = (DboProduceLanguage) iterator.next();
				DboProduceLanguage oldLanguage = (DboProduceLanguage) oldIterator.next();
				if (!StringUtil.equal(language.getName(), oldLanguage.getName()))
				{
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public boolean isBlockChanged(DboProduce oldProduce)
	{
		return (!((produce.getBlock() == null && oldProduce.getBlock() == null) || (produce.getBlock() != null && produce.getBlock().equals(oldProduce.getBlock()))));
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		if (produce != null && !blockReadOnly)
		{
			produce.setBlock(null);
		}
		super.reset(mapping, request);
	}

	public boolean isSaveReadOnly()
	{
		return saveReadOnly;
	}

	/**
	 * LangRow
	 */
	static public class LangRow
	{
		String langCode;
		String langName;
		String text;

		public LangRow()
		{
		}

		public LangRow(String langCode, String langName, String text)
		{
			this.langCode = langCode;
			this.langName = langName;
			this.text = text;
		}

		public String getLangCode()
		{
			return langCode;
		}

		public void setLangCode(String langCode)
		{
			this.langCode = langCode;
		}

		public String getLangName()
		{
			return langName;
		}

		public void setLangName(String langName)
		{
			this.langName = langName;
		}

		public String getText()
		{
			return text;
		}

		public void setText(String text)
		{
			this.text = text;
		}
	}

	/**
	 * CatalogNumberRow
	 */
	static public class CatalogNumberRow
	{
		DboCatalogNumber catalogNumber;

		public CatalogNumberRow()
		{
		}

		public CatalogNumberRow(DboCatalogNumber catalogNumber)
		{
			this.catalogNumber = catalogNumber;
		}

		public DboCatalogNumber getCatalogNumber()
		{
			return catalogNumber;
		}

		public void setCatalogNumber(DboCatalogNumber catalogNumber)
		{
			this.catalogNumber = catalogNumber;
		}

		public DboMontageAdjustment getMontageAdjustment()
		{
			if (catalogNumber.getMontageAdjustment() == null)
			{
				catalogNumber.setMontageAdjustment(new DboMontageAdjustment());
			}
			return catalogNumber.getMontageAdjustment();
		}

		public DboCatalogNumber copyOfCatalogNumber(DboProduce produce)
		{
			return new DboCatalogNumber(produce, catalogNumber.getStuffCategory(), catalogNumber.getNumber(),
							catalogNumber.getMontageAdjustment());
		}
	}

	public boolean isCustomCodeHistoryButtonReadonly()
	{
		return customCodeHistoryButtonReadonly;
	}

	public void setCustomCodeHistoryButtonReadonly(boolean customCodeHistoryButtonReadonly)
	{
		this.customCodeHistoryButtonReadonly = customCodeHistoryButtonReadonly;
	}
}
