package cn.com.ebmp.freesql.builder.xml.dynamic;

import cn.com.ebmp.freesql.builder.factory.Configuration;

public class WhereSqlNode extends TrimSqlNode
{

	public WhereSqlNode(Configuration configuration, SqlNode contents)
	{
		super(configuration, contents, "WHERE", "AND |OR ", null, null);
	}

}
