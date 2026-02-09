package net.sam.dcl.report.pdf;

import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.DocumentException;
import net.sam.dcl.controller.actions.BaseAction;

import java.util.ArrayList;

/**
 * User: dg
 * Date: 31.03.2009
 * Time: 20:14:48
 */
class AutoWidthsTable extends PdfPTable
{
  float[] calculatedWidths;
  int currrentCell = 0;
  int numColumns;
  float sideMargins;
  ArrayList<Integer> reducibleColumnIndexes = new ArrayList<Integer>();

  AutoWidthsTable(int numColumns)
  {
    super(numColumns);
    this.numColumns = numColumns;
    calculatedWidths = new float[numColumns];
  }

  AutoWidthsTable(int numColumns, float[] minWidths)
  {
    this(numColumns);
    System.arraycopy(minWidths, 0, calculatedWidths, 0, numColumns);
  }

  AutoWidthsTable(int numColumns, int columnIdx, float minWidth)
  {
    super(numColumns);
    this.numColumns = numColumns;
    calculatedWidths = new float[numColumns];
    calculatedWidths[columnIdx] = minWidth;
  }

  void incCell()
  {
    if (currrentCell == numColumns - 1)
    {
      currrentCell = 0;
    }
    else
    {
      currrentCell++;
    }
  }

  public void setSideMargins(float sideMargins)
  {
    this.sideMargins = sideMargins;
  }

  public void addReducibleColumnIndex(int reducibleColumnIndex)
  {
    reducibleColumnIndexes.add(reducibleColumnIndex);  
  }

  private boolean isReducibleColumn(int reducibleColumn)
  {
    for ( Integer column : reducibleColumnIndexes )
    {
      if ( column.equals(reducibleColumn) )
      {
        return true;
      }
    }

    return false;
  }

  @Override
  public void addCell(PdfPCell cell)
  {

    if (cell.getColspan() == 1 && cell.getPhrase() != null)
    {
      float width = cell.getColumn().getWidth(cell.getPhrase()) +
              cell.getEffectivePaddingLeft() + cell.getEffectivePaddingRight() +
              (cell.hasBorder(PdfPCell.LEFT) ? cell.getBorderWidthLeft() : 0.0f) +
              (cell.hasBorder(PdfPCell.RIGHT) ? cell.getBorderWidthRight() : 0.0f);
      if ( calculatedWidths[currrentCell] < width )
      {
        calculatedWidths[currrentCell] = width;
      }
      incCell();
    }
    else
    {
      for (int i = 0; i < cell.getColspan(); i++)
      {
        incCell();
      }
    }

    super.addCell(cell);
  }

  public void setMinWidthForColumn(float width, int columnIdx)
  {
    calculatedWidths[columnIdx] = width;
  }

  @Override
  public void addCell(String text)
  {
    throw new BaseAction.NotImplementedException();
  }

  @Override
  public void addCell(PdfPTable table)
  {
    throw new BaseAction.NotImplementedException();
  }

  @Override
  public void addCell(Image image)
  {
    throw new BaseAction.NotImplementedException();
  }

  @Override
  public void addCell(Phrase phrase)
  {
    throw new BaseAction.NotImplementedException();
  }

  public void recalcWidths() throws DocumentException
  {
    float[] relative = new float[calculatedWidths.length];
    float totalExceptSkiped = 0;
    for (int i = 0; i < calculatedWidths.length; i++)
    {
      if (!isReducibleColumn(i))
      {
        totalExceptSkiped += calculatedWidths[i];
      }

    }
    for (int i = 0; i < calculatedWidths.length; i++)
    {
      if (!isReducibleColumn(i))
      {
        relative[i] = calculatedWidths[i];
      }
      else
      {
        float rWidth = (getTotalWidth() - sideMargins - totalExceptSkiped) / reducibleColumnIndexes.size();
        if (calculatedWidths[i] > rWidth)
        {
          relative[i] = rWidth;
        }
        else
        {
          relative[i] = calculatedWidths[i];
        }
      }
    }
    setWidths(relative);
  }
}
