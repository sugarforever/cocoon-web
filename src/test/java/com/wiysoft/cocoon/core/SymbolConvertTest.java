package com.wiysoft.cocoon.core;

import com.wiysoft.cocoon.core.inf.Convert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by weiliyang on 7/18/15.
 */
public class SymbolConvertTest {

    private Convert convert;

    @Before
    public void setup() {
        this.convert = new SymbolConvert();
    }

    @Test
    public void testConvert1() {
        String code = "002001";
        String actualConverted = this.convert.convert(code);
        String expectedConverted = "sz002001";
        Assert.assertEquals(expectedConverted, actualConverted);
    }

    @Test
    public void testConvert2() {
        String code = "300001";
        String actualConverted = this.convert.convert(code);
        String expectedConverted = "sz300001";
        Assert.assertEquals(expectedConverted, actualConverted);
    }

    @Test
    public void testConvert3() {
        String code = "600001";
        String actualConverted = this.convert.convert(code);
        String expectedConverted = "sh600001";
        Assert.assertEquals(expectedConverted, actualConverted);
    }

    @Test
    public void testConvert4() {
        String code = "";
        String actualConverted = this.convert.convert(code);
        String expectedConverted = "";
        Assert.assertEquals(expectedConverted, actualConverted);
    }

    @Test
    public void testConvert5() {
        String code = null;
        String actualConverted = this.convert.convert(code);
        String expectedConverted = "";
        Assert.assertEquals(expectedConverted, actualConverted);
    }
}
