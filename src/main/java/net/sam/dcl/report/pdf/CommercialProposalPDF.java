package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import net.sam.dcl.beans.Blank;
import net.sam.dcl.beans.ContactPerson;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.config.Config;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.form.CommercialProposalPrintForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.NumberToWordsRussian;
import net.sam.dcl.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;
import java.awt.*;

/**
 * Demonstrates the use of PageEvents.
 */
public class CommercialProposalPDF extends DocumentTemplatePDF
{
  CommercialProposalPrintForm form = null;
  Blank blank = null;
  DboAttachment imgFacsimile;
  String docName = "";
  String docName1 = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public CommercialProposalPDF(OutputStream outStream, Blank blank, CommercialProposalPrintForm form, DboAttachment imgFacsimile)
  {
    super(outStream, blank.getBln_charset());
    headerLeftMargin = headerRightMargin = 0;
    if (form.isContract())
    {
      topMargin = bottomMargin = 5;
    }
    else
    {
      topMargin = bottomMargin = 15;
    }
    if (form.isInvoice())
    {
      this.docName = words.getMessage("rep.CommercialProposal.number2", form.getNumber(), form.getDate());
      this.docName1 = "";
    }
    else if (form.isContract())
    {
      this.docName = words.getMessage(
              "rep.CommercialProposal.number3_1",
              new Object[]
                      {
                              form.getNumber(),
                              form.getDate()
                      }
      );
      this.docName1 = words.getMessage(
              "rep.CommercialProposal.number3_2",
              new Object[]
                      {
                              form.getCreateUser().getUserFullName(),
                              form.getCreateUser().getUsr_phone(),
                              form.getCreateUser().getUsr_fax()
                      }
      );
    }
    else
    {
      this.docName = words.getMessage("rep.CommercialProposal.number1", form.getNumber(), form.getDate());
      this.docName1 = "";
    }
    this.blank = blank;
    this.form = form;
    this.imgFacsimile = imgFacsimile;
    noBorder = 0;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    firstPagePagesCountFont = fontArial11Normal;
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();

    if (form.isContract())
    {
      processContract(width);
    }
    else
    {
      processHead();
      processHead2();
      processGrid(width, defSpacing);
      processFooter(width);
      processFooter2(width);
    }
  }

  private void processFooter2(float width) throws DocumentException
  {
    PdfPTable table;
    PdfPCell pdfPCell;
    table = new PdfPTable(3);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{0.3f, 0.3f, 0.4f});
    table.getDefaultCell().setBorder(noBorder);

