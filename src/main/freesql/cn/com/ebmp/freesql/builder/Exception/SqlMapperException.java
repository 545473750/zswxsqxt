package cn.com.ebmp.freesql.builder.Exception;

public class SqlMapperException extends RuntimeException
{
	private static final long serialVersionUID = 6805253141674575364L;

	public SqlMapperException(Throwable cause)
	{
		super(cause);
	}

	public SqlMapperException(String message)
	{
		super(message);
	}
}
