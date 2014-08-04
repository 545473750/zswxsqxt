package cn.com.ebmp.freesql.parsing;

import cn.com.ebmp.freesql.exception.PersistenceException;

public class ParsingException extends PersistenceException
{
	private static final long serialVersionUID = 8088473453732353830L;

	public ParsingException()
	{
		super();
	}

	public ParsingException(String message)
	{
		super(message);
	}

	public ParsingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParsingException(Throwable cause)
	{
		super(cause);
	}
}
