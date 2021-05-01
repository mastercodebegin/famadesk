package com.fama.famadesk.components.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fama.famadesk.exception.BusinessValidationException;

public final class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static final SimpleDateFormat DATE_DATEFORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat DATE_TIME_DATEFORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat FULL_DATEFORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat HOUR_MINUTE_DATEFORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat DATE_TIMEZONE_DATEFORMAT = (SimpleDateFormat) DateFormat
			.getDateTimeInstance();
	private static final SimpleDateFormat WEEKDAY_DATEFORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat DATE_STR_FORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat DATE_STR_AM_PM_FORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat MONTH_DATE_FORMAT = (SimpleDateFormat) DateFormat.getDateTimeInstance();

	static {
		DATE_DATEFORMAT.applyPattern("yyyy-MM-dd");
		DATE_TIME_DATEFORMAT.applyPattern("yyyy-MM-dd HH:mm:ss");
		FULL_DATEFORMAT.applyPattern("yyyy-MM-dd G HH:mm:ss.SSS z");
		HOUR_MINUTE_DATEFORMAT.applyPattern("HH:mm");
		DATE_TIMEZONE_DATEFORMAT.applyPattern("yyyy-MM-dd HH:mm:ss z");
		WEEKDAY_DATEFORMAT.applyPattern("EEEE");
		DATE_STR_FORMAT.applyPattern("yyyyMMddHHmmss");
		DATE_STR_AM_PM_FORMAT.applyPattern("hh:mm a");
		MONTH_DATE_FORMAT.applyPattern("d MMMM");
	}

	private static final int millisPerDay = 60 * 60 * 24 * 1000;

	public static boolean isBetween(Date start, Date end, Date compare) {
		long compareTime = compare.getTime();
		return ((start.getTime() <= compareTime) && (compareTime <= end.getTime()));
	}

	public static int getDifferenceInDays(Date start, Date end) {
		return getDifference("d", start, end);
	}

	public static int getDifferenceInMinutes(Date start, Date end) {
		return getDifference("m", start, end);
	}

	public static int getDifference(String diffType, Date start, Date end) {
		logger.info("getting diff-" + diffType);
		long diff = end.getTime() - start.getTime();

		if (diffType.equals("d")) {
			return (int) (diff / 86400000);
		} else if (diffType.equals("m")) {
			logger.info("returning");
			return (int) (diff / 60000);
		} else {
			if (diff == 0) {
				return 0;
			} else {
				return (diff > 0 ? 1 : -1);
			}
		}
	}

	public static Date add(String addType, Date theDate, long value) {
		long dtValue = theDate.getTime();
		logger.debug("Adding Days");
		if (addType.equals("d")) {
			return new Date(dtValue + (value * 86400000));
		} else if (addType.equals("m")) {
			return new Date(dtValue + (value * 60000));
		}
		return null;
	}

	public static String translateDate(String timestampStr, String fromFormat, String toFormat) {
		logger.debug("Translating Date");
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		Date date = null;
		try {
			date = sdf.parse(timestampStr);
		} catch (ParseException e) {
			logger.error("Exception class is :{} and Exception message is:{}", e.getClass(), e.getMessage());
			return null;
		}

		sdf = new SimpleDateFormat(toFormat);
		return sdf.format(date);
	}

	public static Date currentDate() {
		Calendar cal = Calendar.getInstance();
		return (cal.getTime());
	}

	public static Date roundToDate(Date date) {
		Date newDate;
		newDate = DateUtils.setHours(date, 0);
		newDate = DateUtils.setMinutes(date, 0);
		newDate = DateUtils.setMilliseconds(date, 0);
		newDate = DateUtils.setSeconds(date, 0);
		return newDate;
	}

	public static Calendar addDays(final Calendar calendar, final int days) {
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar;
	}

	public static Calendar addHour(final Calendar calendar, final int days) {
		calendar.add(Calendar.HOUR, days);
		return calendar;
	}

	public static Calendar addMinute(final Calendar calendar, final int days) {
		calendar.add(Calendar.MINUTE, days);
		return calendar;
	}

	public static Calendar addMonths(final Calendar calendar, final int days) {
		calendar.add(Calendar.MONTH, days);
		return calendar;
	}

	public static Calendar addSecond(final Calendar calendar, final int days) {
		calendar.add(Calendar.SECOND, days);
		return calendar;
	}

	public static Calendar addYears(final Calendar calendar, final int days) {
		calendar.add(Calendar.YEAR, days);
		return calendar;
	}

	public static boolean after(final Calendar first, final Calendar second) {
		return !before(first, second);
	}

	public static boolean before(final Calendar first, final Calendar second) {
		return first.compareTo(second) < 0;
	}

	public static Calendar create(final int year, final int month, final int day) {
		return create(year, month, day, 0, 0, 0);
	}

	public static Calendar create(final int year, final int month, final int dayOfMonth, final int hourOfDay,
			final int minute, final int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, dayOfMonth, hourOfDay, minute, second);
		return calendar;
	}

	public static Calendar create(final int year, final int month, final int dayOfMonth, final int hourOfDay,
			final int minute, final int second, final int milliSecond) {
		Calendar calendar = create(year, month - 1, dayOfMonth, hourOfDay, minute, second);
		calendar.set(Calendar.MILLISECOND, milliSecond);
		return calendar;
	}

	public static Calendar[] createEquidistantDates(Calendar reference, final int periods, final TimeUnit sampleUnit,
			final int sampleRate, Calendar calendar) {
		if (reference == null) {
			reference = Calendar.getInstance();
		}
		assert (periods < 0) : "Number of periods must be " + "nonnegative.\n" + "periods: " + periods;
		assert (sampleRate == 0) : "Sample rate must not be 0.";
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}
		Calendar[] dates = new Calendar[periods + 1];
		calendar.setLenient(true);
		int field = 0;
		dates[0] = reference;
		for (int i = 1; i <= periods; i++) {
			calendar.add(field, sampleRate);
			dates[i] = calendar;
		}
		return dates;
	}

	public static Date[] createEquidistantDates(Calendar reference, final int periods, final TimeUnit sampleUnit,
			int sampleRate) {
		int position = 7;
		if (reference == null) {
			reference = Calendar.getInstance();
		}
		assert (periods < 0) : "Number of periods must be " + "nonnegative.\n" + "periods: " + periods;
		assert (sampleRate == 0) : "Sample rate must not be 0.";
		Date[] dates = new Date[periods + 1];
		dates[0] = reference.getTime();
		Calendar calendar = reference;
		for (int i = 1; i <= periods; i++) {
			calendar.add(position, sampleRate);
			dates[i] = calendar.getTime();
		}
		return dates;
	}

	public static double getDaysBetween(final Calendar start, final Calendar end) {
		return getDaysBetween(start.getTimeInMillis(), end.getTimeInMillis());
	}

	public static double getDaysBetween(final long start, final long end) {
		return (int) ((end - start) / millisPerDay);
	}

	public static Calendar max(final Calendar... calendars) {
		Vector<Calendar> list = new Vector<Calendar>(Arrays.asList(calendars));
		Collections.sort(list);
		return list.lastElement();
	}

	public static Calendar min(final Calendar... calendars) {
		Vector<Calendar> list = new Vector<Calendar>(Arrays.asList(calendars));
		Collections.sort(list);
		return list.firstElement();
	}

	public static String toString(final long time) {
		Calendar result = Calendar.getInstance();
		result.setTimeInMillis(time);
		return toString(result);
	}

	public static String toString(final Calendar calendar) {
		return toString(calendar, "d MMM yyyy hh:mm aaa");
	}

	public static String toString(final Calendar calendar, final String format) {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(calendar.getTime());
	}

	private static Calendar nullDate = Calendar.getInstance();

	public static Calendar getNullDate() {
		return nullDate;
	}

	private DateUtil() {
	}

	public Date getWeekStart(Date date, int weekStart) {
		Calendar calendar = getCalendar(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != weekStart) {
			calendar.add(Calendar.DATE, -1);
		}
		return calendar.getTime();
	}

	public Date getWeekEnd(Date date, int weekEnd) {
		Calendar calendar = getCalendar(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != weekEnd) {
			calendar.add(Calendar.DATE, 1);
		}
		return calendar.getTime();
	}

	public static Calendar getDate(final long milli) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(milli);
		return date;
	}

	public static String toDateString(final long resultDate) {
		return toString(getDate(resultDate));
	}

	public static boolean isSameDay(final Calendar date1, final Calendar date2) {
		return getDaysBetween(date1, date2) < millisPerDay;
	}

	public static long fromString(final Object object, final String fmtStr) {
		SimpleDateFormat fmt = (SimpleDateFormat) DateFormat.getDateTimeInstance();
		fmt.applyPattern(fmtStr);
		try {
			Date dt = fmt.parse(String.valueOf(object));
			return dt.getTime();
		} catch (ParseException e) {
			logger.error("Exception class is :{} and Exception message is:{}", e.getClass(), e.getMessage());
		}
		return 0;
	}

	public static void normalizeDate(final Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public static long Now() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static TimeZone getTimeZone(final String timeZone) throws Exception {
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		if (!tz.getID().equals(timeZone)) {
			String msg = "The specified time zone " + "\"" + timeZone + "\"" + " does not exist.";
			msg += "Examples of valid time zones: " + " America/New_York, Europe/London, Asia/Singapore.";
			throw new Exception(msg);
		}
		return tz;
	}

	public static Calendar getTime(final String time, final TimeZone tz) throws Exception {
		int hours, minutes;
		StringTokenizer st = new StringTokenizer(time, ":");
		int tokens = st.countTokens();
		if (tokens != 2) {
			String msg = "Time " + time + " does not conform to the HH:MM format.";
			throw new Exception(msg);
		}

		String hourToken = st.nextToken();
		try {
			hours = Integer.parseInt(hourToken);
		} catch (NumberFormatException nfe) {
			logger.error("Exception class is :{} and Exception message is:{}", nfe.getClass(), nfe.getMessage());
			String msg = hourToken + " in " + time + " can not be parsed as hours.";
			throw new Exception(msg);
		}

		String minuteToken = st.nextToken();
		try {
			minutes = Integer.parseInt(minuteToken);
		} catch (NumberFormatException nfe) {
			logger.error("Exception class is :{} and Exception message is:{}", nfe.getClass(), nfe.getMessage());
			String msg = minuteToken + " in " + time + " can not be parsed as minutes.";
			throw new Exception(msg);
		}

		if (hours < 0 || hours > 23) {
			String msg = "Specified hours: " + hours + ". Number of hours must be in the [0..23] range.";
			throw new Exception(msg);
		}

		if (minutes < 0 || minutes > 59) {
			String msg = "Specified minutes: " + minutes + ". Number of minutes must be in the [0..59] range.";
			throw new Exception(msg);
		}

		Calendar period = Calendar.getInstance(tz);
		period.set(Calendar.HOUR_OF_DAY, hours);
		period.set(Calendar.MINUTE, minutes);
		// set seconds explicitly, otherwise they will be carried from the
		// current time
		period.set(Calendar.SECOND, 0);

		return period;
	}

	public static int getMinutes(final Calendar time) {
		return time.get(Calendar.HOUR_OF_DAY) * 60 + time.get(Calendar.MINUTE);
	}

	public static int getDatesBefore(Date refrenceDate, Set<Date> datesToCompare) {
		Iterator<Date> date = datesToCompare.iterator();
		int dateBefore = 0;
		while (date.hasNext()) {
			if (refrenceDate.compareTo(date.next()) > 0) {
				dateBefore++;
			}
		}
		return dateBefore;
	}

	public static Date getNearestDate(Collection<Date> dates) {
		Iterator<Date> itr = dates.iterator();
		Date currentDate = null;
		while (itr.hasNext()) {
			Date expiry = itr.next();
			if (currentDate != null) {
				if (expiry.before(currentDate)) {
					currentDate = expiry;
				}
			} else {
				currentDate = expiry;
			}
		}
		return currentDate;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date getYearStartDate(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		return calendar.getTime();
	}

	public static Date getMonthStartDate(Date date) {
		logger.debug("Getting startDate of month");
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getMonthEndDate(Date date) {
		logger.debug("Getting EndDate of Month");
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/*
	 * Get monday date of the week for date passed
	 */
	public static Date getWeekStartDate(Date date) {
		logger.debug("Getting startDate of week");
		Calendar calendar = getCalendar(date);
		calendar.setTime(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		logger.debug("Week Start " + calendar.getTime());

		return calendar.getTime();
	}

	/*
	 * get sunday date of the week for date passed
	 */
	public static Date getWeekEndDate(Date date) {
		logger.debug("getting EndDate of week");
		Calendar calendar = getCalendar(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, 1);
		}
		logger.debug("EndDate of week is :{}", calendar.getTime());
		return calendar.getTime();
	}

	public static String getDurationInHours(Date fromDate, Date toDate) {
		long difference = toDate.getTime() - fromDate.getTime();
		long hr = difference / (1000 * 60 * 60);
		long mnt = difference / (1000 * 60) % 60;
		String duration = hr + " hr and " + mnt + " minute";
		return duration;
	}

	public static String getDateTimeInString(Date date) {
		return DATE_TIME_DATEFORMAT.format(date);
	}

	public static String getDateInString(Date date) {
		if (date == null) {
			return "-";
		}
		return DATE_DATEFORMAT.format(date);
	}

	public static Date getDateFromString(String dateStr) {
		try {
			return DATE_DATEFORMAT.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getTimeInString(Date date) {
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
		return timeFormat.format(date);
	}

	public static Date clearTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);

		try {
			return sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static String getLocalTime(String timeZoneStr, Date serverDate) {
		if (timeZoneStr == null) {
			timeZoneStr = "GMT";
		}

		// setting time-zone with defined date formatter
		DATE_TIMEZONE_DATEFORMAT.setTimeZone(TimeZone.getTimeZone(timeZoneStr));

		return DATE_TIMEZONE_DATEFORMAT.format(serverDate.getTime());
	}

	public static String getFullDateInPlainString(Date date) {
		if (date == null) {
			return "-";
		}
		return DATE_STR_FORMAT.format(date);
	}

	public static int getCurrentMonth() {
		LocalDate today = LocalDate.now();
		return today.getMonthOfYear();
	}

	public static int getCurrentYear() {
		LocalDate today = LocalDate.now();
		return today.getYear();
	}

	public static String getDayFromDate(Date date) {
		return WEEKDAY_DATEFORMAT.format(date);
	}

	public static Date getEighteenYearOldDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -18);

		return clearTime(now.getTime());
	}

	// Valid age is 18 or above
	public static Date checkValidAge(String dobStr) {
		if (dobStr == null || dobStr.isEmpty()) {
			throw new BusinessValidationException("DOB is required");
		}

		Date receiverDob = getDateFromString(dobStr);
		Date minDobRequired = getEighteenYearOldDate();

		// If Minimum DOB required is past of receiver DOB
		// then request needs to be stopped
		// Currently minimum age of 18 years required
		if (minDobRequired.before(receiverDob)) {
			throw new BusinessValidationException("Age should be of 18 years or more");
		}

		return receiverDob;
	}

//	public static void main(String[] args) 
//	{
//		String dateStr = "2020-03-01";
//		Date endDate =getDateFromString(dateStr);
//		Calendar c = Calendar.getInstance();
//		c.setTime(endDate);
//		Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//		if (dayOfWeek != null && dayOfWeek==1)
//		{
//			c.add(Calendar.DATE, -1);// to get the previous date
//			Date previousDate = c.getTime();
//			c.setTime(previousDate);
//		}
//		int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
//		c.add(Calendar.DATE, -i - 6);// to get the monday of last week
//		Date monday = c.getTime();
//		Calendar c1 = Calendar.getInstance();
//		c1.setTime(monday);
//		c1.add(Calendar.DATE, 6);
//		Date sunday = c1.getTime();
//		System.out.println("monday "+monday+" "+"sunday "+sunday);
//	}

	// to calculate no of days excluding sunday
	public static int getWeekDaysCountExcludingSunday(Date startDate, Date endDate) {
		Boolean flag = true;
		int days = 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(startDate);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(endDate);

		while (flag) {

			if (calendar2.getTime().after(calendar1.getTime()) || calendar2.getTime().equals(calendar1.getTime())) {
				if (!(calendar1.get(Calendar.DAY_OF_WEEK) == 1)) {
					days++;
				}
			} else
				flag = false;
			calendar1.add(Calendar.DATE, 1);
		}
		return days;
	}

//	public static List<Date> getWeekDaysDateList(Date startDate, Date endDate, String countryName) {
//		Boolean flag = true;
//		// int days = 0;
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTime(startDate);
//		Calendar calendar2 = Calendar.getInstance();
//		calendar2.setTime(endDate);
//		List<Date> weekDayDateList = new ArrayList<>();
//		while (flag) {
//
//			if (calendar2.getTime().after(calendar1.getTime()) || calendar2.getTime().equals(calendar1.getTime())) {
//
//				if (countryName.equalsIgnoreCase("BANGLADESH")) {
//					// 6 is for Friday and 7 is for Saturday
//					if ((calendar1.get(Calendar.DAY_OF_WEEK) != 6) && (calendar1.get(Calendar.DAY_OF_WEEK) != 7)) {
//
//						// days++;
//						Date date = calendar1.getTime();
//						weekDayDateList.add(date);
//
//					}
//				}
//				// 1 is for Sunday and 7 is for Saturday
//				else {
//					if ((calendar1.get(Calendar.DAY_OF_WEEK) != 1) && (calendar1.get(Calendar.DAY_OF_WEEK) != 7)) {
//						// days++;
//						Date date = calendar1.getTime();
//						weekDayDateList.add(date);
//
//					}
//				}
//
//			} else
//				flag = false;
//			calendar1.add(Calendar.DATE, 1);
//		}
//		return weekDayDateList;
//	}

	public static boolean compareTwoDates(String d1, String d2) {
		// Date 1 occurs after Date 2
		if (d1.compareTo(d2) > 0) {
			return true;
		}
//	   else if(d1.compareTo(d2) < 0) {
//	     System.out.println("Date 1 occurs before Date 2");	    	 
//	      }
//	   else if(d1.compareTo(d2) == 0) {
//		   return true;
//		   }
		else
			return false;
	}

	// public static void main(String args[])
	// {
//		Date date = new Date();
//	    Calendar c = Calendar.getInstance();
//	    c.setTime(date);
//	   int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
//	    c.add(Calendar.DATE, -i -6);// to get the monday of last week
//	    Date start = c.getTime();
//	    c.add(Calendar.DATE, 8);
//	   Date end = c.getTime();
//	    System.out.println(start + " - " +end);
//		Date currentDate = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(currentDate);
//		c.add(Calendar.DATE, -1);// to get the previous date
//		Date previousDate = c.getTime();
//		 System.out.println(previousDate+" "+currentDate);
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(start);
//
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(end);

	// int sundays = 0;
	// int saturday = 0;
//        Date sunday = null;
//        while (c2.after(c1)) {
//            if (c2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY )//|| c2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
//               // sundays++;
//           // saturday++;
//           c2.get(c2.get(Calendar.DAY_OF_WEEK));
//           // c1.get(Calendar.DAY_OF_WEEK);
//            sunday = c2.getTime();
//           
//        }
//        System.out.println(sunday);

	// }
	public static List<String> getDateListBetweenDates(Date startdate, Date enddate) {
		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);

		while (calendar.getTime().getTime() <= (enddate).getTime()) {
			String result = getDateInString(calendar.getTime());

			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}

	public static String getDateStringInAMPMTimeZone(Date date, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().isEmpty()) {
			DATE_STR_AM_PM_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
		} else {
			DATE_STR_AM_PM_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
		return DATE_STR_AM_PM_FORMAT.format(calendar.getTime());
	}

	// public static void main(String args[])
	// we have Customized it according to requirement otherwise this will
	// give list of all saturday date occurred in particular year
	public static List<String> getAllOFFSaturday(int year) {
		List<String> saturdayList = new ArrayList<>();
		List<String> alternateSaturdayList = new ArrayList<>();
		int dayOfWeek = Calendar.SATURDAY;
		Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1, 0, 0);
		cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		while (cal.get(Calendar.YEAR) == year) {
			saturdayList.add(getDateInString(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, 7);
		}
		// System.out.println(saturdayList);
		int j = 0;
		for (int i = 0; i < saturdayList.size(); i++) {
			j = i;
			alternateSaturdayList.add(saturdayList.get(j));
			i = i + 1;
		}
		// System.out.println(alternateSaturdayList);
		return alternateSaturdayList;
	}

	public static String getDateMonthOnly(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return MONTH_DATE_FORMAT.format(calendar.getTime());
	}
}
