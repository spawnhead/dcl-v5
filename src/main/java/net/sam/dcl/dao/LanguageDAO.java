package net.sam.dcl.dao;

import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Language;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class LanguageDAO
{
  public static Language load(IActionContext context, String id) throws Exception
  {
    Language language = new Language(id);
    if (load(context, language))
    {
      return language;
    }
    throw new LoadException(language, id);
  }

  public static boolean load(IActionContext context, Language language) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-language", language, null));
  }

  public static Language loadByLngLetterId(IActionContext context, String id, String letterId) throws Exception
  {
    Language language = new Language(null, null, letterId);
    if (loadByLngLetterId(context, language))
    {
      return language;
    }
    throw new LoadException(language, id);
  }

  public static boolean loadByLngLetterId(IActionContext context, Language language) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-language_by_tetter_id", language, null));
  }
}
