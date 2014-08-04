package com.opendata.calendar.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opendata.calendar.model.SysCalendar;
import com.opendata.calendar.service.SysCalendarManager;
import com.opendata.calendar.vo.query.SysCalendarQuery;

/**
 * 
 */

@Namespace("/calendar")
@Results(
{ @Result(name = "list", location = "/WEB-INF/pages/calendar/SysCalendar/list.jsp", type = "dispatcher"),
		@Result(name = "query", location = "/WEB-INF/pages/calendar/SysCalendar/query.jsp", type = "dispatcher"),
		@Result(name = "create", location = "/WEB-INF/pages/calendar/SysCalendar/create.jsp", type = "dispatcher"),
		@Result(name = "edit", location = "/WEB-INF/pages/calendar/SysCalendar/edit.jsp", type = "dispatcher"),
		@Result(name = "show", location = "/WEB-INF/pages/calendar/SysCalendar/show.jsp", type = "dispatcher"),
		@Result(name = "showMonthCanendars", location = "/WEB-INF/pages/calendar/SysCalendar/showMonthCanendars.jsp", type = "dispatcher"),
		@Result(name = "listAction", location = "../calendar/SysCalendar!list.do", type = "redirectAction"),
		@Result(name = "showMonthCanendarsAction", location = "../calendar/SysCalendar!showMonthCanendars.do", type = "redirectAction", params =
		{ "year", "${year}", "month", "${month}" })

})
public class SysCalendarAction extends BaseStruts2Action implements Preparable, ModelDriven
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 647981756576066786L;

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	// forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP = "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	protected static final String SHOW_MONTH_CANENDARS_JSP = "showMonthCanendars";

	// redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	protected static final String SHOW_MONTH_CANENDARS_ACTION = "showMonthCanendarsAction";
	private SysCalendarManager sysCalendarManager;

	private SysCalendar sysCalendar;
	java.lang.String id = null;
	private String[] items;

	public void prepare() throws Exception
	{
		if (isNullOrEmptyString(id))
		{
			sysCalendar = new SysCalendar();
		} else
		{
			sysCalendar = (SysCalendar) sysCalendarManager.getById(id);
		}
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setSysCalendarManager(SysCalendarManager manager)
	{
		this.sysCalendarManager = manager;
	}

	public Object getModel()
	{
		return sysCalendar;
	}

	public void setId(java.lang.String val)
	{
		this.id = val;
	}

	public void setItems(String[] items)
	{
		this.items = items;
	}

	/** 执行搜索 */
	public String list()
	{
		SysCalendarQuery query = newQuery(SysCalendarQuery.class, DEFAULT_SORT_COLUMNS);
		Page page = sysCalendarManager.findPage(query);
		savePage(page, query);
		return LIST_JSP;
	}

	/** 查看对象 */
	public String show()
	{
		return SHOW_JSP;
	}

	/** 查询一个月份的日期 */
	public String showMonthCanendars()
	{
		// 设置当前年、月
		this.setCurrentYearMonth();
		// 查询指定月份的日期集合
		List<SysCalendar> list = sysCalendarManager.getMonthCalendars(sysCalendar.getYear(), sysCalendar.getMonth());
		if (null == list || list.size() == 0)
		{
			// 当所查询的月份日期不存在时，保存“查询年份”的日期
			sysCalendarManager.saveYearCalendars(sysCalendar.getYear());
			list = sysCalendarManager.getMonthCalendars(sysCalendar.getYear(), sysCalendar.getMonth());
		}
		// 获取每个月第一周的第一天的星期
		int week = list.get(0).getWeek();
		sysCalendar.setWeek(week);
		// 日期表格最后一行最后一天结束后，剩余的空表格数量
		int y = 7 - (list.size() - (7 - (week - 1))) % 7;
		sysCalendar.setLastNullTdCount(y);

		for (SysCalendar c : list)
		{
			// 当前日期在日期表格的位置是否在最后一列
			int flag = (c.getDay() + week - 1) % 7;
			// 如果是最后一列，则另起一行，添加</tr><tr>
			c.setTrFlag(flag);
		}
		getRequest().setAttribute("cdt", new Date());
		getRequest().setAttribute("canendarList", list);
		return SHOW_MONTH_CANENDARS_JSP;
	}

	/** 进入新增页面 */
	public String create()
	{
		return CREATE_JSP;
	}

	/** 保存新增对象 */
	public String save()
	{
		sysCalendarManager.save(sysCalendar);
		Flash.current().success(CREATED_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}

	/** 进入更新页面 */
	public String edit()
	{
		getRequest().setAttribute("wrokStates", getWrokStates());
		return EDIT_JSP;
	}

	/** 保存更新对象 */
	public String update()
	{
		sysCalendarManager.update(this.sysCalendar);
		Flash.current().success(UPDATE_SUCCESS);
		return SHOW_MONTH_CANENDARS_ACTION;
	}

	/** 删除对象 */
	public String delete()
	{
		for (int i = 0; i < items.length; i++)
		{
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String) params.get("id"));
			sysCalendarManager.removeById(id);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	// 设置当前查询日期的年月
	private void setCurrentYearMonth()
	{
		Calendar cal = Calendar.getInstance();
		if (sysCalendar.getYear() == null)
		{
			int year = cal.get(Calendar.YEAR);
			sysCalendar.setYear(year);
		}
		if (sysCalendar.getMonth() == null)
		{
			int month = cal.get(Calendar.MONTH) + 1;
			sysCalendar.setMonth(month);
		} else if (sysCalendar.getMonth().intValue() > 12)
		{
			int month = 1;
			sysCalendar.setMonth(month);
			sysCalendar.setYear(sysCalendar.getYear() + 1);
		} else if (sysCalendar.getMonth().intValue() < 1)
		{
			sysCalendar.setMonth(12);
			sysCalendar.setYear(sysCalendar.getYear() - 1);
		}
	}

	private static Map<String, String> getWrokStates()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "非工作日");
		map.put("1", "工作日");
		return map;
	}

}
