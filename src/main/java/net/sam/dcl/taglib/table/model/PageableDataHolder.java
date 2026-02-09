package net.sam.dcl.taglib.table.model;

/**
 * PageAble DataHolder interface.
 *
 * @author DG
 */
public interface PageableDataHolder extends DataHolder
{
	String NEXT_PAGE = "NEXT_PAGE";
	String PREV_PAGE = "PREV_PAGE";

	/**
	 * Get current page number.
	 *
	 * @return page
	 */
	public int getPage();

	/**
	 * Goto  page.
	 */
	public void setPage(int page);

	/**
	 * @return is next page avilable
	 */
	boolean hasNextPage();

}
