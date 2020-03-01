package com.info.manage.util;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.util.Strings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * xxy
 * 2018/8/30
 * 描述 时间工具类
 */

public class Dates extends DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATAFORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATAFORMAT_SLASHDATE = "yyyy/MM/dd";
    public static final String DATAFORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATAFORMAT_YYYYMM = "yyyyMM";

     //一天的总毫秒数
    public static final long ONE_DAY_MILLISECONDS = 86400000;

    // 一分钟的总毫秒数
    public static final long ONE_MINUTE_MILLISECONDS = 6000;

    public static String getDateTime(Date date, String pattern) {
        if (Strings.isEmpty(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 2019-01-13T11:23:40.000+0000  做特殊转换
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateTimeStr(Date date, String pattern) {
        if (Strings.isEmpty(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone( TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public static void main(String[] args) throws ParseException {
        String oldDateStr = "2019-01-13T11:23:40.000+0000";
        DateFormat df = new SimpleDateFormat ( "yyyy-MM-dd'T'hh:mm:ss.SSSZ" );
        Date result = df.parse ( oldDateStr );
        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
        sdf.setTimeZone ( TimeZone.getTimeZone ( "GMT" ) );
        System.out.println ( sdf.format ( result ) );
    }
}