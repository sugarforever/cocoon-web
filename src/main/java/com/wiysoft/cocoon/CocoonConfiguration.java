package com.wiysoft.cocoon;

import com.wiysoft.cocoon.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */

@Configuration
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "cocoon.application")
public class CocoonConfiguration {

    private String holidays;
    private String codes;
    private String fixedRateString;
    private String scheduledExpression;
    private String firstDay;

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getHolidays() {
        return holidays;
    }

    public String getCodes() {
        return codes;
    }

    public String getFixedRateString() {
        return fixedRateString;
    }

    public void setFixedRateString(String fixedRateString) {
        this.fixedRateString = fixedRateString;
    }

    public String getScheduledExpression() {
        return scheduledExpression;
    }

    public void setScheduledExpression(String scheduledExpression) {
        this.scheduledExpression = scheduledExpression;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public String[] getHolidaysArray() {
        return StringUtils.isBlank(holidays) ? new String[0] : holidays.trim().split("\\s*,\\s*");
    }

    public String[] getCodesArray() {
        return StringUtils.isBlank(codes) ? new String[0] : codes.trim().split("\\s*,\\s*");
    }

    public boolean isHoliday(String date) {
        List<String> holidays = Arrays.asList(getHolidaysArray());
        if (holidays.contains(date)) {
            return true;
        } else {
            Date d = CommonUtil.getDateFromStr(date, "yyyy-MM-dd");
            if (d == null) {
                return false;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
        }
    }

}
