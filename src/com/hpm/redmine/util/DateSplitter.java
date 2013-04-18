package com.hpm.redmine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.hpm.redmione.report.Day;

public class DateSplitter {

/*	public static void main(String[] args) {

		String string = "Sun Apr 28 00:00:00 IST 2013";

		convertToDate(string);

	}

	public static Date convertToDate(String date) {
		Date dateObj = null;
		try {
			dateObj = new SimpleDateFormat("EEE MMMM dd HH:mm:ss z yyyy",
					Locale.ENGLISH).parse(date);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return dateObj;
	}
*/
	@SuppressWarnings("static-access")
	public Day generateDay(Date date) {

		Day currentDate = null;
		int i = date.getDay();
		if (i == 0) {
			currentDate = currentDate.SUNDAY;
		} else if (i == 1) {
			currentDate = currentDate.MONDAY;
		} else if (i == 2) {
			currentDate = currentDate.TUESDAY;
		} else if (i == 3) {
			currentDate = currentDate.WEDNESDAY;
		} else if (i == 4) {
			currentDate = currentDate.THRUSDAY;
		} else if (i == 5) {
			currentDate = currentDate.FRIDAY;
		}

		return currentDate;
	}

	public String getDisplayDate(Date date) {
		SimpleDateFormat FORMATTER;

		FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

		return FORMATTER.format(date);
	}
}
