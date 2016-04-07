package com.ferrumlabs.services.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ferrumlabs.services.interfaces.IChristianCalendarService;

@Service("ChristianCalendarService")
public class ChristianCalendarService implements IChristianCalendarService {

	@Override
	public DateTime getEasterDate(int year) {
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int n = (h + l - 7 * m + 114) / 31;
		int p = (h + l - 7 * m + 114) % 31;
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.clear();
		calendar.set(year, n-1, p + 1);
		return new DateTime(calendar.getTime());
	}
}
