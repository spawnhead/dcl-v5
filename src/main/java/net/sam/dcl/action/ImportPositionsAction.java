package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.util.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ImportPositionsAction extends DBAction
{
  ImportData.LeftRecord findLeftRecordByDocId(List<ImportData.LeftRecord> leftlList, String doc_id, boolean useMainId)
  {
    for (ImportData.LeftRecord record : leftlList)
    {
      if ( useMainId )
      {
        if (StringUtil.equal(doc_id, record.getId()))
        {
          return record;
        }
      }
      else
      {
        if (StringUtil.equal(doc_id, record.getDoc_id()))
        {
          return record;
        }
      }
    }
    return null;
  }

  ImportData.RightRecord findRightRecordByDocId(List<ImportData.RightRecord> rightlList, String doc_id, boolean useMainId)
  {
    for (ImportData.RightRecord record : rightlList)
    {
      if ( useMainId )
      {
        if (StringUtil.equal(doc_id, record.getPosition().getId()))
        {
          return record;
        }
      }
      else
      {
        if (StringUtil.equal(doc_id, record.getPosition().getDoc_id()))
        {
          return record;
        }
      }
    }
    return null;
  }

  double getRighProduceCountByDocId(List<ImportData.RightRecord> rightlList, String doc_id, boolean useMainId)
  {
    double count = 0;
    for (ImportData.RightRecord record : rightlList)
    {
      if ( useMainId )
      {
        if (StringUtil.equal(doc_id, record.getPosition().getId()))
        {
          count += record.getPosition().getCount();
        }
      }
      else
      {
        if (StringUtil.equal(doc_id, record.getPosition().getDoc_id()))
        {
          count += record.getPosition().getCount();
        }
      }
    }
    return count;
  }

  ImportData.RightRecord findRightRecordByPrdId(List<ImportData.RightRecord> rightlList, int id)
  {
    for (ImportData.RightRecord record : rightlList)
    {
      if (id == record.getPosition().getProduce().getId())
      {
        return record;
      }
    }
    return null;
  }

  ImportData.LeftRecord findLeftRecordByPrdId(List<ImportData.LeftRecord> rightlList, int id)
  {
    for (ImportData.LeftRecord record : rightlList)
    {
      if (id == record.getProduce().getId())
      {
        return record;
      }
    }
    return null;
  }

  int findLeftRecordByParentDocId(List originalList, String parent_doc_id)
  {
    for (int i = 0; i < originalList.size(); i++)
    {
      ImportData.LeftRecord record = (ImportData.LeftRecord) originalList.get(i);
      if (StringUtil.equal(parent_doc_id, record.getId()))
      {
        return i;
      }
    }
    return originalList.size();
  }
}
