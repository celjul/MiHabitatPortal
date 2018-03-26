package com.bstmexico.mihabitat.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class DateUtils {

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getLastDayYear(Integer year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Integer getDays(Date inicio, Date fin) {
		return (int) ((inicio.getTime() - fin.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static Collection<String> getPeriodos() {
		Collection<String> periodos = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, +13);
		for (int i = 0; i <= 24; i++) {
			calendar.add(Calendar.MONTH, -1);
			periodos.add(new SimpleDateFormat("MM-yyyy").format(calendar
					.getTime()));
		}
		return periodos;
	}

	public static String getDate(Date date, String formato) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		return format.format(date);
	}
}
