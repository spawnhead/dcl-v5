package net.sam.dcl.report.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.OrderProduce;
import net.sam.dcl.form.ContractorsPrintForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.OutputStream;

//test
/**
 * Demonstrates the use of PageEvents.
 */
public class ContractorsPDF extends DocumentTemplatePDF
{
  protected static Log log = LogFactory.getLog(OrderProduce.class);
  ContractorsPrintForm form = null;
  Blank blank = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words;

  public ContractorsPDF(OutputStream outStream, ContractorsPrintForm form)
  {
    super(outStream, "Cp1251");
    this.form = form;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead();
    processGrid(width);
    processFooter(width);
  }


  private void processFooter(float width) throws DocumentException
  {
  }

  private void processGrid(float width) throws DocumentException
  {
    PdfPTable devTable = new PdfPTable(1);
    devTable.setWidthPercentage(100);
    devTable.setSplitLate(false);
    devTable.getDefaultCell().setBorder(noBorder);
    devTable.addCell(cell(" ", getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER));

    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.34f, 0.33f, 0.33f});
    table.getDefaultCell().setBorder(noBorder);

    int iterator = 0;
    for (Contractor contractor : form.getContractors())
    {
      PdfPTable contractorTable = new PdfPTable(1);
      table.setSplitLate(false);
      table.setWidthPercentage(100);

      contractorTable.addCell(cell(" " + contractor.getFullname(), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER));
      contractorTable.addCell(
              cell(" " +
                contractor.getStreet() +
                (StringUtil.isEmpty(contractor.getStreet()) ? "" : ", ") +
                contractor.getBuilding(), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER
              )
        );
      contractorTable.addCell(
              cell(" " +
                contractor.getIndex() +
                (StringUtil.isEmpty(contractor.getIndex()) ? "" : ", ") +
                contractor.getPlace() +
                (StringUtil.isEmpty(contractor.getPlace()) ? "" : ", ") +
                contractor.getRegion(), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER
              )
        );
      contractorTable.addCell(cell(" " + contractor.getCountry().getName(), getFontArial9Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER));

      table.addCell(contractorTable);

      iterator++;
      if ( iterator % 3 == 0 )
      {
        table.addCell(devTable);
        table.addCell(devTable);
        table.addCell(devTable);
      }

      if ( iterator % 24 == 0 )
      {
        document.newPage();
      }
    }

    int size = form.getContractors().size();
    if (size < 3 || (size % 3 < 3 && size % 3 != 0) )
    {
      int rightCount = size < 3 ? size : size % 3;
      for (int i = rightCount; i < 3; i++)
      {
        table.addCell(devTable);  
      }
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);

    document.newPage();
  }

  private void processHead() throws DocumentException
  {
  }

  public void printPages(PdfContentByte dc)
  {
  }

  public void printDocumentName(PdfContentByte dc)
  {
  }

  public void onCloseDocument(PdfWriter writer, Document document)
  {
  }

}