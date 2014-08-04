package com.opendata.calendar.util;

import java.util.*;

public class SolarTerm {
	public static void main(String[] s) throws Exception {
		SolarTerm st = new SolarTerm();

		if (s.length == 3) {
			System.out.println(st.getSoralTerm(Integer.parseInt(s[0]),
					Integer.parseInt(s[1]), Integer.parseInt(s[2])));
		}

		System.out.println(st.getSoralTerm(2011, 12, 22));
		System.out.println(st.getSoralTerm(2015, 4, 5)+"--------");
	}

	final static long[] STermInfo = new long[] { 0, 21208, 42467, 63836, 85337,
			107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343,
			285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795,
			462224, 483532, 504758 };

	private static final String[] SolarTerm = new String[] { "小寒", "大寒", "立春",
			"雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
			"立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };

	public SolarTerm() {
	}

	/** 核心方法 根据日期得到节气 */
	public String getSoralTerm(Date Date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Date);

		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);

		return getSoralTerm(y, m, d);
	}

	/** 核心方法 根据日期(y年m月d日)得到节气 */
	public String getSoralTerm(int y, int m, int d) {
		String solarTerms;
		if (d == sTerm(y, (m - 1) * 2))
			solarTerms = SolarTerm[(m - 1) * 2];
		else if (d == sTerm(y, (m - 1) * 2 + 1))
			solarTerms = SolarTerm[(m - 1) * 2 + 1];
		else {
			// 到这里说明非节气时间
			solarTerms = "";
		}
		return solarTerms;
	}

	// ===== y年的第n个节气为几日(从0小寒起算)
	private int sTerm(int y, int n) {
		Calendar cal = Calendar.getInstance();
		cal.set(1900, 0, 6, 2, 5, 0);
		long temp = cal.getTime().getTime();
		cal.setTime(new Date(
				(long) ((31556925974.7 * (y - 1900) + STermInfo[n] * 60000L) + temp)));

		return cal.get(Calendar.DAY_OF_MONTH);
	}

}