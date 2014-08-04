package cn.com.ebmp.freesql.type;

import cn.com.ebmp.freesql.exception.PersistenceException;

public class TypeException extends PersistenceException
{

	public TypeException()
	{
		super();
	}

	public TypeException(String message)
	{
		super(message);
	}

	public TypeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TypeException(Throwable cause)
	{
		super(cause);
	}

}
