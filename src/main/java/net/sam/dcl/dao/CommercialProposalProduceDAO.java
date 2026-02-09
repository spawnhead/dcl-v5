package net.sam.dcl.dao;

import net.sam.dcl.beans.CommercialProposalProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class CommercialProposalProduceDAO
{
  public static CommercialProposalProduce loadLpcRestCount(IActionContext context, String cpr_id, String cpr_date, String lpc_id) throws Exception
  {
    CommercialProposalProduce commercialProposalProduce = new CommercialProposalProduce(cpr_id, cpr_date, lpc_id);
    if (loadReserved(context, commercialProposalProduce))
    {
      return commercialProposalProduce;
    }
    throw new LoadException(commercialProposalProduce, cpr_id);
  }

  public static boolean loadReserved(IActionContext context, CommercialProposalProduce produceCostProduce) throws Exception
  {
    return (DAOUtils.load(context, "select-reserved-produce_cost-for-commercial_proposal", produceCostProduce, null));
  }
}