    if (form.isInvoice())
    {
      table.addCell(cell(words.getMessage("rep.CommercialProposal.respect2"), getFontArial11Normal(), 3));
      if (form.isAssembleMinsk())
      {
        table.addCell(cell(words.getMessage("rep.CommercialProposal.attentionInvoiceAssemble"), getFontArial11Bold(), 3));
      }
      else
      {
        table.addCell(cell(words.getMessage("rep.CommercialProposal.attentionInvoiceText"), getFontArial11Bold(), 3));
      }
    }
    else
    {
      if (!form.isPrintExecutor() && null != imgFacsimile)
      {
        PdfPTable innerTable = new PdfPTable(1);
        innerTable.setWidths(new float[]{1f});

        innerTable.addCell(cell(" ", getFontArial11Normal(), 1));
        innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.respect1"), getFontArial11Normal(), 1));
        innerTable.addCell(cell(form.getUser().getUsr_position(), getFontArial11Normal(), 1));
        innerTable.addCell(cell(form.getUser().getUsr_phone(), getFontArial11Normal(), 1));
        innerTable.addCell(cell(form.getUser().getUsr_fax(), getFontArial11Normal(), 1));
        innerTable.addCell(cell(form.getUser().getUsr_email(), getFontArial11Normal(), 1));
        table.addCell(innerTable);

        try
        {
          Image facsimileImage = Image.getInstance(imgFacsimile.getContent());
          facsimileImage.scaleToFit(facsimileImage.getWidth() * 0.4f, facsimileImage.getHeight() * 0.4f);
          PdfPCell pdfPCellImg = new PdfPCell(facsimileImage, false);
          pdfPCellImg.setBorder(PdfPCell.NO_BORDER);
          pdfPCellImg.setVerticalAlignment(PdfPCell.ALIGN_TOP);
          table.addCell(pdfPCellImg);
        }
        catch (IOException e)
        {
          table.addCell(cell(e.getLocalizedMessage(), getFontArial11Bold(), 1));
        }

        innerTable = new PdfPTable(1);
        innerTable.setWidths(new float[]{1f});

        innerTable.addCell(cell(" ", getFontArial11Normal(), 1));
        innerTable.addCell(cell(" ", getFontArial11Normal(), 1));
        innerTable.addCell(cell(form.getUser().getUsr_surname() + " " + form.getUser().getUsr_name(), getFontArial11Normal(), 1));
        innerTable.addCell(cell(" ", getFontArial11Normal(), 1));
        table.addCell(innerTable);
      }
      else
      {
        table.addCell(cell(words.getMessage("rep.CommercialProposal.respect1"), getFontArial11Normal(), 3));
        table.addCell(cell(form.getUser().getUsr_position(), getFontArial11Normal(), 1));

        pdfPCell = new PdfPCell();
        pdfPCell.setBorderWidthBottom(1);
        pdfPCell.setBorderColorBottom(Color.black);
        pdfPCell.setBorder(PdfPCell.BOTTOM);
        table.addCell(pdfPCell);

        table.addCell(cell(form.getUser().getUsr_surname() + " " + form.getUser().getUsr_name(), getFontArial11Normal(), 1));
        //номера телефонов не впечатываем, если "Впечатать исполнителя" = ДА
        if (!form.isPrintExecutor())
        {
          table.addCell(cell(form.getUser().getUsr_phone() + ", Fax: " + form.getUser().getUsr_fax() + ", " + form.getUser().getUsr_email(), getFontArial9Normal(), 3));
        }
        if (form.isPrintExecutor())
        {
          table.addCell(cell("", getFontArial8Normal(), 3));
          table.addCell(cell(words.getMessage("rep.CommercialProposal.executor"), getFontArial8Normal(), 3));
          table.addCell(cell(form.getExecutor().getUsr_position() + " " + form.getExecutor().getUsr_surname() + " " + form.getExecutor().getUsr_name(), getFontArial8Normal(), 3));
          table.addCell(cell(form.getExecutor().getUsr_phone(), getFontArial8Normal(), 3));
        }
      }
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void processFooter(float width) throws DocumentException
  {
    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{1f});

    Phrase phrase = new Phrase();

    if (form.isInvoice())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.total"), getFontArial11Bold()));
      phrase.add(new Chunk(" " + StringUtil.getStrSum(form.getTotalPrint(), form.getCurrency().isNeedRound(), form.getCurrency().getName(), words), getFontArial11Normal()));
      table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    }

    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.cost"), getFontArial11Bold()));
    phrase.add(new Chunk(" " + form.getPriceCondition().getNameExtended() + " " + form.getCountry(), getFontArial11Normal()));
    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.pay_cond"), getFontArial11Bold()));
    if (form.isAssembleMinsk())
    {
      String condition;
      if (form.getPrepayPercent() == 100)
      {
        condition = words.getMessage("rep.CommercialProposal.conditionPrepay1", form.getFinalDateFullFormat());
      }
      else if (form.getPrepayPercent() == 0)
      {
        condition = words.getMessage("rep.CommercialProposal.conditionPrepay3", form.getDelayDays(), form.getDelayDaysInWords());
      }
      else
      {
        condition = words.getMessage("rep.CommercialProposal.conditionPrepay2", new Object[]{
                StringUtil.double2appCurrencyString(form.getPrepayPercent()),
				        StringUtil.getStrSum(form.getPrepaySum(), false, "BYN", words),
                form.getFinalDateFullFormat(),
                StringUtil.double2appCurrencyString(100 - form.getPrepayPercent()),
				        StringUtil.getStrSum(form.getTotalPrint() - form.getPrepaySum(), false, "BYN", words),
                form.getDelayDays(),
                form.getDelayDaysInWords()
        }
        );
      }
      phrase.add(new Chunk(" " + condition, getFontArial11Normal()));
    }
    else
    {
      phrase.add(new Chunk(" " + form.getPayCondition(), getFontArial11Normal()));
    }
    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
 
    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.delivery_cond"), getFontArial11Bold()));
    if (form.isAssembleMinsk())
    {
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.delivery_cond_assemble"), getFontArial11Normal()));
    }
    else
    {
      phrase.add(new Chunk(" " + form.getDeliveryCondition().getNameExtended() + " " + form.getDeliveryAddress(), getFontArial11Normal()));
    }
    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.delivery_date"), getFontArial11Bold()));
    if (form.isAssembleMinsk())
    {
      String delivery;
      if (form.getPrepayPercent() == 100)
      {
        delivery = words.getMessage("rep.CommercialProposal.deliveryPrepay1");
      }
      else if (form.getPrepayPercent() == 0)
      {
        delivery = words.getMessage("rep.CommercialProposal.deliveryPrepay3", form.getFinalDateFullFormat());
      }
      else
      {
        delivery = words.getMessage("rep.CommercialProposal.deliveryPrepay2");
      }
      phrase.add(new Chunk(" " + delivery, getFontArial11Normal()));
    }
    else
    {
      phrase.add(new Chunk(" " + form.getDeliveryTerm(), getFontArial11Normal()));
    }
    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    if (!form.isInvoice())
    {
      phrase = new Phrase();
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.notes"), getFontArial11Bold()));
      phrase.add(new Chunk(" " + form.getAdditionalInfo(), getFontArial11Normal()));
      table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    }

