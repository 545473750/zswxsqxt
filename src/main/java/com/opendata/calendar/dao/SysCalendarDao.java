package com.opendata.calendar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.calendar.model.SysCalendar;
import com.opendata.calendar.vo.query.SysCalendarQuery;

@Repository
public class SysCalendarDao extends BaseHibernateDao<SysCalendar,java.lang.String>{

	public Class getEntityClass() {
		return SysCalendar.class;
	}
	/**
	 * 仅限于列表条件查询，对于业务方法，不允许直接使用此方法
	 * @return
	 */
	public Page findPage(SysCalendarQuery query) {
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',username in ({username}),偷懒时可以使用字符串拼接 
		String sql = "select t from SysCalendar t where 1=1 "
				  		+ "/~ and t.day = {day} ~/"
				  		+ "/~ and t.month = {month} ~/"
				  		+ "/~ and t.remark = {remark} ~/"
				  		+ "/~ and t.state = {state} ~/"
					+ "/~ and t.sysDate >= {sysDateBegin} ~/"
					+ "/~ and t.sysDate <= {sysDateEnd} ~/"
				  		+ "/~ and t.week = {week} ~/"
				  		+ "/~ and t.year = {year} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
	/**
	 * 查询指定月份的日期集合
	 * @param year 
	 * @param month
	 * @return
	 */
	public List<SysCalendar> getMonthCalendars(Integer year,Integer month)
	{
		String hql = "from com.opendata.calendar.model.SysCalendar t where t.year=? and  t.month=? order by day asc";
		return super.findFastByHql(hql, year,month);
	}
	
	/**
	 * 根据开始日期，结束日期，查询该日间段内所有的工作日历
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<SysCalendar> findWorkDate(String beginDate,String endDate)
	{
		String hql="select t from SysCalendar t where t.sysDate>=? and  t.sysDate<=? and t.state=1 order by t.sysDate asc";
		return super.findFastByHql(hql, beginDate,endDate);
	}

}
