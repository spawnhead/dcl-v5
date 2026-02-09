package net.sam.dcl.test.pdf;

/**
 * @author: DG
 * Date: Aug 13, 2005
 * Time: 6:22:36 PM
 */
 import java.io.*;
 import com.lowagie.text.*;
 import com.lowagie.text.pdf.*;
 import net.sam.dcl.report.pdf.DocumentTemplatePDF;

public class TestRU {

     static byte allChars[];

     static {
         allChars = new byte[256];
         for (int k = 0; k < 256; ++k){
             allChars[k] = (byte)(k);
         }
     }

     public static void main(String[] args) {
         Document document = new Document(PageSize.A4, 50, 50, 50, 50);
         try {
             //FontFactory.registerDirectory("C:/WIN.XP/Fonts");
             System.out.println(FontFactory.registerDirectory("C:/WIN.XP/Fonts"));
             //Font font = new Font(Font.HELVETICA, 12);
             String encoding = "Cp1251";
             String trs = new String(allChars, encoding);
             PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\test_ru.pdf"));
             document.open();
             BaseFont bf;
             String name;
             //name = "c:\\win.xp\\fonts\\arial.ttf";
             //bf = BaseFont.createFont(name, encoding, true);
             Font fn = FontFactory.getFont(DocumentTemplatePDF.ARIAL, encoding,true, 14);
             bf = fn.getBaseFont();

             //document.add(new Paragraph(name + "\n" + encoding + "\n\n\n"));
             document.add(new Paragraph(trs, new Font(bf, 14)));
             document.add(new Paragraph(trs, fn));
             document.add(new Paragraph("Тест", new Font(bf, 14)));
             document.newPage();
             document.close();
         }
         catch(Exception de) {
             System.err.println(de.getMessage());
         }
         System.out.println("Finished!");
     }
 }