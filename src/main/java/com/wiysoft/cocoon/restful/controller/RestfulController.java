package com.wiysoft.cocoon.restful.controller;

import com.wiysoft.cocoon.CocoonConfiguration;
import com.wiysoft.cocoon.core.CoreRunner;
import com.wiysoft.cocoon.core.SymbolConvert;
import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.rawparser.DefaultCocoonLineParser;
import com.wiysoft.cocoon.rawparser.DefaultCocoonRawParser;
import com.wiysoft.cocoon.repositories.SlotRepository;
import com.wiysoft.cocoon.restful.model.RestfulStatus;
import com.wiysoft.cocoon.services.SlotService;
import com.wiysoft.cocoon.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */
@RestController
@RequestMapping("/rest")
public class RestfulController {

    @Autowired
    private SlotService slotService;
    @Autowired
    private CocoonConfiguration cocoonConfiguration;

    @RequestMapping("/hello")
    public RestfulStatus hello() {
        return new RestfulStatus("ok", cocoonConfiguration.getCodes() + ";" + cocoonConfiguration.getHolidays());
    }

    @RequestMapping("/pull-slots/{code}/{date}")
    public Object pullSlots(@PathVariable String code, @PathVariable String date) {

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(date)) {
            return new RestfulStatus("error", "Invalid code and date parameters.");
        }

        if (cocoonConfiguration.isHoliday(date)) {
            return new RestfulStatus("warn", "It's holiday on " + date);
        }

        boolean exists = this.slotService.slotsWithCodeAndDateExist(code, date);

        if (!exists) {
            CoreRunner runner = new CoreRunner();
            DefaultCocoonRawParser rawParser = new DefaultCocoonRawParser();
            rawParser.setCocoonLineParser(new DefaultCocoonLineParser());
            runner.setCocoonRawParser(rawParser);
            runner.setSymbolConvert(new SymbolConvert());

            List<Slot> slots = runner.pull(code, CommonUtil.getDateFromStr(date, "yyyy-MM-dd"));
            this.slotService.saveAll(slots);
            return new RestfulStatus("ok", String.format("%d slots saved.", slots.size()));
        } else {
            return new RestfulStatus("warn", String.format("Slots for %s on %s already exist.", code, date));
        }
    }
}
