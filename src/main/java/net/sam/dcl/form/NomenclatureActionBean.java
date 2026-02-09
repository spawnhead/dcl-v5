package net.sam.dcl.form;

import net.sam.dcl.beans.Produce;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.controller.ProcessAfter;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dbo.*;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionForward;
import org.hibernate.Hibernate;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class NomenclatureActionBean extends ActionBean
{
  String filter_category;
  String filter_catalog_number;
  String filter_produce;
  String filter_type;
  String filter_params;
  String filter_add_params;
  String filter_cusCode;
  String filter_cusCode_id;
  String filter_common;
  Integer stf_id;
  String stf_name;
  String show_blocked;
  Integer notCheckDoubleId;
  String notCheckDouble;
  String ignoreFilter;
  String restoreFilter;

  String cat_id;
  String category_name;
  Integer mergeTargetId;
  Long produceCount = 0L;
  String prd_id;
  DboCategory rootCategory;

  HolderImplUsingList grid = new HolderImplUsingList();
  HolderImplUsingList gridCNDetails = new HolderImplUsingList();

  GridResult<String> producesSelectedIds;
  DboAttachment templateExcel;

  @ProcessAfter
  public ActionForward processAfter(ActionForward forward)
  {
    resetProducesSelectedIds();
    return forward;
  }

  public ActionForward input() throws Exception
  {
    filter_category = null;
    filter_catalog_number = null;
    filter_produce = null;
    filter_type = null;
    filter_params = null;
    filter_add_params = null;
    filter_cusCode = null;
    filter_cusCode_id = null;
    filter_common = null;
    mergeTargetId = null;
    cat_id = "";
    if (!isSelectMode())
    {
      stf_id = null;
      stf_name = null;
      show_blocked = null;
    }
    else
    {
      if (null != stf_id)
      {
        stf_name = ((DboStuffCategory) hibSession.get(DboStuffCategory.class, stf_id)).getName();
      }
      else
      {
        stf_name = null;
      }
      show_blocked = "1";
    }
    resetProducesSelectedIds();
    rootCategory = null;
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    templateExcel = attachmentsService.firstAttach("DCL_TEMPLATE", ConstDefinitions.templateIdNomenclature);

    return mapping.getInputForward();
  }

  public ActionForward filter() throws Exception
  {
    rootCategory = null;
    return mapping.getInputForward();
  }

  public ActionForward ajaxTree() throws Exception
  {
    return mapping.findForward("ajaxTree");
  }


  public ActionForward moveProduces() throws Exception
  {
    if (producesSelectedIds.getRecords().size() > 0)
    {
      List<String> list = producesSelectedIds.toList();
      int fromIndex = 0;
      int size = 500;
      int toIndex = list.size() > size ? size : list.size();
      List<String> listForUpdate = list.subList(fromIndex, toIndex);
      while (listForUpdate.size() > 0)
      {
        String res = "";
        int i = 0;
        for (String id : listForUpdate)
        {
          res += id;
          if (i != listForUpdate.size() - 1)
          {
            res += ",";
          }
          i++;
        }

        hibSession.beginTransaction();
        hibSession.createQuery("update DboProduce p set p.category = :cat_id where p in (" + res + ")")
                .setString("cat_id", cat_id)
                .executeUpdate();
        hibSession.getTransaction().commit();

        if (toIndex - fromIndex < size)
          break;

        fromIndex += size;
        toIndex = list.size() > toIndex + size ? toIndex + size : list.size();
        listForUpdate = list.subList(fromIndex, toIndex);
      }

      resetProducesSelectedIds();
    }
    return ajaxGrid();
  }

  public ActionForward mergeProduces() throws Exception
  {
    boolean foundError = false;
    if (producesSelectedIds.getRecords().size() == 0)
    {
      StrutsUtil.addError(this, "Nomenclature.only_one_produce", null);
      foundError = true;
    }
    if (producesSelectedIds.getRecords().size() > 1)
    {
      StrutsUtil.addError(this, "Nomenclature.too_many_produces", null);
      foundError = true;
    }

    if (!foundError)
    {
      DboProduce sourceProduce = (DboProduce) hibSession.get(DboProduce.class, new Integer(producesSelectedIds.getRecordList().get(0)));
      DboProduce targetProduce = (DboProduce) hibSession.get(DboProduce.class, mergeTargetId);
      if (!targetProduce.isBBlock())
      {
        if (sourceProduce.isBBlock())
        {
          StrutsUtil.addError(this, "error.position.blocked", null);
          foundError = true;
        }
      }
      if (!targetProduce.isCustomCodeEmpty() && !sourceProduce.isCustomCodeEmpty())
      {
        List<NomenclatureProduceCustomCodeHistoryForm.CustomCode> targetCustomCodeHistory = NomenclatureProduceCustomCodeHistoryHelper.getAllCustomCodesFromHistory(String.valueOf(targetProduce.getId()));
        List<NomenclatureProduceCustomCodeHistoryForm.CustomCode> sourceCustomCodeHistory = NomenclatureProduceCustomCodeHistoryHelper.getAllCustomCodesFromHistory(String.valueOf(sourceProduce.getId()));
        CollectionUtils.transform(targetCustomCodeHistory, new Transformer() {
          @Override
          public String transform(Object o) {
            return ((NomenclatureProduceCustomCodeHistoryForm.CustomCode) o).getDate_created();
          }
        });
        Set customCodesCreatedDates = new HashSet(targetCustomCodeHistory);
        for(NomenclatureProduceCustomCodeHistoryForm.CustomCode customCode : sourceCustomCodeHistory){
          if (!customCodesCreatedDates.add(customCode.getDate_created())) {
            StrutsUtil.addError(this, "error.custom.codes.with.same.dates", null);
            foundError = true;
            break;
          }
        }
      }
      for (DboCatalogNumber catalogNumber : sourceProduce.getCatalogNumbers().values())
      {
        if (targetProduce.isExistWithSameStuffCategoryAnotherMontageAdjustment(catalogNumber))
        {
          StrutsUtil.addError(this, "error.catalog-numbers.differentMontageAdjustment", null);
          foundError = true;
          break;
        }
      }
    }

    if (foundError)
    {
      mergeTargetId = null;
      resetProducesSelectedIds();
      return mapping.getInputForward();
    }

    String oldId = producesSelectedIds.getRecordList().get(0);
    String newId = mergeTargetId.toString();
    request.getSession().setAttribute(Produce.oldProduceId, oldId);
    request.getSession().setAttribute(Produce.newProduceId, newId);
    return mapping.findForward("mergeProduces");
  }

  public ActionForward ajaxGrid() throws Exception
  {
    if (StringUtil.isEmpty(cat_id))
    {
      grid.setDataList(Collections.emptyList());
      produceCount = 0L;
    }
    else
    {
      DAOUtils.fillGrid(grid, sqlResource.get("select-nomenclature-produces"), this, DboProduceWithSG.class, null, null);
      produceCount = (Long) hibSession.getNamedQuery("count-produces-in-category")
              .setInteger("cat_id", Integer.parseInt(cat_id))
              .uniqueResult();
    }

    request.setAttribute("checkBlocked", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        DboProduceWithSG pr = (DboProduceWithSG) context.getBean();
        if (pr.isBBlock())
        {
          return "grid-row-locked";
        }
        return "grid-row";
      }
    });

    request.setAttribute("editChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return (getCurrentUser().isOnlyLogistic());
      }
    });

    request.setAttribute("showCommentChecker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        DboProduceWithSG pr = (DboProduceWithSG) context.getBean();
        return pr.isHasComment();
      }
    }
    );

    return mapping.findForward("ajaxGrid");
  }

  public ActionForward getProduceDetails() throws Exception
  {
    List<DboCatalogNumber> numbers = hibSession.getNamedQuery("get-produce-details")
            .setInteger("prd_id", Integer.parseInt(prd_id))
            .list();
    gridCNDetails.setDataList(numbers);
    return mapping.findForward("ajaxCNGrid");
  }

  public ActionForward importProduces() throws Exception
  {
    return mapping.findForward("importProduces");
  }

  public ActionForward addCategory() throws Exception
  {
    DboCategory category = new DboCategory(category_name);
    if (!StringUtil.isEmpty(cat_id))
    {
      DboCategory parent = (DboCategory) hibSession.load(DboCategory.class, new Integer(cat_id));
      parent.addChild(category);
    }
    hibSession.save(category);
    cat_id = category.getId().toString();
    rootCategory = null;
    return mapping.findForward("ajaxAddCategory");
  }

  public ActionForward changeCategory() throws Exception
  {
    if (!StringUtil.isEmpty(cat_id))
    {
      DboCategory parent = (DboCategory) hibSession.load(DboCategory.class, new Integer(cat_id));
      parent.setName(category_name);
    }
    rootCategory = null;
    return mapping.findForward("ajax");
  }

  public ActionForward deleteCategory() throws Exception
  {
    hibSession.createQuery("delete from DboCategory where id=:id")
            .setString("id", cat_id)
            .executeUpdate();
    rootCategory = null;
    return mapping.findForward("ajax");
  }

  public ActionForward createNomenclatureProduce() throws Exception
  {
    SelectFromGridAction.interruptSelectMode(this);
    return mapping.findForward("createNomenclatureProduce");
  }

  public ActionForward retFromEdit() throws Exception
  {
    SelectFromGridAction.restoreSelectMode(this);
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    templateExcel = attachmentsService.firstAttach("DCL_TEMPLATE", ConstDefinitions.templateIdNomenclature);

    return mapping.getInputForward();
  }

  public ActionForward downloadDoubleValues() throws Exception
  {
    try
    {
      List<String> list = hibSession.createSQLQuery("select DOUBLE_STR as RES from DCL_GET_DOUBLE_PRODUCES(:stfId)")
              .addScalar("RES", Hibernate.STRING)
              .setInteger("stfId", stf_id == null ? -1 : stf_id)
              .list();
      StringBuffer result = new StringBuffer();
      for (String aList : list)
      {
        result.append(aList);
        result.append("\n");
      }
      AttachmentsService.downloadStream(request, response,
              new ByteArrayInputStream(result.toString().getBytes("UTF-8")), "double-values.txt");
      return null;
    }
    catch (RuntimeException e)
    {
      StrutsUtil.addError(this, e);
      return mapping.getInputForward();
    }
  }

  public ActionForward uploadTemplate() throws Exception
  {
    return mapping.findForward("uploadTemplate");
  }

  public ActionForward setNotCheckDouble() throws Exception
  {
    DboProduce produce = (DboProduce) hibSession.get(DboProduce.class, notCheckDoubleId);
    if (produce != null)
    {
      produce.setNotCheckDouble(StringUtil.isEmpty(notCheckDouble) ? null : "1");
    }
    else
    {
      StrutsUtil.addError(this, "Nomenclature.cant.find.produce", null);
    }
    return mapping.findForward("ajax");
  }

  public String getFilter_category()
  {
    return filter_category;
  }

  public void setFilter_category(String filter_category)
  {
    this.filter_category = filter_category;
  }

  public String getCat_id()
  {
    return cat_id;
  }

  public Integer getCat_id_integer()
  {
    return StringUtil.isEmpty(cat_id) ? null : Integer.parseInt(cat_id);
  }

  public void setCat_id(String cat_id)
  {
    this.cat_id = cat_id;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public User getCurrentUser()
  {
    return UserUtil.getCurrentUser(request);
  }

  public String getCategory_name()
  {
    return category_name;
  }

  public void setCategory_name(String category_name)
  {
    this.category_name = category_name;
  }

  public String getFilter_catalog_number()
  {
    return filter_catalog_number;
  }

  public void setFilter_catalog_number(String filter_catalog_number)
  {
    this.filter_catalog_number = filter_catalog_number;
  }

  public Integer getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(Integer stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getFilter_produce()
  {
    return filter_produce;
  }

  public void setFilter_produce(String filter_produce)
  {
    this.filter_produce = filter_produce;
  }

  public String getFilter_type()
  {
    return filter_type;
  }

  public void setFilter_type(String filter_type)
  {
    this.filter_type = filter_type;
  }

  public String getFilter_params()
  {
    return filter_params;
  }

  public void setFilter_params(String filter_params)
  {
    this.filter_params = filter_params;
  }

  public String getFilter_add_params()
  {
    return filter_add_params;
  }

  public void setFilter_add_params(String filter_add_params)
  {
    this.filter_add_params = filter_add_params;
  }

  public String getFilter_cusCode()
  {
    return filter_cusCode;
  }

  public void setFilter_cusCode(String filter_cusCode)
  {
    this.filter_cusCode = filter_cusCode;
  }

  public void resetProducesSelectedIds()
  {
    producesSelectedIds = new GridResult(String.class);
  }

  public GridResult<String> getProducesSelectedIds()
  {
    return producesSelectedIds;
  }

  public void setProducesSelectedIds(GridResult<String> producesSelectedIds)
  {
    this.producesSelectedIds = producesSelectedIds;
  }

  public String getFilter_cusCode_id()
  {
    return filter_cusCode_id;
  }

  public void setFilter_cusCode_id(String filter_cusCode_id)
  {
    this.filter_cusCode_id = filter_cusCode_id;
  }

  public Integer getMergeTargetId()
  {
    return mergeTargetId;
  }

  public void setMergeTargetId(Integer mergeTargetId)
  {
    this.mergeTargetId = mergeTargetId;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public Long getProduceCount()
  {
    return produceCount;
  }

  public void setProduceCount(Long produceCount)
  {
    this.produceCount = produceCount;
  }

  public boolean isSelectMode()
  {
    return SelectFromGridAction.isSelectMode(request.getSession());
  }

  public void setPrd_id(String prd_id)
  {
    this.prd_id = prd_id;
  }

  public HolderImplUsingList getGridCNDetails()
  {
    return gridCNDetails;
  }

  public void setGridCNDetails(HolderImplUsingList gridCNDetails)
  {
    this.gridCNDetails = gridCNDetails;
  }

  static public class DboProduceWithSG extends DboProduce implements Setable, Getable
  {
    String productList;
    String numberList;
    String attachmentsCount;
    String createUserName;
    String unitName;

    public void set(String field, String value)
    {
      if ("ID".equals(field)) setId(Integer.parseInt(value));
      else if ("NAME".equals(field)) setName(value);
      else if ("type".equals(field)) setType(value);
      else if ("PARAMS".equals(field)) setParams(value);
      else if ("addParams".equals(field)) setAddParams(value);
      else if ("cusCode".equals(field)) setCustomCode(value);
      else if ("productList".equals(field)) setProductList(value);
      else if ("numberList".equals(field)) setNumberList(value);
      else if ("PRD_BLOCK".equals(field)) setBlock(StringUtil.parseShort(value));
      else if ("attachmentsCount".equals(field)) setAttachmentsCount(value);
      else if ("notCheckDouble".equals(field)) setNotCheckDouble(value);
      else if ("createUserName".equals(field)) createUserName = value;
      else if ("unitName".equals(field)) setUnitName(value);
    }

    public String get(String field)
    {
      if ("id".equals(field)) return String.valueOf(getId());
      else if ("name".equals(field)) return getName();
      else if ("type".equals(field)) return getType();
      else if ("params".equals(field)) return getParams();
      else if ("addParams".equals(field)) return getAddParams();
      else if ("cusCode".equals(field)) return getCusCode();
      else if ("numberList".equals(field)) return getNumberList();
      else if ("productList".equals(field)) return getProductList();
      else if ("PRD_BLOCK".equals(field)) return StringUtil.toString(getBlock());
      else if ("attachmentsCount".equals(field)) return getAttachmentsCount();
      else if ("notCheckDouble".equals(field)) return getNotCheckDouble();
      else if ("unitName".equals(field)) return getUnitName();
      else if ("comment".equals(field)) return getComment();
      else return "";
    }

    public String getProductList()
    {
      return productList;
    }

    public void setProductList(String productList)
    {
      this.productList = productList;
    }

    public String getNumberList()
    {
      return numberList;
    }

    public void setNumberList(String numberList)
    {
      this.numberList = numberList;
    }

    public String getAttachmentsCount()
    {
      return attachmentsCount;
    }

    public void setAttachmentsCount(String attachmentsCount)
    {
      this.attachmentsCount = attachmentsCount;
    }

    public String getCreateUserName()
    {
      return createUserName;
    }

    public void setCreateUserName(String createUserName)
    {
      this.createUserName = createUserName;
    }

    public String getUnitName()
    {
      return unitName;
    }

    public void setUnitName(String unitName)
    {
      this.unitName = unitName;
    }

    public String getComment()
    {
      return getComment(getCreateUserName());
    }

    public boolean isHasComment()
    {
      return !StringUtil.isEmpty(getComment());
    }
  }

  public DboCategory getRootCategory()
  {
    return rootCategory;
  }

  public void setRootCategory(DboCategory rootCategory)
  {
    this.rootCategory = rootCategory;
  }

  public String getShow_blocked()
  {
    return show_blocked;
  }

  public String getNotCheckDouble()
  {
    return notCheckDouble;
  }

  public void setNotCheckDouble(String notCheckDouble)
  {
    this.notCheckDouble = notCheckDouble;
  }

  public Integer getNotCheckDoubleId()
  {
    return notCheckDoubleId;
  }

  public void setNotCheckDoubleId(Integer notCheckDoubleId)
  {
    this.notCheckDoubleId = notCheckDoubleId;
  }

  public DboAttachment getTemplateExcel()
  {
    return templateExcel;
  }

  public void setTemplateExcel(DboAttachment templateExcel)
  {
    this.templateExcel = templateExcel;
  }

  public String getTemplateId()
  {
    if (null == templateExcel || null == templateExcel.getId())
    {
      return "";
    }
    return templateExcel.getId().toString();
  }

  public boolean isShowDownload()
  {
    return !StringUtil.isEmpty(getTemplateId());
  }

  public String getTemplateIdNomenclature()
  {
    return String.valueOf(ConstDefinitions.templateIdNomenclature);
  }

  public String getIgnoreFilter()
  {
    return ignoreFilter;
  }

  public void setIgnoreFilter(String ignoreFilter)
  {
    this.ignoreFilter = ignoreFilter;
  }

  public String getRestoreFilter()
  {
    return restoreFilter;
  }

  public void setRestoreFilter(String restoreFilter)
  {
    this.restoreFilter = restoreFilter;
  }

  public String getFilter_common()
  {
    return filter_common;
  }

  public void setFilter_common(String filter_common)
  {
    this.filter_common = filter_common;
  }
}
