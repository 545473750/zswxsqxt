package com.opendata.calendar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.opendata.common.base.BaseEntity;

/**
 * @author 陈永锋
 * @date:2012/08/06
 * @description:工作日历实体
 */
public class SysCalendar extends BaseEntity implements
		java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	public static final String TABLE_ALIAS = "工作日历";
	public static final String ALIAS_DAY = "日期";
	public static final String ALIAS_MONTH = "月份";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_STATE = "工作状态";
	public static final String ALIAS_SYS_DATE = "系统日期";
	public static final String ALIAS_WEEK = "星期";
	public static final String ALIAS_YEAR = "年份";

	public static final String FORMAT_SYS_DATE = DATE_TIME_FORMAT;

	private java.lang.String id;// 主键
	private java.lang.Integer year;// 年
	private java.lang.Integer month;// 月
	private java.lang.Integer day;// 日期
	private String sysDate;// 年月日 （日期 ）
	private java.lang.Integer week;// 星期
	private java.lang.Integer state;// 状态 0：非工作日，1：工作日
	private java.lang.String remark;// 备注
	public String lunarDay;// 阴历日期全称
	public String simpleLunarDay;// 阴历日期简称
	private String festival;// 节日全称
	private String simpleFestival;// 节日简称
	private String solarTerms; // 节气
	public Integer trFlag;// 此字段不存储数据库
	public Integer lastNullTdCount;// 此字段不存储数据库

	public  SysCalendar() {
	}
	
	public  SysCalendar(String id) {
		this.id = id;
	}

	public Integer getTrFlag() {
		return trFlag;
	}

	public void setTrFlag(Integer trFlag) {
		this.trFlag = trFlag;
	}
	public Integer getLastNullTdCount() {
		return lastNullTdCount;
	}

	public void setLastNullTdCount(Integer lastNullTdCount) {
		this.lastNullTdCount = lastNullTdCount;
	}
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.Integer getYear() {
		return year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public java.lang.Integer getMonth() {
		return month;
	}

	public void setMonth(java.lang.Integer month) {
		this.month = month;
	}

	public java.lang.Integer getDay() {
		return day;
	}

	public void setDay(java.lang.Integer day) {
		this.day = day;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public java.lang.Integer getWeek() {
		return week;
	}

	public void setWeek(java.lang.Integer week) {
		this.week = week;
	}

	public java.lang.Integer getState() {
		return state;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public String getLunarDay() {
		return lunarDay;
	}

	public void setLunarDay(String lunarDay) {
		this.lunarDay = lunarDay;
	}

	public String getSimpleLunarDay() {
		return simpleLunarDay;
	}

	public void setSimpleLunarDay(String simpleLunarDay) {
		this.simpleLunarDay = simpleLunarDay;
	}

	public String getFestival() {
		return festival;
	}

	public void setFestival(String festival) {
		this.festival = festival;
	}

	public String getSimpleFestival() {
		return simpleFestival;
	}

	public void setSimpleFestival(String simpleFestival) {
		this.simpleFestival = simpleFestival;
	}

	public String getSolarTerms() {
		return solarTerms;
	}

	public void setSolarTerms(String solarTerms) {
		this.solarTerms = solarTerms;
	}

}
