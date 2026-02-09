package net.sam.dcl.report.pdf;

import net.sam.dcl.form.NoticeForShippingPrintForm;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.ContactPerson;
import net.sam.dcl.beans.ShippingPosition;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.config.Config;

import java.util.Locale;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * Demonstrates the use of PageEvents.
 */
public class NoticeForShippingPDF extends DocumentTemplatePDF
{
  NoticeForShippingPrintForm form = null;
  Blank blank = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public NoticeForShippingPDF(OutputStream outStream, Blank blank, NoticeForShippingPrintForm form)
  {
    super(outStream, blank.getBln_charset());
    headerLeftMargin = headerRightMargin = 0;
    topMargin = bottomMargin = 15;
    this.blank = blank;
    this.form = form;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    firstPagePagesCountFont = getFontArial11Normal();
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead();
    processGrid(width);
    processFooter(width);
  }

  private void processFooter(float width) throws DocumentException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{1f});

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.sign"), getFontArial10Normal(), 1));

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processGrid(float width) throws DocumentException
  {
    PdfPTable table = new PdfPTable(5);
    table.setSplitLate(true);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{0.05f, 0.6f, 0.15f, 0.08f, 0.12f});
    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.num"), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER,
            PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.NoticeForShipping.article_name"), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.NoticeForShipping.article_num"), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.NoticeForShipping.unit"), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.NoticeForShipping.count"), getFontArial10Bold(), 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

    for (int i = 0; i < form.getProduces().size(); i++)
    {
      ShippingPosition produce = (ShippingPosition) form.getProduces().get(i);

      table.addCell(cell(String.valueOf(i + 1), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(produce.getProduce().getProduce().getFullName(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(produce.getProduce().getCatalogNumberForStuffCategory(), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(produce.getProduce().getProduce().getUnit().getName(), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      table.addCell(cell(produce.getCountFormatted(), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
    }
    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processHead() throws DocumentException, IOException
  {
    Contractor contractor = form.getContractorWhere();
    PdfPTable table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.1f, 0.5f, 0.4f});
    table.getDefaultCell().setBorder(noBorder);
    table.getDefaultCell().setLeading(0, defRelLeading);

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.where"), getFontArial10Normal(), 1));
    table.addCell(cell(contractor.getFullname() + "\n" + contractor.getAddress() + "\n", getFontArial10Normal(), 1));

    File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
    Image logoImage = Image.getInstance(file.getAbsolutePath());
    logoImage.scaleToFit((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) * 0.4f, 100000);
    logoImage.setBorder(PdfPCell.BOX);
    logoImage.enableBorderSide(PdfPCell.BOX);
    PdfPCell logoCell = new PdfPCell(logoImage, false);
    logoCell.setPaddingTop(-spaceAfterHeader);
    logoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    logoCell.setBorder(noBorder);
    table.addCell(logoCell);

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= table.getTotalHeight();

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.getDefaultCell().setLeading(0, defRelLeading);

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.title"), getFontArial10Bold(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= table.getTotalHeight();

    table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.1f, 0.7f, 0.2f});
    table.getDefaultCell().setBorder(noBorder);
    table.getDefaultCell().setLeading(0, defRelLeading);

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.contract"), getFontArial10Normal(), 1));
    table.addCell(cell(words.getMessage("rep.NoticeForShipping.num_date", form.getContractWhere().getCon_number(), form.getContractWhere().getCon_date_formatted()), getFontArial10Normal(), 1));

    PdfPTable dateTable = new PdfPTable(2);
    dateTable.setSplitLate(false);
    dateTable.setWidthPercentage(100);
    dateTable.setWidths(new float[]{0.08f, 0.12f});
    dateTable.getDefaultCell().setBorder(noBorder);
    dateTable.getDefaultCell().setLeading(0, defRelLeading);

    dateTable.addCell(cell(words.getMessage("rep.NoticeForShipping.date"), getFontArial10Normal(), 1));
    dateTable.addCell(cell(form.getShp_notice_date(), getFontArial10Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));

    table.addCell(dateTable);

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= table.getTotalHeight();

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.getDefaultCell().setLeading(0, defRelLeading);

    table.addCell(cell(words.getMessage("rep.NoticeForShipping.contractor", form.getContractor().getFullname()), getFontArial10Normal(), 1));

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= table.getTotalHeight();
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setWidths(new float[]{0.5f, 0.5f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    addImage(width, head, blank.getImage(Blank.topImg));

    head.setTotalWidth(width);
    return head;
  }

  protected PdfPTable generateFooter(float width) throws DocumentException, IOException
  {
    PdfPTable foot = new PdfPTable(2);
    foot.setWidths(new float[]{0.5f, 0.5f});
    foot.getDefaultCell().setBorder(noBorder);

    addImage(width, foot, blank.getImage(Blank.bottomImg));
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

  protected Phrase headerPhrase(Contractor contractor, com.lowagie.text.Font fontBold, com.lowagie.text.Font font)
  {
    Phrase phrase = new Phrase();
    phrase.add(new Phrase(contractor.getFullname() + "\n", fontBold));
    String tmp = "";
    if (contractor.getContactPersons().size() > 0)
    {
      ContactPerson person = contractor.getContactPersons().get(0);
      tmp = words.getMessage("rep.NoticeForShipping.phone_fax", person.getCps_phone(), person.getCps_fax());
    }
    phrase.add(new Phrase(
            contractor.getAddress() + "\n" +
                    tmp + words.getMessage("rep.NoticeForShipping.email") + contractor.getEmail(), font));
    return phrase;
  }

  protected Phrase footerPhrase(Contractor contractor, com.lowagie.text.Font fontBold, com.lowagie.text.Font font)
  {
    Phrase phrase = new Phrase();
    phrase.add(new Phrase(contractor.getFullname() + "\n", fontBold));
    phrase.add(new Phrase(words.getMessage("rep.NoticeForShipping.bank", contractor.getBank_props()), font));
    if (!StringUtil.isEmpty(contractor.getAccount1()))
    {
      phrase.add(new Phrase(contractor.getAccount1() + "\n", font));
    }
    if (!StringUtil.isEmpty(contractor.getAccount2()))
    {
      phrase.add(new Phrase(contractor.getAccount2() + "\n", font));
    }
    if (!StringUtil.isEmpty(contractor.getUnp()))
    {
      phrase.add(new Phrase(words.getMessage("rep.NoticeForShipping.unp", contractor.getUnp()), font));
    }
    return phrase;
  }

  public void printPages(PdfContentByte dc)
  {
    //writing Страница [current_page] из [XXX]
    String text = words.getMessage("rep.NoticeForShipping.page", Integer.toString(writer.getPageNumber()));
    float textSize = fontArial7P5Normal.getBaseFont().getWidthPoint(text, fontArial7P5Normal.getCalculatedSize());
    float textBase = bottomMargin + footer.getTotalHeight() + 3;
    dc.beginText();
    dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());

    float adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("000", fontArial7P5Normal.getCalculatedSize());
    dc.setTextMatrix(document.right() - textSize - adjust, textBase);
    dc.showText(text);
    dc.endText();
    addPagesCountTemplate(dc, document.right() - adjust, textBase);
  }

  public void printDocumentName(PdfContentByte dc)
  {
  }
}
