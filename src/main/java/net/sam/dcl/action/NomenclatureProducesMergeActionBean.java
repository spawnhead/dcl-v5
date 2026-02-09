package net.sam.dcl.action;

import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.dbo.*;
import net.sam.dcl.beans.Produce;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.Hibernate;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class NomenclatureProducesMergeActionBean extends ActionBean
{
  protected static Log log = LogFactory.getLog(NomenclatureProducesMergeActionBean.class);

  String oldId;
  String newId;

  DboProduce sourceProduce;
  DboUnit sourceUnit = new DboUnit();
  List<NomenclatureProduceActionBean.LangRow> sourceLanguageTranslations;
  String leftNameENValue;
  String leftNameDEValue;

  DboProduce targetProduce;
  DboUnit targetUnit = new DboUnit();
  List<NomenclatureProduceActionBean.LangRow> targetLanguageTranslations;
  String rightNameENValue;
  String rightNameDEValue;

  String leftNameRU;
  String leftNameEN;
  String leftNameDE;
  String leftType;
  String leftParams;
  String leftAddParams;
  String leftUnit;
  String leftMaterial;
  String leftPurporse;
  String leftSpecification;
  String leftPrinciple;

  String rightNameRU;
  String rightNameEN;
  String rightNameDE;
  String rightType;
  String rightParams;
  String rightAddParams;
  String rightUnit;
  String rightMaterial;
  String rightPurporse;
  String rightSpecification;
  String rightPrinciple;

  public ActionForward show() throws Exception
  {
    return mapping.findForward("form");
  }

  public ActionForward merge() throws Exception
  {
    oldId = (String)request.getSession().getAttribute(Produce.oldProduceId);
    newId = (String)request.getSession().getAttribute(Produce.newProduceId);

    if ( StringUtil.isEmpty(oldId) || StringUtil.isEmpty(newId) )
    {
      return mapping.findForward("back");
    }

    sourceProduce = (DboProduce) hibSession.get(DboProduce.class, new Integer(oldId));
    if (sourceProduce.getUnit() != null)
    {
      sourceUnit = new DboUnit(sourceProduce.getUnit());
    }
    Hibernate.initialize(sourceProduce.getProduceLanguages());

    targetProduce = (DboProduce) hibSession.get(DboProduce.class, new Integer(newId));
    if (targetProduce.getUnit() != null)
    {
      targetUnit = new DboUnit(targetProduce.getUnit());
    }
    Hibernate.initialize(targetProduce.getProduceLanguages());

    sourceLanguageTranslations = hibSession
            .getNamedQuery("languages-for-produce-except-one")
            .setString("code", Config.getString(Constants.nomenclatureProduceMainLang))
            .setInteger("produce_id", sourceProduce.getId())
            .setResultTransformer(new AliasToBeanResultTransformer(NomenclatureProduceActionBean.LangRow.class))
            .list();
    setLeftNameENValue(sourceLanguageTranslations.get(0).getText());
    setLeftNameDEValue(sourceLanguageTranslations.get(1).getText());

    targetLanguageTranslations = hibSession
            .getNamedQuery("languages-for-produce-except-one")
            .setString("code", Config.getString(Constants.nomenclatureProduceMainLang))
            .setInteger("produce_id", targetProduce.getId())
            .setResultTransformer(new AliasToBeanResultTransformer(NomenclatureProduceActionBean.LangRow.class))
            .list();
    setRightNameENValue(targetLanguageTranslations.get(0).getText());
    setRightNameDEValue(targetLanguageTranslations.get(1).getText());

    setLeftNameRU("");
    setLeftNameEN("");
    setLeftNameDE("");
    setLeftType("");
    setLeftParams("");
    setLeftAddParams("");
    setLeftUnit("");
    setLeftMaterial("");
    setLeftPurporse("");
    setLeftSpecification("");
    setLeftPrinciple("");
    setRightNameRU("1");
    setRightNameEN("1");
    setRightNameDE("1");
    setRightType("1");
    setRightParams("1");
    setRightAddParams("1");
    setRightUnit("1");
    setRightMaterial("1");
    setRightPurporse("1");
    setRightSpecification("1");
    setRightPrinciple("1");

    return show();
  }

  public ActionForward process() throws Exception
  {
    if ( !StringUtil.isEmpty(getLeftNameRU()) )
      targetProduce.setName(sourceProduce.getName());

    NomenclatureProduceActionBean.LangRow langRow = targetLanguageTranslations.get(0);
    DboProduceLanguage pLanguage = targetProduce.getProduceLanguages().get(langRow.getLangCode());
    String enName = !StringUtil.isEmpty(getLeftNameEN()) ? getLeftNameENValue() : getRightNameENValue();
    if (pLanguage != null)
    {
      pLanguage.setName(enName);
    }
    else
    {
      if (!StringUtil.isEmpty(enName))
      {
        pLanguage = new DboProduceLanguage(targetProduce, DboLanguage.DAO.loadByLangCode(langRow.getLangCode()));
        pLanguage.setName(enName);
        targetProduce.addProduceLanguage(pLanguage);
      }
    }
    hibSession.flush();

    langRow = targetLanguageTranslations.get(1);
    pLanguage = targetProduce.getProduceLanguages().get(langRow.getLangCode());
    String deName = !StringUtil.isEmpty(getLeftNameDE()) ? getLeftNameDEValue() : getRightNameDEValue();
    if (pLanguage != null)
    {
      pLanguage.setName(deName);
    }
    else
    {
      if (!StringUtil.isEmpty(deName))
      {
        pLanguage = new DboProduceLanguage(targetProduce, DboLanguage.DAO.loadByLangCode(langRow.getLangCode()));
        pLanguage.setName(deName);
        targetProduce.addProduceLanguage(pLanguage);
      }
    }
    hibSession.flush();

    if ( !StringUtil.isEmpty(getLeftType()) )
      targetProduce.setType(sourceProduce.getType());
    if ( !StringUtil.isEmpty(getLeftParams()) )
      targetProduce.setParams(sourceProduce.getParams());
    if ( !StringUtil.isEmpty(getLeftAddParams()) )
      targetProduce.setAddParams(sourceProduce.getAddParams());
    targetProduce.setUnit(!StringUtil.isEmpty(getLeftUnit()) ? sourceUnit : targetUnit);
    if ( !StringUtil.isEmpty(getLeftMaterial()) )
      targetProduce.setMaterial(sourceProduce.getMaterial());
    if ( !StringUtil.isEmpty(getLeftPurporse()) )
      targetProduce.setPurpose(sourceProduce.getPurpose());
    if ( !StringUtil.isEmpty(getLeftSpecification()) )
      targetProduce.setSpecification(sourceProduce.getSpecification());
    if ( !StringUtil.isEmpty(getLeftPrinciple()) )
      targetProduce.setPrinciple(sourceProduce.getPrinciple());
    hibSession.flush();

    hibSession.update(targetProduce);
    hibSession.flush();
    hibSession.createSQLQuery("execute procedure DCL_UPDATE_DEP_DOCS_FOR_PRD(:srcProduce, :targetProduce);")
            .setInteger("srcProduce", sourceProduce.getId())
            .setInteger("targetProduce", targetProduce.getId())
            .executeUpdate();

    hibSession.getTransaction().commit();

    return mapping.findForward("back");
  }

  public String getOldId()
  {
    return oldId;
  }

  public void setOldId(String oldId)
  {
    this.oldId = oldId;
  }

  public String getNewId()
  {
    return newId;
  }

  public void setNewId(String newId)
  {
    this.newId = newId;
  }

  public DboProduce getSourceProduce()
  {
    return sourceProduce;
  }

  public void setSourceProduce(DboProduce sourceProduce)
  {
    this.sourceProduce = sourceProduce;
  }

  public DboUnit getSourceUnit()
  {
    return sourceUnit;
  }

  public void setSourceUnit(DboUnit sourceUnit)
  {
    this.sourceUnit = sourceUnit;
  }

  public String getLeftNameENValue()
  {
    return leftNameENValue;
  }

  public void setLeftNameENValue(String leftNameENValue)
  {
    this.leftNameENValue = leftNameENValue;
  }

  public String getLeftNameDEValue()
  {
    return leftNameDEValue;
  }

  public void setLeftNameDEValue(String leftNameDEValue)
  {
    this.leftNameDEValue = leftNameDEValue;
  }

  public DboProduce getTargetProduce()
  {
    return targetProduce;
  }

  public void setTargetProduce(DboProduce targetProduce)
  {
    this.targetProduce = targetProduce;
  }

  public DboUnit getTargetUnit()
  {
    return targetUnit;
  }

  public void setTargetUnit(DboUnit targetUnit)
  {
    this.targetUnit = targetUnit;
  }

  public String getRightNameENValue()
  {
    return rightNameENValue;
  }

  public void setRightNameENValue(String rightNameENValue)
  {
    this.rightNameENValue = rightNameENValue;
  }

  public String getRightNameDEValue()
  {
    return rightNameDEValue;
  }

  public void setRightNameDEValue(String rightNameDEValue)
  {
    this.rightNameDEValue = rightNameDEValue;
  }

  public String getLeftNameRU()
  {
    return leftNameRU;
  }

  public void setLeftNameRU(String leftNameRU)
  {
    this.leftNameRU = leftNameRU;
  }

  public String getLeftNameEN()
  {
    return leftNameEN;
  }

  public void setLeftNameEN(String leftNameEN)
  {
    this.leftNameEN = leftNameEN;
  }

  public String getLeftNameDE()
  {
    return leftNameDE;
  }

  public void setLeftNameDE(String leftNameDE)
  {
    this.leftNameDE = leftNameDE;
  }

  public String getLeftType()
  {
    return leftType;
  }

  public void setLeftType(String leftType)
  {
    this.leftType = leftType;
  }

  public String getLeftParams()
  {
    return leftParams;
  }

  public void setLeftParams(String leftParams)
  {
    this.leftParams = leftParams;
  }

  public String getLeftAddParams()
  {
    return leftAddParams;
  }

  public void setLeftAddParams(String leftAddParams)
  {
    this.leftAddParams = leftAddParams;
  }

  public String getLeftUnit()
  {
    return leftUnit;
  }

  public void setLeftUnit(String leftUnit)
  {
    this.leftUnit = leftUnit;
  }

  public String getLeftMaterial()
  {
    return leftMaterial;
  }

  public void setLeftMaterial(String leftMaterial)
  {
    this.leftMaterial = leftMaterial;
  }

  public String getLeftPurporse()
  {
    return leftPurporse;
  }

  public void setLeftPurporse(String leftPurporse)
  {
    this.leftPurporse = leftPurporse;
  }

  public String getLeftSpecification()
  {
    return leftSpecification;
  }

  public void setLeftSpecification(String leftSpecification)
  {
    this.leftSpecification = leftSpecification;
  }

  public String getLeftPrinciple()
  {
    return leftPrinciple;
  }

  public void setLeftPrinciple(String leftPrinciple)
  {
    this.leftPrinciple = leftPrinciple;
  }

  public String getRightNameRU()
  {
    return rightNameRU;
  }

  public void setRightNameRU(String rightNameRU)
  {
    this.rightNameRU = rightNameRU;
  }

  public String getRightNameEN()
  {
    return rightNameEN;
  }

  public void setRightNameEN(String rightNameEN)
  {
    this.rightNameEN = rightNameEN;
  }

  public String getRightNameDE()
  {
    return rightNameDE;
  }

  public void setRightNameDE(String rightNameDE)
  {
    this.rightNameDE = rightNameDE;
  }

  public String getRightType()
  {
    return rightType;
  }

  public void setRightType(String rightType)
  {
    this.rightType = rightType;
  }

  public String getRightParams()
  {
    return rightParams;
  }

  public void setRightParams(String rightParams)
  {
    this.rightParams = rightParams;
  }

  public String getRightAddParams()
  {
    return rightAddParams;
  }

  public void setRightAddParams(String rightAddParams)
  {
    this.rightAddParams = rightAddParams;
  }

  public String getRightUnit()
  {
    return rightUnit;
  }

  public void setRightUnit(String rightUnit)
  {
    this.rightUnit = rightUnit;
  }

  public String getRightMaterial()
  {
    return rightMaterial;
  }

  public void setRightMaterial(String rightMaterial)
  {
    this.rightMaterial = rightMaterial;
  }

  public String getRightPurporse()
  {
    return rightPurporse;
  }

  public void setRightPurporse(String rightPurporse)
  {
    this.rightPurporse = rightPurporse;
  }

  public String getRightSpecification()
  {
    return rightSpecification;
  }

  public void setRightSpecification(String rightSpecification)
  {
    this.rightSpecification = rightSpecification;
  }

  public String getRightPrinciple()
  {
    return rightPrinciple;
  }

  public void setRightPrinciple(String rightPrinciple)
  {
    this.rightPrinciple = rightPrinciple;
  }
}