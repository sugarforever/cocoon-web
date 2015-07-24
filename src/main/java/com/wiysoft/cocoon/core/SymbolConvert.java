package com.wiysoft.cocoon.core;

import com.wiysoft.cocoon.core.inf.Convert;
import org.apache.commons.lang.StringUtils;

/**
 * Created by weiliyang on 7/18/15.
 */
public class SymbolConvert implements Convert {

    @Override
    public String convert(String original) {
        String converted = "";
        if (StringUtils.isNotEmpty(original)) {
            if (original.startsWith("3") || original.startsWith("002")) {
                converted = "sz" + original;
            } else if (original.startsWith("6")) {
                converted = "sh" + original;
            }
        }
        return converted;
    }

}
