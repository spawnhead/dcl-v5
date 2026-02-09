package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.Contractor;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class MergeContractorsForm extends BaseDispatchValidatorForm
{
  String oldId;
  String newId;

  Contractor contractorLeft = new Contractor();
  String leftName;
  String leftFullName;
  String leftIndex;
  String leftRegion;
  String leftPlace;
  String leftStreet;
  String leftBuilding;
  String leftAddInfo;
  String leftPhone;
  String leftFax;
  String leftEMail;
  String leftBank;
  String leftUNP;
  String leftOKPO;
  String leftReputation;
  String leftCountry;
  String leftComment;

  Contractor contractorRight = new Contractor();
  String rightName;
  String rightFullName;
  String rightIndex;
  String rightRegion;
  String rightPlace;
  String rightStreet;
  String rightBuilding;
  String rightAddInfo;
  String rightPhone;
  String rightFax;
  String rightEMail;
  String rightBank;
  String rightUNP;
  String rightOKPO;
  String rightReputation;
  String rightCountry;
  String rightComment;

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

  public Contractor getContractorLeft()
  {
    return contractorLeft;
  }

  public void setContractorLeft(Contractor contractorLeft)
  {
    this.contractorLeft = contractorLeft;
  }

  public String getLeftName()
  {
    return leftName;
  }

  public void setLeftName(String leftName)
  {
    this.leftName = leftName;
  }

  public String getLeftFullName()
  {
    return leftFullName;
  }

  public void setLeftFullName(String leftFullName)
  {
    this.leftFullName = leftFullName;
  }

  public String getLeftIndex()
  {
    return leftIndex;
  }

  public void setLeftIndex(String leftIndex)
  {
    this.leftIndex = leftIndex;
  }

  public String getLeftRegion()
  {
    return leftRegion;
  }

  public void setLeftRegion(String leftRegion)
  {
    this.leftRegion = leftRegion;
  }

  public String getLeftPlace()
  {
    return leftPlace;
  }

  public void setLeftPlace(String leftPlace)
  {
    this.leftPlace = leftPlace;
  }

  public String getLeftStreet()
  {
    return leftStreet;
  }

  public void setLeftStreet(String leftStreet)
  {
    this.leftStreet = leftStreet;
  }

  public String getLeftBuilding()
  {
    return leftBuilding;
  }

  public void setLeftBuilding(String leftBuilding)
  {
    this.leftBuilding = leftBuilding;
  }

  public String getLeftAddInfo()
  {
    return leftAddInfo;
  }

  public void setLeftAddInfo(String leftAddInfo)
  {
    this.leftAddInfo = leftAddInfo;
  }

  public String getLeftPhone()
  {
    return leftPhone;
  }

  public void setLeftPhone(String leftPhone)
  {
    this.leftPhone = leftPhone;
  }

  public String getLeftFax()
  {
    return leftFax;
  }

  public void setLeftFax(String leftFax)
  {
    this.leftFax = leftFax;
  }

  public String getLeftEMail()
  {
    return leftEMail;
  }

  public void setLeftEMail(String leftEMail)
  {
    this.leftEMail = leftEMail;
  }

  public String getLeftBank()
  {
    return leftBank;
  }

  public void setLeftBank(String leftBank)
  {
    this.leftBank = leftBank;
  }

  public String getLeftUNP()
  {
    return leftUNP;
  }

  public void setLeftUNP(String leftUNP)
  {
    this.leftUNP = leftUNP;
  }

  public String getLeftOKPO()
  {
    return leftOKPO;
  }

  public void setLeftOKPO(String leftOKPO)
  {
    this.leftOKPO = leftOKPO;
  }

  public String getLeftReputation()
  {
    return leftReputation;
  }

  public void setLeftReputation(String leftReputation)
  {
    this.leftReputation = leftReputation;
  }

  public String getLeftCountry()
  {
    return leftCountry;
  }

  public void setLeftCountry(String leftCountry)
  {
    this.leftCountry = leftCountry;
  }

	public String getLeftComment()
	{
		return leftComment;
	}

	public void setLeftComment(String leftComment)
	{
		this.leftComment = leftComment;
	}

	public Contractor getContractorRight()
  {
    return contractorRight;
  }

  public void setContractorRight(Contractor contractorRight)
  {
    this.contractorRight = contractorRight;
  }

  public String getRightName()
  {
    return rightName;
  }

  public void setRightName(String rightName)
  {
    this.rightName = rightName;
  }

  public String getRightFullName()
  {
    return rightFullName;
  }

  public void setRightFullName(String rightFullName)
  {
    this.rightFullName = rightFullName;
  }

  public String getRightIndex()
  {
    return rightIndex;
  }

  public void setRightIndex(String rightIndex)
  {
    this.rightIndex = rightIndex;
  }

  public String getRightRegion()
  {
    return rightRegion;
  }

  public void setRightRegion(String rightRegion)
  {
    this.rightRegion = rightRegion;
  }

  public String getRightPlace()
  {
    return rightPlace;
  }

  public void setRightPlace(String rightPlace)
  {
    this.rightPlace = rightPlace;
  }

  public String getRightStreet()
  {
    return rightStreet;
  }

  public void setRightStreet(String rightStreet)
  {
    this.rightStreet = rightStreet;
  }

  public String getRightBuilding()
  {
    return rightBuilding;
  }

  public void setRightBuilding(String rightBuilding)
  {
    this.rightBuilding = rightBuilding;
  }

  public String getRightAddInfo()
  {
    return rightAddInfo;
  }

  public void setRightAddInfo(String rightAddInfo)
  {
    this.rightAddInfo = rightAddInfo;
  }

  public String getRightPhone()
  {
    return rightPhone;
  }

  public void setRightPhone(String rightPhone)
  {
    this.rightPhone = rightPhone;
  }

  public String getRightFax()
  {
    return rightFax;
  }

  public void setRightFax(String rightFax)
  {
    this.rightFax = rightFax;
  }

  public String getRightEMail()
  {
    return rightEMail;
  }

  public void setRightEMail(String rightEMail)
  {
    this.rightEMail = rightEMail;
  }

  public String getRightBank()
  {
    return rightBank;
  }

  public void setRightBank(String rightBank)
  {
    this.rightBank = rightBank;
  }

  public String getRightUNP()
  {
    return rightUNP;
  }

  public void setRightUNP(String rightUNP)
  {
    this.rightUNP = rightUNP;
  }

  public String getRightOKPO()
  {
    return rightOKPO;
  }

  public void setRightOKPO(String rightOKPO)
  {
    this.rightOKPO = rightOKPO;
  }

  public String getRightReputation()
  {
    return rightReputation;
  }

  public void setRightReputation(String rightReputation)
  {
    this.rightReputation = rightReputation;
  }

  public String getRightCountry()
  {
    return rightCountry;
  }

  public void setRightCountry(String rightCountry)
  {
    this.rightCountry = rightCountry;
  }

	public String getRightComment()
	{
		return rightComment;
	}

	public void setRightComment(String rightComment)
	{
		this.rightComment = rightComment;
	}
}