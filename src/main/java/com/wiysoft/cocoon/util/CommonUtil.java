package com.wiysoft.cocoon.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weiliyang on 7/24/15.
 */
public final class CommonUtil {

    public static Date getDateFromStr(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStrFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
