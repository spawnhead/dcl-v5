package net.sam.dcl.report.excel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.List;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.report.pdf.DocumentTemplatePDF;

/**
 * @author: DG
 * Date: 07.06.2006
 * Time: 16:31:45
 */
public class Grid2Excel
{
  protected static Log log = LogFactory.getLog(Grid2Excel.class);
  protected HSSFWorkbook wb;
  protected HSSFSheet sheet;
  protected HSSFCellStyle textLeft;
  protected HSSFCellStyle textCenter;
  protected HSSFCellStyle dateCenter;
  protected HSSFCellStyle numberRight;

  protected HSSFCellStyle headerStyleDate;
  protected HSSFCellStyle headerStyleNumber;
  protected HSSFCellStyle headerStyleText;

  private String name = "";

  public HSSFWorkbook getWb()
  {
    return wb;
  }

  public void setWb(HSSFWorkbook wb)
  {
    this.wb = wb;
  }

  public Grid2Excel(String name)
  {
    this.name = name;
    wb = new HSSFWorkbook();
    sheet = wb.createSheet(name);
    HSSFDataFormat format = wb.createDataFormat();

    HSSFFont font = wb.createFont();
    font.setFontName(DocumentTemplatePDF.ARIAL);
    font.setFontHeightInPoints((short) 10);

    textLeft = wb.createCellStyle();
    textLeft.setFont(font);
    textLeft.setAlignment(HorizontalAlignment.LEFT);
    //textLeft.setDataFormat(HSSFDataFormat.getFormat("@"));
    //jakarta POI can't find "@" because getFormat use for until 49
    textLeft.setDataFormat((short) 49);

    dateCenter = wb.createCellStyle();
    dateCenter.setFont(font);
    dateCenter.setAlignment(HorizontalAlignment.CENTER);
    dateCenter.setDataFormat(format.getFormat("m/d/yy h:mm"));

    numberRight = wb.createCellStyle();
    numberRight.setFont(font);
    numberRight.setAlignment(HorizontalAlignment.RIGHT);
    numberRight.setDataFormat(format.getFormat("###0.00"));

    HSSFFont headerFont = wb.createFont();
    headerFont.setFontName(DocumentTemplatePDF.ARIAL);
    headerFont.setFontHeightInPoints((short) 10);

    headerStyleText = wb.createCellStyle();
    headerStyleText.setFont(headerFont);
    headerStyleText.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    headerStyleText.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyleText.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    headerStyleText.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    headerStyleText.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    headerStyleText.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    headerStyleText.setBorderRight(HSSFCellStyle.BORDER_THIN);
    headerStyleText.setRightBorderColor(IndexedColors.BLACK.getIndex());
    headerStyleText.setBorderTop(HSSFCellStyle.BORDER_THIN);
    headerStyleText.setTopBorderColor(IndexedColors.BLACK.getIndex());
    headerStyleText.setDataFormat((short) 49);
    //headerStyleText.setRotation((short)90);
  }

  public void process(OutputStream wout, List excelTable) throws IOException
  {

    List headers = (List) excelTable.get(0);
    HSSFRow row = sheet.createRow((short) 0);
    for (short i = 0; i < headers.size(); i++)
    {
      HSSFCell cell = row.createCell(i);
      // Removed setEncoding for POI 5.x compatibility
      cell.setCellValue((String) headers.get(i));
      cell.setCellStyle(headerStyleText);
      sheet.setColumnWidth(i, (short) (20 * 256));
    }

    for (int i = 1; i < excelTable.size(); i++)
    {
      List record = (List) excelTable.get(i);
      row = sheet.createRow((short) i);
      for (short j = 0; j < record.size(); j++)
      {
        HSSFCell cell = row.createCell(j);
        // Removed setEncoding for POI 5.x compatibility
        Object obj = record.get(j);

        boolean settedStyle = false; //заглушка, чтобы не менять по всему коду
        if (obj instanceof CellFormat)
        {
          cell.setCellStyle(((CellFormat)obj).getStyle());
          obj = ((CellFormat)obj).getObj();
          settedStyle = true;
        }

        if (obj instanceof Double)
        {
          if ( !settedStyle )
          {
            cell.setCellStyle(numberRight);
          }

          double val = ((Double) obj).doubleValue();
          if (Double.isNaN(val))
          {
            cell.setCellErrorValue((byte) 0);
          }
          else
          {
            cell.setCellValue(val);
          }
        }
        else if (obj instanceof String)
        {
          String str = (String) obj;
          str = str.replaceAll("\r\n", "\n");
          cell.setCellValue(str);
        }
      }
    }
    wb.write(wout);
  }

  public void doGrid2Excel(IActionContext context, List grid) throws IOException
  {
    ResponseCollectFilter.resetNeedResponseCollect(context.getRequest());
    context.getResponse().setContentType("application/download");
    context.getResponse().setHeader("Content-disposition", " attachment;filename=\"report.xls\"");

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    process(out, grid);
    context.getResponse().getOutputStream().write(out.toByteArray());
    out.close();
  }

  static public class CellFormat
  {
    HorizontalAlignment align = HorizontalAlignment.LEFT;
    short color = HSSFFont.COLOR_NORMAL;
    boolean bold = false; // Replaced boldweight with boolean for POI 5.x
    Object obj;

    protected HSSFCellStyle style;

    public CellFormat(HSSFWorkbook wb, Object obj, HorizontalAlignment align)
    {
      this.obj = obj;
      this.align = align;

      initStyle(wb);
    }

    public CellFormat(HSSFWorkbook wb, Object obj, HorizontalAlignment align, short color, boolean bold)
    {
      this.obj = obj;
      this.align = align;
      this.color = color;
      this.bold = bold;

      initStyle(wb);
    }

    private void initStyle(HSSFWorkbook wb)
    {
      HSSFFont font = wb.createFont();
      font.setFontName(DocumentTemplatePDF.ARIAL);
      font.setFontHeightInPoints((short) 10);
      font.setColor(color);
      font.setBold(bold); // Replaced setBoldweight with setBold for POI 5.x

      HSSFDataFormat format = wb.createDataFormat();

      style = wb.createCellStyle();
      style.setFont(font);
      style.setAlignment(align);
      style.setDataFormat(format.getFormat("###0.00"));
    }

    public HorizontalAlignment getAlign()
    {
      return align;
    }

    public void setAlign(HorizontalAlignment align)
    {
      this.align = align;
    }

    public short getColor()
    {
      return color;
    }

    public void setColor(short color)
    {
      this.color = color;
    }

    public boolean isBold()
    {
      return bold;
    }

    public void setBold(boolean bold)
    {
      this.bold = bold;
    }

    public Object getObj()
    {
      return obj;
    }

    public void setObj(Object obj)
    {
      this.obj = obj;
    }

    public HSSFCellStyle getStyle()
    {
      return style;
    }

    public void setStyle(HSSFCellStyle style)
    {
      this.style = style;
    }
  }
}
