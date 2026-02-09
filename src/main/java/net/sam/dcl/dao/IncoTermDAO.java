package net.sam.dcl.dao;

import net.sam.dcl.beans.IncoTerm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class IncoTermDAO
{

  public static IncoTerm load(IActionContext context, String id) throws Exception
  {
    IncoTerm incoTerm = new IncoTerm(id);
    if (load(context, incoTerm))
    {
      return incoTerm;
    }
    throw new LoadException(incoTerm, id);
  }

  public static IncoTerm loadByName(IActionContext context, String name) throws Exception
  {
    IncoTerm incoTerm = new IncoTerm(null, name);
    if ( !loadByName(context, incoTerm) )
    {
      incoTerm.setName("");
    }

    return incoTerm;
  }

  public static boolean load(IActionContext context, IncoTerm incoTerm) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-inco_term", incoTerm, null));
  }

  public static boolean loadByName(IActionContext context, IncoTerm incoTerm) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-inco_term-name", incoTerm, null));
  }

  public static List<IncoTerm> loadDependentTerms(IActionContext context, String termName) throws Exception
  {
    if ( StringUtil.isEmpty(termName) )
    {
      return new ArrayList<IncoTerm>();
    }
    String[] terms = IncoTerm.termDepeninces.get(termName.toUpperCase());
    TermNames termNames = new TermNames(StringUtil.stringArray2String(terms, "'", "'"));
    return DAOUtils.fillList(context, "select-inco_terms-for-ids", termNames, IncoTerm.class, null, null);
  }

  static public class TermNames
  {
    String names;

    public TermNames(String names)
    {
      this.names = names;
    }

    public String getNames()
    {
      return names;
    }

    public void setNames(String names)
    {
      this.names = names;
    }
  }
}
