package cn.com.ebmp.freesql.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.ebmp.freesql.mapping.ResultMapping;

public class ResultMappingAssistant
{
	private String sql = "";
	//private String content = "";
	private String selectToken = "select ";
	private String fromToken = " from";
	private List<ResultMapping> resultMappingList = new ArrayList<ResultMapping>();

	public ResultMappingAssistant(String sql)
	{
		Pattern p = Pattern.compile("\\t|\r|\n");
		Matcher m = p.matcher(sql);
		String newSql = m.replaceAll(" ");
		this.sql = newSql.trim();
	}

	public List<ResultMapping> builder()
	{
		String mappingString = this.findContent();
		builderResultMapping(mappingString);
		return this.resultMappingList;
	}

	/**
	 * 获得所有的字段属性
	 * 
	 * @param splitContent
	 */
	private void builderResultMapping(String splitContent)
	{
		String segmentContent = splitContent;
		String pattern = "\\([^()]*+\\)";
		String temp = null;
		while (!segmentContent.equals(temp))
		{
			temp = segmentContent;
			segmentContent = temp.replaceAll(pattern, "");
		}
		String[] segmentArray = segmentContent.split(",");
		this.buildResultMapping(segmentArray);
	}

	private void buildResultMapping(String[] segmentArray)
	{
		for (int i = 0; i < segmentArray.length; i++)
		{
			String segment = segmentArray[i];
			if (!segment.trim().equals(""))
			{
				String[] sp = segment.trim().split(" ");
				if ((!sp[sp.length - 1].trim().equals("")) && (!sp[0].trim().equals("")))
				{
					String column = sp[0];
					if (column.indexOf(".") != -1)
					{
						column = column.substring(column.lastIndexOf(".") + 1);
					}
					String property = sp[sp.length - 1];
					if (property.indexOf(".") != -1)
					{
						property = property.substring(property.lastIndexOf(".") + 1);
					}
					resultMappingList.add(new ResultMapping(property, column, null));
				}
			}
		}

	}

	/**
	 * 读取 SQL语句中 select 至 from 中的内容
	 
	private String findContent()
	{
		int selectIndex = this.sql.toLowerCase().indexOf(selectToken) + selectToken.length();
		int formIndex = this.sql.toLowerCase().indexOf(fromToken);
		this.findContent(selectIndex, formIndex, sql);
		content = content.substring(0, content.lastIndexOf(fromToken)) + ",";
		return content;
	}
*/
	private String findContent()
	{
		Stack sqlStack=new Stack();
		String [] sqlSeg=this.sql.toLowerCase().split(" ");
		String [] realSeg=this.sql.split(" ");
		StringBuffer tmpSql=new StringBuffer();
		for(int i=0;i<sqlSeg.length;i++)
		{
			if(sqlSeg[i].trim().equals("select")||sqlSeg[i].trim().equals("(select"))
			{
				sqlStack.push("select");//放入select
			}else if(sqlSeg[i].trim().equals("from"))
			{
				if(!sqlStack.isEmpty())
				{
					sqlStack.pop();//弹出
				}
			}
			if(sqlStack.isEmpty())
			{
				break;
			}
			if(!sqlSeg[i].trim().equals(""))
			{
				tmpSql.append(realSeg[i]+" ");
			}
		}
		return tmpSql.toString();
	}
	
	
	
	/**
	 * 递归查询指定内容
	 * 
	 * @param originalText
	 * @return
	 
	private String findContent(int beginIndex, int endIndex, String originalContent)
	{
		String subCotent = originalContent.substring(beginIndex, endIndex);
		content += subCotent + fromToken;
		Pattern p = Pattern.compile("select \\s*| from\\s*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(subCotent);
		if (m.find())
		{
			String nextContent = originalContent.substring(endIndex + fromToken.length(),originalContent.length());
			this.findContent(0, nextContent.indexOf(fromToken), nextContent);
		}
		return content;
	}
	*/
	/**
	 *  判断此sql是否为简单查询sql
	 * @return
	 */
	public boolean isSimpleColumn()
	{
		String lsql=this.sql.toLowerCase();
		int fromIndex=lsql.indexOf(fromToken);
		int groupByIndex=lsql.indexOf(" group ");
		int unionByIndex=lsql.indexOf(" union ");
		if(fromIndex!=-1)
		{
			String newSql=lsql.substring(0,fromIndex);
			int lastSelectIndex=newSql.lastIndexOf(selectToken);
			int firstSelectIndex=newSql.indexOf(selectToken);
			if((lastSelectIndex==firstSelectIndex)&&groupByIndex==-1&&unionByIndex==-1)
			{
				return true;
			}
		}
		return false;
	}
}