    if (!form.isHideFinalDate())
    {
      phrase = getFinalDatePhrase();
      if (form.isInvoice() || !form.isFinalDateAbove())
      {
        table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
      }
    }

    if (form.isInvoice())
    {
      phrase = new Phrase();
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.purchasePurpose"), getFontArial11Bold()));
      phrase.add(new Chunk(" " + form.getPurchasePurpose().getName(), getFontArial11Normal()));
      table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private Phrase getFinalDatePhrase()
  {
    Phrase phrase = new Phrase();
    if (form.isInvoice())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.end_date2"), getFontArial11Bold()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.end_date1"), getFontArial11Bold()));
    }
    if (form.isAssembleMinsk())
    {
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.end_date_to", form.getFinalDateFullFormat()), getFontArial11Normal()));
    }
    else
    {
      phrase.add(new Chunk(" " + form.getFinalDate(), getFontArial11Normal()));
    }
    return phrase;
  }

  private void processGrid(float width, float addSpaces) throws DocumentException
  {
    Font headerTableFont = getFontArial8P5Bold();
    Font headerTableFontLess = getFontArial6P5Bold();
    Font bodyTableFont = getFontArial9P5Normal();
    Font bodyTableFontLess = getFontArial7P5Normal();
    Font bottomTableFont = getFontArial9P5Bold();

    AutoWidthsTable table;
    int columnCount = 8;
    int unitColumnIdx = 3;
    if (StringUtil.isEmpty(form.getNdsByString()))
    {
      columnCount = 6;
    }
    if (form.isPrintUnit())
    {
      columnCount++;
    }
    if (form.isContract())
    {
      columnCount++;
    }
    if (form.isPrintPriceListBy())
    {
      columnCount += 2;
    }
    if (!form.isPrintCatalogNumber())
    {
      columnCount--;
      unitColumnIdx--;
    }
    table = new AutoWidthsTable(columnCount);
    if (form.isPrintUnit())
    {
      table.setMinWidthForColumn(26, unitColumnIdx);
    }
    if (form.isPrintCatalogNumber())
    {
      table.addReducibleColumnIndex(unitColumnIdx - 1);
    }

    table.setSideMargins(leftMargin + rightMargin);
    table.addReducibleColumnIndex(1);
    table.setSplitLate(true);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.getDefaultCell().setBorder(noBorder);

    table.addCell(cell(words.getMessage("rep.CommercialProposal.num"), headerTableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if (form.isContract())
    {
      table.addCell(cell(words.getMessage("rep.CommercialProposal.1c_number_title"), headerTableFontLess, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    table.addCell(cell(words.getMessage("rep.CommercialProposal.article_name"), headerTableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if (form.isPrintCatalogNumber())
    {
      table.addCell(cell(words.getMessage("rep.CommercialProposal.article_num"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
              PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    if (form.isPrintUnit())
    {
      table.addCell(cell(words.getMessage("rep.CommercialProposal.article_unit"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
              PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    table.addCell(cell(words.getMessage("rep.CommercialProposal.article_cost"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.CommercialProposal.article_count"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if (form.isPrintPriceListBy())
    {
      table.addCell(cell(words.getMessage("rep.CommercialProposal.lpc_price_list_by"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
              PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(words.getMessage("rep.CommercialProposal.discount_percent"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
              PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    table.addCell(cell(words.getMessage("rep.CommercialProposal.article_value"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
            PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if (!StringUtil.isEmpty(form.getNdsByString()))
    {
      if (form.isInvoice() || form.isContract())
      {
        table.addCell(cell(words.getMessage("rep.CommercialProposal.article_nds_invoice", form.getNDS()), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
                PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      else
      {
        table.addCell(cell(words.getMessage("rep.CommercialProposal.article_nds"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
                PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell(words.getMessage("rep.CommercialProposal.article_nds_value"), headerTableFont, 1, PdfPCell.ALIGN_CENTER,
              PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    for (int i = 0; i < form.getProduces().size(); i++)
    {
      CommercialProposalPrintForm.Produce produce = form.getProduces().get(i);

      table.addCell(cell(String.valueOf(i + 1), bodyTableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      if (form.isContract())
      {
        table.addCell(cell(produce.getNumber1C(), bodyTableFontLess, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell(produce.getName() + (form.isAssembleMinsk() && form.isInvoice() ? " " + words.getMessage("rep.CommercialProposal.1c_number", produce.getNumber1C()) : ""), bodyTableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      if (form.isPrintCatalogNumber())
      {
        table.addCell(cell(produce.getNumber(), bodyTableFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      }
      if (form.isPrintUnit())
      {
        table.addCell(cell(produce.getUnit(), bodyTableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell(produce.getCost(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      table.addCell(cell(produce.getCount(), bodyTableFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      if (form.isPrintPriceListBy())
      {
        table.addCell(cell(produce.getPriceListBy(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(produce.getDiscountPercent(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      table.addCell(cell(produce.getTotal(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      if (!StringUtil.isEmpty(form.getNdsByString()))
      {
        table.addCell(cell(produce.getNds(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
        table.addCell(cell(produce.getCostNDS(), bodyTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
    }
    Iterator iterator = form.getAdditionalRows().keySet().iterator();
    while (iterator.hasNext())
    {
      String key = (String) iterator.next();
      int countColumnMerge = 5;
      if (form.isPrintUnit())
      {
        countColumnMerge++;
      }
      if (form.isContract())
      {
        countColumnMerge++;
      }
      if (form.isPrintPriceListBy())
      {
        countColumnMerge += 2;
      }
      if (!form.isPrintCatalogNumber())
      {
        countColumnMerge--;
      }
      table.addCell(cell(key, bottomTableFont, countColumnMerge, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      CommercialProposalPrintForm.AddRow addRow = form.getAdditionalRows().get(key);
      table.addCell(cell(addRow.getTotal(), bottomTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      if (!StringUtil.isEmpty(form.getNdsByString()))
      {
        table.addCell(cell(addRow.getNds(), bottomTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
        table.addCell(cell(addRow.getCost_nds(), bottomTableFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
      }
    }
    table.setSpacingAfter(addSpaces);
    table.recalcWidths();
    document.add(table);
  }

  private void processHead2() throws DocumentException
  {
    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);

    table.setWidths(new float[]{0.96f, 0.04f});
    table.getDefaultCell().setBorder(noBorder);

    Phrase phrase = new Phrase();
    if (form.isInvoice())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.title2"), fontArial11Bold));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.title1"), fontArial11Bold));
    }
    phrase.add(new Chunk(" " + form.getNumber(), fontArial11Normal));
    phrase.add(new Chunk("  " + words.getMessage("rep.CommercialProposal.date"), fontArial11Bold));
    phrase.add(new Chunk(" " + form.getDate(), fontArial11Normal));

    table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    phrase = getFinalDatePhrase();
    if (!form.isInvoice() && form.isFinalDateAbove())
    {
      table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    }

    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.concering"), getFontArial10Bold()));
    phrase.add(new Chunk(" " + form.getConcering(), getFontArial10Normal()));

    table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.page_count"), fontArial11Bold));

    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_CENTER, noBorder));
    table.addCell(cell("", fontArial11Normal, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_CENTER, noBorder));

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight());

    addFirstPageTemplate(writer.getDirectContent(), document.getPageSize().getWidth() - rightMargin - 18, calcHeight + 5);

    if (!form.isInvoice())
    {
      Paragraph para = new Paragraph(form.getPreamble(), getFontArial11Normal());
      para.setSpacingAfter(defSpacing);
      para.setLeading(14);
      document.add(para);
    }
  }

  private void processHead() throws DocumentException, IOException
  {
    Contractor contractor = form.getContractor();
    ContactPerson contactPerson = form.getContactPerson();
    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{0.6f, 0.4f});
    table.getDefaultCell().setBorder(noBorder);

    PdfPTable innerTable = new PdfPTable(2);
    innerTable.setWidths(new float[]{0.22f, 0.78f});
    innerTable.getDefaultCell().setBorder(noBorder);
    innerTable.getDefaultCell().setLeading(0, defRelLeading);
    if (form.isInvoice())
    {
      innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.payer"), fontArial10Normal, 1));
    }
    else
    {
      innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.where"), fontArial10Normal, 1));
    }
    innerTable.addCell(cell(contractor.getFullname() + "\n" + contractor.getAddress() + "\n\n", fontArial10Bold, 1));

    if (!form.isInvoice())
    {
      innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.receiver"), fontArial10Normal, 1));
      innerTable.addCell(cell(contactPerson.getCps_position() + (StringUtil.isEmpty(contactPerson.getCps_position()) ? "" : " ") + contactPerson.getCps_name() + "\n\n", fontArial10Bold, 1));
    }

    innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.phone"), fontArial10Normal, 1));
    innerTable.addCell(cell(contactPerson.getCps_phone(), fontArial10Bold, 1));

    innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.fax"), fontArial10Normal, 1));
    innerTable.addCell(cell(contactPerson.getCps_fax(), fontArial10Bold, 1));
    table.addCell(innerTable);


    innerTable = new PdfPTable(1);
    innerTable.setWidths(new float[]{1f});
    innerTable.getDefaultCell().setBorder(noBorder);
    innerTable.getDefaultCell().setLeading(0, defRelLeading);

    File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
    Image logoImage = Image.getInstance(file.getAbsolutePath());
    logoImage.scaleToFit((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) * 0.4f, 100000);
    logoImage.setBorder(PdfPCell.BOX);
    logoImage.enableBorderSide(PdfPCell.BOX);
    PdfPCell logoCell = new PdfPCell(logoImage, false);
    if (!form.isInvoice())
    {
      logoCell.setPaddingTop(-spaceAfterHeader);
    }
    logoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    logoCell.setBorder(noBorder);
    if (form.isInvoice())
    {
      innerTable.addCell(cell(words.getMessage("rep.CommercialProposal.provider"), fontArial10Normal, 1));
    }
    innerTable.addCell(logoCell);

    if (!form.isInvoice())
    {
      innerTable.addCell(cell("", getFontArial10Bold(), 1));

      file = new File(Config.getString("img-logo.dir"), form.getLogoImage());
      logoImage = Image.getInstance(file.getAbsolutePath());
      logoImage.scalePercent(77);
      logoCell = new PdfPCell(logoImage, false);
      logoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
      logoCell.setBorder(noBorder);
      innerTable.addCell(logoCell);
    }
    table.addCell(innerTable);

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight() + defSpacing);
  }

  private void addTableContractor(PdfPTable table, Contractor contractor, String header) throws DocumentException
  {
    PdfPTable innerTableContractor = new PdfPTable(1);
    innerTableContractor.setSplitLate(false);
    innerTableContractor.setWidths(new float[]{1f});
    innerTableContractor.getDefaultCell().setBorder(noBorder);
    innerTableContractor.addCell(cell(header, getFontArial8Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    Phrase phrase = new Phrase();
    phrase.add(new Chunk(contractor.getFullname() + "\n", getFontArial8Normal()));
    if (StringUtil.isEmpty(contractor.getAddress()))
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk(contractor.getAddress(), getFontArial8Normal()));
    }
    if (StringUtil.isEmpty(contractor.getAccountsFormatted()))
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractAccounts"), getFontArial8Normal()));
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractAccounts", contractor.getAccountsFormatted()), getFontArial8Normal()));
    }
    if (StringUtil.isEmpty(contractor.getBank_props()))
    {
      phrase.add(new Chunk("\n" + words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk("\n" + contractor.getBank_props(), getFontArial8Normal()));
    }
    if (StringUtil.isEmpty(contractor.getUnp()))
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractUNP"), getFontArial8Normal()));
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractUNP", contractor.getUnp()), getFontArial8Normal()));
    }
    if (StringUtil.isEmpty(contractor.getPhone()))
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractPhone"), getFontArial8Normal()));
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractPhone", contractor.getPhone()), getFontArial8Normal()));
    }
    if (StringUtil.isEmpty(contractor.getFax()))
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractFax"), getFontArial8Normal()));
      phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.contractNoInfo"), getFontArial8BoldRed()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractFax", contractor.getFax()), getFontArial8Normal()));
    }

    innerTableContractor.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder, 2));

    table.addCell(innerTableContractor);
  }

  private void processContract(float width) throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);
    table.setWidths(new float[]{0.5f, 0.5f});

    table.addCell(cell(words.getMessage("rep.CommercialProposal.contract", form.getNumber()), getFontArial8Bold(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, noBorder));
    table.addCell(cell(words.getMessage("rep.CommercialProposal.year", form.getDateFullFormat()), getFontArial8Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    table.addCell(cell(words.getMessage("rep.CommercialProposal.Minsk"), getFontArial8Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_CENTER, noBorder));

    Phrase phrase = new Phrase();
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.supplier"), getFontArial8Bold()));
    phrase.add(new Chunk(" " + words.getMessage("rep.CommercialProposal.contractHeaderText1",
            form.getContactPersonSeller().getCps_contract_comment(),
            form.getContractor().getFullname(),
            StringUtil.isEmpty(form.getContactPersonCustomer().getCps_id()) || StringUtil.isEmpty(form.getContactPersonCustomer().getCps_contract_comment()) ? words.getMessage("rep.CommercialProposal.contractHeaderText2") : form.getContactPersonCustomer().getCps_contract_comment()
    ), getFontArial8Normal()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractHeaderText3"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractHeaderText4"), getFontArial8Normal()));
    table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    document.add(table);
    calcHeight -= table.getTotalHeight();

    processGrid(width, 2);

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);
    table.setWidths(new float[]{1f});

    phrase = new Phrase();
    String contractHeaderText5Point3_1 = words.getMessage("rep.CommercialProposal.contractHeaderText5_1");
    if (form.getGuarantyInMonth() > 0)
    {
      contractHeaderText5Point3_1 = words.getMessage("rep.CommercialProposal.contractHeaderText5_2", form.getGuarantyInMonth(), NumberToWordsRussian.toWords(new BigDecimal(form.getGuarantyInMonth())));
    }
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractHeaderText5",
				    StringUtil.getStrSum(form.getTotalPrint(), false, "BYN", words),
				    StringUtil.getStrSum(form.getNdsPrint(), false, "BYN", words),
            form.getPurchasePurpose().getName(),
            contractHeaderText5Point3_1
    ), getFontArial8Normal()));

    if (form.getPrepayPercent() == 100)
    {
      processContract100Percent(phrase);
    }
    else if (form.getPrepayPercent() == 0)
    {
      processContract0Percent(phrase);
    }
    else
    {
      processContractOther(phrase);
    }
    processContractCommonBottomText(phrase);

    table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder, 2));

    document.add(table);
    calcHeight -= (table.getTotalHeight());

    table = new PdfPTable(2);
    table.setSplitLate(true);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);
    table.setWidths(new float[]{0.5f, 0.5f});

    table.addCell(cell(words.getMessage("rep.CommercialProposal.contractAddresses"), getFontArial8Bold(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, noBorder));

    addTableContractor(table, form.getContractorSeller(), words.getMessage("rep.CommercialProposal.contractSeller"));
    addTableContractor(table, form.getContractor(), words.getMessage("rep.CommercialProposal.contractCustomer"));
    if (!StringUtil.isEmpty(form.getConsignee().getId()))
    {
      table.addCell(cell(" ", getFontArial8Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
      addTableContractor(table, form.getConsignee(), words.getMessage("rep.CommercialProposal.contractConsignee"));
    }

    table.addCell(cell(words.getMessage("rep.CommercialProposal.contractSign"), getFontArial8Bold(), 2, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, noBorder));

    PdfPTable innerTableFromSeller = new PdfPTable(1);
    innerTableFromSeller.setSplitLate(false);
    innerTableFromSeller.setWidths(new float[]{1f});
    innerTableFromSeller.getDefaultCell().setBorder(noBorder);
    innerTableFromSeller.addCell(cell(words.getMessage("rep.CommercialProposal.contractFromSeller"), getFontArial8Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    innerTableFromSeller.addCell(cell(words.getMessage("rep.CommercialProposal.contractLine", form.getContactPersonSeller().getCps_name()), getFontArial8Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    table.addCell(innerTableFromSeller);

    PdfPTable innerTableFromCustomer = new PdfPTable(1);
    innerTableFromCustomer.setSplitLate(false);
    innerTableFromCustomer.setWidths(new float[]{1f});
    innerTableFromCustomer.getDefaultCell().setBorder(noBorder);
    innerTableFromCustomer.addCell(cell(words.getMessage("rep.CommercialProposal.contractFromCustomer"), getFontArial8Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));
    innerTableFromCustomer.addCell(cell(words.getMessage("rep.CommercialProposal.contractLine", form.getContactPersonCustomer().getCps_name()), getFontArial8Bold(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, noBorder));

    table.addCell(innerTableFromCustomer);

    document.add(table);
    calcHeight -= (table.getTotalHeight());
  }

  private void processContract100Percent(Phrase phrase) throws DocumentException, IOException
  {
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText1"), getFontArial8Bold()));
    if (form.isProviderDelivery())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_100_providerDelivery", form.getProviderDeliveryAddress(), form.getDeliveryCountDay(), form.getDeliveryCountDayInWords()), getFontArial8Normal()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_100"), getFontArial8Normal()));
    }
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText3"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText4_100", form.getFinalDateFullFormat()), getFontArial8Normal()));
  }

  private void processContract0Percent(Phrase phrase) throws DocumentException, IOException
  {
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText1"), getFontArial8Bold()));
    if (form.isProviderDelivery())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_0_providerDelivery", form.getProviderDeliveryAddress(), form.getDeliveryCountDay(), form.getDeliveryCountDayInWords()), getFontArial8Normal()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_0", form.getFinalDateFullFormat()), getFontArial8Normal()));
    }
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText3"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText4_0", form.getDelayDays(), form.getDelayDaysInWords()), getFontArial8Normal()));
  }

  private void processContractOther(Phrase phrase) throws DocumentException, IOException
  {
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText1"), getFontArial8Bold()));
    if (form.isProviderDelivery())
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_2_providerDelivery", form.getProviderDeliveryAddress(), form.getDeliveryCountDay(), form.getDeliveryCountDayInWords()), getFontArial8Normal()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText2_2"), getFontArial8Normal()));
    }
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText3"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractText4_2", new Object[]{
            StringUtil.double2appCurrencyString(form.getPrepayPercent()),
            form.getPrepaySum(),
            form.getFinalDateFullFormat(),
            StringUtil.double2appCurrencyString(100 - form.getPrepayPercent()),
            StringUtil.double2appCurrencyString(form.getTotalPrint() - form.getPrepaySum()),
            form.getDelayDays(),
            form.getDelayDaysInWords()
    }), getFontArial8Normal()));
  }

  private void processContractCommonBottomText(Phrase phrase) throws DocumentException, IOException
  {
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText1"), getFontArial8Bold()));
    if (form.getPrepayPercent() == 100)
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText2_100"), getFontArial8Normal()));
    }
    else if (form.getPrepayPercent() == 0)
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText2_0"), getFontArial8Normal()));
    }
    else
    {
      phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText2_2"), getFontArial8Normal()));
    }
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText3"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText4"), getFontArial8Normal()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText5"), getFontArial8Bold()));
    phrase.add(new Chunk(words.getMessage("rep.CommercialProposal.contractBottomText6"), getFontArial8Normal()));
  }

  protected PdfPTable generateHeader(float width) throws DocumentException, IOException
  {
    PdfPTable head = new PdfPTable(2);
    head.setWidths(new float[]{0.5f, 0.5f});
    head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    if (!form.isContract())
    {
      addImage(width, head, blank.getImage(Blank.topImg));
    }

    head.setTotalWidth(width);
    return head;
  }

  protected PdfPTable generateFooter(float width) throws DocumentException, IOException
  {
    PdfPTable foot = new PdfPTable(2);
    if (!form.isInvoice() && !form.isContract())
    {
      foot.setWidths(new float[]{0.5f, 0.5f});
      foot.getDefaultCell().setBorder(noBorder);

      addImage(width, foot, blank.getImage(Blank.bottomImg));
    }

    foot.setTotalWidth(width);
    return foot;
  }

  protected void addImage(float width, PdfPTable head, String image) throws BadElementException, IOException
  {
    File file = new File(Config.getString("img-head.dir"), image);
    Image topImage = Image.getInstance(file.getAbsolutePath());

    topImage.scaleToFit(width, 100000);
    topImage.setBorder(PdfPCell.BOX);
    PdfPCell topCell = new PdfPCell(topImage, false);
    topCell.setBorder(noBorder);

    topCell.setColspan(2);
    head.addCell(topCell);
  }

  public void printPages(PdfContentByte dc)
  {
    //writing Страница [current_page] из [XXX]
    String text;
    if (form.isContract())
    {
      text = words.getMessage("rep.CommercialProposal.pageForContract", writer.getPageNumber());
    }
    else
    {
      text = words.getMessage("rep.CommercialProposal.page", writer.getPageNumber());
    }
    float textSize = fontArial7P5Normal.getBaseFont().getWidthPoint(text, fontArial7P5Normal.getCalculatedSize());
    float textBase = bottomMargin + footer.getTotalHeight() + 3;
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
    dc.beginText();
    dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
    float adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("0", fontArial7P5Normal.getCalculatedSize());
    float textBase = bottomMargin + footer.getTotalHeight() + 13;
    dc.setTextMatrix(document.left() + adjust, textBase);
    dc.showText(docName);
    dc.endText();

    dc.beginText();
    dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
    adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("0", fontArial7P5Normal.getCalculatedSize());
    textBase = bottomMargin + footer.getTotalHeight() + 3;
    dc.setTextMatrix(document.left() + adjust, textBase);
    dc.showText(docName1);
    dc.endText();
  }
}
