package net.sam.dcl.test;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.MontageAdjustment;

public class TestSelectForm extends BaseDispatchValidatorForm {
  String name1;
  String name2;
  String name3;
  String kp_id;
  String kp_name;
  String nomenclature_id;
  String nomenclature_name;
  MontageAdjustment montageAdjustment = new MontageAdjustment(); 


  public String getName1() {
    return name1;
  }
  public void setName1(String name1) {
    this.name1 = name1;
  }
  public String getName2() {
    return name2;
  }
  public void setName2(String name2) {
    this.name2 = name2;
  }
  public String getName3() {
    return name3;
  }
  public void setName3(String name3) {
    this.name3 = name3;
  }

	public String getKp_id() {
		return kp_id;
	}

	public void setKp_id(String kp_id) {
		this.kp_id = kp_id;
	}

	public String getKp_name() {
		return kp_name;
	}

	public void setKp_name(String kp_name) {
		this.kp_name = kp_name;
	}

	public String getNomenclature_id() {
		return nomenclature_id;
	}

	public void setNomenclature_id(String nomenclature_id) {
		this.nomenclature_id = nomenclature_id;
	}

	public String getNomenclature_name() {
		return nomenclature_name;
	}

	public void setNomenclature_name(String nomenclature_name) {
		this.nomenclature_name = nomenclature_name;
	}

  public MontageAdjustment getMontageAdjustment()
  {
    return montageAdjustment;
  }

  public void setMontageAdjustment(MontageAdjustment montageAdjustment)
  {
    this.montageAdjustment = montageAdjustment;
  }
}
