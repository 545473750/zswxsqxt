package com.opendata.calendar.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.common.util.Util;
import com.opendata.calendar.dao.SysCalendarDao;
import com.opendata.calendar.model.SysCalendar;
import com.opendata.calendar.util.Lunar;
import com.opendata.calendar.util.SolarTerm;
import com.opendata.calendar.vo.query.SysCalendarQuery;

/**
 * 
 */

@Service
@Transactional
public class SysCalendarManager extends BaseManager<SysCalendar, java.lang.String>
{

	private SysCalendarDao sysCalendarDao;

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写 */
	public void setSysCalendarDao(SysCalendarDao dao)
	{
		this.sysCalendarDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.sysCalendarDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(SysCalendarQuery query)
	{
		return sysCalendarDao.findPage(query);
	}

	/**
	 * 保存一年的日期
	 * 
	 * @param year
	 */
	public void saveYearCalendars(Integer year)
	{
		Calendar cal = Calendar.getInstance();
		int m = 1;
		while (m <= 12)
		{
			cal.set(year, m - 1, 1, 0, 0, 0);
			for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++)
			{
				cal.set(year, m - 1, i);
				int week = cal.get(Calendar.DAY_OF_WEEK);
				SysCalendar syscal = new SysCalendar();
				syscal.setDay(i);
				syscal.setMonth(m);
				syscal.setWeek(week);
				syscal.setYear(year);
				syscal.setSysDate(Util.getTimeByDate(cal.getTime()));
				if (week == 1 || week == 7)
				{
					syscal.setState(0);// 非工作日
				} else
				{
					syscal.setState(1);
				}
				SysCalendar sysalendar = getSysCalendar(syscal);
				if(this.sysCalendarDao.countByHql(" from SysCalendar t where t.year=? and t.month=? and t.day=?", sysalendar.getYear(),sysalendar.getMonth(),sysalendar.getDay())<1)
				{
					sysCalendarDao.save(sysalendar);
				}
			}
			m++;
		}
	}

