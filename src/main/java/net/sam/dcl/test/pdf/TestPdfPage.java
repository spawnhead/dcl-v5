package net.sam.dcl.test.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import net.sam.dcl.report.pdf.DocumentTemplatePDF;

/**
 * Demonstrates the use of PageEvents.
 */
public class TestPdfPage extends PdfPageEventHelper {

  float leftMargin = 50;
  float rightMargin = 50;
  float topMargin = 30;
  float bottomMargin = 30;

  float spaceAfterHeader = 20;
  float spaceBeforeFooter = 20;

  float lineLeftMargin = 50;

  PdfPTable header = null;
  PdfPTable footer = null;

  void process(){
    try {
      //FontFactory.registerDirectory(fontDir);
      FontFactory.registerDirectories();
      header = getHeader(PageSize.A4.getWidth() - leftMargin - rightMargin);
      footer = getFooter(PageSize.A4.getWidth() - leftMargin - rightMargin);
      Document document = new Document(PageSize.A4, leftMargin,rightMargin,
          header.getTotalHeight()+topMargin+spaceAfterHeader,
          footer.getTotalHeight()+bottomMargin+spaceBeforeFooter);
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
      writer.setPageEvent(this);
      document.open();
      String text = "Lots of text. ";
      for (int k = 0; k < 10; ++k)
        text += text;
      document.add(new Paragraph(text));
      document.close();
    }
    catch (Exception de) {
      de.printStackTrace();
    }
  }
  /**
   * Demonstrates the use of PageEvents.
   *
   * @param args no arguments needed
   */

  public static void main(String[] args) {
    TestPdfPage pdfPage = new TestPdfPage();
    pdfPage.process();

  }

  /**
   * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
   */
  public void onEndPage(PdfWriter writer, Document document) {
    try {
      Rectangle page = document.getPageSize();
//      PdfPTable head = getHeader(page.width() - document.leftMargin() - document.rightMargin());

      PdfContentByte dc = writer.getDirectContent();

      header.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight()  - topMargin,dc);
      footer.writeSelectedRows(0, -1, document.leftMargin(), bottomMargin+footer.getTotalHeight(),dc);

      dc.saveState();
      dc.setColorStroke(Color.green);
      dc.setColorFill(Color.green);
      /*dc.moveTo(document.leftMargin(),page.height()  - topMargin);
      dc.lineTo(document.leftMargin(),page.height()  - topMargin- header.getTotalHeight());*/

      dc.moveTo(document.leftMargin()+lineLeftMargin,page.getHeight()  - topMargin - header.getRowHeight(0)+1);
      dc.lineTo(page.getWidth()-document.rightMargin(),page.getHeight() - topMargin - header.getRowHeight(0)+1);

      dc.moveTo(document.leftMargin(),bottomMargin + footer.getTotalHeight());
      dc.lineTo(page.getWidth()-document.rightMargin(),bottomMargin + footer.getTotalHeight());
      dc.stroke();
      dc.restoreState();

    }
    catch (Exception e) {
      throw new ExceptionConverter(e);
    }
  }
  private PdfPTable getHeader(float width) throws DocumentException, IOException {
    PdfPTable head = new PdfPTable(3);
    head.setWidths(new float[]{0.2f,0.6f,0.2f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    Image  li = Image.getInstance("header-left-img.gif");
    li.scaleToFit(10000,40);
    PdfPCell lc = new PdfPCell(li,false);
/*    lc.setBorder(PdfPCell.BOTTOM);
    lc.setBorderColor(Color.green); */
    lc.setBorder(PdfPCell.NO_BORDER);

    head.addCell(lc);
    Image mi = Image.getInstance("header-midle-img.gif");
    mi.scaleToFit(10000,25);
    PdfPCell mc = new PdfPCell(mi,false);
    mc.setHorizontalAlignment(PdfPCell.LEFT);
    mc.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
    /*mc.setBorder(PdfPCell.BOTTOM);
    mc.setBorderColor(Color.green);*/
    mc.setBorder(PdfPCell.NO_BORDER);
    //mc.setPaddingLeft(5);
    head.addCell(mc);
    Font font = FontFactory.getFont(DocumentTemplatePDF.TIMES_NEW_ROMAN,"Cp1251",9,Font.BOLD);


    PdfPCell rc = new PdfPCell(new Phrase("EQUIPMENT\nSALE &\nMAINTENANCE",font));
    rc.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    /*rc.setBorder(PdfPCell.BOTTOM);
    rc.setBorderColor(Color.green);*/
    rc.setBorder(PdfPCell.NO_BORDER);
    head.addCell(rc);

    head.addCell("");
    head.addCell("");
    font = FontFactory.getFont(DocumentTemplatePDF.ARIAL,"Cp1251",8,Font.NORMAL);
    PdfPCell rbc = new PdfPCell(new Phrase("www.lintera.info",font));
    rbc.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    rbc.setBorder(PdfPCell.NO_BORDER);
    head.addCell(rbc);

    head.setTotalWidth(width);
    //head.calculateHeightsFast();
    return head;
  }
  private PdfPTable getFooter(float width) throws DocumentException, IOException {
    PdfPTable foot = new PdfPTable(2);
    //footer.setWidths(new float[]{0.2f,0.6f,0.2f});
    //footer.getDefaultCell().setBorder(0);

    Font font = FontFactory.getFont(DocumentTemplatePDF.ARIAL,"Cp1251",7.5f,Font.BOLD);

    PdfPCell lc = new PdfPCell(new Phrase(
        "ЗАКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО “ЛИНТЕРА”\n" +
        "Литовская республика\n" +
        "55101, г. Йонава, ул. Укмергес, 22\n" +
        "Тел.: (+370 349) 61161.\n" +
        "Факс: (+370 349) 61297.\n" +
        "E-mail: jonava@lintera.info",font));
    lc.setBorder(PdfPCell.TOP);
    lc.setBorderColor(Color.green);
    lc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    //lc.setMinimumHeight(10);
    foot.addCell(lc);

    PdfPCell rc = new PdfPCell(new Phrase(
        "ПРЕДСТАВИТЕЛЬСТВО ЗАКРЫТОГО АКЦИОНЕРНОГО\n" +
        "ОБЩЕСТВА “ЛИНТЕРА” В РЕСПУБЛИКЕ БЕЛАРУСЬ\n" +
        "220030, г. Минск, ул.Энгельса, 34а-306\n" +
        "Тел.: (+375 17) 206-66-31\n" +
        "Факс: (+375 17) 206-60-41\n" +
        "E-mail: minsk@lintera.info",font));
    rc.setBorder(PdfPCell.TOP);
    rc.setBorderColor(Color.green);
    rc.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    //rc.setMinimumHeight(10);
    foot.addCell(rc);

    foot.setTotalWidth(width);
    //foot.calculateHeightsFast();
    return foot;
  }

}
