package net.sam.dcl.test.pdf;

/**
 * @author: DG
 * Date: Aug 12, 2005
 * Time: 2:47:30 PM
 */

import java.io.FileOutputStream;
import java.awt.*;

import com.lowagie.text.*;
import com.lowagie.text.List;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Add a big table to a PDF with document.add().
 */
public class AddBigTable {

  /**
   * Big PdfPTable with document.add().
   *
   * @param args no arguments needed
   */
  public static void main(String[] args) {

    System.out.println("document.add(BigTable)");
    // step1
    Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
    try {
      // step2
      PdfWriter writer = PdfWriter.getInstance(document,
          new FileOutputStream("AddBigTable.pdf"));
      // step3

      List list = new List(true, 20);
      list.add(new ListItem("First line"));
      list.add(new ListItem("The second line is longer to see what happens once the end of the line is reached. Will it start on a new line?"));
      list.add(new ListItem("Third line"));

      HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
      HeaderFooter footer = new HeaderFooter(new Phrase("This is page\n helo\n and agian "), new Phrase("."));
      header.setBackgroundColor(Color.green);
      header.setTop(10);
      header.setBottom(110);
      header.paragraph().add(list);

      document.setHeader(header);
      document.setFooter(footer);

      document.open();

      // step4
      String[] bogusData = {"M0065920", "SL", "FR86000P", "PCGOLD",
          "119000", "96 06", "2001-08-13", "4350", "6011648299",
          "FLFLMTGP", "153", "119000.00"};
      int NumColumns = 12;

      PdfPTable datatable = new PdfPTable(NumColumns);
      int headerwidths[] = {9, 4, 8, 10, 8, 11, 9, 7, 9, 10, 4, 10}; // percentage
      datatable.setWidths(headerwidths);
      datatable.setWidthPercentage(100); // percentage
      datatable.getDefaultCell().setPadding(3);
      datatable.getDefaultCell().setBorderWidth(2);
      datatable.getDefaultCell().setHorizontalAlignment(
          Element.ALIGN_CENTER);
      datatable.addCell("Clock #");
      datatable.addCell("Trans Type");
      datatable.addCell("Cusip");
      datatable.addCell("Long Name");
      datatable.addCell("Quantity");
      datatable.addCell("Fraction Price");
      datatable.addCell("Settle Date");
      datatable.addCell("Portfolio");
      datatable.addCell("ADP Number");
      datatable.addCell("Account ID");
      datatable.addCell("Reg Rep ID");
      datatable.addCell("Amt To Go ");

      datatable.setHeaderRows(2); // this is the end of the table header

      datatable.getDefaultCell().setBorderWidth(1);
      for (int i = 1; i < 750; i++) {
        if (i % 2 == 1) {
          datatable.getDefaultCell().setGrayFill(0.9f);
        }
        bogusData[0] = String.valueOf(i);
        for (int x = 0; x < NumColumns; x++) {

          datatable.addCell(bogusData[x]);
        }
        if (i % 2 == 1) {
          datatable.getDefaultCell().setGrayFill(0.0f);
        }
      }
      document.add(datatable);
    } catch (Exception de) {
      de.printStackTrace();
    }
    // step5
    document.close();
  }
}