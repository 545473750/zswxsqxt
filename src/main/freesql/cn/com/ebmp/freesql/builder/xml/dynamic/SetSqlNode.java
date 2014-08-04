package cn.com.ebmp.freesql.builder.xml.dynamic;

import cn.com.ebmp.freesql.builder.factory.Configuration;

public class SetSqlNode extends TrimSqlNode {

	public SetSqlNode(Configuration configuration, SqlNode contents) {
		super(configuration, contents, "SET", null, null, ",");
	}

}
