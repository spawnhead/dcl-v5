package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.User;
import net.sam.dcl.config.Config;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.action.NomenclatureProduceActionBean;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboAttachment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Demonstrates the use of PageEvents.
 */
public class TechnicalDescriptionPDF extends DocumentTemplatePDF
{
  NomenclatureProduceActionBean nomenclatureProduce = null;
  Blank blank;
  String docName = "";
  User user = new User();
  DboAttachment imgAttachment;
  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public TechnicalDescriptionPDF(OutputStream outStream, Blank blank, NomenclatureProduceActionBean nomenclatureProduce, User curUser, DboAttachment imgAttachment)
  {
    super(outStream, blank.getBln_charset());

    this.docName = words.getMessage("rep.TechnicalDescription.number", "", "");
    this.blank = blank;
    this.nomenclatureProduce = nomenclatureProduce;
    this.user = curUser;
    this.imgAttachment = imgAttachment;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    firstPagePagesCountFont = getFontArial11Normal();
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead();
    processGrid(width);
  }

  private void processGrid(float width) throws DocumentException
  {
    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{0.4f, 0.6f});

    DboProduce produce = nomenclatureProduce.getProduce();
    table.addCell(cell(words.getMessage("rep.TechnicalDescription.name"), getFontArial11Bold(), 1));
    String name = produce.getName();
    if (nomenclatureProduce.getLanguageTranslations().size() > 0)
    {
      name = nomenclatureProduce.getLanguageTranslations().get(0).getText();
    }
    table.addCell(cell(name + " " + produce.getType() + " " + produce.getParams() + " " + produce.getAddParams(), getFontArial11Normal(), 1));
    document.add(table);

