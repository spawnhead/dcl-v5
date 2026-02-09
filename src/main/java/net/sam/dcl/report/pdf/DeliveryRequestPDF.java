package net.sam.dcl.report.pdf;

import net.sam.dcl.form.DeliveryRequestPrintForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.DeliveryRequestProduce;
import net.sam.dcl.dao.ProduceDAO;

import java.io.OutputStream;
import java.io.IOException;
import java.util.Locale;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfContentByte;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//test
/**
 * Demonstrates the use of PageEvents.
 */
public class DeliveryRequestPDF extends DocumentTemplatePDF
{
  protected static Log log = LogFactory.getLog(DeliveryRequestPDF.class);
  DeliveryRequestPrintForm form = null;
  String docName = "";

  float defSpacing = 10;
  float calcHeight = 0;
  LocaledPropertyMessageResources words = new LocaledPropertyMessageResources("resources/report", new Locale("RU"));

  public DeliveryRequestPDF(OutputStream outStream, DeliveryRequestPrintForm form)
  {
    super(outStream, "Cp1251");
    this.form = form;
  }

  public void parentProcess() throws DocumentException, IOException
  {
    firstPagePagesCountFont = getFontArial9Bold();
    calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
    float width = document.getPageSize().getWidth();
    processHead();
    processGrid(width);
    processFooter(width);
  }

