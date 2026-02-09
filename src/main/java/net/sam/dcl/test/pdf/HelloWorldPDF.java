package net.sam.dcl.test.pdf;


import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Generates a simple 'Hello World' PDF file.
 *
 * @author blowagie
 */

public class HelloWorldPDF {

	/**
	 * Generates a PDF file with the text 'Hello World'
	 *
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("Hello World");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			PdfWriter.getInstance(document,
					new FileOutputStream("HelloWorld.pdf"));

			// step 3: we open the document
			document.open();
			// step 4: we add a paragraph to the document
			document.add(new Paragraph("Hello World"));
      String text = "Lots of text. ";
      for (int k = 0; k < 10; ++k){
        text += text;
        document.add(new Paragraph(5*k,text));
      }

      Paragraph p = new Paragraph(2);
      p.add(new Paragraph("Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line"));
      p.add(new Paragraph("Long line2\nLong line2"));
      p.add(new Paragraph(40,"Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line Long line"));
      p.add(new Paragraph(40,"Long line2\nLong line2"));
      document.add(p);
      document.close();
    } catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}
