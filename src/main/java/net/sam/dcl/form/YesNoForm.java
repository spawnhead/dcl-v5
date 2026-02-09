package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class YesNoForm extends BaseDispatchValidatorForm
{
	String showOkCancel;
	String msg;
	String noSeconds;
	String yesSeconds;

	public String getShowOkCancel()
	{
		return showOkCancel;
	}

	public void setShowOkCancel(String showOkCancel)
	{
		this.showOkCancel = showOkCancel;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getNoSeconds()
	{
		return noSeconds;
	}

	public void setNoSeconds(String noSeconds)
	{
		this.noSeconds = noSeconds;
	}

	public String getYesSeconds()
	{
		return yesSeconds;
	}

	public void setYesSeconds(String yesSeconds)
	{
		this.yesSeconds = yesSeconds;
	}
}
