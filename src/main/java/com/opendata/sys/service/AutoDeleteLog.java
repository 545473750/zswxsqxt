package com.opendata.sys.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.opendata.common.util.Platform;
import com.opendata.sys.model.Log;
import com.opendata.sys.vo.query.LogQuery;

public class AutoDeleteLog {
	
	



	public void deleteLog(){
		LogManager logManager = (LogManager)Platform.getBean("logManager");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//系统设置定时器，每天凌晨0点开始清理日志。 保留的日志时段以月为单位，可设置是系统参数 
		int saveMonth = 12;
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -saveMonth);
		
		logManager.remove(null, sdf.format(calendar.getTime()));
		
		
	}


}
