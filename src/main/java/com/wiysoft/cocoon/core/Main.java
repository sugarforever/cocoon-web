package com.wiysoft.cocoon.core;

import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.rawparser.DefaultCocoonLineParser;
import com.wiysoft.cocoon.rawparser.DefaultCocoonRawParser;

import java.util.Calendar;
import java.util.List;

/**
 * Created by weiliyang on 7/18/15.
 */
public class Main {

    public void main(String[] args) {
        CoreRunner runner = new CoreRunner();
        DefaultCocoonRawParser rawParser = new DefaultCocoonRawParser();
        rawParser.setCocoonLineParser(new DefaultCocoonLineParser());
        runner.setCocoonRawParser(rawParser);
        runner.setSymbolConvert(new SymbolConvert());

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 16);

        List<Slot> slots = runner.pull("002001", c.getTime());

        System.out.println(slots.size() + " slots found for 002001 at " + c.getTime());

        for (Slot slot : slots) {
            System.out.println(slot);
        }
    }

}
