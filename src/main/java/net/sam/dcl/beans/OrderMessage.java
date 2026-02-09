package net.sam.dcl.beans;

import java.io.Serializable;

public class OrderMessage implements Serializable
{
	public static String managerRoleId = "2";

	String ord_id;
	String prc_id;
	String msg;

	public OrderMessage()
	{
	}

	public String getManagerRoleId()
	{
		return managerRoleId;
	}

	public String getOrd_id()
	{
		return ord_id;
	}

	public void setOrd_id(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public String getPrc_id()
	{
		return prc_id;
	}

	public void setPrc_id(String prc_id)
	{
		this.prc_id = prc_id;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof OrderMessage)) return false;

		OrderMessage that = (OrderMessage) o;

		if (!msg.equals(that.msg)) return false;
		if (!ord_id.equals(that.ord_id)) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = ord_id.hashCode();
		result = 31 * result + msg.hashCode();
		return result;
	}
}