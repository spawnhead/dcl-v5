package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.ContractorRequestStage;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.RateNDSDAO;
import net.sam.dcl.form.ContractorRequestPrintActForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Demonstrates the use of PageEvents.
 */
public class ContractorRequestActPDF extends DocumentTemplatePDF
{
  protected static Log log = LogFactory.getLog(ContractorRequestActPDF.class);

  ContractorRequestPrintActForm form = null;
  Blank blank = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public ContractorRequestActPDF(OutputStream outStream, ContractorRequestPrintActForm form)
  {
    super(outStream, form.getBlank().getBln_charset());
    headerLeftMargin = headerRightMargin = 0;
    this.blank = form.getBlank();
    this.form = form;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead(width);
    if (form.isPNP())
    {
      processGridPNP(width);
    }
    else if (form.isPrintReclamationAct())
    {
      processGridReclamationAct(width);
    }
    else if (form.isPrintSellerRequest())
    {
      processGridSellerRequest(width);
    }
    else if (form.isPrintSellerAgreement())
    {
      processGridSellerAgreement(width);
    }
    else if (form.isPNPTimeSheet())
    {
      processPNPTimeSheet();
    }
    else
    {
      processGridGuarantyService(width);
    }

    processFooter(width);
  }


  private void processPNPTimeSheet() throws DocumentException
  {
    Font normalFont = getFontArial9Normal();
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.note2", form.getCrq_number()), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTableName") + " № " + form.getCrq_number() + "-" + form.getVisitNumber(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.page1from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));
    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(5);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.1f, 0.6f, 0.1f, 0.1f, 0.1f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    for (int i = 0; i < 43; i++)
    {
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTableName") + " № " + form.getCrq_number() + "-" + form.getVisitNumber(), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.page2from2"), normalFont, 5, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));
    table.setSpacingAfter(20);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    for (int i = 0; i < 3; i++)
    {
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    table.setSpacingAfter(defSpacing);
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardText1"), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    for (int i = 0; i < 6; i++)
    {
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    document.add(table);

    addChangeDetailsTable(normalFont, 11, "rep.ContractorRequest.changeDetailsCol3P", new float[]{0.73f, 0.07f, 0.12f, 0.08f});

    table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.3f, 0.4f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardText2"), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", getFontArial7Normal(), 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));
    for (int i = 0; i < 3; i++)
    {
      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
      table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

      table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

      table.addCell(cell(" ", getFontArial7Normal(), 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }

    document.add(table);


    table = new PdfPTable(5);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.2f, 0.15f, 0.15f, 0.2f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardBuyer"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell(" ", getFontArial7Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardStamp"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processFooter(float width) throws DocumentException
  {
  }

  private void processGridPNP(float width) throws DocumentException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial9Normal();
    table.addCell(cell(words.getMessage("rep.ContractorRequest.title", form.getCrq_number()), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.titleDate"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));

    Phrase phrase = new Phrase();

    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.contractor", form.getContractor().getFullname()) + "\r\n", normalFont));

    String contactPersonInfo = StringUtil.isEmpty(form.getContactPerson().getCps_position()) ? "" : form.getContactPerson().getCps_position() + " ";
    contactPersonInfo += form.getContactPerson().getCps_name();
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.contactPerson", contactPersonInfo) + "\r\n", normalFont));

    String phoneInfo = StringUtil.isEmpty(form.getContractor().getPhone()) ? "" : form.getContractor().getPhone();
    phoneInfo += (!StringUtil.isEmpty(form.getContactPerson().getCps_phone()) & !StringUtil.isEmpty(form.getContactPerson().getCps_phone())) ? ", " + form.getContactPerson().getCps_phone() : "";
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.phone", phoneInfo) + "\r\n", normalFont));

    String faxInfo = StringUtil.isEmpty(form.getContractor().getFax()) ? "" : form.getContractor().getFax();
    faxInfo += (!StringUtil.isEmpty(form.getContactPerson().getCps_fax()) & !StringUtil.isEmpty(form.getContactPerson().getCps_fax())) ? ", " + form.getContactPerson().getCps_fax() : "";
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.fax", faxInfo) + "\r\n", normalFont));

    String eMailInfo = StringUtil.isEmpty(form.getContractor().getEmail()) ? "" : form.getContractor().getEmail();
    eMailInfo += (!StringUtil.isEmpty(form.getContactPerson().getCps_email()) & !StringUtil.isEmpty(form.getContactPerson().getCps_email())) ? ", " + form.getContactPerson().getCps_email() : "";
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.eMail", eMailInfo), normalFont));

    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    phrase = new Phrase();

    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.seller", form.getCrq_seller()) + "\r\n", normalFont));
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.manager", form.getManager().getUserFullName()) + "\r\n", normalFont));
    phrase.add(new Chunk(words.getMessage("rep.ContractorRequest.specialist", form.getSpecialist().getUserFullName()), normalFont));

    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.text1", form.getCrq_seller(), form.getContractor().getFullname(), form.getContract().getCon_number(), form.getContract().getCon_date_formatted()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.equipment"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.2f, 0.8f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCrq_equipment(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.ctn_number"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCtn_number(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.stf_name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getStf_name(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.lps_serial_num"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_serial_num(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.lps_year_out"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_year_out(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.text2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.city", form.getCrq_city()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.33f, 0.67f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.signSellet1"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.signSellet2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.signSellet3"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.signBuyer1"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.signBuyer2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.signBuyer3"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    document.newPage();

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.note1", form.getCrq_number()), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.text3"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.chief", form.getChief().getUserFullName()), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.chiefDate"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(4);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.6f, 0.1f, 0.15f, 0.15f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.stagesTitle"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.stagesDate"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    PdfPTable tableInner = new PdfPTable(2);
    tableInner.setSplitLate(false);
    tableInner.setWidthPercentage(100);
    tableInner.setWidths(new float[]{0.5f, 0.5f});
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.stagesFIOSign"), normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.stagesSpecSeller"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.stagesSpecBuyer"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    table.addCell(cell(tableInner, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    for (int i = 0; i < form.getStages().size(); i++)
    {
      ContractorRequestStage contractorRequestStage = (ContractorRequestStage) form.getStages().get(i);
      if (StringUtil.isEmpty(contractorRequestStage.getNeedPrint()))
      {
        continue;
      }

      phrase = new Phrase();
      phrase.add(new Chunk(contractorRequestStage.getName() + "\r\n", getFontArial6Bold()));
      phrase.add(new Chunk(contractorRequestStage.getCommentPDF(), getFontArial6Normal()));
      table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    tableInner = new PdfPTable(5);
    tableInner.setSplitLate(false);
    tableInner.setWidthPercentage(100);
    tableInner.setWidths(new float[]{0.3f, 0.19f, 0.02f, 0.3f, 0.19f});
    tableInner.getDefaultCell().setBorder(PdfPCell.BOX);

    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.text4"), getFontArial9Bold(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.TOP + PdfPCell.RIGHT + PdfPCell.LEFT));

    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.FIO"), getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT));
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.sign"), getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    tableInner.addCell(cell("", getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.FIO"), getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    tableInner.addCell(cell(words.getMessage("rep.ContractorRequest.sign"), getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.RIGHT));

    for (int i = 0; i < 4; i++)
    {
      tableInner.addCell(cell("", getFontArial9Normal(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.RIGHT + PdfPCell.LEFT));
      tableInner.addCell(cell("", getFontArial9Normal(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.RIGHT + PdfPCell.LEFT));

      tableInner.addCell(cell("", getFontArial9Normal(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM + PdfPCell.LEFT));
      tableInner.addCell(cell("", getFontArial9Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
      tableInner.addCell(cell("", getFontArial9Normal(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM + PdfPCell.RIGHT));
    }
    tableInner.addCell(cell("", getFontArial9Normal(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.RIGHT + PdfPCell.LEFT));
    tableInner.addCell(cell("", getFontArial9Normal(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM + PdfPCell.RIGHT + PdfPCell.LEFT));


    table.addCell(cell(tableInner, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.text5"), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.notes"), getFontArial9Normal(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    for (int i = 0; i < 5; i++)
    {
      table.addCell(cell("", getFontArial9Normal(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
      table.addCell(cell("", getFontArial9Normal(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

  }

  private PdfPTable formTopWithRightImage(float width) throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.6f, 0.4f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial7Normal();
    if (form.isPrintEnumerationWork())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.consumer"), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.customer"), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }
    table.addCell(cell(words.getMessage("rep.ContractorRequest.executor"), getFontArial9Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));

    PdfPTable innerTable = new PdfPTable(1);
    innerTable.setSplitLate(false);
    innerTable.setWidthPercentage(100);
    innerTable.setWidths(new float[]{1f});
    innerTable.getDefaultCell().setBorder(noBorder);

    innerTable.addCell(cell(form.getContractor().getFullname(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.contractorAddress", form.getContractor().getAddress()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.phone", form.getContractor().getPhone()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.fax", form.getContractor().getFax()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.eMail", form.getContractor().getEmail()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));

    table.addCell(cell(innerTable, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, noBorder));

    innerTable = new PdfPTable(1);
    innerTable.setSplitLate(false);
    innerTable.setWidthPercentage(100);
    innerTable.setWidths(new float[]{1f});
    innerTable.getDefaultCell().setBorder(noBorder);
    File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
    Image rightImage = Image.getInstance(file.getAbsolutePath());

    rightImage.scaleToFit(width * 0.3f, 100000);
    PdfPCell topCell = new PdfPCell(rightImage, false);
    topCell.setBorder(PdfPCell.NO_BORDER);
    topCell.setPaddingTop(1);
    innerTable.addCell(topCell);

    table.addCell(innerTable);

    table.setSpacingAfter(defSpacing);
    return table;
  }

  private PdfPTable formTableEquipment(String equipmentStr, String customerOrConsumerStr) throws DocumentException, IOException
  {
    Font normalFont = getFontArial6Normal();
    PdfPTable table = new PdfPTable(6);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.2f, 0.1f, 0.2f, 0.15f, 0.2f, 0.15f});
    table.addCell(cell(equipmentStr, getFontArial6Bold(), 6, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCrq_equipment(), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.stf_name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getStf_name(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.ctn_number"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCtn_number(), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.lps_serial_num"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_serial_num(), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.lps_year_out"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_year_out(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.shp_date"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getShp_date(), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.lps_enter_in_use_date"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_year_out(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.crq_operating_time"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCrq_operating_time(), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.con_spc_number_date"), normalFont, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCon_spc_number_date(), normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.con_seller"), normalFont, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCon_seller(), normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    if (customerOrConsumerStr != null)
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.equipmentInfoIsCorrect"), getFontArial6Bold(), 6, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
      table.addCell(cell(customerOrConsumerStr, normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
      table.addCell(cell("", normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM));
      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.signCustomerOrConsumer"), getFontArial5Normal(), 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    }

    table.setSpacingAfter(defSpacing);
    return table;
  }

  private void formBottomWithStampSign(String varText) throws DocumentException
  {
    Font normalFont = getFontArial6Normal();
    PdfPTable table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.2f, 0.4f, 0.4f});

    for (int i = 0; i < 1/*3*/; i++)
    {
      table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

      table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(5);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.1f, 0.1f, 0.15f, 0.15f});

    table.addCell(cell(varText, normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardStamp"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processGridGuarantyService(float width) throws DocumentException, IOException
  {
    document.add(formTopWithRightImage(width));
    document.add(formTableEquipment(words.getMessage("rep.ContractorRequest.equipment"), form.isService() ? words.getMessage("rep.ContractorRequest.customerLower") : words.getMessage("rep.ContractorRequest.consumerLower")));

    Font normalFont = getFontArial6Normal();

    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    if (form.isPrintEnumerationWork())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.enumerationWorkTitle", form.getCrq_number() + "-" + form.getVisitId()), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.actTitle1", form.getCrq_number() + "-" + form.getVisitId()), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      if (form.isService())
        table.addCell(cell(words.getMessage("rep.ContractorRequest.byContract", form.getContractForWork().getCon_number(), form.getContractForWork().getCon_date_formatted()), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

    if (form.isPrintEnumerationWork())
    {
      table = new PdfPTable(1);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{1f});
      table.getDefaultCell().setBorder(noBorder);

      table.addCell(cell(words.getMessage("rep.ContractorRequest.page1from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));

      table.setSpacingAfter(defSpacing);
      document.add(table);

      table = new PdfPTable(5);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.71f, 0.06f, 0.07f, 0.06f});

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1Span2"), getFontArial6Bold(), 2, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(form.getCrq_ticket_number(), getFontArial6Bold(), 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2Mod"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      for (int i = 0; i < 31; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
    }
    else
    {
      if (form.isService())
      {
        table = new PdfPTable(2);
        table.setSplitLate(false);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.6f, 0.4f});
        table.getDefaultCell().setBorder(noBorder);

        table.addCell(cell(words.getMessage("rep.ContractorRequest.actNote"), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, noBorder, 1));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.page1from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));

        table.setSpacingAfter(defSpacing);
        document.add(table);
      }
      else
      {
        table = new PdfPTable(1);
        table.setSplitLate(false);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f});
        table.getDefaultCell().setBorder(noBorder);

        table.addCell(cell(words.getMessage("rep.ContractorRequest.page1from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));

        table.setSpacingAfter(defSpacing);
        document.add(table);
      }

      table = new PdfPTable(7);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.51f, 0.06f, 0.07f, 0.06f, 0.08f, 0.12f});

      int rowCount = 31; //количество строк в основной таблице
      if (form.isService())
        rowCount -= 2;

      if (!form.isService())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1Span1"), getFontArial6Bold(), 2, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(form.getCrq_ticket_number(), getFontArial6Bold(), 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell("", getFontArial6Bold(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX, 5, Color.lightGray));
      }

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol6"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol7"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      for (int i = 0; i < rowCount; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

    document.newPage();

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    if (form.isPrintEnumerationWork())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.enumerationWorkTitle", form.getCrq_number() + "-" + form.getVisitId()), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.actTitle1", form.getCrq_number() + "-" + form.getVisitId()), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      if (form.isService())
        table.addCell(cell(words.getMessage("rep.ContractorRequest.byContract", form.getContractForWork().getCon_number(), form.getContractForWork().getCon_date_formatted()), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }
    table.addCell(cell(words.getMessage("rep.ContractorRequest.page2from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    if (form.isPrintEnumerationWork())
    {
      table = new PdfPTable(5);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.71f, 0.06f, 0.07f, 0.06f});

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2Mod"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      for (int i = 0; i < 13; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardText1"), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      for (int i = 0; i < 7; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      table.addCell(cell(" ", normalFont, 7, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
      document.add(table);

      addChangeDetailsTable(normalFont, 6, "rep.ContractorRequest.changeDetailsCol3G", new float[]{0.805f, 0.045f, 0.09f, 0.06f});

      table = new PdfPTable(5);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.71f, 0.06f, 0.07f, 0.06f});

      table.addCell(cell(" ", normalFont, 5, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.consumerNotes"), normalFont, 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

      for (int i = 0; i < 8; i++)
      {
        table.addCell(cell(" ", normalFont, 5, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }
      table.addCell(cell(" ", normalFont, 5, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
    }
    else
    {
      table = new PdfPTable(7);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.51f, 0.06f, 0.07f, 0.06f, 0.08f, 0.12f});

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol6"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol7"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      int rowCount = 15; //количество строк
      if (form.isService())
        rowCount -= 10;

      for (int i = 0; i < rowCount; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardText1"), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.LEFT));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTextX"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTextX"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol2_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol3_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol4_1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol5"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol6"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardCol7"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      for (int i = 0; i < 4; i++)
      {
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTimeInCell"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOX, 2));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      PdfPTable parentTable = new PdfPTable(1);
      if (form.isService())
      {
        document.add(table);

        parentTable.setSplitLate(false);
        parentTable.setWidthPercentage(100);
        parentTable.setWidths(new float[]{1f});
        parentTable.getDefaultCell().setBorder(PdfPCell.BOX);

        table = new PdfPTable(7);
        table.setSplitLate(false);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.1f, 0.51f, 0.06f, 0.07f, 0.06f, 0.08f, 0.12f});

        table.addCell(cell(words.getMessage("rep.ContractorRequest.sumOutNDSDiagnosis"), normalFont, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

        table.addCell(cell(words.getMessage("rep.ContractorRequest.sumOutNDSRepair"), normalFont, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

        table.addCell(cell(words.getMessage("rep.ContractorRequest.sumOutNDSVisit"), normalFont, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      table.addCell(cell(words.getMessage("rep.ContractorRequest.sumOutNDS"), normalFont, 6, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      IActionContext context = ActionContext.threadInstance();
      double ndsRate = 18;
      try
      {
        ndsRate = RateNDSDAO.loadForDate(context, form.getCrq_receive_date()).getPercent();
      }
      catch (Exception e)
      {
        log.error(e);
      }
      table.addCell(cell(words.getMessage("rep.ContractorRequest.sumNDS", StringUtil.double2appCurrencyStringWithoutDec(ndsRate)), normalFont, 6, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.sumWithNDS"), normalFont, 6, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      if (form.isService())
      {
        table.setSpacingAfter(2);
        parentTable.addCell(cell(table, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX, 1.2f));

        document.add(parentTable);

        table = new PdfPTable(7);
        table.setSplitLate(false);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.1f, 0.51f, 0.06f, 0.07f, 0.06f, 0.08f, 0.12f});

        table.addCell(cell("  ", getFontArial3Normal(), 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.TOP, 1f));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.sumWithNDSInWords"), getFontArial5Normal(), 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
        table.addCell(cell("  ", normalFont, 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }

      table.addCell(cell(" ", normalFont, 7, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));

      document.add(table);

      addChangeDetailsTable(normalFont, 6, "rep.ContractorRequest.changeDetailsCol3S", new float[]{0.805f, 0.045f, 0.09f, 0.06f});

      table = new PdfPTable(7);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.1f, 0.51f, 0.06f, 0.07f, 0.06f, 0.08f, 0.12f});

      table.addCell(cell(" ", normalFont, 7, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.customerNotes"), normalFont, 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

      for (int i = 0; i < 8; i++)
      {
        table.addCell(cell(" ", normalFont, 7, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }

      if (!form.isService())
      {
        table.addCell(cell(" ", normalFont, 7, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.executorWorkMade"), normalFont, 7, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));
      }
    }

    document.add(table);

    table = new PdfPTable(4);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.4f, 0.2f, 0.2f, 0.2f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTpExecutor"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    if (form.isService())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardTpCustomer"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.receiverExtended"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    if (form.isService())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardWorkExecuted"), getFontArial6Bold(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardWorkExecutedG"), getFontArial6Bold(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

    table = new PdfPTable(9);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.12f, 0.12f, 0.12f, 0.12f, 0.04f, 0.12f, 0.12f, 0.12f, 0.12f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.executor"), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    if (form.isService())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.customer"), normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.customerG"), normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    }

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardStamp"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardStamp"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.dateTemplate"), normalFont, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.dateTemplate"), normalFont, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    if ((form.isPrintEnumerationWork() || form.isPrintExecutedWorkAct()) && (form.isNeedDefectAct() || form.isNeedReclamationAct()))
    {
      document.newPage();

      table = new PdfPTable(1);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{1f});
      table.getDefaultCell().setBorder(noBorder);

      if (form.isNeedDefectAct() && form.isPrintEnumerationWork())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actWorkTitleEnumeration", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actWorkTitleAct", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      if (form.isNeedReclamationAct())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actReclamationTitle1", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actReclamationTitle2", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      table.addCell(cell(words.getMessage("rep.ContractorRequest.page1from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));

      table.setSpacingAfter(defSpacing);
      document.add(table);

      document.add(formTopWithRightImage(width));
      if (form.isNeedDefectAct())
      {
        document.add(formTableEquipment(words.getMessage("rep.ContractorRequest.equipment"), null));
      }
      if (form.isNeedReclamationAct())
      {
        document.add(formTableEquipment(words.getMessage("rep.ContractorRequest.equipmentExamine"), null));
      }

      table = new PdfPTable(1);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{1f});
      table.getDefaultCell().setBorder(noBorder);

      int lineCount = 17;
      if (form.isNeedReclamationAct())
      {
        lineCount = 16;
      }

      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        lineCount = 11;
      }

      table.addCell(cell(words.getMessage("rep.ContractorRequest.explCondition"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
      for (int i = 0; i < lineCount; i++)
      {
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }

      table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.defectDescription"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
      for (int i = 0; i < lineCount; i++)
      {
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }

      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.expectedDefect"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
        for (int i = 0; i < lineCount; i++)
        {
          table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
        }
      }

      table.setSpacingAfter(defSpacing);
      document.add(table);


      document.newPage();

      table = new PdfPTable(1);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{1f});
      table.getDefaultCell().setBorder(noBorder);

      if (form.isNeedDefectAct() && form.isPrintEnumerationWork())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actWorkTitleEnumeration", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actWorkTitleAct", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      if (form.isNeedReclamationAct())
      {
        table.addCell(cell(words.getMessage("rep.ContractorRequest.actReclamationTitle1", form.getCrq_number() + "-" + form.getVisitId()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      }
      table.addCell(cell(words.getMessage("rep.ContractorRequest.page2from2"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, noBorder, 1));


      lineCount = 15;
      if (form.isNeedReclamationAct())
      {
        lineCount = 12;
      }

      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        lineCount = 18;
      }

      if (form.isNeedDefectAct() && form.isPrintEnumerationWork())
      {
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.expectedDefect"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
        for (int i = 0; i < lineCount; i++)
        {
          table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
        }
      }

      table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.conclusion"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
      for (int i = 0; i < lineCount; i++)
      {
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
      }

      table.setSpacingAfter(defSpacing);
      document.add(table);


      table = new PdfPTable(2);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.8f, 0.1f});
      table.getDefaultCell().setBorder(noBorder);

      table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsMod"), getFontArial6Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

      table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

      int detailsCount = 10;
      if (form.isNeedDefectAct() && form.isPrintExecutedWorkAct())
      {
        detailsCount = 25;
      }

      for (int i = 1; i < detailsCount + 1; i++)
      {
        table.addCell(cell(Integer.toString(i), getFontArial6Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX, 3));
        table.addCell(cell(" ", getFontArial6Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }

      if (form.isNeedDefectAct())
      {
        table.addCell(cell(" ", getFontArial6Normal(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.executorMember"), normalFont, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

        table.setSpacingAfter(defSpacing);
        document.add(table);

        formBottomWithStampSign(words.getMessage("rep.ContractorRequest.consumerMember"));
      }

      if (form.isNeedReclamationAct())
      {
        table.addCell(cell(" ", getFontArial4Normal(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
        table.addCell(cell(words.getMessage("rep.ContractorRequest.signComission"), getFontArial6Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

        table.setSpacingAfter(defSpacing);
        document.add(table);

        addSignComissionTable();
      }

    }
  }

  private void addChangeDetailsTable(Font normalFont, int rowCount, String messageKey, float[] widths) throws DocumentException
  {
    PdfPTable changeDetails = new PdfPTable(4);
    changeDetails.setSplitLate(false);
    changeDetails.setWidthPercentage(100);
    changeDetails.setWidths(widths);
    changeDetails.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetails"), getFontArial6Bold(), 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.NO_BORDER));

    PdfPCell changeDetailsColumn1 = cell(words.getMessage("rep.ContractorRequest.changeDetailsCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX);
    changeDetailsColumn1.setRowspan(2);
    changeDetails.addCell(changeDetailsColumn1);
    PdfPCell changeDetailsColumn2 = cell(words.getMessage("rep.ContractorRequest.changeDetailsCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX);
    changeDetailsColumn2.setRowspan(2);
    changeDetails.addCell(changeDetailsColumn2);
    changeDetails.addCell(cell(words.getMessage(messageKey), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    changeDetails.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsCol4"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    PdfPCell changeDetailsColumn34Description = cell(words.getMessage("rep.ContractorRequest.changeDetailsCol34Description"), normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX);
    changeDetails.addCell(changeDetailsColumn34Description);
    for (int i = 1; i < rowCount; i++)
    {
      changeDetails.addCell(cell(Integer.toString(i), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      changeDetails.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      changeDetails.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      changeDetails.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    changeDetails.addCell(cell("", normalFont, 4, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.TOP));
    document.add(changeDetails);
  }

  private void addSignComissionTable() throws DocumentException, IOException
  {
    Font normalFont = getFontArial6Normal();

    PdfPTable table = new PdfPTable(5);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.1f, 0.15f, 0.25f, 0.3f, 0.2f});

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.presidentComission"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.membersComission"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.presidentLintera", form.getCrq_seller()), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));

    table.addCell(cell(" ", normalFont, 5, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

    table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));


    table.addCell(cell(words.getMessage("rep.ContractorRequest.stamp"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.contractorMember", form.getContractor().getFullname()), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));

    for (int i = 0; i < 2; i++)
    {
      table.addCell(cell(" ", normalFont, 5, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

      table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
      table.addCell(cell("", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));

      table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardPosition"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
      table.addCell(cell(words.getMessage("rep.ContractorRequest.timeboardSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processGridReclamationAct(float width) throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(6);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.05f, 0.15f, 0.05f, 0.15f, 0.05f, 0.55f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial6Normal();

    table.addCell(cell(words.getMessage("rep.ContractorRequest.titleReclamation", form.getCrq_number() + "-" + form.getVisitId(), form.getCrq_reclamation_date()), getFontArial10Bold(), 6, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(form.getContractor().getFullname(), getFontArial6Bold(), 6, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.addressOnly"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(form.getContractor().getAddress(), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.phoneOnly"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(form.getContractor().getPhone(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.faxOnly"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(form.getContractor().getFax(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.eMailOnly"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(form.getContractor().getEmail(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    document.add(formTableEquipment(words.getMessage("rep.ContractorRequest.equipmentExamine"), null));

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.explCondition"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
    for (int i = 0; i < 5; i++)
    {
      table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    }

    table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.defectDescription"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
    for (int i = 0; i < 5; i++)
    {
      table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    }

    table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.expectedDefect"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
    for (int i = 0; i < 5; i++)
    {
      table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    }

    table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.conclusionComission"), getFontArial6Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM, 1));
    for (int i = 0; i < 5; i++)
    {
      table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOTTOM));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.8f, 0.1f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsMod"), getFontArial6Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsCol1"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.changeDetailsCol2"), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    for (int i = 1; i < 6; i++)
    {
      table.addCell(cell(Integer.toString(i), getFontArial5Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX, 3));
      table.addCell(cell(" ", getFontArial4Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    table.addCell(cell(" ", getFontArial4Normal(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, noBorder));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.signComission"), getFontArial6Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, noBorder));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    addSignComissionTable();
  }

  private void processGridSellerRequest(float width) throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial10Normal();

    table.addCell(cell(words.getMessage("rep.ContractorRequest.titleSellerRequest"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.titleSellerRequest1", form.getCrq_number() + "-" + form.getVisitId(), form.getCrq_lintera_request_date()), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestText", form.getContractor().getFullname(), form.getContractor().getCountry().getName(), form.getContract().getNumberWithDate()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    table = new PdfPTable(6);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.34f, 0.19f, 0.15f, 0.12f, 0.08f, 0.12f});
    table.getDefaultCell().setBorder(PdfPCell.BOX);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol1"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol2"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol3"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol4"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol5"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestTableCol6"), getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(form.getCrq_equipment(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCtn_number(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getStf_name(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_serial_num(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_year_out(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getLps_enter_in_use_date(), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.6f, 0.25f, 0.15f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestText2", form.getCrq_number() + "-" + form.getVisitId()), normalFont, 3, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", normalFont, 3, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestDirector"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestDirectorName"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processGridSellerAgreement(float width) throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);

    Font normalFont = getFontArial10Normal();

    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerAgreementDate", form.getCrq_lintera_agreement_date()), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerAgreementConcerning", form.getCrq_number() + "-" + form.getVisitId(), form.getCrqSellerAgreementDateDialog()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerAgreementText", form.getCrq_number() + "-" + form.getVisitId(), form.getCrqSellerAgreementDateDialog()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    if (form.isNeedDetailReturn())
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerAgreementTextDetail1", form.getCrq_number() + "-" + form.getVisitId(), form.getCrqSellerAgreementDateDialog()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerAgreementTextDetail2", form.getCrq_number() + "-" + form.getVisitId(), form.getCrqSellerAgreementDateDialog()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

    table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.6f, 0.25f, 0.15f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestDirector"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
    table.addCell(cell(" ", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOTTOM));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestDirectorName"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processHead(float width) throws DocumentException, IOException
  {
    if (form.isPrintSellerRequest() || form.isPrintSellerAgreement())
    {
      PdfPTable table = new PdfPTable(2);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setWidths(new float[]{0.6f, 0.4f});
      table.getDefaultCell().setBorder(noBorder);

      PdfPTable innerTable = new PdfPTable(1);
      innerTable.setWidths(new float[]{1f});
      innerTable.getDefaultCell().setBorder(noBorder);
      innerTable.getDefaultCell().setLeading(0, defRelLeading);

      innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestHeaderWhere", form.getContractorWhere().getFullname()), getFontArial10Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
      innerTable.addCell(cell(form.getContractorWhere().getAddress(), getFontArial10Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
      innerTable.addCell(cell(words.getMessage("rep.ContractorRequest.sellerRequestHeaderFax", form.getContractorWhere().getFax()), getFontArial10Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

      table.addCell(innerTable);

      File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
      Image logoImage = Image.getInstance(file.getAbsolutePath());
      logoImage.scaleToFit((width - document.leftMargin() - document.rightMargin()) * 0.4f, 100000);
      logoImage.setBorder(PdfPCell.BOX);
      logoImage.enableBorderSide(PdfPCell.BOX);
      PdfPCell logoCell = new PdfPCell(logoImage, false);
      logoCell.setPaddingTop(-spaceAfterHeader);
      logoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
      logoCell.setBorder(noBorder);
      table.addCell(logoCell);
      table.setSpacingAfter(defSpacing);
      document.add(table);
      calcHeight -= (table.getTotalHeight() + defSpacing);
    }
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setWidths(new float[]{0.5f, 0.5f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    if (!form.isPrintReclamationAct())
    {
      addImage(width, head, blank.getImage(Blank.topImg));
    }

    head.setTotalWidth(width);
    return head;
  }

  protected PdfPTable generateFooter(float width) throws DocumentException, IOException
  {
    if (form.isPrintSellerRequest() || form.isPrintSellerAgreement())
    {
      PdfPTable foot = new PdfPTable(2);
      foot.setWidths(new float[]{0.5f, 0.5f});
      foot.getDefaultCell().setBorder(noBorder);

      addImage(width, foot, blank.getImage(Blank.bottomImg));
      foot.setTotalWidth(width);
      return foot;
    }

    PdfPTable foot = new PdfPTable(1);
    foot.setWidths(new float[]{1f});
    foot.getDefaultCell().setBorder(noBorder);
    foot.setTotalWidth(width);
    return foot;
  }

  protected void addImage(float width, PdfPTable head, String image) throws BadElementException, IOException
  {
    File file = new File(Config.getString("img-head.dir"), image);
    Image topImage = Image.getInstance(file.getAbsolutePath());

    topImage.scaleToFit(width, 100000);
    topImage.setBorder(PdfPCell.BOX);
    PdfPCell topCell = new PdfPCell(topImage, false);
    topCell.setBorder(noBorder);

    topCell.setColspan(2);
    head.addCell(topCell);
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