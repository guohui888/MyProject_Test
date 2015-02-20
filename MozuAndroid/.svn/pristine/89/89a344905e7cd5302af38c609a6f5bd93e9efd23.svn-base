package cn.com.zhoufu.mouth.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;

/**
 * 时间转换类
 * 
 * @author fuxianwei
 */
public class TimeConvertUtils extends DateUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	// 标准时间转换时间戳
	public static Long convert(String time) {
		try {
			if (TextUtils.isEmpty(time)) {
				return null;
			} else {
				return sdf3.parse(time).getTime();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将示自从标准基准时间以来的指定秒数转换为yyyy-MM-dd格式
	 * 
	 * @param second
	 * @return
	 */
	public static String secondToDate(long second) {
		Date date = new Date(second * 1000);
		return sdf2.format(date);
	}

	public static String secondToTime(long second) {
		Date date = new Date(second * 1000);
		return sdf3.format(date);
	}

	/**
	 * 将示自从标准基准时间以来的指定秒数转换为h : m格式
	 * 
	 * @param second
	 * @return
	 */
	public static String countTime(long second) {
		String result = "";
		long difftime = second - System.currentTimeMillis() / 1000;
		if (difftime > 0) {
			long h = difftime / 3600;
			long m = difftime % 3600 / 60;
			result = "" + h + "时" + m + "分";
		} else {
			result = "0时0分";
		}
		return result;
	}

	/**
	 * 转换标准格式
	 * 
	 * @param date
	 * @return
	 */
	public static String convert(Long date, Context context) {
		if (date == null || date == 0) {
			return "";
		}
		if (System.currentTimeMillis() - date <= 5 * 60 * 1000) {
			return "在线";
		}
		Calendar mCalendar = Calendar.getInstance();
		Calendar mCalendar2 = Calendar.getInstance();
		mCalendar2.setTimeInMillis(date);
		final int day1 = mCalendar.get(Calendar.DAY_OF_YEAR);
		final int day2 = mCalendar2.get(Calendar.DAY_OF_YEAR);
		final int differ = Math.abs(day1 - day2);
		// 如果是7天以内使用系统的计算时间方法
		if (differ < 7) {
			return DateUtils.getRelativeTimeSpanString(date).toString();
		} else if (differ < 14) {
			return "2周内";
		} else if (differ < 21) {
			return "3周内";
		} else if (differ < 30) {
			return String.format("%d天前", differ);
		} else if (differ < 90) {
			return "3个月内";
		} else {
			return "1年内";
		}
	}

	public static CharSequence convertTime(Long date, Context context) {
		if (date == null || date == 0) {
			return "";
		}
		return DateUtils.getRelativeTimeSpanString(date, System.currentTimeMillis(), MINUTE_IN_MILLIS, FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR | FORMAT_NUMERIC_DATE);
		// return DateUtils.getRelativeTimeSpanString(date).toString();
	}

	public static String convert(String date, Context context) throws ParseException {
		int flags = 0;
		String time = "";
		long dates = sdf.parse(date).getTime();
		try {
			if (DateUtils.isToday(dates)) {
				time = DateUtils.getRelativeTimeSpanString(dates).toString();
			} else if (isyestoday(dates)) {
				time = DateUtils.getRelativeTimeSpanString(dates).toString();
			} else if (islastoday(dates)) {
				time = DateUtils.getRelativeTimeSpanString(dates).toString();
			} else {
				flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME;
				time = (String) DateUtils.formatDateTime(context, dates, flags);
			}
		} catch (Exception e) {
		}
		return time;
	}

	/**
	 * 转换标准时间格式
	 * 
	 * @param date
	 * @return
	 */
	public static String convert(Date date) {
		return sdf.format(date);
	}

	public static String convert(Long time) {
		if (time == null)
			return null;
		return sdf3.format(new Date(time));
	}

	public static boolean isyestoday(long when) {
		Time time = new Time();
		time.set(when);

		int thenYear = time.year;
		int thenMonth = time.month;
		int thenMonthDay = time.monthDay;

		time.set(System.currentTimeMillis());
		return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == (time.monthDay - 1));
	}

	public static boolean islastoday(long when) {
		Time time = new Time();
		time.set(when);

		int thenYear = time.year;
		int thenMonth = time.month;
		int thenMonthDay = time.monthDay;

		time.set(System.currentTimeMillis());
		return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == (time.monthDay - 2));
	}

	/**
	 * 返回当前日期，前后num天
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static String addDate(int num) {
		Calendar c = Calendar.getInstance();
		c.roll(Calendar.DAY_OF_YEAR, num);
		String result = sdf2.format(c.getTime());
		return result;
	}

	public static boolean CheckTime(Long time) {
		Long interval = System.currentTimeMillis() - time;
		if (interval >= 3600000) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
}
