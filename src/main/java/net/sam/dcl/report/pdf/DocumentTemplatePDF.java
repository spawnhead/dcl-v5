package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Demonstrates the use of PageEvents.
 */
public abstract class DocumentTemplatePDF extends PdfPageEventHelper
{
  float spaceAfterHeader = 20;
  float spaceBeforeFooter = 20;

  PdfPTable header = null;
  PdfPTable footer = null;

  public PdfTemplate pagesCountTemplate;
  public PdfTemplate firstPageTemplate;

  Font firstPagePagesCountFont;
  Document document = null;
  PdfWriter writer = null;
  float defRelLeading = 1.2f;
  public static final String ARIAL = "Arial";
  public static final String TIMES_NEW_ROMAN = "Times New Roman";
  protected static final char SPACE = ' ';
  int noBorder = PdfPCell.NO_BORDER;
  float headerLeftMargin = 50;
  float headerRightMargin = 25;
  float leftMargin = 50;
  float rightMargin = 25;
  float topMargin = 30;
  String charset;
  OutputStream outStream = null;
  float bottomMargin = 20;

  float scale = 100;

  public Font fontArial7P5Normal;
  public Font fontArial9Normal;
  public Font fontArial9Bold;
  public Font fontArial10Normal;
  public Font fontArial10NormalUnderline;
  public Font fontArial10Bold;
  public Font fontArial11Normal;
  public Font fontArial11Bold;

  protected DocumentTemplatePDF(OutputStream outStream, String charset)
  {
    this.charset = charset;
    this.outStream = outStream;
  }

  public void process() throws DocumentException, IOException
  {
    fontArial7P5Normal = FontFactory.getFont(ARIAL, charset, true, 7.5f, Font.NORMAL);
    fontArial9Normal = FontFactory.getFont(ARIAL, charset, true, 9f, Font.NORMAL);
    fontArial9Bold = FontFactory.getFont(ARIAL, charset, true, 9f, Font.BOLD);
    fontArial10Normal = FontFactory.getFont(ARIAL, charset, true, 10f, Font.NORMAL);
    fontArial10NormalUnderline = FontFactory.getFont(ARIAL, charset, true, 10f, Font.UNDERLINE);
    fontArial10Bold = FontFactory.getFont(ARIAL, charset, true, 10f, Font.BOLD);
    fontArial11Normal = FontFactory.getFont(ARIAL, charset, true, 11f, Font.NORMAL);
    fontArial11Bold = FontFactory.getFont(ARIAL, charset, true, 11f, Font.BOLD);

    header = generateHeader(PageSize.A4.getWidth() - headerLeftMargin - headerRightMargin);
    footer = generateFooter(PageSize.A4.getWidth() - headerLeftMargin - headerRightMargin);
    document = new Document(PageSize.A4, leftMargin, rightMargin,
            header.getTotalHeight() + topMargin + spaceAfterHeader,
            footer.getTotalHeight() + bottomMargin + spaceBeforeFooter);

    writer = PdfWriter.getInstance(document, outStream);
    writer.setPageEvent(this);

    document.open();

    pagesCountTemplate = writer.getDirectContent().createTemplate(100, 100);
    pagesCountTemplate.setBoundingBox(new Rectangle(-20, -20, 100, 100));

    firstPageTemplate = writer.getDirectContent().createTemplate(100, 100);
    firstPageTemplate.setBoundingBox(new Rectangle(-20, -20, 100, 100));

    parentProcess();
    document.close();
  }

  abstract public void parentProcess() throws DocumentException, IOException;

  abstract public void printPages(PdfContentByte dc);

  abstract public void printDocumentName(PdfContentByte dc);

  public void onEndPage(PdfWriter writer, Document document)
  {
    try
    {
      Rectangle page = document.getPageSize();
      PdfContentByte dc = writer.getDirectContent();
      //writing header
      header.writeSelectedRows(0, -1, headerLeftMargin, page.getHeight() - topMargin, dc);
      //writing footer
      footer.writeSelectedRows(0, -1, headerLeftMargin, bottomMargin + footer.getTotalHeight(), dc);

      //writing Страница [current_page] из [XXX]
      printPages(dc);

      //writing DocumentName
      printDocumentName(dc);
    }
    catch (Exception e)
    {
      throw new ExceptionConverter(e);
    }
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setTotalWidth(width);
    return head;
  }

  protected PdfPTable generateFooter(float width) throws DocumentException, IOException
  {
    PdfPTable foot = new PdfPTable(2);
    foot.setTotalWidth(width);
    return foot;
  }

