package net.sam.dcl.test.pdf;

import java.io.FileOutputStream;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;

/**
 * Define the widths of the columns of a PdfPTable.
 */
public class CellWidths {

  /**
   * Width manipulations of cells.
   *
   * @param args
   *            no arguments needed
   */
  public static void main(String[] args) {

    System.out.println("Width");
    // step1
    Document document = new Document(PageSize.A4, 36, 36, 36, 36);
    try {
      // step2
      PdfWriter writer = PdfWriter.getInstance(document,
          new FileOutputStream("CellWidths.pdf"));
      // step3
      document.open();
      // step4
      float[] widths = {0.1f, 0.1f, 0.05f, 0.75f};
      PdfPTable table = new PdfPTable(widths);
      table.addCell("10%");
      table.addCell("10%");
      table.addCell("5%");
      table.addCell("75%");
      table.addCell("aa");
      table.addCell("aa");
      PdfPCell cell = new PdfPCell(new Phrase("aaaaaaa"));
      //cell.setNoWrap(true);
      cell.setMinimumHeight(100);
      table.addCell(cell);
      table.addCell("aaaaaaaaaaaaaaa");
      table.addCell("bb");
      table.addCell("bb");
      table.addCell("b");
      table.addCell("bbbbbbbbbbbbbbb");
      table.addCell("cc");
      table.addCell("cc");
      table.addCell("c");
      table.addCell("ccccccccccccccc");
      document.add(table);
      document.add(new Paragraph("We change the percentages:\n\n"));
      widths[0] = 20f;
      widths[1] = 20f;
      widths[2] = 10f;
      widths[3] = 50f;
      table.setWidths(widths);
      document.add(table);
      widths[0] = 40f;
      widths[1] = 40f;
      widths[2] = 20f;
      widths[3] = 300f;
      Rectangle r = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
      table.setWidthPercentage(widths, r);
      document.add(new Paragraph("We change the percentage using absolute widths:\n\n"));
      document.add(table);
      document.add(new Paragraph("We use a locked width:\n\n"));
      table.setTotalWidth(300);
      table.setLockedWidth(true);
      document.add(table);
    } catch (Exception de) {
      de.printStackTrace();
    }
    // step5
    document.close();
  }
}
