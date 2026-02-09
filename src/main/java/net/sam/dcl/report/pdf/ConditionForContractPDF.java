package net.sam.dcl.report.pdf;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.form.ConditionForContractPrintForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.ConditionForContractProduce;

import java.util.Locale;
import java.io.OutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

//test
/**
 * Demonstrates the use of PageEvents.
 */
public class ConditionForContractPDF extends DocumentTemplatePDF
{
  ConditionForContractPrintForm form = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public ConditionForContractPDF(OutputStream outStream, ConditionForContractPrintForm form)
  {
    super(outStream, "Cp1251");
    this.form = form;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead();
    processGrid(width);
    processFooter(width);
  }


  private void processFooter(float width) throws DocumentException
  {
  }

  private void processGrid(float width) throws DocumentException
  {
    if ( StringUtil.isEmpty(form.getSeller().getId()) || StringUtil.isEmpty(form.getCfc_doc_type()) )
    {
      return;
    }

    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.7f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial11Normal();
    if ( !StringUtil.isEmpty(form.getCreateUser().getUsr_id()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.create"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getUsr_date_create() + " " + form.getCreateUser().getUserFullName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    if ( !StringUtil.isEmpty(form.getEditUser().getUsr_id()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.edit"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getUsr_date_edit() + " " + form.getEditUser().getUserFullName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    if ( !StringUtil.isEmpty(form.getPlaceUser().getUsr_id()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.place"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getUsr_date_place() + " " + form.getPlaceUser().getUserFullName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    table.addCell(cell(words.getMessage("rep.ConditionForContract.seller"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getSeller().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.contractor"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getFullname(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.juridical_address"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getAddress(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.bank_address"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getBank_props(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.account1"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getAccountsBYNFormatted(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.account_val"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getAccountsOtherFormatted(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.properties"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContract.unp") + " " +
                       form.getContractor().getUnp() + " " +
                       words.getMessage("rep.ConditionForContract.okpo") + " " +
                       form.getContractor().getOkpo() + " " +
                       words.getMessage("rep.ConditionForContract.phone") + " " +
                       form.getContractor().getPhone() + " " +
                       words.getMessage("rep.ConditionForContract.fax") + " " +
                       form.getContractor().getFax(),
                       normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_doc_type"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    if ( "0".equals(form.getCfc_doc_type()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_doc_type1"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }
    if ( "1".equals(form.getCfc_doc_type()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_doc_type2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    if ( "0".equals(form.getCfc_doc_type()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_con_number_txt"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_con_number_txt(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_con_date"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_con_date(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.currency"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCurrency().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }
    
    if ( "1".equals(form.getCfc_doc_type()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.contract"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getContractNumberWithDate(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.spc_numbers"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getSpc_numbers(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_spc_number_txt"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_spc_number_txt(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_spc_date"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_spc_date(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ConditionForContract.currency"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCurrency().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    table.addCell(cell(words.getMessage("rep.ConditionForContract.purchasePurpose"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getPurchasePurpose().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_pay_cond"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCfc_pay_cond(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_delivery_cond"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCfc_delivery_cond(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    if (Constants.linteraSellerId.equals(form.getSeller().getId()))
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_custom_point"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_custom_point(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_guarantee_cond"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCfc_guarantee_cond(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_montage_cond"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCfc_montage_cond(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    if ( "0".equals(form.getCfc_doc_type()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_date_con_to"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(form.getCfc_date_con_to(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }

    table.addCell(cell(words.getMessage("rep.ConditionForContract.cfc_count_delivery"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCfc_count_delivery(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.contactPersonSign"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContactPersonSign().getCps_name(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.positionSign"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContactPersonSign().getCps_position(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.on_reasonSign"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContactPersonSign().getCps_on_reason(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.contactPerson"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContactPerson().getCps_name(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ConditionForContract.propertiesContactPerson"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContract.position") + " " +
                       (StringUtil.isEmpty(form.getContactPerson().getCps_position()) ? "" : form.getContactPerson().getCps_position() + " ") +
                       words.getMessage("rep.ConditionForContract.personPhone") + " " +
                       (StringUtil.isEmpty(form.getContactPerson().getCps_phone()) ? "" : form.getContactPerson().getCps_phone() + " ") +
                       words.getMessage("rep.ConditionForContract.personFax") + " " +
                       (StringUtil.isEmpty(form.getContactPerson().getCps_fax()) ? "" : form.getContactPerson().getCps_fax() + " ") +
                       words.getMessage("rep.ConditionForContract.personEmail") + " " +
                       (StringUtil.isEmpty(form.getContactPerson().getCps_email()) ? "" : form.getContactPerson().getCps_email()),
                       normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    document.newPage();

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.addCell(cell(words.getMessage("rep.ConditionForContract.specification"), getFontArial11Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.setSpacingAfter(defSpacing);
    document.add(table);

    if ( Constants.linteraSellerId.equals(form.getSeller().getId()) )
    {
      table = new PdfPTable(8);
    }
    if ( Constants.techServiceSellerId.equals(form.getSeller().getId()) )
    {
      table = new PdfPTable(11);
    }
    table.setSplitLate(true);
    table.setWidthPercentage(100);
    // 0 - ЗАО "Линтера", 1 - ИП "ЛинтераТехСервис"
    if ( Constants.linteraSellerId.equals(form.getSeller().getId()) )
    {
      table.setWidths(new float[]{0.05f, 0.25f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.15f});
    }
    if ( Constants.techServiceSellerId.equals(form.getSeller().getId()) )
    {
      table.setWidths(new float[]{0.05f, 0.2f, 0.1f, 0.1f, 0.05f, 0.05f, 0.1f, 0.05f, 0.1f, 0.1f, 0.1f});
    }
    table.getDefaultCell().setBorder(noBorder);

    Font tableFont = getFontArial8Bold();
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.number"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.produce_name"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.catalog_num"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_price"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_count"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.unit"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_cost"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if ( Constants.techServiceSellerId.equals(form.getSeller().getId()) )
    {
      table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_nds_rate"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_nds"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_nds_cost"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.commercial_proposal"), tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    tableFont = getFontArial8Normal();
    for (int i = 0; i < form.getProduces().size() - 1; i++)
    {
      ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getProduces().get(i);
      table.addCell(cell(conditionForContractProduce.getNumber(), tableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getProduceFullName(), tableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getCatalogNumberForStuffCategory(), tableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getCcp_price_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getCcp_count_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getUnitName(), tableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getCcp_cost_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      if ( Constants.techServiceSellerId.equals(form.getSeller().getId()) )
      {
        table.addCell(cell(conditionForContractProduce.getCcp_nds_rate_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(conditionForContractProduce.getCcp_nds_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(conditionForContractProduce.getCcp_nds_cost_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell(conditionForContractProduce.getCpr_number_date(), tableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    if (form.getProduces().size() != 0)
    {
      int colSpan = 5;
      ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getProduces().get(form.getProduces().size() - 1);
      table.addCell(cell("", tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.total"), tableFont, colSpan, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(conditionForContractProduce.getCcp_cost_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      if ( Constants.techServiceSellerId.equals(form.getSeller().getId()) )
      {
        table.addCell(cell("", tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(conditionForContractProduce.getCcp_nds_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(conditionForContractProduce.getCcp_nds_cost_formatted(), tableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell("", tableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processHead() throws DocumentException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.addCell(cell(words.getMessage("rep.ConditionForContract.title"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight());
  }


  public void printPages(PdfContentByte dc)
  {
  }

  public void printDocumentName(PdfContentByte dc)
  {
  }

  public void onCloseDocument(PdfWriter writer, Document document)
  {
  }
}
