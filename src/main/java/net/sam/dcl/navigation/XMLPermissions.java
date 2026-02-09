package net.sam.dcl.navigation;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sam.dcl.config.Config;

/**
 * User: Dima
 * Date: Mar 21, 2005
 * Time: 7:33:39 PM
 */
public class XMLPermissions
{

  protected static Log log = LogFactory.getLog(XMLPermissions.class);

  private final Object mutex = new Object();
  private List<String> files = new ArrayList<String>();

  public XMLPermissions()
  {

  }

  public XMLPermissions(String file)
  {
    addFile(file);
  }

  public Action findAction(String url)
  {
    synchronized (mutex)
    {
      for (Action action : actions)
      {
        String name = action.getName();
        for (StringTokenizer tokenizer = new StringTokenizer(name, ","); tokenizer.hasMoreTokens();)
        {
          String token = tokenizer.nextToken();
          // т.к. проверяется совпадение первых символов (для того, чтобы работали стандартные акшн с разными диспачами),
          // нужно избегать ситуаций, когда имеем одинаковые начальные названия, но разные права
          // Пример:
          // <action name="/Menu.do?current_menu_id=id.contract"> и
          // <action name="/Menu.do?current_menu_id=id.contractors">
          // для второй строки действуют права первой.
          if (url.startsWith(token))
          {
            return action;
          }
        }
      }
    }
    return null;
  }

  public void addFile(String file)
  {
    synchronized (mutex)
    {
      files.add(file);
    }
  }

  public void clear()
  {
    synchronized (mutex)
    {
      actions.clear();
    }
  }

  public void load()
  {

    synchronized (mutex)
    {
      for (String file : files)
      {
        load(file);
      }
    }
  }

  public void reload()
  {
    synchronized (mutex)
    {
      clear();
      load();
    }
  }

  List<Action> actions = new ArrayList<Action>();

  public void addAction(Action action)
  {
    actions.add(action);
  }

  public void load(String fileName)
  {

    Digester digester = new Digester();
    digester.push(this);
    digester.setValidating(true);
    try
    {
      File file = new File(fileName);
      URL url = this.getClass().getResource("/resources/dtd/xml-permissions_1_1.dtd");
      digester.register("xml-permissions_1_1.dtd", url.toString());

      digester.addObjectCreate("xml-permissions/action", Action.class);
      digester.addSetProperties("xml-permissions/action");
      digester.addSetNext("xml-permissions/action", "addAction");

      addRole(digester, "admin", Role.ADMIN.class);
      addRole(digester, "manager", Role.MANAGER.class);
      addRole(digester, "declarant", Role.DECLARANT.class);
      addRole(digester, "economist", Role.ECONOMIST.class);
      addRole(digester, "lawyer", Role.LAWYER.class);
      addRole(digester, "user_in_lithuania", Role.USER_IN_LITHUANIA.class);
      addRole(digester, "other_user_in_Minsk", Role.OTHER_USER_IN_MINSK.class);
      addRole(digester, "staff_of_service", Role.STAFF_OF_SERVICE.class);
      addRole(digester, "logistic", Role.LOGISTIC.class);

      digester.parse(file);
    }
    catch (Exception e)
    {
      log.error("Error while parsing:" + fileName, e);
    }
  }

  private void addRole(Digester digester, String name, Class clazz)
  {
    digester.addObjectCreate("xml-permissions/action/" + name, clazz);
    digester.addSetProperties("xml-permissions/action/" + name);
    digester.addSetNext("xml-permissions/action/" + name, "addRole");
  }

  public static void main(String[] args)
  {
    XMLPermissions xmlPermissions = new XMLPermissions(Config.getString("global.permission-resources"));
    xmlPermissions.reload();
    System.out.println("loaded");
  }
}
