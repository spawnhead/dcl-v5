package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.config.Config;
import net.sam.dcl.form.ContractorRequestPrintLetterRequestForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Demonstrates the use of PageEvents.
 */
public class ContractorRequestLetterRequestPDF extends DocumentTemplatePDF
{
  ContractorRequestPrintLetterRequestForm form = null;
  Blank blank = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public ContractorRequestLetterRequestPDF(OutputStream outStream, ContractorRequestPrintLetterRequestForm form)
  {
    super(outStream, form.getBlank().getBln_charset());
    headerLeftMargin = 20;
    headerRightMargin = 0;
    topMargin =127;// =4.5 см
    bottomMargin = 15;
    leftMargin = 20;
    rightMargin = 50;
    this.blank = form.getBlank();
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
    Font normalFont = getFontArial9Normal();

    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.letterText1", form.getChief().getUserFullName(), form.getContract().getCon_number(), form.getContract().getCon_date_formatted()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.7f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.date"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.department_name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getContractor().getFullname(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.address"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.fio_phone"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.2f, 0.8f});

    //отступ перед таблицей
    table.addCell(cell("", normalFont, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.NO_BORDER));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.equipment1"), normalFont, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.NO_BORDER));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCrq_equipment(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.ctn_number"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getCtn_number(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.stf_name"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    table.addCell(cell(form.getStf_name(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});

    table.addCell(cell(words.getMessage("rep.ContractorRequest.letterText2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);


    table = new PdfPTable(4);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.05f, 0.3f, 0.35f});

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.position"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.letterFIO"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell("", normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 4, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));

    table.setSpacingAfter(defSpacing);
    document.add(table);

    
    table = new PdfPTable(5);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.3f, 0.05f, 0.25f, 0.1f, 0.3f});

    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM, PdfPCell.BOTTOM));

    table.addCell(cell(words.getMessage("rep.ContractorRequest.chief_position"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.letterSign"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell("", normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.letterSignDescription"), getFontArial5Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP));

    table.addCell(cell("", normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell("", normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));
    table.addCell(cell(words.getMessage("rep.ContractorRequest.stamp"), normalFont, 5, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processHead() throws DocumentException, IOException
  {
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setWidths(new float[]{0.5f, 0.5f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    addImage(width, head, blank.getImage(Blank.leftImg));

    head.setTotalWidth(width);
    return head;
  }

  protected void addImage(float width, PdfPTable head, String image) throws BadElementException, IOException
  {
    File file = new File(Config.getString("img-head.dir"), image);
    Image leftImage = Image.getInstance(file.getAbsolutePath());
    leftImage.scaleToFit((width - headerLeftMargin - headerRightMargin) * 0.4f, 100000);

    leftImage.setBorder(PdfPCell.NO_BORDER);
    PdfPCell imageCell = new PdfPCell(leftImage, false);
    imageCell.setBorder(noBorder);
    imageCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    head.addCell(imageCell);
    head.addCell("");
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