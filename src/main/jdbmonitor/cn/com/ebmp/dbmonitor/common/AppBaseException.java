package cn.com.ebmp.dbmonitor.common;

public class AppBaseException extends Exception
{
	private static final long serialVersionUID = 864115045656376434L;

	public AppBaseException()
	{
		super();
	}

	public AppBaseException(String message)
	{
		super(message);
	}

	public AppBaseException(Throwable cause)
	{
		super(cause);
	}

	public AppBaseException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
