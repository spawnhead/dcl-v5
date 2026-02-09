package net.sam.dcl.action;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;

import javax.servlet.http.HttpSession;


/**
 * Created by IntelliJ IDEA.
 * Date: 11.08.2005
 * Time: 18:21:59
 * To change this template use File | Settings | File Templates.
 */
public class Outline
{

  final static public String MENU_ID_NAME = "current_menu_id";


  static public Outline OFFICE = new Outline("menus.office", "/OfficeAction.do?dispatch=input", "id.office");
  static public Outline USER_SETTINGS = new Outline("menus.personalSettings", "/UserSettingsAction.do?dispatch=input", "id.personalSettings");

  static public Outline PERSONAL_OFFICE = new Outline("menus.personalOffice", "/PersonalOfficeAction.do?dispatch=input", "id.personalOffice", new Outline[]{OFFICE, USER_SETTINGS});


  static public Outline COMMERCIAL_PROPOSAL = new Outline("menus.commercial_proposal", "/CommercialProposalsAction.do?dispatch=input", "id.commercial_proposal");
  static public Outline CONDITION_FOR_CONTRACT = new Outline("menus.condition_for_contract", "/ConditionsForContractAction.do?dispatch=input", "id.condition_for_contract");
  static public Outline CONTRACT = new Outline("menus.contract", "/ContractsAction.do?dispatch=input", "id.contractDoc");
  static public Outline ORDER = new Outline("menus.order", "/OrdersAction.do?dispatch=input", "id.order");
  static public Outline ASSEMBLE = new Outline("menus.assemble", "/AssemblesAction.do?dispatch=input", "id.assemble");
  static public Outline IN_DELIVERY_REQUEST = new Outline("menus.in_delivery_request", "/DeliveryRequestsAction.do?dispatch=input&direction=in", "id.in_delivery_request");
  static public Outline OUT_DELIVERY_REQUEST = new Outline("menus.out_delivery_request", "/DeliveryRequestsAction.do?dispatch=input&direction=out", "id.out_delivery_request");
  static public Outline SPECIFICATION_IMPORT = new Outline("menus.specification_import", "/SpecificationImportsAction.do?dispatch=input", "id.specification_import");
  static public Outline PRODUCE_COST = new Outline("menus.produce_cost", "/ProducesCostAction.do?dispatch=input", "id.produce_cost");
  static public Outline PAYMENT = new Outline("menus.payment", "/PaymentsAction.do?dispatch=input", "id.payment", 85);
  static public Outline SHIPPING = new Outline("menus.shipping", "/ShippingsAction.do?dispatch=input", "id.shipping");
  static public Outline CONTRACT_CLOSED = new Outline("menus.contract_closed", "/ContractsClosedAction.do?dispatch=input", "id.cclosed", 130);
  static public Outline CONTRACTOR_REQUEST = new Outline("menus.request_contractor", "/ContractorRequestsAction.do?dispatch=input", "id.requestContractor");
  static public Outline TIMEBOARD = new Outline("menus.timeboard", "/TimeboardsAction.do?dispatch=input", "id.timeboard");
  static public Outline CURRENT_WORKS = new Outline("menus.current_works", "/CurrentWorksAction.do?dispatch=input", "id.current_works");
  static public Outline WOODWORK_WORK_FILES = new Outline("menus.woodwork_work_files", "/WoodworkWorkFilesAction.do?dispatch=show", "id.woodwork_work_files");
  static public Outline OUTGOING_LETTER = new Outline("menus.outgoing_letter", "/OutgoingLettersAction.do?dispatch=input", "id.outgoingLetter");
  static public Outline INSTRUCTION = new Outline("menus.instruction", "/InstructionsAction.do?dispatch=input", "id.instruction");

  static public Outline CONTRACT_WORK = new JSOutline("menus.contract_work", "id.contract_work", new Outline[]{COMMERCIAL_PROPOSAL, CONDITION_FOR_CONTRACT, CONTRACT}, "260", 115);
  static public Outline GOODS_IN = new JSOutline("menus.goods_in", "id.goods_in", new Outline[]{ORDER, ASSEMBLE, IN_DELIVERY_REQUEST, SPECIFICATION_IMPORT, PRODUCE_COST}, "240", 130);
  static public Outline GOODS_OUT = new JSOutline("menus.goods_out", "id.goods_out", new Outline[]{OUT_DELIVERY_REQUEST, SHIPPING}, "220", 130);
  static public Outline WOODWORK_SERVICE = new JSOutline("menus.woodwork_service", "id.woodworkService", new Outline[]{CONTRACTOR_REQUEST, TIMEBOARD, CURRENT_WORKS, WOODWORK_WORK_FILES}, "180", 150);
  static public Outline OFFICE_WORK_AND_REFERENCE = new JSOutline("menus.office_work_and_reference", "id.officeWorkAndReference", new Outline[]{OUTGOING_LETTER, INSTRUCTION}, "240", 245);

