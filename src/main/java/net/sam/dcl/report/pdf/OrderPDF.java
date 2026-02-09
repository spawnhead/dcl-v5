package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.form.OrderForm;
import net.sam.dcl.form.OrderPrintForm;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//test

/**
 * Demonstrates the use of PageEvents.
 */
public class OrderPDF extends DocumentTemplatePDF
{
	protected static Log log = LogFactory.getLog(OrderProduce.class);
	OrderPrintForm form = null;
	Blank blank = null;
	String docName = "";

	float defSpacing = 10;
	float calcHeight = 0;
	LocaledPropertyMessageResources words;

	public OrderPDF(OutputStream outStream, Blank blank, OrderPrintForm form, LocaledPropertyMessageResources words)
	{
		super(outStream, blank.getBln_charset());
		headerLeftMargin = headerRightMargin = 0;
		topMargin = bottomMargin = 15;
		this.words = words;
		this.docName = words.getMessage("rep.Order.number", form.getNumber(), form.getDate());
		this.blank = blank;
		this.form = form;
	}

	public void parentProcess() throws DocumentException, IOException
	{
		firstPagePagesCountFont = fontArial9Bold;
		calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
		float width = document.getPageSize().getWidth();
		processHead(width);
		processHead2();
		processGrid(width);
		processFooter(width);
		processFooter2(width);
	}

