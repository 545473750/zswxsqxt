package com.opendata.calendar.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * 
 */


public class SysCalendarQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.String id;
	/** day */
	private java.lang.Integer day;
	/** month */
	private java.lang.Integer month;
	/** remark */
	private java.lang.String remark;
	/** state */
	private java.lang.Integer state;
	/** sysDate */
	private java.util.Date sysDateBegin;
	private java.util.Date sysDateEnd;
	/** week */
	private java.lang.Integer week;
	/** year */
	private java.lang.Integer year;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.Integer getDay() {
		return this.day;
	}
	
	public void setDay(java.lang.Integer value) {
		this.day = value;
	}
	
	public java.lang.Integer getMonth() {
		return this.month;
	}
	
	public void setMonth(java.lang.Integer value) {
		this.month = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
	public java.util.Date getSysDateBegin() {
		return this.sysDateBegin;
	}
	
	public void setSysDateBegin(java.util.Date value) {
		this.sysDateBegin = value;
	}	
	
	public java.util.Date getSysDateEnd() {
		return this.sysDateEnd;
	}
	
	public void setSysDateEnd(java.util.Date value) {
		this.sysDateEnd = value;
	}
	
	public java.lang.Integer getWeek() {
		return this.week;
	}
	
	public void setWeek(java.lang.Integer value) {
		this.week = value;
	}
	
	public java.lang.Integer getYear() {
		return this.year;
	}
	
	public void setYear(java.lang.Integer value) {
		this.year = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