    if (nomenclatureProduce.getCatalogNumberRows().size() != 0)
    {
      table = new PdfPTable(2);
      table.setSplitLate(false);
      table.setWidthPercentage(100);
      table.setTotalWidth(width);
      table.setWidths(new float[]{0.5f, 0.5f});
      table.addCell(cell(words.getMessage("rep.TechnicalDescription.stuffCategory"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.TechnicalDescription.catalogNumber"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
      for (NomenclatureProduceActionBean.CatalogNumberRow catalogNumber : nomenclatureProduce.getCatalogNumberRows())
      {
        table.addCell(cell(catalogNumber.getCatalogNumber().getStuffCategory().getName(), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
        table.addCell(cell(catalogNumber.getCatalogNumber().getNumber(), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
      }
      document.add(table);
    }

    table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{0.4f, 0.6f});
    table.addCell(cell(words.getMessage("rep.TechnicalDescription.photo"), getFontArial11Bold(), 1));
    if (imgAttachment != null)
    {
      try
      {
        Image topImage = Image.getInstance(imgAttachment.getContent());

        float imageWidth = width - leftMargin - rightMargin;
        int imageHeight = 550;
        if (topImage.getWidth() * 0.6f > imageWidth || topImage.getHeight() * 0.6f > imageHeight)
        {
          topImage.scaleToFit(imageWidth, imageHeight);
        }
        else
        {
          topImage.scaleToFit(topImage.getWidth() * 0.6f, topImage.getHeight() * 0.6f);
        }
        PdfPCell tmp = new PdfPCell(topImage, false);
        tmp.setBorder(PdfPCell.NO_BORDER);
        tmp.setColspan(2);
        table.addCell(cell("", getFontArial11Bold(), 1));
        table.addCell(tmp);
      }
      catch (IOException e)
      {
        table.addCell(cell(e.getLocalizedMessage(), getFontArial11Bold(), 1));
      }
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.TechnicalDescription.photo.included"), getFontArial11Normal(), 1));
    }


    document.add(table);

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{1});

    table.addCell(cell(words.getMessage("rep.TechnicalDescription.material"), getFontArial11Bold(), 1));
    table.addCell(cell(produce.getMaterial(), getFontArial11Normal(), 1));

    table.addCell(cell(words.getMessage("rep.TechnicalDescription.purpose"), getFontArial11Bold(), 1));
    table.addCell(cell(produce.getPurpose(), getFontArial11Normal(), 1));

    table.addCell(cell(words.getMessage("rep.TechnicalDescription.specification"), getFontArial11Bold(), 1));
    table.addCell(cell(produce.getSpecification(), getFontArial11Normal(), 1));

    table.addCell(cell(words.getMessage("rep.TechnicalDescription.principal"), getFontArial11Bold(), 1));
    table.addCell(cell(produce.getPrinciple(), getFontArial11Normal(), 1));

    table.setSpacingAfter(50);


    document.add(table);


    //PdfPCell tmp;
    AutoWidthsTable autoWidthsTable = new AutoWidthsTable(3, new float[]{0.0f, 100f, 0.0f});
    autoWidthsTable.setSplitLate(false);
    autoWidthsTable.setWidthPercentage(100);
    autoWidthsTable.setTotalWidth(width);

    autoWidthsTable.setWidths(new float[]{0.5f, 0.2f, 0.3f});
    autoWidthsTable.getDefaultCell().setBorder(noBorder);

    autoWidthsTable.addCell(cell(user.getUsr_position(), getFontArial11Normal(), 1));

    PdfPCell tmp = new PdfPCell();
    tmp.setBorderWidthBottom(1);
    tmp.setBorderColorBottom(Color.black);
    tmp.setBorder(PdfPCell.BOTTOM);
    autoWidthsTable.addCell(tmp);

    autoWidthsTable.addCell(cell(user.getUsr_surname() + " " + user.getUsr_name(), getFontArial11Normal(), 1));


    autoWidthsTable.setSpacingAfter(20);
    autoWidthsTable.recalcWidths();
    document.add(autoWidthsTable);

  }


  private void processHead() throws DocumentException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.addCell(cell(words.getMessage("rep.TechnicalDescription.title"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight());
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setWidths(new float[]{0.6f, 0.4f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    addHeaderImage(width, head);


    PdfPCell leftCell = new PdfPCell();
    leftCell.setLeading(0f, defRelLeading);
    leftCell.setBorder(PdfPCell.NO_BORDER);

//    leftCell.setBorderColor(Color.green);
    leftCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    head.addCell(leftCell);

    addRightImage(width * 0.4f, head);

    head.setTotalWidth(width);
    return head;
  }

  protected PdfPTable generateFooter(float width) throws DocumentException, IOException
  {
    PdfPTable foot = new PdfPTable(1);

    PdfPCell lc = new PdfPCell();
    lc.setFixedHeight(12);
    lc.setBorder(PdfPCell.TOP);
    lc.setBorderColor(Color.green);
    lc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    lc.setLeading(0f, defRelLeading);
    foot.addCell(lc);

    foot.setTotalWidth(width);
    return foot;
  }

  protected void addHeaderImage(float width, PdfPTable head) throws DocumentException, IOException
  {
    File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.topImg));
    Image topImage = Image.getInstance(file.getAbsolutePath());

    topImage.scaleToFit(width, 100000);
    PdfPCell topCell = new PdfPCell(topImage, false);
    topCell.setBorder(PdfPCell.NO_BORDER);

    topCell.setColspan(2);
    head.addCell(topCell);
  }

  protected void addRightImage(float width, PdfPTable head) throws DocumentException, IOException
  {
    File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
    Image topImage = Image.getInstance(file.getAbsolutePath());

    topImage.scaleToFit(width, 100000);
    PdfPCell topCell = new PdfPCell(topImage, false);
    topCell.setBorder(PdfPCell.NO_BORDER);
    topCell.setPaddingTop(1);
    head.addCell(topCell);
  }


  public void printPages(PdfContentByte dc)
  {
    //writing Страница [current_page] из [XXX]
    String text = words.getMessage("rep.TechnicalDescription.page", writer.getPageNumber());
    float textSize = fontArial7P5Normal.getBaseFont().getWidthPoint(text, fontArial7P5Normal.getCalculatedSize());
    float textBase = bottomMargin + 3;
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
    //writing DocumentName
    String text = docName;
    dc.beginText();
    dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
    float adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("0", fontArial7P5Normal.getCalculatedSize());
    float textBase = bottomMargin + 3;
    dc.setTextMatrix(document.left() + adjust, textBase);
    dc.showText(text);
    dc.endText();
  }
}