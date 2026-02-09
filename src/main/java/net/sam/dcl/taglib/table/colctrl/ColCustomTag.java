package net.sam.dcl.taglib.table.colctrl;

import net.sam.dcl.message.EMessage;
import net.sam.dcl.taglib.table.TableTag;
import net.sam.dcl.taglib.table.TableUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.VarStringParser;
import net.sam.dcl.util.StrutsUtil;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import java.util.Map;

/**
 * Tag.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public class ColCustomTag extends ColBaseTag
{
	String property;
	String image;
	String tooltip = "";
	String tooltipProperty;
	Map lookUp;
	/**
	 * Reference to logger.
	 */
	static Logger LOGGER = Logger.getLogger(ColCustomTag.class);

	/**
	 * Constructor.
	 */
	public ColCustomTag()
	{
	}

	/**
	 * Generate the required input tag.
	 *
	 * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException
	{
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

		checkHideOnSelect();
		if (isHideOnSelect())
		{
			return SKIP_BODY;
		}

		StringBuilder results = new StringBuilder("<td" + tableTag.getTextForScrollableGrid() + TableUtils.getStyleString(this) + " align='" + align + "'");
		if (!StringUtil.isEmpty(width))
		{
			results.append(" width='").append(width).append("'");
		}
		results.append(" >");
		if (!isShow())
		{
			results.append("&nbsp;");
			TagUtils.getInstance().write(pageContext, results.toString());
			return SKIP_BODY;
		}
		int retVal = EVAL_BODY_BUFFERED;
		if (!StringUtil.isEmpty(getImage()))
		{
			results.append("  <image class='grid-image-without-border' src='").append(response.encodeURL(image)).append("' tabindex=-1 style='cursor:pointer;'");

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
			results.append(" /> &nbsp;");
		}

		if (!StringUtil.isEmpty(getProperty()))
		{
			try
			{
				String prop = tableTag.getGridDataProperty(property);
				results.append(StringUtil.getString(prop));
			}
			catch (Exception e)
			{
				StrutsUtil.addError(pageContext, new EMessage(e));
				throw new JspException(e);
			}
			retVal = SKIP_BODY;
		}
		TagUtils.getInstance().write(pageContext, results.toString());
		return retVal;
	}

	public int doEndTag() throws JspException
	{
		if (!isHideOnSelect())
		{
			StringBuffer result = GetBodyContent();
			result.append("</td>");
			TagUtils.getInstance().write(pageContext, result.toString());
		}
		bodyContent = null;
		return EVAL_PAGE;
	}

	StringBuffer GetBodyContent() throws JspException
	{
		StringBuffer result = new StringBuffer();
		if (bodyContent != null)
		{
			String body = bodyContent.getString();
			TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
			if (tableTag != null)
			{
				VarStringParser parser = new VarStringParser(new CallbackImpl(tableTag));
				try
				{
					body = parser.parse(body);
				}
				catch (Exception e)
				{
					StrutsUtil.addError(pageContext, new EMessage(e));
					throw new JspException(e);
				}

			}
			result.append(body);
		}
		return result;
	}

	/**
	 * Release any acquired resources.
	 */
	public void release()
	{
		super.release();
	}

	class CallbackImpl implements VarStringParser.Callback
	{
		TableTag tableTag;

		public CallbackImpl(TableTag tableTag)
		{
			this.tableTag = tableTag;
		}

		public String process(String var) throws Exception
		{
			return tableTag.getGridDataProperty(var);
		}
	}

	public String getProperty()
	{
		return property;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
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