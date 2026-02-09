package net.sam.dcl.test.pdf;

/**
 * @author: DG
 * Date: Aug 11, 2005
 * Time: 6:33:52 PM
 */
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

/**
 * Reads the pages of an existing PDF file and puts 2 pages from the existing doc
 * into one of the new doc.
 */
public class TwoOnOne {
    /**
     * Reads the pages of an existing PDF file and puts 2 pages from the existing doc
     * into one of the new doc.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        System.out.println("Import pages as images");
        try {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader("ChapterSection.pdf");
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();
            PdfDictionary dic = reader.getPageN(1);
            // we retrieve the size of the first page
            Rectangle psize = reader.getPageSize(1);
            float width = psize.getHeight();
            float height = psize.getWidth();

            // step 1: creation of a document-object
            Document document = new Document(new Rectangle(width, height));
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("2on1.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add content
            PdfContentByte cb = writer.getDirectContent();
            int i = 0;
            int p = 0;
            while (i < n) {
                document.newPage();
                p++;
                i++;
                PdfImportedPage page1 = writer.getImportedPage(reader, i);
                cb.addTemplate(page1, .5f, 0, 0, .5f, 60, 120);
                if (i < n) {
                    i++;
                    PdfImportedPage page2 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page2, .5f, 0, 0, .5f, width / 2 + 60, 120);
                }
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 14);
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "page " + p + " of " + ((n / 2) + (n % 2 > 0? 1 : 0)), width / 2, 40, 0);
                cb.endText();
            }
            // step 5: we close the document
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }
}
