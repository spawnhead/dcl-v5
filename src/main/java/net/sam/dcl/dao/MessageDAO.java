package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class MessageDAO
{
  public static void deleteInformationMessage(IActionContext context, Message message) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-information_message"), message, null);
  }

  public static void deletePaymentMessage(IActionContext context, PaymentMessage paymentMessage) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-payment_message"), paymentMessage, null);
  }

  public static void deleteContractMessage(IActionContext context, Message message) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-contract_message"), message, null);
  }

	public static void deleteOrderMessage(IActionContext context, Message message) throws Exception
 {
   DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-order_message"), message, null);
 }

  public static void deleteConditionForContractMessage(IActionContext context, Message message) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-condition_for_contract_message"), message, null);
  }

	public static void deleteConditionForContractMessagesForEconomist(IActionContext context, UserLink userLink) throws Exception
 {
   DAOUtils.update(context.getConnection(), context.getSqlResource().get("delete-condition_for_contract_messages_for_economist"), userLink, null);
 }

  public static void saveInformationMessages(IActionContext context, Message message) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-information_messages"), message, null);
  }

  public static void savePaymentMessages(IActionContext context, Payment payment) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-payment_messages"), payment, null);
  }

  public static void saveContractMessages(IActionContext context, ContractMessage contractMessage) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-contract_messages"), contractMessage, null);
  }

  public static void saveConditionForContractMessages(IActionContext context, ConditionForContractMessage conditionForContractMessage) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-condition_for_contract_messages"), conditionForContractMessage, null);
  }

  public static void saveOrderMessages(IActionContext context, OrderMessage orderMessage) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-order_messages"), orderMessage, null);
  }

  public static void saveConditionForContractMessagesForEconomist(IActionContext context, UserLink userLink) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("save-condition_for_contract_messages_for_economist"), userLink, null);
  }
}