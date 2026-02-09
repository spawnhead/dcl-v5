package net.sam.dcl.service.helper;

import net.sam.dcl.service.AttachmentFileRenamer;
import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpecificationFileRenamer implements AttachmentFileRenamer
{
  Contract contract;
  Specification specification;

  public SpecificationFileRenamer(Contract contract, Specification specification)
  {
    this.contract = contract;
    this.specification = specification;
  }

  public String rename(String original)
  {
    String extention = null;
    int idx = original.lastIndexOf('.');
    if (idx != -1)
    {
      extention = original.substring(idx);
    }
    SimpleDateFormat appFormat = new SimpleDateFormat("yyyy.MM.dd");
    String date = appFormat.format(StringUtil.getCurrentDateTime());

    String renamed = contract.getContractor().getName().trim() + "_" + date + "_" + contract.getCon_number().trim() + "_" + specification.getSpc_number().trim();
    if (extention != null)
    {
      renamed += extention;
    }
    return renamed;
  }
}