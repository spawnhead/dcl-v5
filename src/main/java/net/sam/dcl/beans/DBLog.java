package net.sam.dcl.beans;

public class DBLog
{
  String log_id;
  Action action = new Action();
  User user = new User();
  String log_time;
  String log_ip;

  public DBLog()
  {
  }

  public DBLog(String actionId, String log_ip)
  {
    this.action = new Action(actionId);
    this.log_ip = log_ip;
  }

  public DBLog(String usrId, String actionId, String log_ip)
  {
    this.user = new User(usrId);
    this.action = new Action(actionId);
    this.log_ip = log_ip;
  }

  public DBLog(String usrId, String actionId, String actionSystemName, String log_ip)
  {
    this.user = new User(usrId);
    this.action = new Action(actionId, actionSystemName);
    this.log_ip = log_ip;
  }

  public String getLog_id()
  {
    return log_id;
  }

  public void setLog_id(String log_id)
  {
    this.log_id = log_id;
  }

  public Action getAction()
  {
    return action;
  }

  public void setAction(Action action)
  {
    this.action = action;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getLog_time()
  {
    return log_time;
  }

  public void setLog_time(String log_time)
  {
    this.log_time = log_time;
  }

  public String getLog_ip()
  {
    return log_ip;
  }

  public void setLog_ip(String log_ip)
  {
    this.log_ip = log_ip;
  }
}
