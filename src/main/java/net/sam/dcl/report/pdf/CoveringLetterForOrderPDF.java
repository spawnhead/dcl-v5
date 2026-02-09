package net.sam.dcl.report.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.CoveringLetterForOrderPrintForm;
import net.sam.dcl.form.OrderForm;
import net.sam.dcl.service.helper.ProduceMovementHelper;
import net.sam.dcl.log.Log;
import net.sam.dcl.report.pdf.covering.letter.RestInfo;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

//test

/**
 * Demonstrates the use of PageEvents.
 */
public class CoveringLetterForOrderPDF extends DocumentTemplatePDF
{
	CoveringLetterForOrderPrintForm form = null;
	String docName = "";

	float defSpacing = 10;
	float calcHeight = 0;
	LocaledPropertyMessageResources words;
	private IActionContext actionContext = ActionContext.threadInstance();

	public CoveringLetterForOrderPDF(OutputStream outStream, CoveringLetterForOrderPrintForm form, LocaledPropertyMessageResources words)
	{
		super(outStream, "Cp1251");
		this.form = form;
		this.words = words;
	}

	public void parentProcess() throws DocumentException, IOException
	{
		calcHeight = document.getPageSize().getHeight() - (header.getTotalHeight() + topMargin + spaceAfterHeader);
		float width = document.getPageSize().getWidth();
		boolean firstPage = true;
		for (CoveringLetterForOrderPrintForm.Page page : form.getPages())
		{
			if (!firstPage)
			{
				document.newPage();
			}
			firstPage = false;
			processGrid(width, page);
		}
		try
		{
			processFooter(width);
		}
		catch (Exception e)
		{
			Log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	protected PdfPTable generateHeader(float width) throws DocumentException, IOException
	{
		PdfPTable table = new PdfPTable(1);
		table.setSplitLate(false);
		table.setWidthPercentage(100);
		table.setWidths(new float[]{1f});
		table.getDefaultCell().setBorder(noBorder);
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.title"), fontArial11Bold, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.1.val", form.isHasEmptySpec() ? (form.getNumber() + " S") : form.getNumber(), form.getDate()), fontArial11Bold, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.currentDate", StringUtil.date2appDateString(StringUtil.getCurrentDateTime())), fontArial7P5Normal, 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE));
		table.setSpacingAfter(defSpacing);
		table.setTotalWidth(width);
		return table;
	}

	public void printPages(PdfContentByte dc)
	{
		//writing Страница [current_page] из [XXX]
		String text = words.getMessage("rep.CoveringLetterForOrder.page", writer.getPageNumber());
		float textSize = fontArial7P5Normal.getBaseFont().getWidthPoint(text, fontArial7P5Normal.getCalculatedSize());
		float textBase = PageSize.A4.getHeight() - topMargin - header.getTotalHeight();
		dc.beginText();
		dc.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());

		float adjust = fontArial7P5Normal.getBaseFont().getWidthPoint("000", fontArial7P5Normal.getCalculatedSize());
		dc.setTextMatrix(document.right() - textSize - adjust, textBase);
		dc.showText(text);
		dc.endText();
		addPagesCountTemplate(dc, document.right() - adjust, textBase);
	}

	private UserLanguage findUserLang(User user)
	{
		UserLanguage lang = new UserLanguage();
		for (int i = 0; i < user.getUser_language().size(); i++)
		{
			UserLanguage userLanguage = user.getUser_language().get(i);
			if (userLanguage.getLanguage().getLetterId().equals("RU"))
			{
				lang = userLanguage;
			}
		}
		return lang;
	}

	private void processFooter(float width) throws Exception
	{
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);
		table.setWidths(new float[]{0.6f, 0.4f});

		if (form.isHasEmptySpec())
		{
			for (CoveringLetterForOrderPrintForm.ContractorNumbers contractorNumbers : form.getContractorNumbers())
			{
				if (StringUtil.isEmpty(contractorNumbers.getContractor()))
				{
					if (!StringUtil.isEmpty(form.getOrd_by_guaranty()))
					{
						table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.guaranty1", contractorNumbers.getNumbersAndCountsFormatted()), getFontArial11Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
					}
					else
					{
						table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.stock1", contractorNumbers.getNumbersAndCountsFormatted()), getFontArial11Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
					}
				}
				else
				{
					if (!StringUtil.isEmpty(form.getOrd_by_guaranty()))
					{
						table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.guaranty2", contractorNumbers.getNumbersAndCountsFormatted(), contractorNumbers.getContractor()), getFontArial11Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
					}
					else
					{
						table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.stock2", contractorNumbers.getNumbersAndCountsFormatted(), contractorNumbers.getContractor()), getFontArial11Bold(), 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
					}
				}
			}
		}