  private void processHead() throws DocumentException, IOException
  {
    PdfPTable tableRight = new PdfPTable(2);
    tableRight.setSplitLate(false);
    tableRight.setWidthPercentage(100);
    tableRight.setWidths(new float[]{0.6f, 0.4f});
    tableRight.getDefaultCell().setBorder(noBorder);

    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.affirm"), getFontArial11Normal(), 1));

    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.who_affirm"), getFontArial11Normal(), 1));

    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.date_affirm"), getFontArial11Normal(), 1));

    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.request_took"), getFontArial11Normal(), 1));

    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.line"), getFontArial11Normal(), 1));
    tableRight.addCell(cell("", getFontArial11Bold(), 1));
    tableRight.addCell(cell(words.getMessage("rep.DeliveryRequest.line"), getFontArial11Normal(), 1));

    tableRight.setSpacingAfter(defSpacing);
    document.add(tableRight);
    calcHeight -= (tableRight.getTotalHeight());

    PdfPTable table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.title"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    if ( !StringUtil.isEmpty(form.getDlr_place_request()) )
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.num_date", form.getDlr_number(), form.getUsr_date_place()), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.rough"), getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    }
    //0 - to Minsk, 1 - from Minsk
    if ( "0".equals(form.getDlr_minsk()) )
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.wherefrom", form.getDlr_wherefrom()), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    }
    else
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.wherefrom1", form.getDlr_wherefrom()), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
    }
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( !StrutsUtil.getMessage(context, "DeliveryRequest.dlr_comment_default").equals(form.getDlr_comment()) )
      {
        table.addCell(cell(words.getMessage("rep.DeliveryRequest.comment", form.getDlr_comment()), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight());
  }

  private void processFooter(float width) throws DocumentException
  {
    if ( !StringUtil.isEmpty(form.getDlr_include_in_spec()) )
    {
      PdfPTable tableIncludeInSpec = new PdfPTable(1);
      tableIncludeInSpec.setSplitLate(false);
      tableIncludeInSpec.setWidthPercentage(100);
      tableIncludeInSpec.setWidths(new float[]{1f});
      tableIncludeInSpec.getDefaultCell().setBorder(noBorder);
      tableIncludeInSpec.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_text_incl_in_spec"), getFontArial11Bold(), 1));
      tableIncludeInSpec.setSpacingAfter(defSpacing);
      document.add(tableIncludeInSpec);
    }

    PdfPTable table = new PdfPTable(2);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);

    table.setWidths(new float[]{0.6f, 0.4f});

    table.addCell(cell(words.getMessage("rep.DeliveryRequest.make_up", form.getPlaceUser().getUsr_position(), form.getPlaceUser().getUserFullName()), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.line"), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));

    table.addCell(cell("", getFontArial11Normal(), 1));
    table.addCell(cell("", getFontArial11Normal(), 1));

    /**
     * 1. <должность> <фамилия имя> из справочника "Пользователи" начальника отдела, код сотрудника которого указан в номере документа
     * 2. Если заявку составил пользователь, который в справочнике "Пользователи" помечен как начальник отдела, то эта строка в печатном документе вообще не отображается
     */
    if ( !form.getPlaceUser().isChiefDepartment() )
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.conform", form.getChiefDep().getUsr_position(), form.getChiefDep().getUserFullName()), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE));
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.line"), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));

      table.addCell(cell("", getFontArial11Normal(), 1));
      table.addCell(cell("", getFontArial11Normal(), 1));
    }

    table.setSpacingAfter(defSpacing);
    document.add(table);
    calcHeight -= (table.getTotalHeight());

    table = new PdfPTable(1);
    table.setSplitLate(false);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1f});
    table.getDefaultCell().setBorder(noBorder);
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_text1"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_text2"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_line"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_line"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_line"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_line"), getFontArial11Normal(), 1));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.bottom_line"), getFontArial11Normal(), 1));
    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  private void produceRow(DeliveryRequestProduce produce, PdfPTable table, boolean needAddColumn, boolean haveDepend, boolean isDepend)
  {
    Font normalFont = getFontArial10Normal();

    Integer number = 0;
    if ( !isDepend )
    {
      number = new Integer(produce.getNumber());
    }
    number += 1;
    table.addCell(cell(isDepend ? "" : number.toString(), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if ( haveDepend )
    {
      Phrase phrase = new Phrase();
      phrase.add(new Chunk(produce.getProduce().getName(), normalFont));
      phrase.add(new Chunk("\n" + words.getMessage("rep.DeliveryRequest.haveDepend"), getFontArial10Bold()));
      table.addCell(cell(phrase, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    else
    {
      table.addCell(cell((isDepend ? " - " : "") + produce.getProduce().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    table.addCell(cell(produce.getProduce().getType(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(produce.getProduce().getParams(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(produce.getProduce().getAddParams(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(produce.getCatalogNumberForStuffCategory(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(produce.getProduce().getUnit().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if (produce.getDrp_count() == (double) ((long) produce.getDrp_count()))
    {
      table.addCell(cell(StringUtil.double2appCurrencyStringByMask(produce.getDrp_count(), "###0"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    else
    {
      table.addCell(cell(StringUtil.double2appCurrencyStringByMask(produce.getDrp_count(), "###0.00"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
    if ( StringUtil.isEmpty(form.getDlr_need_deliver()) && needAddColumn )
    {
      if ( form.isNumDateOrdShowForFairTrade() )
      {
        table.addCell(cell(produce.getOrd_number_date(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
      else if ( !StringUtil.isEmpty(form.getDlr_ord_not_form()) )
      {
        table.addCell(cell(words.getMessage("rep.DeliveryRequest.no_ord"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
      }
    }
    table.addCell(cell(produce.getDrp_purpose(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if ( !StringUtil.isEmpty(form.getDlr_need_deliver()) )
    {
      table.addCell(cell(produce.getSpi_number_date(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }
  }

  private void processGrid(float width) throws DocumentException
  {
    PdfPTable table;
    int colCount = 9;
    boolean needAddColumn = false;
    if ( form.isNumDateOrdShowForFairTrade() || !StringUtil.isEmpty(form.getDlr_ord_not_form()) || !StringUtil.isEmpty(form.getDlr_need_deliver()) )
    {
      needAddColumn = true;
      colCount++;
    }
    table = new PdfPTable(colCount);
    table.setSplitLate(true);
    table.setWidthPercentage(100);
    table.setTotalWidth(width);
    if ( needAddColumn )
    {
      if ( !StringUtil.isEmpty(form.getDlr_need_deliver()) )
      {
        table.setWidths(new float[]{0.05f, 0.15f, 0.08f, 0.1f, 0.1f, 0.11f, 0.07f, 0.06f, 0.15f, 0.13f});
      }
      else
      {
        table.setWidths(new float[]{0.05f, 0.15f, 0.08f, 0.1f, 0.1f, 0.11f, 0.07f, 0.06f, 0.13f, 0.15f});
      }
    }
    else
    {
      table.setWidths(new float[]{0.05f, 0.2f, 0.08f, 0.1f, 0.1f, 0.13f, 0.08f, 0.06f, 0.2f});
    }
    table.getDefaultCell().setBorder(noBorder);

    Font boldFont = getFontArial8Bold();
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.num"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_name"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_type"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_params"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_add_params"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_num"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_unit"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_count"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if ( StringUtil.isEmpty(form.getDlr_need_deliver()) && needAddColumn )
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.ord_num_date"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));  
    }
    table.addCell(cell(words.getMessage("rep.DeliveryRequest.article_purpose"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    if ( !StringUtil.isEmpty(form.getDlr_need_deliver()) )
    {
      table.addCell(cell(words.getMessage("rep.DeliveryRequest.spi_num_date"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
    }

    for (int i = 0; i < form.getProduces().size(); i++)
    {
      DeliveryRequestProduce produce = (DeliveryRequestProduce) form.getProduces().get(i);
      produceRow(produce, table, needAddColumn, !StringUtil.isEmpty(produce.getDrp_have_depend()), false);

      if (!StringUtil.isEmpty(produce.getDrp_have_depend()))
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          java.util.List lst = DAOUtils.fillList(context, "select-drp_depended_lines", produce, DeliveryRequestProduce.class, null, null);
          for (int j = 0; j < lst.size(); j++)
          {
            DeliveryRequestProduce deliveryRequestProduceDepend = (DeliveryRequestProduce) lst.get(j);
            if (null != deliveryRequestProduceDepend.getProduce().getId())
            {
              deliveryRequestProduceDepend.setProduce(ProduceDAO.loadProduce(deliveryRequestProduceDepend.getProduce().getId()));
              if ( "-1".equals(deliveryRequestProduceDepend.getId()) )
              {
                deliveryRequestProduceDepend.setSip_count(deliveryRequestProduceDepend.getDrp_count() * deliveryRequestProduceDepend.getDrp_count());
              }
              produceRow(deliveryRequestProduceDepend, table, needAddColumn, false, true);
            }
          }
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

    }
    table.setSpacingAfter(defSpacing);
    document.add(table);
  }

  public void printPages(PdfContentByte dc)
  {
    String text = words.getMessage("rep.DeliveryRequest.page", writer.getPageNumber());
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
  }
}