  static public Outline JORNALS = new Outline("menus.journals", "/JournalsAction.do?dispatch=input", "id.journals", new Outline[]{CONTRACT_WORK, GOODS_IN, GOODS_OUT, PAYMENT, CONTRACT_CLOSED, WOODWORK_SERVICE, OFFICE_WORK_AND_REFERENCE});


  static public Outline CONTRACTORS = new Outline("menus.contractors", "/ContractorsAction.do?dispatch=input", "id.contractors");
  static public Outline NOMENCLATURE = new Outline("menus.nomenclature", "/NomenclatureAction.do?dispatch=input", "id.nomenclature");
  static public Outline CUSTOM_CODE = new Outline("menus.custom_code", "/CustomCodesAction.do?dispatch=execute", "id.custom_code");

  static public Outline CURRENCIES = new Outline("menus.currencies", "/CurrenciesAction.do?dispatch=execute", "id.currencies");
  static public Outline STUFF_CATEGORIES = new Outline("menus.stuff_categories", "/StuffCategoriesAction.do?dispatch=show", "id.stuff_categories");
  static public Outline MONTAGE_ADJUSTMENT = new Outline("menus.montage_adjustment", "/MontageAdjustmentsAction.do?dispatch=input", "id.montage_adjustment");
  static public Outline UNITS = new Outline("menus.units", "/UnitsAction.do", "id.units");
  static public Outline PURPOSES = new Outline("menus.purposes", "/PurposesAction.do", "id.purposes");
  static public Outline SHIPPING_DOC_TYPES = new Outline("menus.shipping_doc_types", "/ShippingDocTypesAction.do", "id.doc_types_shipping");
  static public Outline RATES_NDS = new Outline("menus.ratesNDS", "/RatesNDSAction.do", "id.ratesNDS");
  static public Outline COUNTRIES = new Outline("menus.countries", "/CountriesAction.do?dispatch=show", "id.countries");

  static public Outline REFERENCES_DOWN = new JSOutline("menus.references_down", "id.references", new Outline[]{CURRENCIES, STUFF_CATEGORIES, MONTAGE_ADJUSTMENT, UNITS, PURPOSES, SHIPPING_DOC_TYPES, RATES_NDS, COUNTRIES}, "240");

  static public Outline REFERENCES = new Outline("menus.references", "/ReferencesAction.do?dispatch=input", "id.references", new Outline[]{CONTRACTORS, NOMENCLATURE, CUSTOM_CODE, REFERENCES_DOWN});

  static public Outline ORDERS_STATISTICS = new Outline("menus.orders_statistics", "/OrdersStatisticsAction.do?dispatch=input", "id.statistics_orders");
  static public Outline ORDERS_UNEXECUTED = new Outline("menus.orders_unexecuted", "/OrdersUnexecutedAction.do?dispatch=input", "id.unexecuted_orders");
  static public Outline ORDERS_LOGISTICS = new Outline("menus.orders_logistics", "/OrdersLogisticsAction.do?dispatch=input", "id.logistics_order");

  static public Outline MARGIN = new Outline("menus.margin", "/MarginAction.do?dispatch=input", "id.margin", 60);
  static public Outline GOODS_REST_MINSK = new Outline("menus.goods_rest_minsk", "/GoodsRestAction.do?dispatch=input", "id._minsk_goods_rest", 95);
  static public Outline LITHUANIA_GOODS_REST = new Outline("menus.lithuania_goods_rest", "/GoodsRestLithuaniaAction.do?dispatch=show", "id.lithuania_goods_rest");
  static public Outline LITHUANIA_GOODS_CIRCULATION = new Outline("menus.lithuania_goods_circulation", "/GoodsCirculationAction.do?dispatch=input", "id.lithuania_goods_circulation");
  static public Outline GOODS_REST = new JSOutline("menus.goods_rest", "id.goods_rest", new Outline[]{GOODS_REST_MINSK, LITHUANIA_GOODS_REST, LITHUANIA_GOODS_CIRCULATION}, "120", 95);
  static public Outline CALCULATION_STATE = new Outline("menus.calculation_state", "/CalculationStateAction.do?dispatch=input", "id.calculation_state", 125);
  static public Outline SHIPPING_REPORT = new Outline("menus.shipping_report", "/ShippingReportAction.do?dispatch=input", "id.rep_shipping", 70);
  static public Outline GOODS_CIRCULATION = new Outline("menus.goods_circulation", "/GoodsCirculationAction.do?dispatch=input", "id.goods_circulation", 80);
  static public Outline PRODUCE_COST_REPORT = new Outline("menus.prdCostReport", "/ProduceCostReportAction.do?dispatch=input", "id.prdCostReport", 125);