	/**
	 * 保存一年的日历
	 * 
	 * @param dateString
	 *            日期字符串
	 */
	public void save(String dateString)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(Util.StringToDate(dateString));
		this.saveYearCalendars(cal.get(Calendar.YEAR));
	}

	/**
	 * 保存一年的日历
	 * 
	 * @param date
	 *            日期
	 */
	public void save(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		this.saveYearCalendars(cal.get(Calendar.YEAR));
	}

	/**
	 * 查询指定月份的日期集合
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<SysCalendar> getMonthCalendars(Integer year, Integer month)
	{
		return sysCalendarDao.getMonthCalendars(year, month);
	}

	private SysCalendar getSysCalendar(SysCalendar syscal)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(Util.StringToDate(syscal.getSysDate()));
		// int year = syscal.getYear();
		int month = syscal.getMonth();
		int day = syscal.getDay();

		Lunar lunar = new Lunar();// 阴历
		lunar.getLunar(cal);

		String chinaDayString = Lunar.getChinaDayString(lunar.getDay());
		syscal.setLunarDay(Lunar.chineseNumber[lunar.getMonth() - 1] + "月" + chinaDayString);// 阴历全称，例如二月十九

		if ("初一".equals(chinaDayString))
		{
			chinaDayString = String.valueOf(Lunar.chineseNumber[lunar.getMonth() - 1]) + "月 ";
		}

		/** ***********阳历节日************************* */
		syscal.setSimpleLunarDay(chinaDayString);// 阴历简称

		if (month == 1 && day == 1)
		{
			syscal.setSimpleFestival("元旦");// 节日简称
			syscal.setFestival("元旦");// 节日全称
			syscal.setState(0); // 法定假日
		}

		if (month == 5 && day == 1)
		{
			syscal.setSimpleFestival("劳动节");// 节日简称
			syscal.setFestival("五一国际劳动节");// 节日全称
			syscal.setState(0); // 法定假日
		}

		if (month == 6 && day == 1)
		{
			syscal.setSimpleFestival("儿童节");// 节日简称
			syscal.setFestival("六一国际儿童节");// 节日全称
		}

		if (month == 10 && day == 1)
		{
			syscal.setSimpleFestival("国庆节");// 节日简称
			syscal.setFestival("国庆节");// 节日全称
			syscal.setState(0); // 法定假日
		}

		/** **************阴历节日********************** */
		// int lunaryear = lunar.getYear(); //阴历年
		int lunarlimonth = lunar.getMonth(); // 阴历月
		int lunarday = lunar.getDay(); // 阴历日

		/** **************阴历固定日期节日********************** */
		if (lunarlimonth == 1 && lunarday == 1)
		{
			syscal.setSimpleFestival("春节");// 节日简称
			syscal.setFestival("春节");
			syscal.setState(0); // 法定假日
		}
		if (lunarlimonth == 5 && lunarday == 5)
		{
			syscal.setSimpleFestival("端午节");// 节日简称
			syscal.setFestival("端午节");
			syscal.setState(0); // 法定假日
		}
		if (lunarlimonth == 8 && lunarday == 15)
		{
			syscal.setSimpleFestival("中秋节");// 节日简称
			syscal.setFestival("中秋节");
			syscal.setState(0); // 法定假日
		}

		/** **************阴历不固定日期节日--24节气********************** */

		SolarTerm s = new SolarTerm();
		String solarTerms = s.getSoralTerm(Util.StringToDate(syscal.getSysDate())); // 24节气
		syscal.setSolarTerms(solarTerms);

		return syscal;
	}

	/**
	 * 根据开始日期，结束日期，查询该日间段内所有的工作日历
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<SysCalendar> findWorkDate(Date beginDate, Date endDate)
	{
		String beginDateString = Util.getTimeByDate(beginDate);
		String endDateString = Util.getTimeByDate(endDate);
		return this.sysCalendarDao.findWorkDate(beginDateString, endDateString);
	}

	/**
	 * 根据开始日期，结束日期，查询该日间段内所有的工作日历
	 * 
	 * @param beginDateString
	 * @param endDateString
	 * @return
	 */
	public List<SysCalendar> findWorkDateString(String beginDateString, String endDateString)
	{
		return this.sysCalendarDao.findWorkDate(beginDateString, endDateString);
	}

	/**
	 * 查询某一个月的 “工作日” 的数量
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public Integer findWorkDateCount(Integer year, Integer month)
	{
		String hql = " from SysCalendar t where t.year=? and  t.month=? and t.state=1 order by t.sysDate asc";
		return sysCalendarDao.countByHql(hql, year, month);
	}

	/**
	 * 查询时间段内的“工作日”数量
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public Integer findWorkDateCount(String begin, String end)
	{
		String hql = " from SysCalendar t where t.sysDate>=? and  t.sysDate<=? and t.state=1 order by t.sysDate asc";
		return sysCalendarDao.countByHql(hql, begin, end);
	}

	/**
	 * 获取某一日期的上一个工作日对象；如果dateString为空，则默认为当前日期
	 * 
	 * @param dateString
	 *            某一日期
	 * @return
	 */
	public SysCalendar findPreviousWorkDate(String dateString)
	{
		if (StringUtils.isBlank(dateString))
		{
			dateString = Util.getPreTimeByDate(new Date(), 1);//
		}
		String hql = "select t from SysCalendar t where    t.sysDate<=? and t.state=1 order by t.sysDate desc";
		List<SysCalendar> list = sysCalendarDao.findFastByHql(hql, dateString);
		if (list.size() < 1)
		{
			this.save(dateString);
			List<SysCalendar> newlist = sysCalendarDao.findFastByHql(hql, dateString);
			return newlist.get(0);
		}
		return list.get(0);
	}

	/**
	 * 获取当前日期的上一个工作日对象
	 * 
	 * @param date
	 *            当前日期
	 * @return
	 */
	public SysCalendar findPreviousWorkDate(Date date)
	{
		String current = Util.getTimeByDate(date);
		return findPreviousWorkDate(current);
	}

	/**
	 * 获取某一日期的下一个工作日对象；如果dateString为空，则默认为当前日期
	 * 
	 * @param dateString
	 * @return
	 */
	public SysCalendar findNextWorkDate(String dateString)
	{
		if (StringUtils.isBlank(dateString))
		{
			dateString = Util.getPreTimeByDate(new Date(), 10);//
		}
		String hql = "select t from SysCalendar t where    t.sysDate>? and t.state=1 order by t.sysDate ";
		List<SysCalendar> list = sysCalendarDao.findFastByHql(hql, dateString);
		if (list.size() < 1)
		{
			this.save(dateString);
			List<SysCalendar> newlist = sysCalendarDao.findFastByHql(hql, dateString);
			return newlist.get(0);
		}
		return list.get(0);
	}

	/**
	 * 获取某一日期的下一个工作日对象
	 * 
	 * @param date
	 * @return
	 */
	public SysCalendar findNextWorkDate(Date date)
	{
		String current = Util.getTimeByDate(date);
		return findNextWorkDate(current);
	}

	/**
	 * 查询某个月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public SysCalendar findFirstDayOfMonth(int year, int month)
	{
		String hql = "select t from SysCalendar t where    t.year=? and t.month=? and t.state=1 order by t.sysDate ";
		List<SysCalendar> list = sysCalendarDao.findFastByHql(hql, year, month);
		if (list.size() == 0)
		{
			this.saveYearCalendars(year);
			list = sysCalendarDao.findFastByHql(hql, year, month);
		}
		return list.get(0);
	}

	/**
	 * 查询某个日期所在月的第一天
	 * 
	 * @param dateString
	 * @return
	 */
	public SysCalendar findFirstDayOfMonth(String dateString)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(Util.StringToDate(dateString));
		return this.findFirstDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
	}

	/**
	 * 查询某个日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public SysCalendar findFirstDayOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return this.findFirstDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	/**
	 * 查询某个月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public SysCalendar findLastDayOfMonth(int year, int month)
	{
		String hql = "select t from SysCalendar t where    t.year=? and t.month=? and t.state=1 order by t.sysDate desc";
		List<SysCalendar> list = sysCalendarDao.findFastByHql(hql, year, month);
		if (list.size() == 0)
		{
			this.saveYearCalendars(year);
			list = sysCalendarDao.findFastByHql(hql, year, month);
		}
		return list.get(0);
	}

	/**
	 * 查询某个日期所在月的最后一天
	 * 
	 * @param dateString
	 * @return
	 */
	public SysCalendar findLastDayOfMonth(String dateString)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(Util.StringToDate(dateString));
		return this.findLastDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	/**
	 * 查询某个日期所在月的最后一天
	 * 
	 * @param dateString
	 * @return
	 */
	public SysCalendar findLastDayOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return this.findLastDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}
}
