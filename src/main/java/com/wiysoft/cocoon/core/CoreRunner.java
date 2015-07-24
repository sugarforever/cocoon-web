package com.wiysoft.cocoon.core;

import com.wiysoft.cocoon.core.inf.Convert;
import com.wiysoft.cocoon.core.inf.Runner;
import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.crawler.CocoonCrawler;
import com.wiysoft.cocoon.crawler.config.Config;
import com.wiysoft.cocoon.rawparser.CocoonRawParser;
import com.wiysoft.cocoon.rawparser.model.Entry;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by weiliyang on 7/17/15.
 */
public class CoreRunner implements Runner {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Convert symbolConvert;
    private CocoonRawParser cocoonRawParser;

    public void setSymbolConvert(Convert symbolConvert) {
        this.symbolConvert = symbolConvert;
    }

    public void setCocoonRawParser(CocoonRawParser cocoonRawParser) {
        this.cocoonRawParser = cocoonRawParser;
    }

    @Override
    public List<Slot> pull(String code, Date date) {
        List<Slot> slots = new ArrayList<Slot>();

        if (StringUtils.isEmpty(code) || date == null) {
            return slots;
        }

        String strDate = getDateStr(date);
        String symbol = getSymbolBy(code);

        Config config = new Config();
        config.setMethod(Config.HttpMethod.GET);
        config.setResponseDecodeCharset("gb2312");
        config.setUri("http://market.finance.sina.com.cn/downxls.php?date=" + strDate + "&symbol=" + symbol);

        CocoonCrawler cc = new CocoonCrawler(config);
        List<Entry> entries = parseResponse(cc.run());

        if (entries != null) {
            for (Entry entry : entries) {
                Slot slot = new Slot();
                slot.setChange(entry.getChange());
                slot.setChangePercentage(entry.getChange() / entry.getPrice());
                slot.setCode(code);
                slot.setDate(setYearMonthDay(entry.getDate(), date));
                slot.setName("");
                slot.setPrice(entry.getPrice());
                slot.setType(entry.getType());
                slot.setValue(entry.getValue());
                slot.setVolume(entry.getVolume());

                slots.add(slot);
            }
        }

        return slots;
    }

    private String getDateStr(Date date) {
        return DATE_FORMAT.format(date);
    }

    private String getSymbolBy(String code) {
        return this.symbolConvert == null ? code : this.symbolConvert.convert(code);
    }

    private List<Entry> parseResponse(String response) {
        List<Entry> entries = null;
        if (this.cocoonRawParser != null) {
            entries = this.cocoonRawParser.parse(response);
        }

        return entries;
    }

    private Date setYearMonthDay(Date hourMinuteSecond, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hourMinuteSecond);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();
    }
}
