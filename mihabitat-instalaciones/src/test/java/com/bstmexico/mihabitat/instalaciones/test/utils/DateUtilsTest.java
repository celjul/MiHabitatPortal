package com.bstmexico.mihabitat.instalaciones.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilsTest {

	public static Date getDate(int years, int months, int days) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, years);
		cal.set(Calendar.DATE, days);
		cal.set(Calendar.MONTH, (months - 1));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	
	public static Date getDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(format.format(date));
		} catch (ParseException e) {
			return null;
		}
	}
}
