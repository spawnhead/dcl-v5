package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.db.VDbConnection;

import java.util.List;

public class ContractorDAO
{
  public static Contractor load(IActionContext context, String id) throws Exception
  {
    Contractor contractor = new Contractor(id);
    if (load(context, contractor))
    {
      return contractor;
    }
    throw new LoadException(contractor, id);
  }

  public static Contractor loadByUNP(IActionContext context, String id, String unp) throws Exception
  {
    Contractor contractor = new Contractor(id, unp);
    if (loadByUNP(context, contractor))
    {
      return contractor;
    }
    else
    {
      return null;
    }
  }

  public static Contractor loadByAccount(IActionContext context, String account) throws Exception
  {
    Contractor contractor = new Contractor(null, null, account);
    if (loadByAccount(context, contractor))
    {
      return contractor;
    }
    else
    {
      return null;
    }
  }

  public static boolean load(IActionContext context, Contractor contractor) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-contractor", contractor, null))
    {
      if (!StringUtil.isEmpty(contractor.getReputation().getId()))
      {
        ReputationDAO.load(context, contractor.getReputation());
      }

      return true;
    }
    return false;
  }

  public static boolean loadByUNP(IActionContext context, Contractor contractor) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-contractor-unp", contractor, null))
    {
      return true;
    }
    return false;
  }

  public static boolean loadByAccount(IActionContext context, Contractor contractor) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-contractor-account", contractor, null))
    {
      loadDoubleAccount(context, contractor);

      if (!StringUtil.isEmpty(contractor.getReputation().getId()))
      {
        ReputationDAO.load(context, contractor.getReputation());
      }

      return true;
    }
    return false;
  }

  public static void loadDoubleAccount(IActionContext context, Contractor contractor) throws Exception
  {
    if ( !StringUtil.isEmpty(contractor.getDoubleAccount()) )
    {
      VResultSet resultSet = DAOUtils.executeQuery(context, "dao-load-contractor-double_by_account", contractor, null);
      List<String> ctrNames = DAOUtils.resultSet2StringList(resultSet);
      if ( ctrNames.size() == 1 )
      {
        contractor.setDoubleAccount(null);
        return;
      }
      StringBuffer result = new StringBuffer();
      for (int i = 0; i < ctrNames.size(); i++)
      {
        result.append(ctrNames.get(i));
        if (i != ctrNames.size() - 1)
        {
          result.append(", ");
        }
      }
      contractor.setCtrNames(result.toString());
    }
  }

  public static void save(IActionContext context, Contractor contractor) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contractor-update-dao"), contractor, null);
  }

  public static void saveBlock(IActionContext context, Contractor contractor) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contractor-update-block"), contractor, null);
    if ("1".equals(contractor.getBlock()))
    {
      DAOUtils.update(context.getConnection(), context.getSqlResource().get("contact_persons-update-block"), contractor, null);
    }
  }

  public static void saveUsers(IActionContext context, Contractor contractor) throws Exception
  {
    VDbConnection conn = context.getConnection();
    String newManagerUsrIds = "";
    for (int i = 0; i < contractor.getUsers().size(); i++)
    {
      ContractorUser contractorUser = contractor.getUsers().get(i);
      User user = UserDAO.loadWithRoles(context, contractorUser.getUser().getUsr_id()); //грузим роли
      if (!DAOUtils.load(context, "select-contractor_user", contractorUser, null) && user.isManager()) //новый пользователь
      {
        newManagerUsrIds += contractorUser.getUser().getUsr_id() + ", ";
      }
    }
    if (!StringUtil.isEmpty(newManagerUsrIds))
    {
      newManagerUsrIds = newManagerUsrIds.substring(0, newManagerUsrIds.length() - 2);
      DeletePaymenMsgHelper deletePaymenMsgHelper = new DeletePaymenMsgHelper(contractor.getId(), newManagerUsrIds);
      DAOUtils.update(conn, context.getSqlResource().get("delete_payment_msg_for_users"), deletePaymenMsgHelper, null);
      //теперь куратор есть - убираем признак
      DAOUtils.update(conn, context.getSqlResource().get("update_payment_msg_for_contractor"), deletePaymenMsgHelper, null);
    }
    DAOUtils.update(conn, context.getSqlResource().get("delete_contractor_users"), contractor, null);
    for (int i = 0; i < contractor.getUsers().size(); i++)
    {
      ContractorUser user = contractor.getUsers().get(i);
      if ( !StringUtil.isEmpty(user.getUser().getUsr_id()) )
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_contractor_user"), user, null);
      }
    }
  }

  public static void saveAccounts(IActionContext context, Contractor contractor) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_accounts"), contractor, null);
    for (int i = 0; i < contractor.getAccounts().size(); i++)
    {
      Account account = contractor.getAccounts().get(i);
      DAOUtils.update(conn, context.getSqlResource().get("insert_account"), account, null);
    }
  }

  public static void saveContactPersons(IActionContext context, Contractor contractor) throws Exception
  {
    VDbConnection conn = context.getConnection();
    for (int i = 0; i < contractor.getContactPersons().size(); i++)
    {
      ContactPerson contactPerson = contractor.getContactPersons().get(i);
      if ( StringUtil.isEmpty(contactPerson.getCps_id()) )
      {
        DAOUtils.load(conn, context.getSqlResource().get("contact-person-insert"), contactPerson, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("contact-person-update"), contactPerson, null);
      }
      ContactPersonDAO.saveUsers(context, contactPerson);
    }
  }

  public static void updateDependedDocs(IActionContext context, ForUpdateDependedDocuments updateDependedDocuments) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contractor-update_depended_docs"), updateDependedDocuments, null);
  }

  static public class DeletePaymenMsgHelper
  {
    String ctr_id;
    String new_user_ids;

    DeletePaymenMsgHelper(String ctr_id, String exist_ids)
    {
      this.ctr_id = ctr_id;
      this.new_user_ids = exist_ids;
    }

    public String getCtr_id()
    {
      return ctr_id;
    }

    public void setCtr_id(String ctr_id)
    {
      this.ctr_id = ctr_id;
    }

    public String getNew_user_ids()
    {
      return new_user_ids;
    }

    public void setNew_user_ids(String new_user_ids)
    {
      this.new_user_ids = new_user_ids;
    }
  }
}
