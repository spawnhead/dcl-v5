package net.sam.dcl.beans;

import java.io.Serializable;

public class ContractorRequestStage implements Serializable
{
  String number;
  String id;
  String parentId;
  String name;
  String comment;
  String needPrint;

  public ContractorRequestStage()
  {
  }

  public ContractorRequestStage(ContractorRequestStage stage)
  {
    this.number = stage.getNumber();
    this.id = stage.getId();
    this.parentId = stage.getParentId();
    this.name = stage.getName();
    this.comment = stage.getComment();
    this.needPrint = stage.getNeedPrint();
  }

  public ContractorRequestStage(String number, String name, String comment, String needPrint)
  {
    this.number = number;
    this.name = name;
    this.comment = comment;
    this.needPrint = needPrint;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public ContractorRequestStage(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getParentId()
  {
    return parentId;
  }

  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }

  public String getName()
  {
    return name;
  }

  public String getNameFormatted()
  {
    return "<b>" + name + "</b>";
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getComment()
  {
    return comment;
  }

  public String getCommentFormatted()
  {
    return comment.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
  }

  public String getCommentPDF()
  {
    return comment.replaceAll(ReportDelimiterConsts.html_separator, "\r\n").replaceAll("&nbsp;", " ");
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getNeedPrint()
  {
    return needPrint;
  }

  public void setNeedPrint(String needPrint)
  {
    this.needPrint = needPrint;
  }
}