  static public Outline ORDERS_REPORTS = new JSOutline("menus.ordReports", "id.fake", new Outline[]{ORDERS_STATISTICS, ORDERS_UNEXECUTED, ORDERS_LOGISTICS}, "140", 60);

  static public Outline REPORTS = new Outline("menus.reports", "/ReportsAction.do?dispatch=input", "id.reports", new Outline[]{MARGIN, GOODS_REST, CALCULATION_STATE, ORDERS_REPORTS, SHIPPING_REPORT, GOODS_CIRCULATION, PRODUCE_COST_REPORT});

  static public Outline EXIT = new Outline("menus.exit", "/Logout.do?dispatch=input", "id.exit");

  static public Outline USERS = new Outline("menus.users", "/UsersAction.do?dispatch=execute", "id.users");
  static public Outline ROLES = new Outline("menus.roles", "/RolesAction.do", "id.roles");
  static public Outline DEPARTMENTS = new Outline("menus.departments", "/DepartmentsAction.do", "id.departments");
  static public Outline LANGUAGES = new Outline("menus.languages", "/LanguagesAction.do", "id.languages");
  static public Outline ROUTES = new Outline("menus.routes", "/RoutesAction.do?dispatch=execute", "id.routes");
  static public Outline SELLERS = new Outline("menus.sellers", "/SellersAction.do?dispatch=execute", "id.sellers");
  static public Outline BLANKS = new Outline("menus.blanks", "/BlanksAction.do?dispatch=execute", "id.blanks");
  static public Outline FILES_PATHS = new Outline("menus.filesPaths", "/FilesPathsAction.do", "id.filesPaths");
  static public Outline SETTINGS = new Outline("menus.settings", "/SettingsAction.do", "id.settings");
  static public Outline ACTIONS = new Outline("menus.actions", "/ActionsAction.do", "id.actions");
  static public Outline LOG = new Outline("menus.log", "/LogsAction.do?dispatch=input", "id.log");
  static public Outline SESSIONS = new Outline("menus.sessions", "/Sessions.do", "id.sessions");
  static public Outline LOCKED_RECORDS = new Outline("menus.locked_records", "/LockedRecords.do?dispatch=list", "id.locked_records");

  static public Outline ADM_REFERENCE = new JSOutline("menus.admReference", "id.fake", new Outline[]{DEPARTMENTS, LANGUAGES, ROUTES, SELLERS, BLANKS, FILES_PATHS}, "120");

  static public Outline ADM_ZONE = new Outline("menus.adm_zone", "/AdmZone.do", "id.adm_zone", new Outline[]{USERS, ROLES, ADM_REFERENCE, SETTINGS, ACTIONS, LOG, SESSIONS, LOCKED_RECORDS});

  static public Outline TEST_SELECT = new Outline("menus.test_select", "/TestSelectAction.do?dispatch=input", "id.test_select");
  static public Outline TEST_HIR_GRID = new Outline("menus.test_hir_grid", "/TestHirGridAction.do?dispatch=show", "id.test_hir_grid");
  static public Outline TEST_NEW_GRID = new Outline("menus.test_new_grid", "/TestNewGridAction.do?dispatch=show", "id.test_new_grid");
  static public Outline DEV_MARGIN = new Outline("menus.dev_margin", "/MarginDevAction.do?dispatch=input", "id.dev_margin");
  static public Outline DEV_CALCULATION_STATE = new Outline("menus.dev_calculation_state", "/CalculationStateDevAction.do", "id.dev_calculation_state", 125);
  static public Outline TEST_PDF = new Outline("menus.test_pdf", "/CoveringLetterForOrderPrintAction.do", "id.test_pdf");