  public void onCloseDocument(PdfWriter writer, Document document)
  {
    pagesCountTemplate.beginText();
    pagesCountTemplate.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
    pagesCountTemplate.setTextMatrix(0, 0);
    String res = StringUtil.padWithLeadingSymbol(String.valueOf(writer.getPageNumber() - 1), 3, SPACE);
    pagesCountTemplate.showText(res);
    pagesCountTemplate.endText();

    firstPageTemplate.beginText();
    firstPageTemplate.setFontAndSize(firstPagePagesCountFont.getBaseFont(), firstPagePagesCountFont.getCalculatedSize());
    firstPageTemplate.setTextMatrix(0, 0);
    res = StringUtil.padWithLeadingSymbol(String.valueOf(writer.getPageNumber() - 1), 3, SPACE);
    firstPageTemplate.showText(res);
    firstPageTemplate.endText();
  }

  protected void addPagesCountTemplate(PdfContentByte dc, float x, float y)
  {
    dc.addTemplate(pagesCountTemplate, x, y);
  }

  protected void addFirstPageTemplate(PdfContentByte dc, float x, float y)
  {
    dc.addTemplate(firstPageTemplate, x, y);
  }

  public static void main(String[] args)
  {
    LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", Locale.GERMANY);

    System.out.println("test.value=" + words.getMessage("test.value", "1", "2"));
  }

  public void line(float height)
  {
    PdfContentByte dc = writer.getDirectContent();
    dc.saveState();
    dc.setColorStroke(Color.red);
    dc.setColorFill(Color.red);
    dc.setLineWidth(1);
    dc.moveTo(leftMargin, height);
    dc.lineTo(document.getPageSize().getWidth() - rightMargin, height);
    dc.stroke();
    dc.restoreState();
  }

  protected PdfPCell cell(String txt, Font font, int colSpan)
  {
    return cell(txt, font, colSpan, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP);
  }

  protected PdfPCell cell(String txt, Font font, int colSpan, int horizontalAlignment, int verticalAlignment)
  {
    return cell(txt, font, colSpan, horizontalAlignment, verticalAlignment, noBorder);
  }

  protected PdfPCell cell(String txt, Font font, int colSpan, int horizontalAlignment, int verticalAlignment, int border, int bottomPadding, Color color)
  {
    return cell(new Phrase(txt, font), colSpan, horizontalAlignment, verticalAlignment, border, bottomPadding, color);
  }

  //с дефолтным белым цветом
  protected PdfPCell cell(String txt, Font font, int colSpan, int horizontalAlignment, int verticalAlignment, int border, int bottomPadding)
  {
    return cell(new Phrase(txt, font), colSpan, horizontalAlignment, verticalAlignment, border, bottomPadding, Color.white);
  }

  //с дефолтным отступом снизу в 5 едениц, белым цветом
  protected PdfPCell cell(String txt, Font font, int colSpan, int horizontalAlignment, int verticalAlignment, int border)
  {
    return cell(new Phrase(txt, font), colSpan, horizontalAlignment, verticalAlignment, border);
  }

  protected PdfPCell cell(String txt, Font font, int colSpan, int horizontalAlignment, int verticalAlignment, int border, float borderWidth)
  {
    return cell(new Phrase(txt, font), colSpan, horizontalAlignment, verticalAlignment, border, borderWidth);
  }

  //с дефолтным отступом снизу в 5 едениц, белым цветом
  protected PdfPCell cell(Phrase phrase, int colSpan, int horizontalAlignment, int verticalAlignment, int border)
  {
    return cell(phrase, colSpan, horizontalAlignment, verticalAlignment, border, 5, Color.white);
  }

  //с дефолтным отступом снизу в 5 едениц, белым цветом
  protected PdfPCell cell(Phrase phrase, int colSpan, int horizontalAlignment, int verticalAlignment, int border, float borderWidth)
  {
    return cell(phrase, colSpan, horizontalAlignment, verticalAlignment, border, 5, Color.white, borderWidth);
  }

  //с отступом снизу белым цветом
  protected PdfPCell cell(Phrase phrase, int colSpan, int horizontalAlignment, int verticalAlignment, int border, int bottomPadding)
  {
    return cell(phrase, colSpan, horizontalAlignment, verticalAlignment, border, bottomPadding, Color.white);
  }

  protected PdfPCell cell(Phrase phrase, int colSpan, int horizontalAlignment, int verticalAlignment, int border, int bottomPadding, Color color)
  {
    PdfPCell pdfPCell = new PdfPCell(phrase);
    pdfPCell.setLeading(0, defRelLeading);
    pdfPCell.setBorder(border);
    pdfPCell.setColspan(colSpan);
    pdfPCell.setHorizontalAlignment(horizontalAlignment);
    pdfPCell.setVerticalAlignment(verticalAlignment);
    pdfPCell.setPaddingTop(0);
    pdfPCell.setPaddingBottom(bottomPadding);
    pdfPCell.setBackgroundColor(color);
    return pdfPCell;
  }

