package core.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Tang Yong Di
 * @date 2015/5/12
 */
public abstract class JodaUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String MS_FORMAT = "SSS";

    public static final String DEFAULT_DATE_TIME_FORMAT = DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    public static final String DATE_TIME_MS_FORMAT = DATE_FORMAT + " " + DEFAULT_TIME_FORMAT + "." + MS_FORMAT;

    public static final String DATE_TIME_MIN_FORMAT = "yyyyMMddHHmm";
    public static final String DATE_DAY_FORMAT = "yyyyMMdd";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FULL_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String DATE_YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String CHINESE_YEAR_MONTH = "yyyy年MM月";
    public static final String CHINESE_DATE_TIME_FORMAT = "yyyy年MM月dd日 HH时mm分";
    public static final String CHINESE_MONTH_DAY_FORMAT = "MM月dd日";
    public static final String[] CHINESE_WEEK_NAMES = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    // delphi date start FOR flyhand api
    public static final LocalDate DELPHI_DATE_START = new LocalDate(1899, 12, 30);

    public static DateTime now() {
        return new DateTime();
    }

    public static LocalDate today() {
        return new LocalDate();
    }

    public static String localDateToString(LocalDate localDate) {
        return localDateToString(localDate, DATE_FORMAT);
    }

    public static String localDateDayToString(LocalDate localDate) {
        return localDateToString(localDate, DATE_DAY_FORMAT);
    }

    public static String localDateToString(LocalDate localDate, String format) {
        if (localDate == null) {
            return "";
        }
        return localDate.toString(format);
    }

    public static String dateTimeToDateString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(DATE_FORMAT);
    }

    public static String dateTimeToString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(DATE_TIME_FORMAT);
    }

    public static String dateTimeAllToString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(DEFAULT_DATE_TIME_FORMAT);
    }

    public static String dateTimeStampToString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(TIMESTAMP_FORMAT);
    }

    public static String dateTimeMinToString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(DATE_TIME_MIN_FORMAT);
    }

    public static String dateYearMonthToString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(DATE_YEAR_MONTH_FORMAT);
    }

    public static LocalDate parseLocalDate(String dateAsString, String format) {
        if (StringUtils.hasText(dateAsString)) {
            Date date = parseDate(dateAsString, format);
            return new LocalDate(date);
        }
        return null;
    }

    public static LocalTime parseLocalTime(String timeAsString) {
        if (StringUtils.hasText(timeAsString)) {
            return new LocalTime(timeAsString);
        }
        return null;
    }

    public static Date parseDate(String dateAsString, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date string can not be parsed:" + dateAsString, e);
        }
    }

    public static LocalDate parseLocalDate(String localDateAsString) {
        return parseLocalDate(localDateAsString, DATE_FORMAT);
    }

    public static LocalDate parseLocalDateByChinese(String localDateAsString) {
        return parseLocalDate(localDateAsString, CHINESE_DATE_FORMAT);
    }

    /**
     * convert Delphi datetime time to LocalDate
     *
     * @param day 天数
     */
    public static LocalDate parseDelphiLocalDate(Integer day) {
        if (day != null) {
            return DELPHI_DATE_START.plusDays(day);
        }
        return null;
    }

    public static LocalDate parseLocalDate(BigInteger timer) {
        if (timer == null) {
            return null;
        }
        return new LocalDate(timer.longValue());
    }

    public static String dateTimeNow() {
        return JodaUtils.now().toString(JodaUtils.DATE_TIME_FORMAT);
    }

    public static String defaultDateTimeNow() {
        return JodaUtils.now().toString(JodaUtils.DEFAULT_DATE_TIME_FORMAT);
    }

    public static String dateTimeNowFull() {
        return JodaUtils.now().toString(JodaUtils.DATE_TIME_FULL_FORMAT);
    }

    public static String dateTimeNowDay() {
        return JodaUtils.now().toString(JodaUtils.DATE_FORMAT);
    }

    public static String timestampNowStr() {
        return JodaUtils.now().toString(JodaUtils.TIMESTAMP_FORMAT);
    }

    public static String timestampSeconds() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * Check the specify <code>dateTime</code> is or not  in today.
     *
     * @param dateTime DateTime
     * @return True is today,otherwise false
     */
    public static boolean isToday(DateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDate localDate = dateTime.toLocalDate();
        return localDate.isEqual(today());
    }

    public static DateTime parseDateTime(String val) {
        return parseDateTime(val, DATE_TIME_FORMAT);
    }

    public static DateTime parseDayDateTime(String val) {
        return parseDateTime(val, DATE_FORMAT);
    }

    public static DateTime parseTimeDateTime(String val) {
        return parseDateTime(val, DEFAULT_DATE_TIME_FORMAT);
    }

    public static DateTime parseDateTime(BigInteger timer) {
        if (timer == null) return null;
        return new DateTime(timer.longValue());
    }

    public static DateTime parseDateTime(Long timer) {
        if (timer == null) return null;
        return new DateTime(timer);
    }

    public static DateTime parseDateTime(String text, String patten) {
        if (ValidationUtils.isEmpty(text)) {
            return null;
        }
        return DateTimeFormat.forPattern(patten).parseDateTime(text);
    }

    public static LocalDate yesterday() {
        return today().plusDays(-1);
    }

    public static LocalDate tomorrow() {
        return today().plusDays(1);
    }

    public static boolean isAfterToday(LocalDate localDate) {
        return localDate.isAfter(today());
    }

    public static boolean isBeforeToday(LocalDate localDate) {
        return localDate.isBefore(today());
    }

    public static Integer daysToNow(DateTime time) {
        return daysToToday(time.toLocalDate());
    }

    public static Integer daysToToday(LocalDate date) {
        Period period = new Period(JodaUtils.today(), date, PeriodType.days());
        return period.getDays();
    }

    public static DateTime monthStart() {
        LocalDate today = today();
        int year = today.getYear();
        int monthOfYear = today.getMonthOfYear();
        return new DateTime(year, monthOfYear, 1, 0, 0, 0, 0);
    }

    /**
     * 获取指定日期当月第一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime getMonthStart(DateTime dateTime) {
        int year = dateTime.getYear();
        int monthOfYear = dateTime.getMonthOfYear();
        return new DateTime(year, monthOfYear, 1, 0, 0, 0, 0);
    }

    /**
     * 获取指定日期下个月第一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime getNextMonthStart(DateTime dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime.toDate());
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return new DateTime(cal);
    }

    /**
     * 获取指定日期当月最后一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime getMonthLast(DateTime dateTime) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dateTime.toDate());
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new DateTime(ca);
    }

    public static DateTime todayStart() {
        LocalDate today = today();
        int year = today.getYear();
        int monthOfYear = today.getMonthOfYear();
        int dayOfMonth = today.getDayOfMonth();
        return new DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
    }

    public static Long getTimer(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.getMillis();
    }

    public static Long getTimer(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return getTimer(localDate.toDateTimeAtStartOfDay());
    }

    public static String timerToDateString(Number timer) {
        if (timer != null) {
            LocalDate date = new LocalDate(timer.longValue());
            return localDateToString(date);
        }
        return null;
    }

    public static String timerToDateTimeString(BigInteger timer) {
        if (timer != null) {
            DateTime dateTime = new DateTime(timer.longValue());
            return dateTimeAllToString(dateTime);
        }
        return null;
    }

    public static String timerToDateAllTimeString(BigInteger timer) {
        if (timer != null) {
            DateTime dateTime = new DateTime(timer.longValue());
            return dateTimeToString(dateTime);
        }
        return null;
    }

    public static String timerToDateCNString(BigInteger timer) {
        if (timer != null) {
            DateTime dateTime = new DateTime(timer.longValue());
            return chineseDate(dateTime);
        }
        return null;
    }

    public static String chineseDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.toString(CHINESE_DATE_FORMAT);
    }

    public static String chineseDate(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(CHINESE_DATE_FORMAT);
    }

    public static String chineseDateYearMonth(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(CHINESE_YEAR_MONTH);
    }

    public static String chineseDatetime(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.toString(CHINESE_DATE_TIME_FORMAT);
    }

    public static String localTimeToString(LocalTime time) {
        if (time == null) return "";
        return time.toString(TIME_FORMAT);
    }

    public static String toDateTimeMSString(DateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.toString(JodaUtils.DATE_TIME_MS_FORMAT);
    }

    public static String toDateTimeMSString(BigInteger time) {
        if (time == null) return "";
        DateTime dateTime = new DateTime(time.longValue());
        return toDateTimeMSString(dateTime);
    }

    public static BigInteger parseBigInteger(String datetimeStr, String patten) {
        DateTime dateTime = JodaUtils.parseDateTime(datetimeStr, patten);
        return parseBigInteger(dateTime);
    }

    public static BigInteger bigIntegerNow() {
        return parseBigInteger(now());
    }

    public static BigInteger parseBigInteger(DateTime datetime) {
        if (datetime != null) {
            return BigInteger.valueOf(datetime.getMillis());
        }
        return null;
    }

    public static BigInteger parseBigInteger(LocalDate date) {
        if (date != null) {
            DateTime datetime = date.toDateTimeAtStartOfDay();
            return parseBigInteger(datetime);
        }
        return null;
    }

    public static BigInteger parseAddDaysBigInteger(LocalDate date, Integer days) {
        if (date != null) {
            DateTime datetime = date.toDateTimeAtStartOfDay();
            return parseAddDaysBigInteger(datetime, days);
        }
        return null;
    }

    public static BigInteger parseAddDaysBigInteger(String datetimeStr, String patten, Integer days) {
        DateTime dateTime = JodaUtils.parseDateTime(datetimeStr, patten);
        return parseBigInteger(dateTime.plusDays(days));
    }

    public static BigInteger parseAddDaysBigInteger(DateTime datetime, Integer days) {
        if (datetime != null) {
            datetime = datetime.plusDays(days);
            return BigInteger.valueOf(datetime.getMillis());
        }
        return null;
    }

    public static BigInteger bigIntegerAddDays(BigInteger datetimeBigInteger, Integer days) {
        DateTime datetime = new DateTime(datetimeBigInteger.longValue());
        return parseAddDaysBigInteger(datetime, days);
    }

    public static String timerToMonthDayString(BigInteger timer) {
        if (timer == null) return "";
        DateTime dateTime = new DateTime(timer.longValue());
        return dateTime.toString(CHINESE_MONTH_DAY_FORMAT);
    }

    public static String toMonthDayString(LocalDate date) {
        if (date == null) return "";
        return date.toString(CHINESE_MONTH_DAY_FORMAT);
    }

    //分钟数转 小时+分钟 字符串
    public static String minToHour(Integer min) {
        if (min < 60) {
            return min + "分钟";
        }
        Integer remain = min % 60;
        Integer hour = min / 60;
        if (remain == 0) {
            return hour + "小时";
        } else {
            return hour + "小时 " + remain + "分钟";
        }
    }

    //秒数转 分钟+秒 字符串
    public static String secToMin(Integer sec) {
        if (sec < 60) {
            return sec + "秒";
        }
        Integer remain = sec % 60;
        Integer min = sec / 60;
        if (remain == 0) {
            return min + "分";
        } else {
            return min + "分 " + remain + "秒";
        }
    }

    //今天某个日期和现在相差分钟数  再转成文字形式
    public static String twoDayToMin(Date date) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        Date date2 = new Date();
        long cha = date2.getTime() - date.getTime();
        long hour = cha % nd / nh;
        long min = cha % nd % nh / nm;
        long minCha = min + hour * 60;
        return minToHour(Integer.parseInt(minCha + ""));
    }

    //两个时间相差分钟数
    public static Integer twoDayToMin(Date date, Date date2) {
        long cha = Math.abs(date2.getTime() - date.getTime());
        return Integer.parseInt(cha / ((1000 * 60 * 60 * 24)) + "");
    }

    /**
     * 获取与今天相差day天的日期，day为day天后，-day为day天前
     *
     * @param day
     * @return
     */
    public static String getNDayDate(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public static boolean canParseDate(String dateStr, String patten) {
        if (ValidationUtils.isEmpty(dateStr)) {
            return false;
        }
        try {
            DateTimeFormat.forPattern(patten).parseDateTime(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