		table.addCell(cell(findUserLang(form.getManager()).getUsr_position(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
		table.addCell(cell(findUserLang(form.getManager()).getUsr_name() + " " + findUserLang(form.getManager()).getUsr_surname(), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
		table.addCell(cell("", getFontArial11Normal(), 1));
		table.addCell(cell("", getFontArial11Normal(), 1));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.director1"), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.director2"), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
		table.addCell(cell("", getFontArial11Normal(), 1));
		table.addCell(cell("", getFontArial11Normal(), 1));

		table.addCell(cell(form.getDate(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
		table.addCell(cell("", getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP));
		table.setSpacingAfter(defSpacing);
		document.add(table);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSplitLate(true);
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.additionalInfo"), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER));
		table.addCell(cell(" ", getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.additionalInfoTable"), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER));
		document.add(table);

		table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new float[]{0.25f, 0.25f, 0.25f, 0.25f});

		Order order = form.getOrder();
		for (int i = 0; i < form.getProduces().size() - form.getTotalLinesCount(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);
			PdfPTable vTable = new PdfPTable(1);
			vTable.setSplitLate(false);
			vTable.setWidthPercentage(100);
			vTable.getDefaultCell().setBorder(noBorder);

			PdfPTable innerTable = new PdfPTable(1);
			innerTable.setSplitLate(false);
			innerTable.setWidthPercentage(100);
			innerTable.getDefaultCell().setBorder(noBorder);

			Phrase phrase = new Phrase();
			// Should display positive rest with bold ann 11 size. Otherwise - 8 and normal.
			ProduceMovement produceMovement = ProduceMovementHelper.formProduceMovementObject(actionContext, produce.getOrderProduce().getProduce(), null);

			phrase.add(new Chunk(StringUtil.double2appCurrencyString(produce.getOrderProduce().getPrd_circulation180days()), getFontArial11Normal()));
			double restInMinsk = produce.getOrderProduce().getRest_in_minsk();
			Font restInMinskFont = restInMinsk == 0 ? getFontArial8Normal() : getFontArial11Bold();

			double restInLithuania = produceMovement.getCountOrdRest() + produceMovement.getCountTransitRest();
			//дополняем данными по остатку в Литве.
			produce.getOrderProduce().setRestInLithuania(restInLithuania);
			Font restInLithuaniaFont = restInLithuania == 0 ? getFontArial8Normal() : getFontArial11Bold();
			phrase.add(new Chunk(" / " + StringUtil.double2appCurrencyString(restInMinsk) + " / ", restInMinskFont));
			phrase.add(new Chunk(StringUtil.double2appCurrencyString(restInLithuania), restInLithuaniaFont));
			innerTable.addCell(phrase);

			vTable.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.posPrefix", produce.getViewNumber()), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, PdfPCell.BOTTOM));
			vTable.addCell(innerTable);

			table.addCell(vTable);
		}

		PdfPTable devTable = new PdfPTable(1);
		devTable.setWidthPercentage(100);
		devTable.setSplitLate(false);
		devTable.getDefaultCell().setBorder(noBorder);
		devTable.addCell(cell(" ", getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOTTOM));
		devTable.addCell(cell(" ", getFontArial11Bold(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER));

		int size = form.getProduces().size() - form.getTotalLinesCount();
		if (size < 4 || (size % 4 < 4 && size % 4 != 0))
		{
			int rightCount = size < 4 ? size : size % 4;
			for (int i = rightCount; i < 4; i++)
			{
				table.addCell(devTable);
			}
		}

		table.setSpacingAfter(defSpacing);
		document.add(table);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSplitLate(true);
		table.addCell(cell(form.getOrder().getOrd_comment_covering_letter(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER));
		document.add(table);

		// If in order positions with rest in Minsk - form additional table -  rests.
		table = null;
		for (int i = 0; i < form.getProduces().size() - form.getTotalLinesCount(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);
			if (produce.getOrderProduce().getRest_in_minsk() != 0)
			{
				if (table == null)
				{
					table = createRestsTable(width);
				}

				HibernateUtil.associateWithCurentSession(produce.getOrderProduce().getProduce().getUnit());
				String unit = produce.getOrderProduce().getProduce().getUnit().getName();

				List<RestInfo> restInform = DAOUtils.resultSet2List(DAOUtils.executeQuery(actionContext.getConnection(), actionContext.getSqlResource().get("select_rest_info_for_order_letter"), produce.getOrderProduce(), null), RestInfo.class, null);

				for (int j = 0; j < restInform.size(); j++)
				{
					formPosRestColumn(table, i, restInform, j);
					RestInfo restInfo = restInform.get(j);
					table.addCell(cell(restInfo.getQuantityFormatted(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(unit, getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getDate_created().replace('.', '/'), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getContractor_name(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getContractAndSpecification(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT));
				}
			}
		}

		if (table != null)
		{
			PdfPTable header = createRestsTableHeader(width);
			if (table.getTotalHeight() + header.getTotalHeight() > writer.getVerticalPosition(true) - document.bottomMargin())
			{
				document.newPage();
			}
			document.add(header);
			document.add(table);
		}

		// If in order positions with rest in Lithuania - form additional table -  rests.
		table = null;

		for (int i = 0; i < form.getProduces().size() - form.getTotalLinesCount(); i++)
		{
			OrderForm.OrderProduceForForm produce = (OrderForm.OrderProduceForForm) form.getProduces().get(i);
			if (produce.getOrderProduce().getRestInLithuania() != 0)
			{
				if (table == null)
				{
					table = createRestsTable(width);
				}

				HibernateUtil.associateWithCurentSession(produce.getOrderProduce().getProduce().getUnit());
				String unit = produce.getOrderProduce().getProduce().getUnit().getName();

				List<RestInfo> restInform = DAOUtils.resultSet2List(DAOUtils.executeQuery(actionContext.getConnection(), actionContext.getSqlResource().get("select_rest_in_lithuania_info_for_order_letter"), produce.getOrderProduce(), null), RestInfo.class, null);

				for (int j = 0; j < restInform.size(); j++)
				{
					formPosRestColumn(table, i, restInform, j);
					RestInfo restInfo = restInform.get(j);
					table.addCell(cell(restInfo.getQuantityFormatted(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(unit, getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getDate_created().replace('.', '/'), getFontArial11Normal(), 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getContractor_name(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM));
					table.addCell(cell(restInfo.getContractAndSpecification(), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT));
				}
			}
		}

		if (table != null)
		{
			PdfPTable header = createRestsInLithuaniaTableHeader(width);
			if (table.getTotalHeight() + header.getTotalHeight() > writer.getVerticalPosition(true) - document.bottomMargin())
			{
				document.newPage();
			}
			document.add(header);
			document.add(table);
		}
	}

	private void formPosRestColumn(PdfPTable table, int i, Collection<RestInfo> rests, int j)
	{
		if (j == 0)
		{
			PdfPCell cell = cell(words.getMessage("rep.CoveringLetterForOrder.posPrefix", i + 1), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.LEFT);
			if (rests.size() == 1)
			{
				cell.setBorder(PdfPCell.BOTTOM | PdfPCell.LEFT);
			}
			table.addCell(cell);
		}
		else if (j == rests.size() - 1)
		{
			table.addCell(cell("", getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOTTOM | PdfPCell.LEFT));
		}
		else
		{
			table.addCell(cell("", getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.LEFT));
		}
	}

	private PdfPTable createRestsTable(float width) throws DocumentException
	{
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new float[]{0.1f, 0.08f, 0.05f, 0.2f, 0.2f, 0.32f});
		table.setTotalWidth(width);
		table.addCell(cell("", getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.ccp_count"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.ConditionForContractProduces.unit"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.date"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.orderedFor"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.contract.specification"), getFontArial11Normal(), 1, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_CENTER, PdfPCell.BOX));
		return table;
	}

	private PdfPTable createRestsTableHeader(float width) throws DocumentException
	{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);
		table.setSplitLate(false);
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.rests.header"), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
		return table;
	}

	private PdfPTable createRestsInLithuaniaTableHeader(float width) throws DocumentException
	{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setTotalWidth(width);
		table.setSplitLate(false);
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.restsInLithuania.header"), getFontArial11Normal(), 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP));
		return table;
	}

	private void processGrid(float width, CoveringLetterForOrderPrintForm.Page page) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new float[]{
						Config.getFloat("rep.CoveringLetterForOrder.2", 0.35f),
						Config.getFloat("rep.CoveringLetterForOrder.3", 0.65f)
		});
		table.getDefaultCell().setBorder(noBorder);

		Font normalFont = getFontArial11Normal();
		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.2"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(page.getPositions(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.3"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(page.getPurchaserName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.4"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		Phrase phrase = new Phrase();
		if (page.isConOriginal() && page.isSpcOriginal())
		{
			if (StringUtil.isEmpty(page.getContractNumber()))
			{
				phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val.empty-contract"), normalFont));
			}
			else if (StringUtil.isEmpty(page.getSpecificationNumber()))
			{
				phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val.empty-spec", page.getContractNumber(), page.getContractDate()), normalFont));
			}
			else
			{
				phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val", page.getContractNumber(), page.getContractDate(), page.getSpecificationNumber(), page.getSpecificationDate()), normalFont));
			}
		}
		else
		{
			if (!StringUtil.isEmpty(page.getContractNumber()))
			{
				phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy1", page.getContractNumber(), page.getContractDate()), normalFont));
				if (page.isConProject() || page.isConCopy())
				{
					phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy0"), getFontArial11BoldUnderline()));
					phrase.add(new Chunk(" "));
					if (page.isConProject())
					{
						phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy2"), getFontArial11Bold()));
					}
					if (page.isConCopy())
					{
						phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy2_1"), getFontArial11Bold()));
					}
				}
			}
			if (!StringUtil.isEmpty(page.getSpecificationNumber()))
			{
				phrase.add(new Chunk(
								words.getMessage("rep.CoveringLetterForOrder.4.val_copy3", page.getSpecificationNumber(), page.getSpecificationDate()),
								normalFont));
				if (page.isSpcProject() || page.isSpcCopy())
				{
					phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy0"), getFontArial11BoldUnderline()));
					phrase.add(new Chunk(" "));
					if (page.isSpcProject())
					{
						phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy4"), getFontArial11Bold()));
					}
					if (page.isSpcCopy())
					{
						phrase.add(new Chunk(words.getMessage("rep.CoveringLetterForOrder.4.val_copy2_1"), getFontArial11Bold()));
					}
				}
			}
		}
		table.addCell(cell(phrase, 2, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.5"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(page.getSeller(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.6"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(page.getPayCondition(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.7"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(page.getCurrency().getName(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.8"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(StringUtil.double2appCurrencyString(page.getSumm()), normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.9"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < page.getPayments().size() - 1; i++)
		{
			Specification.Payment payment = page.getPayments().get(i);
			stringBuilder.append(StringUtil.dbDateString2appDateString(payment.getDate()));
			stringBuilder.append(" ");
			stringBuilder.append(StringUtil.double2appCurrencyString(payment.getSumm()));
			stringBuilder.append("\n");
		}

		if (page.getPayments().size() != 0)
		{
			Specification.Payment payment = page.getPayments().get(page.getPayments().size() - 1);
			stringBuilder.append(words.getMessage("rep.CoveringLetterForOrder.total"));
			stringBuilder.append(" ");
			stringBuilder.append(StringUtil.double2appCurrencyString(payment.getSumm()));
		}

		table.addCell(cell(stringBuilder.toString(), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.addCell(cell(words.getMessage("rep.CoveringLetterForOrder.10"), normalFont, 1, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));
		table.addCell(cell(StringUtil.double2appCurrencyString(page.getRate()) + "%", normalFont, 1, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, PdfPCell.BOX));

		table.setSplitLate(false);
		table.setSpacingAfter(defSpacing);
		document.add(table);
	}

	public void printDocumentName(PdfContentByte dc)
	{
	}

	public void onCloseDocument(PdfWriter writer, Document document)
	{
		pagesCountTemplate.beginText();
		pagesCountTemplate.setFontAndSize(fontArial7P5Normal.getBaseFont(), fontArial7P5Normal.getCalculatedSize());
		pagesCountTemplate.setTextMatrix(0, 0);
		String res = StringUtil.padWithLeadingSymbol(String.valueOf(writer.getPageNumber() - 1), 3, SPACE);
		pagesCountTemplate.showText(res);
		pagesCountTemplate.endText();
	}
}
