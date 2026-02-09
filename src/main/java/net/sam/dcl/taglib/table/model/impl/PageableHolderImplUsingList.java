package net.sam.dcl.taglib.table.model.impl;

import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

import java.util.List;

/**
 * User: Dima
 * Date: Mar 20, 2005
 * Time: 2:59:39 PM
 */
public class PageableHolderImplUsingList extends HolderImplUsingList implements PageableDataHolder
{
	protected int page = 1;
	protected boolean hasNextPage = false;

	public PageableHolderImplUsingList()
	{
	}

	public PageableHolderImplUsingList(List list)
	{
		super(list);
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void incPage()
	{
		this.page++;
	}

	public void decPage()
	{
		this.page--;
	}

	public boolean hasNextPage()
	{
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage)
	{
		this.hasNextPage = hasNextPage;
	}
}