	private void processFooter2(float width) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);

		table.setWidths(new float[]{0.8f, 0.6f});

		table.addCell(cell(words.getMessage("rep.Order.respect"), getFontArial11Bold(), 1));
		table.addCell(cell("", getFontArial11Normal(), 1));

		table.addCell(cell(findUserLang(form.getDirector()).getUsr_position(), getFontArial11Bold(), 1));
		table.addCell(cell(findUserLang(form.getDirector()).getUsr_name() + " " + findUserLang(form.getDirector()).getUsr_surname(),
						getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_BOTTOM));

		if (form.isLogistSignature())
		{
			table.addCell(cell(findUserLang(form.getLogist()).getUsr_position(), getFontArial11Bold(), 1));
			table.addCell(cell(findUserLang(form.getLogist()).getUsr_name() + " " + findUserLang(form.getLogist()).getUsr_surname(),
							getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_BOTTOM));
		}

		if (form.isDirectorRBSignature())
		{
			table.addCell(cell(findUserLang(form.getDirectorRB()).getUsr_position(), getFontArial11Bold(), 1));
			table.addCell(cell(findUserLang(form.getDirectorRB()).getUsr_name() + " " + findUserLang(form.getDirectorRB()).getUsr_surname(),
							getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_BOTTOM));
		}

		if (form.isChiefDepSignature() && !form.getChiefDep().getUsr_id().equals(form.getManager().getUsr_id()))
		{
			table.addCell(cell(findUserLang(form.getChiefDep()).getUsr_position(), getFontArial11Bold(), 1));
			table.addCell(cell(findUserLang(form.getChiefDep()).getUsr_name() + " " + findUserLang(form.getChiefDep()).getUsr_surname(),
							getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_BOTTOM));
		}

		if (form.isManagerSignature())
		{
			table.addCell(cell(findUserLang(form.getManager()).getUsr_position(), getFontArial11Bold(), 1));
			table.addCell(cell(findUserLang(form.getManager()).getUsr_name() + " " + findUserLang(form.getManager()).getUsr_surname(),
							getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_BOTTOM));
			table.addCell(cell(words.getMessage("rep.Order.email", form.getManager().getUsr_email()), getFontArial11Bold(), 2));
			table.addCell(cell(words.getMessage("rep.Order.phone_fax", form.getManager().getUsr_phone(), form.getManager().getUsr_fax()), getFontArial11Bold(), 2));
		}

		table.setSpacingAfter(defSpacing);
		document.add(table);
	}

	private UserLanguage findUserLang(User user)
	{
		UserLanguage lang = new UserLanguage();
		for (int i = 0; i < user.getUser_language().size(); i++)
		{
			UserLanguage userLanguage = user.getUser_language().get(i);
			if (userLanguage.getLanguage().getLetterId().equals(blank.getLanguage().getLetterId()))
			{
				lang = userLanguage;
			}
		}
		return lang;
	}

	private void processFooter(float width) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);

		table.setWidths(new float[]{0.4f, 0.6f});
		if (form.isPrintDelivery())
		{
			Paragraph para = new Paragraph(
							words.getMessage("rep.Order.delivery", form.getDeliveryCostBy(), form.getDeliveryCost(), form.getCurrency().getName()),
							getFontArial11Normal());
			para.setSpacingAfter(defSpacing);
			para.setLeading(14);
			document.add(para);
		}

		table.addCell(cell(words.getMessage("rep.Order.delivery_cond"), getFontArial11Bold(), 1));
		table.addCell(cell(words.getMessage("rep.Order." + form.getDeliveryCondition().getName()) + " " + form.getDeliveryAddress(), getFontArial11Normal(), 1));

		table.addCell(cell(words.getMessage("rep.Order.delivery_date"), getFontArial11Bold(), 1));
		table.addCell(cell(form.getDeliveryTerm(), getFontArial11Normal(), 1));

		table.addCell(cell(words.getMessage("rep.Order.pay_cond"), getFontArial11Bold(), 1));
		table.addCell(cell(form.getPayCondition(), getFontArial11Normal(), 1));

		Phrase phrase = new Phrase();
		phrase.add(new Chunk(words.getMessage("rep.Order.notes"), getFontArial11Bold()));
		if (StringUtil.isEmpty(form.getAdditionalInfo()))
		{
			phrase.add(new Chunk(" " + form.getAdditionalInfo2(), getFontArial11Normal()));
		}
		else
		{
			phrase.add(new Chunk(" " + form.getAdditionalInfo() + "\n" + form.getAdditionalInfo2(), getFontArial11Normal()));
		}
		table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, noBorder));

		table.setSpacingAfter(defSpacing);
		document.add(table);
	}

	private void processGrid(float width) throws DocumentException
	{
		AutoWidthsTable table;
		int columnCount = 6;
		if ("1".equals(form.getImportFlag()) || "1".equals(form.getCountTotal()))
		{
			if ("1".equals(form.getShow_unit()))
			{
				columnCount++;
			}
		}
		else
		{
			columnCount += 2;
			if ("1".equals(form.getShow_unit()))
			{
				columnCount++;
			}
		}
		table = new AutoWidthsTable(columnCount);
		table.setSideMargins(leftMargin + rightMargin);
		table.addReducibleColumnIndex(1);
		table.setSplitLate(true);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);

		table.getDefaultCell().setBorder(noBorder);

		Font boldFont = getFontArial8Bold();
		Font normalFont = getFontArial11Normal();
		Font normalFontRed = getFontArial11NormalRed();
		table.addCell(cell(words.getMessage("rep.Order.num"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.Order.article_name"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.Order.article_num"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.Order.article_count"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		if ("1".equals(form.getShow_unit()))
		{
			table.addCell(cell(words.getMessage("rep.Order.article_unit"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		}
		if (!"1".equals(form.getImportFlag()))
		{
			table.addCell(cell(words.getMessage("rep.Order.article_brutto", form.getCurrency().getName()), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			if (!"1".equals(form.getCountTotal()))
			{
				table.addCell(cell(words.getMessage("rep.Order.article_discount"), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			}
		}
		if (!"1".equals(form.getCountTotal()))
		{
			table.addCell(cell(words.getMessage("rep.Order.article_netto", form.getCurrency().getName()), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		}
		table.addCell(cell(words.getMessage("rep.Order.article_cost", form.getCurrency().getName()), boldFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));

		int num = 1;
		for (int i = 0; i < form.getProduces().size() - form.getTotalLinesCount(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);

			int border;
			String val = String.valueOf(num);// produce.getViewNumber();
			if (!"1".equals(produce.getOpr_use_prev_number()))
			{
				border = PdfPCell.LEFT + PdfPCell.TOP;
				num++;
			}
			else
			{
				border = PdfPCell.LEFT;
				val = "";
			}

			table.addCell(cell(val, normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, border));
			if (null == produce.getProduce().getId())
			{
				table.addCell(cell(produce.getOpr_produce_name() +
								(StringUtil.isEmpty(produce.getOpr_comment()) ? "" : "\n" + produce.getOpr_comment()),
								normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
			}
			else
			{
				String prdName = produce.getProduceNameForLanguage(blank.getLanguage().getLetterId());
				IActionContext context = ActionContext.threadInstance();
				String defaultStr = "";
				try
				{
					defaultStr = StrutsUtil.getMessage(context, "OrderProduce.default_print_no_translate");
				}
				catch (Exception e)
				{
					log.error(e);
				}
				if (defaultStr.equals(prdName))
				{
					table.addCell(cell(defaultStr, normalFontRed, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
				}
				else
				{
					table.addCell(cell(produce.getProduceNameForLanguage(blank.getLanguage().getLetterId()) + " " +
									(StringUtil.isEmpty(produce.getProduce().getType()) ? "" : produce.getProduce().getType() + " ") +
									(StringUtil.isEmpty(produce.getProduce().getParams()) ? "" : produce.getProduce().getParams() + " ") +
									(StringUtil.isEmpty(produce.getProduce().getAddParams()) ? "" : produce.getProduce().getAddParams() + " ") +
									(StringUtil.isEmpty(produce.getOpr_comment()) ? "" : "\n" + produce.getOpr_comment()),
									normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
				}
			}

			table.addCell(cell(StringUtil.isEmpty(produce.getCatalogNumberForStuffCategory()) ? produce.getOpr_catalog_num() : produce.getCatalogNumberForStuffCategory(), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			if (produce.getOpr_count() == (double) ((long) produce.getOpr_count()))
			{
				table.addCell(cell(StringUtil.double2appCurrencyStringByMask(produce.getOpr_count(), "###0"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			}
			else
			{
				table.addCell(cell(StringUtil.double2appCurrencyStringByMask(produce.getOpr_count(), "###0.00"), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			}
			if ("1".equals(form.getShow_unit()))
			{
				table.addCell(cell(produce.getUnitNameForLanguage(blank.getLanguage().getLetterId()), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			}
			if (!"1".equals(form.getImportFlag()))
			{
				table.addCell(cell(produce.getOpr_price_brutto_formatted(), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
				if (!"1".equals(form.getCountTotal()))
				{
					table.addCell(cell(produce.getOpr_discount_formatted(), normalFont, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
				}
			}
			if (!"1".equals(form.getCountTotal()))
			{
				table.addCell(cell(produce.getOpr_price_netto_formatted(), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
			}
			table.addCell(cell(produce.getOpr_summ_formatted(), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, PdfPCell.BOX));
		}
		int colSpan = 7;
		if ("1".equals(form.getImportFlag()) || "1".equals(form.getCountTotal()))
		{
			colSpan = 5;
		}
		if ("1".equals(form.getShow_unit()))
		{
			colSpan++;
		}
		for (int i = form.getProduces().size() - form.getTotalLinesCount(); i < form.getProduces().size(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);
			table.addCell(cell(produce.getOpr_produce_name(), normalFont, colSpan, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
			table.addCell(cell(produce.getOpr_summ_formatted(), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		}
		if (form.isPrintDelivery())
		{
			table.addCell(cell(words.getMessage("rep.Order.delivery.demand.type", form.getDeliveryCostBy()), normalFont, colSpan, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
			table.addCell(cell(words.getMessage("rep.Order.delivery.demand.cost", form.getDeliveryCost(), form.getCurrency().getName()), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		}
		table.setSpacingAfter(defSpacing);
		table.recalcWidths();
		document.add(table);
	}

	private void processHead2() throws DocumentException
	{
		PdfPTable table = new PdfPTable(6);
		table.setSplitLate(false);
		table.setWidthPercentage(100);

		table.setWidths(new float[]{0.14f, 0.37f, 0.05f, 0.3f, 0.09f, 0.16f});
		table.getDefaultCell().setBorder(noBorder);

		table.addCell(cell("", fontArial11Bold, 2));

		table.addCell(cell(words.getMessage("rep.Order.title"), fontArial11Bold, 4, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE));
		PdfPCell cell = cell(words.getMessage("rep.Order.about"), fontArial9Bold, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP);
		cell.setPaddingTop(2);
		table.addCell(cell);
		table.addCell(cell(form.getConcering(), fontArial11Normal, 1));
		cell = cell(words.getMessage("rep.Order.num2"), fontArial9Bold, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP);
		cell.setPaddingTop(2);
		table.addCell(cell);

		PdfPTable innerTable = new PdfPTable(1);
		innerTable.setSpacingAfter(0);
		innerTable.setSpacingBefore(0);
		innerTable.setSplitLate(false);
		innerTable.setWidthPercentage(100);
		innerTable.setWidths(new float[]{1f});
		innerTable.getDefaultCell().setBorder(noBorder);
		cell = cell(form.getNumber(), fontArial11Normal, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP);
		cell.setPaddingTop(-2);
		innerTable.addCell(cell);
		cell = cell(words.getMessage("rep.Order.use-number"), fontArial9Normal, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP);
		cell.setPaddingTop(-6);
		innerTable.addCell(cell);
		table.addCell(innerTable);

		cell = cell(words.getMessage("rep.Order.date"), fontArial9Bold, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP);
		cell.setPaddingTop(2);
		table.addCell(cell);

		innerTable = new PdfPTable(1);
		innerTable.setSpacingAfter(0);
		innerTable.setSpacingBefore(0);
		innerTable.setSplitLate(false);
		innerTable.setWidthPercentage(100);
		innerTable.setWidths(new float[]{1f});
		innerTable.getDefaultCell().setBorder(noBorder);

		innerTable.addCell(cell(form.getDate(), fontArial11Normal, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
		PdfPCell tmp =
						cell(words.getMessage("rep.Order.page_count"), fontArial9Bold, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_BOTTOM);
		tmp.setPaddingLeft(17);
		innerTable.addCell(tmp);

		table.addCell(innerTable);

		table.setSpacingAfter(defSpacing);
		document.add(table);
		calcHeight -= (table.getTotalHeight());

		addFirstPageTemplate(writer.getDirectContent(), document.getPageSize().getWidth() - rightMargin - 18, calcHeight + 7);

		Paragraph para = new Paragraph(form.getPreamble() + (StringUtil.isEmpty(form.getPreamble2()) ? "" : "\n" + form.getPreamble2()), getFontArial11Normal());
		para.setSpacingAfter(defSpacing);
		para.setLeading(14);
		document.add(para);
	}

	private void processHead(float width) throws DocumentException, IOException
	{
		Contractor contractor = form.getContractor();
		ContactPerson contactPerson = form.getContact_person();
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setWidthPercentage(100);
		table.setWidths(new float[]{0.6f, 0.4f});
		table.getDefaultCell().setBorder(noBorder);

		PdfPTable innerTable = new PdfPTable(2);
		innerTable.setWidths(new float[]{0.20f, 0.80f});
		innerTable.getDefaultCell().setBorder(noBorder);
		innerTable.getDefaultCell().setLeading(0, defRelLeading);

		innerTable.addCell(cell(words.getMessage("rep.Order.receiver"), fontArial10Bold, 1));
		innerTable.addCell(cell(contactPerson.getCps_name() + "\n", fontArial10NormalUnderline, 1));

		innerTable.addCell(cell(words.getMessage("rep.Order.where"), fontArial10Normal, 1));
		innerTable.addCell(cell(contractor.getFullname() + "\n" + contractor.getAddress() + "\n", fontArial10Bold, 1));

		innerTable.addCell(cell(words.getMessage("rep.Order.phone"), fontArial10Normal, 1));
		innerTable.addCell(cell(contactPerson.getCps_phone(), fontArial10Bold, 1));

		innerTable.addCell(cell(words.getMessage("rep.Order.fax"), fontArial10Normal, 1));
		innerTable.addCell(cell(contactPerson.getCps_fax(), fontArial10Bold, 1));
		table.addCell(innerTable);

		File file = new File(Config.getString("img-head.dir"), blank.getImage(Blank.rightImg));
		Image logoImage = Image.getInstance(file.getAbsolutePath());
		logoImage.scaleToFit((width - document.leftMargin() - document.rightMargin()) * 0.4f, 100000);
		logoImage.setBorder(PdfPCell.BOX);
		logoImage.enableBorderSide(PdfPCell.BOX);
		PdfPCell logoCell = new PdfPCell(logoImage, false);
		logoCell.setPaddingTop(-spaceAfterHeader);
		logoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		logoCell.setBorder(noBorder);
		table.addCell(logoCell);
		table.setSpacingAfter(defSpacing);
		document.add(table);
		calcHeight -= (table.getTotalHeight() + defSpacing);
	}

	protected PdfPTable generateHeader(float width) throws DocumentException, IOException
	{
		PdfPTable head = new PdfPTable(2);
		head.setWidths(new float[]{0.5f, 0.5f});
		head.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		addImage(width, head, blank.getImage(Blank.topImg));


		head.setTotalWidth(width);
		return head;
	}

	protected PdfPTable generateFooter(float width) throws DocumentException, IOException
	{
		PdfPTable foot = new PdfPTable(2);
		foot.setWidths(new float[]{0.5f, 0.5f});
		foot.getDefaultCell().setBorder(noBorder);

		addImage(width, foot, blank.getImage(Blank.bottomImg));
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
		//writing ???????? [current_page] ?? [XXX]
		String text = words.getMessage("rep.Order.page", writer.getPageNumber());
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
		String text = docName;
		dc.beginText();
		dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
		float adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("0", fontArial7P5Normal.getCalculatedSize());
		float textBase = bottomMargin + footer.getTotalHeight() + 3;
		dc.setTextMatrix(document.left() + adjust, textBase);
		dc.showText(text);
		dc.endText();

	}

	public LocaledPropertyMessageResources getWords()
	{
		return words;
	}
}
