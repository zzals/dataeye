package kr.co.penta.dataeye.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {
	private static final TimeUtils TIME_UTIL = new TimeUtils();
	
	private TimeUtils() {
    	super();
    }
	
	public Calendar now() {
		return Calendar.getInstance();
	}
	
	public Timestamp getCurrentTimestamp() {
		return new Timestamp(now().getTimeInMillis());
	}
	
	public Date getCurrentDate() {
		return now().getTime();
	}
	
	public String formatDate(final Date date, final String format) {
		if (date == null) {
			return "";
		}
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
		return simpleDateFormat.format(date);
	}
	
	public String formatDate(final Timestamp timestamp, final String format) {
		if (timestamp == null) {
			return "";
		}
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
		return simpleDateFormat.format(timestampToDate(timestamp));
	}
	
	public Timestamp dateToTimestamp(final Date date) {
		return new Timestamp(date.getTime());
	}
	
	public Date timestampToDate(final Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public Timestamp stringToTimestamp(final String source, final String pattern) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		try {
			final Date date = simpleDateFormat.parse(source);
			return dateToTimestamp(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public Date stringToDate(final String source, final String pattern) {
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static TimeUtils getInstance() {
       return TIME_UTIL;
    }
}
