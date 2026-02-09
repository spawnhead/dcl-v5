package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.message.EMessage;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColButtonTag extends ColLinkTag
{
	String tooltip = "";
	String tooltipProperty;

	/**
	 * Reference to logger.
	 */
	static Logger LOGGER = Logger.getLogger(ColButtonTag.class);

	/**
	 * Constructor.
	 */
	public ColButtonTag()
	{
	}

	/**
	 * Generate the required input tag.
	 *
	 * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException
	{
		TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
		checkHideOnSelect();
		if (isHideOnSelect())
		{
			return SKIP_BODY;
		}
		StringBuffer results = new StringBuffer("<td " + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "'");
		if (!StringUtil.isEmpty(width))
		{
			results.append(" width='").append(width).append("'");
		}
		results.append(" >");
		if (!isShow())
		{
			results.append("&nbsp;");
			TagUtils.getInstance().write(pageContext, results.toString());
			return EVAL_BODY_BUFFERED;
		}

		results.append(" <input type=button  class='grid-button'  ");
		results.append(getOnClickAction(true));
		if (!StringUtil.isEmpty(getTooltipProperty()))
		{
			try
			{
				results.append(" title='").append(tableTag.getGridDataProperty(getTooltipProperty()).replaceAll("'", "\"")).append("' ");
			}
			catch (Exception e)
			{
				StrutsUtil.addError(pageContext, new EMessage(e));
				throw new JspException(e);
			}
		}
		else
		{
			results.append(" title='").append(StrutsUtil.getMessage(pageContext, getTooltip())).append("' ");
		}
		TagUtils.getInstance().write(pageContext, results.toString());
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException
	{
		action = null;
		if (!isHideOnSelect())
		{
			StringBuffer results = new StringBuffer();
			TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
			if (isShow())
			{
				results.append(" value=\"");
				if (!StringUtil.isEmpty(property))
				{
					try
					{
						results.append(tableTag.getGridDataProperty(property));
					}
					catch (Exception e)
					{
						StrutsUtil.addError(pageContext, new EMessage(e));
						throw new JspException(e);
					}
				}
				else
				{
					results.append(GetBodyContent());
				}
				results.append("\" ");
				if (isReadOnly()) results.append("disabled ");
				results.append(">");
			}
			results.append("</td>\n");
			TagUtils.getInstance().write(pageContext, results.toString());
		}
		return EVAL_PAGE;
	}

	/**
	 * Release any acquired resources.
	 */
	public void release()
	{
		setTooltip(null);
		super.release();
	}

	public String getTooltip()
	{
		return tooltip;
	}

	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}

	public String getTooltipProperty()
	{
		return tooltipProperty;
	}

	public void setTooltipProperty(String tooltipProperty)
	{
		this.tooltipProperty = tooltipProperty;
	}
}