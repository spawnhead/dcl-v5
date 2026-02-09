package net.sam.dcl.test.pdf;

/**
 * @author: DG
 * Date: Aug 12, 2005
 * Time: 2:56:09 PM
 */

import java.io.FileOutputStream;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;

/**
 * Demonstrates the use of nested tables.
 */
public class NestedTables {

  /**
   * Using nested tables.
   *
   * @param args no arguments needed
   */
  public static void main(String[] args) {

    System.out.println("Nested Tables");
    // step1
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    try {
      // step2
 //     document.removeDocListener();
      PdfWriter writer = PdfWriter.getInstance(document,
          new FileOutputStream("NestedTables.pdf"));
      // step3
      document.open();


      // step4
      PdfPTable table = new PdfPTable(4);
      table.getDefaultCell().setBorder(Cell.NO_BORDER);
      PdfPTable nested1 = new PdfPTable(2);
      nested1.addCell("1.1\n2.1");
      nested1.addCell("1.2");
      PdfPTable nested2 = new PdfPTable(1);
/*      Image img = Image.getInstance("logo.gif");
      img.setAbsolutePosition(100,300);
      img.scaleAbsolute(200,200);*/
      Image img = Image.getInstance("header-midle-img.gif");
      /*img.setBorder(Image.BOX);
      img.setBorderWidth(20);
      img.setBorderColor(Color.red);
      img.setAbsolutePosition(100,300);*/
      //img.
      PdfPCell cell = new PdfPCell(img,false);
      /*cell.setBorder(Image.BOX);
      cell.setBorderWidth(20);
      cell.setBorderColor(Color.red);*/

      //nested2.addCell(imf);
      nested2.addCell(cell);
      document.add(img);
      for (int k = 0; k < 24; ++k) {
        if (k == 1) {
          table.addCell(nested1);
        } else if (k == 20) {
          table.addCell(nested2);
        } else {
          table.addCell("cell " + k);
        }
      }
      document.add(table);
      // step 5: we close the document
      document.close();
    } catch (Exception de) {
      de.printStackTrace();
    }
    // step5
    document.close();
  }
}
