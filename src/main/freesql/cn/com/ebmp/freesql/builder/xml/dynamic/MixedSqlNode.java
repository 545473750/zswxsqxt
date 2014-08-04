package cn.com.ebmp.freesql.builder.xml.dynamic;

import java.util.List;

public class MixedSqlNode implements SqlNode
{
	private List<SqlNode> contents;

	public MixedSqlNode(List<SqlNode> contents)
	{
		this.contents = contents;
	}

	public boolean apply(DynamicContext context)
	{
		for (SqlNode sqlNode : contents)
		{
			sqlNode.apply(context);
		}
		return true;
	}
}