  static public Outline TEST_JS1 = new Outline("menus.test_js1", "/TestSelectAction.do?dispatch=input", "id.test_js1");
  static public Outline TEST_JS2 = new Outline("menus.test_js2", "/TestSelectAction.do?dispatch=input", "id.test_js2");
  static public Outline TEST_JS3 = new Outline("menus.test_js3", "/TestSelectAction.do?dispatch=input", "id.test_js3");

  static public Outline TEST_JS_MENU = new JSOutline("menus.test_js", "id.test_is", new Outline[]{TEST_JS1, TEST_JS2, TEST_JS3}, "140");


  static public Outline DEV_ZONE = new Outline("menus.dev_zone", "/DevZone.do", "id.dev_zone", new Outline[]{DEV_MARGIN, DEV_CALCULATION_STATE, TEST_NEW_GRID, TEST_HIR_GRID, TEST_SELECT, TEST_PDF, TEST_JS_MENU});

  static public Outline[] OUTLINE = new Outline[]{PERSONAL_OFFICE, JORNALS, REFERENCES, REPORTS, ADM_ZONE, EXIT, DEV_ZONE};

  public String title;
  public String url;
  public Outline subItems[];
  public String id;
  public String width;
  public int elementWidth;

  protected Outline(String title, String url, String id)
  {
    this.title = title;
    this.url = url;
    this.id = id;
    if (id == null)
    {
      throw new IllegalArgumentException("id should be not null");
    }
  }

  protected Outline(String title, String url, String id, int elementWidth)
  {
    this.title = title;
    this.url = url;
    this.id = id;
    this.elementWidth = elementWidth;
    if (id == null)
    {
      throw new IllegalArgumentException("id should be not null");
    }
  }

  protected Outline(String title, String url, String id, Outline[] subItems)
  {
    this(title, url, id);
    this.subItems = subItems;
  }

  protected Outline(String title, String url, String id, Outline[] subItems, int elementWidth)
  {
    this(title, url, id);
    this.subItems = subItems;
    this.elementWidth = elementWidth;
  }

  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (!(o instanceof Outline)) return false;

    Outline outline = (Outline) o;

    if (!id.equals(outline.id)) return false;

    return true;
  }

  public int hashCode()
  {
    return id.hashCode();
  }

  public String getId()
  {
    return id;
  }

  private String getURL()
  {
    return url;
  }

  public int getElementWidth()
  {
    return elementWidth;
  }

  public static Outline findOutline(Outline[] outlines, String menuId, boolean recurcive, boolean returnTopLevelOutline, HttpSession session)
  {
    if (StringUtil.isEmpty(menuId) && UserUtil.getCurrentUser(session) != null && UserUtil.getCurrentUser(session).isManager())
    {
      menuId = "id.office";
    }

    for (int i = 0; outlines != null && i < outlines.length; i++)
    {
      Outline outline = outlines[i];
      if (outline.getId().equals(menuId))
      {
        return outline;
      }
      if (recurcive)
      {
        Outline res = findOutline(outline.subItems, menuId, recurcive, returnTopLevelOutline, session);
        if (res != null)
        {
          if (returnTopLevelOutline)
          {
            return outline;
          }
          else
          {
            return res;
          }
        }
      }
    }
    return null;
  }

  public static String getURLById(String menu_id)
  {
    String url = getURLById(OUTLINE, menu_id);
    return url == null ? "" : url;
  }

  private static String getURLById(Outline[] outline, String menu_id)
  {
    if (outline != null)
    {
      for (int i = 0; i < outline.length; i++)
      {
        if (outline[i].getId().equals(menu_id))
        {
          return outline[i].getURL();
        }
        else
        {
          String url = getURLById(outline[i].subItems, menu_id);
          if (url != null)
          {
            return url;
          }
        }
      }
    }
    return null;
  }

  public void clear()
  {
    if (subItems != null)
    {
      for (int i = 0; i < subItems.length; i++)
      {
        Outline subItem = subItems[i];
        subItem.clear();
        subItems[i] = null;
      }
      subItems = null;
    }
  }

  public static void clearOutline()
  {
    for (int i = 0; i < OUTLINE.length; i++)
    {
      OUTLINE[i].clear();
      OUTLINE[i] = null;
    }
  }

  static public class JSOutline extends Outline
  {
    private JSOutline(String title, String id, Outline[] subItems, String width)
    {
      super(title, null, id, subItems);
      this.width = width;
    }

    private JSOutline(String title, String id, Outline[] subItems, String width, int elementWidth)
    {
      super(title, null, id, subItems, elementWidth);
      this.width = width;
    }
  }
}
