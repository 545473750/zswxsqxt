package cn.com.ebmp.freesql.builder.xml.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import cn.com.ebmp.freesql.builder.factory.Configuration;

public class TrimSqlNode implements SqlNode {

	private SqlNode contents;
	private String prefix;
	private String suffix;
	private List<String> prefixesToOverride = new ArrayList<String>();
	private List<String> suffixesToOverride = new ArrayList<String>();
	private Configuration configuration;

	public TrimSqlNode(Configuration configuration, SqlNode contents, String prefix, String prefixesToOverride, String suffix, String suffixesToOverride) {
		this.contents = contents;
		this.prefix = prefix;
		this.prefixesToOverride = parseOverrides(prefixesToOverride);
		this.suffix = suffix;
		this.suffixesToOverride = parseOverrides(suffixesToOverride);
		this.configuration = configuration;
	}

	public boolean apply(DynamicContext context) {
		FilteredDynamicContext filteredDynamicContext = new FilteredDynamicContext(context);
		boolean result = contents.apply(filteredDynamicContext);
		filteredDynamicContext.applyAll();
		return result;
	}

	private List<String> parseOverrides(String overrides) {
		if (overrides != null) {
			final StringTokenizer parser = new StringTokenizer(overrides, "|", false);
			return new ArrayList<String>() {
				private static final long serialVersionUID = -2504816393625384165L;

				{
					while (parser.hasMoreTokens()) {
						add(parser.nextToken().toUpperCase(Locale.ENGLISH));
					}
				}
			};
		}
		return Collections.EMPTY_LIST;
	}

	private class FilteredDynamicContext extends DynamicContext {
		private DynamicContext delegate;
		private boolean prefixApplied;
		private boolean suffixApplied;
		private StringBuilder sqlBuffer;

		public FilteredDynamicContext(DynamicContext delegate) {
			super(configuration, null);
			this.delegate = delegate;
			this.prefixApplied = false;
			this.suffixApplied = false;
			this.sqlBuffer = new StringBuilder();
		}

		public void applyAll() {
			sqlBuffer = new StringBuilder(sqlBuffer.toString().trim());
			String trimmedUppercaseSql = sqlBuffer.toString().toUpperCase(Locale.ENGLISH);
			if (trimmedUppercaseSql.length() > 0) {
				applyPrefix(sqlBuffer, trimmedUppercaseSql);
				applySuffix(sqlBuffer, trimmedUppercaseSql);
			}
			delegate.appendSql(sqlBuffer.toString());
		}

		public Map<String, Object> getBindings() {
			return delegate.getBindings();
		}

		public void bind(String name, Object value) {
			delegate.bind(name, value);
		}

		public int getUniqueNumber() {
			return delegate.getUniqueNumber();
		}

		public void appendSql(String sql) {
			sqlBuffer.append(sql);
		}

		public String getSql() {
			return delegate.getSql();
		}

		private void applyPrefix(StringBuilder sql, String trimmedUppercaseSql) {
			if (!prefixApplied) {
				prefixApplied = true;
				for (String toRemove : prefixesToOverride) {
					if (trimmedUppercaseSql.startsWith(toRemove) || trimmedUppercaseSql.startsWith(toRemove.trim())) {
						sql.delete(0, toRemove.trim().length());
						break;
					}
				}
				if (prefix != null) {
					sql.insert(0, " ");
					sql.insert(0, prefix);
				}
			}
		}

		private void applySuffix(StringBuilder sql, String trimmedUppercaseSql) {
			if (!suffixApplied) {
				suffixApplied = true;
				for (String toRemove : suffixesToOverride) {
					if (trimmedUppercaseSql.endsWith(toRemove) || trimmedUppercaseSql.endsWith(toRemove.trim())) {
						int start = sql.length() - toRemove.trim().length();
						int end = sql.length();
						sql.delete(start, end);
						break;
					}
				}
				if (suffix != null) {
					sql.append(" ");
					sql.append(suffix);
				}
			}
		}

	}

}
