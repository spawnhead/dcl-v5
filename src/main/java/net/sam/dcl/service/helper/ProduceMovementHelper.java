package net.sam.dcl.service.helper;

import net.sam.dcl.beans.ProduceMovement;
import net.sam.dcl.beans.ProduceMovementProduct;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.ProduceMovementDAO;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StrutsUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandra Shkrobova
 * Date: 10/8/14
 */
public class ProduceMovementHelper
{
  public static ProduceMovement formProduceMovementObject(IActionContext context, DboProduce produce, Integer stfId) throws Exception
  {
    Hibernate.initialize(produce.getCatalogNumbers());
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    List<DboCatalogNumber> numbers = hibSession.getNamedQuery("get-produce-details")
            .setInteger("prd_id", produce.getId())
            .list();
    ProduceMovement produceMovement = new ProduceMovement(produce);

    for (DboCatalogNumber catalogNumber : numbers)
    {
      if (stfId != null && !catalogNumber.getStuffCategory().getId().equals(stfId))
        continue;

      ProduceMovementProduct produceMovementProduct = new ProduceMovementProduct();
      produceMovementProduct.setPrd_id(produce.getId());
      produceMovementProduct.setCatalogNumber(catalogNumber);

      List<ProduceMovementProduct.ProduceResultListLine> lst = ProduceMovementDAO.loadProduceMovement(context, produceMovementProduct);
      produceMovementProduct.formRecList(produceMovementProduct.cloneProduceList(lst));
      for (int j = 0; j < produceMovementProduct.getResultList().size(); j++)
      {
        ProduceMovementProduct.ProduceResultListLine produceResultListLine = new ProduceMovementProduct.ProduceResultListLine((ProduceMovementProduct.ProduceResultListLine) produceMovementProduct.getResultList().get(j));
        produceMovement.getResultList().add(produceResultListLine);
      }

      produceMovementProduct.formRecListChain(produceMovementProduct.cloneProduceList(lst));
      for (int j = 0; j < produceMovementProduct.getResultListChain().size(); j++)
      {
        ProduceMovementProduct.ProduceResultListLine produceResultListLine = new ProduceMovementProduct.ProduceResultListLine((ProduceMovementProduct.ProduceResultListLine) produceMovementProduct.getResultListChain().get(j));
        produceMovement.getResultListChain().add(produceResultListLine);
      }

      produceMovement.setShowLegend(produceMovement.isShowLegend() || produceMovementProduct.isShowLegend());
      produceMovement.setShowLegendInput(produceMovement.isShowLegendInput() || produceMovementProduct.isShowLegendInput());
      produceMovement.setShowLegendOrder(produceMovement.isShowLegendOrder() || produceMovementProduct.isShowLegendOrder());
      produceMovement.setShowLegendTransit(produceMovement.isShowLegendTransit() || produceMovementProduct.isShowLegendTransit());
      produceMovement.setCountOrd(produceMovement.getCountOrd() + produceMovementProduct.getCountOrd());
      produceMovement.setCountOrdExecuted(produceMovement.getCountOrdExecuted() + produceMovementProduct.getCountOrdExecuted());
      produceMovement.setCountOrdRest(produceMovement.getCountOrdRest() + produceMovementProduct.getCountOrdRest());
      produceMovement.setCountIn(produceMovement.getCountIn() + produceMovementProduct.getCountIn());
      produceMovement.setCountTransit(produceMovement.getCountTransit() + produceMovementProduct.getCountTransit());
      produceMovement.setCountTransitRest(produceMovement.getCountTransitRest() + produceMovementProduct.getCountTransitRest());
      produceMovement.setCountOut(produceMovement.getCountOut() + produceMovementProduct.getCountOut());
      produceMovement.setRestOrd(produceMovement.getRestOrd() + produceMovementProduct.getRestOrd());
      produceMovement.setRest(produceMovement.getRest() + produceMovementProduct.getRest());
    }
    ProduceMovementProduct.ProduceResultListLine produceResultListLine = new ProduceMovementProduct.ProduceResultListLine();
    produceResultListLine.setId(String.valueOf(produceMovement.getResultList().size()));
    produceResultListLine.setOrd_date(StrutsUtil.getMessage(context, "ProduceMovementList.totalLarge"));
    produceResultListLine.setPrc_date(StrutsUtil.getMessage(context, "ProduceMovementList.totalLarge"));
    produceResultListLine.setOrd_produce_cnt(produceMovement.getCountOrd());
    produceResultListLine.setOrd_produce_cnt_executed(produceMovement.getCountOrdExecuted());
    produceResultListLine.setOrd_produce_cnt_rest(produceMovement.getCountOrdRest());
    produceResultListLine.setTrn_produce_cnt(produceMovement.getCountTransit());
    produceResultListLine.setTrn_produce_cnt_rest(produceMovement.getCountTransitRest());
    produceResultListLine.setPrc_produce_cnt(produceMovement.getCountIn());
    produceResultListLine.setShp_produce_cnt(produceMovement.getCountOut());
    produceResultListLine.setItogLineLarge(true);
    produceMovement.getResultList().add(produceResultListLine);
    produceMovement.getResultListChain().add(new ProduceMovementProduct.ProduceResultListLine(produceResultListLine));

    produceResultListLine = new ProduceMovementProduct.ProduceResultListLine();
    produceResultListLine.setId(String.valueOf(produceMovement.getResultList().size()));
    produceResultListLine.setOrd_date(StrutsUtil.getMessage(context, "ProduceMovementList.restLarge"));
    produceResultListLine.setPrc_date(StrutsUtil.getMessage(context, "ProduceMovementList.restLarge"));
    produceResultListLine.setOrd_produce_cnt(produceMovement.getRestOrd());
    produceResultListLine.setPrc_produce_cnt(produceMovement.getRest());
    produceResultListLine.setItogLineLarge(true);
    produceMovement.getResultList().add(produceResultListLine);
    produceMovement.getResultListChain().add(new ProduceMovementProduct.ProduceResultListLine(produceResultListLine));
    return produceMovement;
  }
}