  protected PdfPCell cell(Phrase phrase, int colSpan, int horizontalAlignment, int verticalAlignment, int border, int bottomPadding, Color color, float borderWidth)
  {
    PdfPCell pdfPCell = new PdfPCell(phrase);
    pdfPCell.setLeading(0, defRelLeading);
    pdfPCell.setBorder(border);
    pdfPCell.setColspan(colSpan);
    pdfPCell.setHorizontalAlignment(horizontalAlignment);
    pdfPCell.setVerticalAlignment(verticalAlignment);
    pdfPCell.setPaddingTop(0);
    pdfPCell.setPaddingBottom(bottomPadding);
    pdfPCell.setBackgroundColor(color);
    pdfPCell.setBorderWidth(borderWidth);
    return pdfPCell;
  }

  protected PdfPCell cell(PdfPTable table, int colSpan, int horizontalAlignment, int verticalAlignment, int border)
  {
    PdfPCell pdfPCell = new PdfPCell(table);
    pdfPCell.setLeading(0, defRelLeading);
    pdfPCell.setBorder(border);
    pdfPCell.setColspan(colSpan);
    pdfPCell.setHorizontalAlignment(horizontalAlignment);
    pdfPCell.setVerticalAlignment(verticalAlignment);
    pdfPCell.setPaddingTop(0);
    pdfPCell.setPaddingBottom(0);
    return pdfPCell;
  }

  protected PdfPCell cell(PdfPTable table, int colSpan, int horizontalAlignment, int verticalAlignment, int border, float borderWidth)
  {
    PdfPCell pdfPCell = new PdfPCell(table);
    pdfPCell.setLeading(0, defRelLeading);
    pdfPCell.setBorder(border);
    pdfPCell.setColspan(colSpan);
    pdfPCell.setHorizontalAlignment(horizontalAlignment);
    pdfPCell.setVerticalAlignment(verticalAlignment);
    pdfPCell.setPaddingTop(0);
    pdfPCell.setPaddingBottom(0);
    pdfPCell.setBorderWidth(borderWidth);
    return pdfPCell;
  }

  public float getScale()
  {
    return scale;
  }

  public void setScale(float scale)
  {
    this.scale = scale;
  }

  public Font getFontArial3Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 3f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial4Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 4f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial5Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 5f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial5Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 5f * scale / 100, Font.BOLD);
  }

  public Font getFontArial6Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 6f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial6Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 6f * scale / 100, Font.BOLD);
  }

  public Font getFontArial6P5Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 6.5f * scale / 100, Font.BOLD);
  }

  public Font getFontArial7Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 7f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial7Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 7f * scale / 100, Font.BOLD);
  }

  public Font getFontArial7P5Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 7.5f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial7P5Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 7.5f * scale / 100, Font.BOLD);
  }

  public Font getFontArial8Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 8f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial8Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 8f * scale / 100, Font.BOLD);
  }

  public Font getFontArial8BoldRed()
  {
    Font fontArial8Bold = FontFactory.getFont(ARIAL, charset, true, 8f * scale / 100, Font.BOLD);
    fontArial8Bold.setColor(Color.red);

    return fontArial8Bold;
  }

  public Font getFontArial8P5Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 8.5f * scale / 100, Font.BOLD);
  }

  public Font getFontArial9Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 9f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial9Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 9f * scale / 100, Font.BOLD);
  }

  public Font getFontArial9P5Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 9.5f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial9P5Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 9.5f * scale / 100, Font.BOLD);
  }

  public Font getFontArial10Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 10f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial10NormalUnderline()
  {
    return FontFactory.getFont(ARIAL, charset, true, 10f * scale / 100, Font.UNDERLINE);
  }

  public Font getFontArial10Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 10f * scale / 100, Font.BOLD);
  }

  public Font getFontArial11Normal()
  {
    return FontFactory.getFont(ARIAL, charset, true, 11f * scale / 100, Font.NORMAL);
  }

  public Font getFontArial11NormalUnderline()
  {
    return FontFactory.getFont(ARIAL, charset, true, 11f * scale / 100, Font.UNDERLINE);
  }

  public Font getFontArial11NormalRed()
  {
    Font fontArial11NormalRed = FontFactory.getFont(ARIAL, charset, true, 11f * scale / 100, Font.NORMAL);
    fontArial11NormalRed.setColor(Color.red);

    return fontArial11NormalRed;
  }

  public Font getFontArial11Bold()
  {
    return FontFactory.getFont(ARIAL, charset, true, 11f * scale / 100, Font.BOLD);
  }

  public Font getFontArial11BoldUnderline()
  {
    return FontFactory.getFont(ARIAL, charset, true, 11f * scale / 100, Font.BOLD | Font.UNDERLINE);
  }
}
