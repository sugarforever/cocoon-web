package com.wiysoft.cocoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */
public class CocoonConfigurationTest {

    private CocoonConfiguration cocoonConfiguration;

    @Before
    public void before() {
        cocoonConfiguration = new CocoonConfiguration();
    }

    @Test
    public void testGetCodes() {
        String codes = "002001,002003, 002006 , 000009 ";
        cocoonConfiguration.setCodes(codes);
        String[] actualCodes = cocoonConfiguration.getCodesArray();
        Assert.assertEquals(4, actualCodes.length);

        List<String> expectedCodes = Arrays.asList(new String[]{"002001", "002003", "002006", "000009"});
        for (String actualCode : actualCodes) {
            Assert.assertTrue(expectedCodes.contains(actualCode));
        }
    }

    @Test
    public void testGetCodes2() {
        String codes = " ";
        cocoonConfiguration.setCodes(codes);
        String[] actualCodes = cocoonConfiguration.getCodesArray();
        Assert.assertEquals(0, actualCodes.length);
    }

    @Test
    public void testGetCodes3() {
        String codes = null;
        cocoonConfiguration.setCodes(codes);
        String[] actualCodes = cocoonConfiguration.getCodesArray();
        Assert.assertEquals(0, actualCodes.length);
    }

    @Test
    public void testGetHolidays() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);
        String[] actualHolidays = cocoonConfiguration.getHolidaysArray();
        Assert.assertEquals(4, actualHolidays.length);

        List<String> expectedHolidays = Arrays.asList(new String[]{"2015-09-02", "2015-09-10", "2015-10-10", "2015-11-11"});
        for (String actualHoliday : actualHolidays) {
            Assert.assertTrue(expectedHolidays.contains(actualHoliday));
        }
    }

    @Test
    public void testGetHolidays2() {
        String holidays = "  ";
        cocoonConfiguration.setHolidays(holidays);
        String[] actualHolidays = cocoonConfiguration.getHolidaysArray();
        Assert.assertEquals(0, actualHolidays.length);
    }

    @Test
    public void testGetHolidays3() {
        String holidays = null;
        cocoonConfiguration.setHolidays(holidays);
        String[] actualHolidays = cocoonConfiguration.getHolidaysArray();
        Assert.assertEquals(0, actualHolidays.length);
    }

    @Test
    public void testIsHoliday() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);

        Assert.assertTrue(cocoonConfiguration.isHoliday("2015-09-02"));
    }

    @Test
    public void testIsHoliday2() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);

        Assert.assertTrue(cocoonConfiguration.isHoliday("2015-09-06"));
    }

    @Test
    public void testIsHoliday3() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);

        Assert.assertFalse(cocoonConfiguration.isHoliday("2015-09-07"));
    }

    @Test
    public void testIsHoliday4() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);

        Assert.assertFalse(cocoonConfiguration.isHoliday(" "));
    }

    @Test
    public void testIsHoliday5() {
        String holidays = "2015-09-02, 2015-09-10 , 2015-10-10  , 2015-11-11 ";
        cocoonConfiguration.setHolidays(holidays);

        Assert.assertFalse(cocoonConfiguration.isHoliday(null));
    }
}
