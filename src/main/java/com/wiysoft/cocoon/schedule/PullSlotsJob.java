package com.wiysoft.cocoon.schedule;

import com.wiysoft.cocoon.CocoonConfiguration;
import com.wiysoft.cocoon.core.CoreRunner;
import com.wiysoft.cocoon.core.SymbolConvert;
import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.rawparser.DefaultCocoonLineParser;
import com.wiysoft.cocoon.rawparser.DefaultCocoonRawParser;
import com.wiysoft.cocoon.restful.model.RestfulStatus;
import com.wiysoft.cocoon.services.SlotService;
import com.wiysoft.cocoon.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */
public class PullSlotsJob implements Job {

    public static interface PullSlotsListener {
        public void onPulled(List<Slot> slots);
    }

    private SlotService slotService;
    private CocoonConfiguration cocoonConfiguration;
    private String code;
    private String date;
    private PullSlotsListener pullSlotsListener;

    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    public void setCocoonConfiguration(CocoonConfiguration cocoonConfiguration) {
        this.cocoonConfiguration = cocoonConfiguration;
    }

    public void setPullSlotsListener(PullSlotsListener pullSlotsListener) {
        this.pullSlotsListener = pullSlotsListener;
    }

    public PullSlotsJob(String code, String date) {
        this.code = code;
        this.date = date;
    }

    @Override
    public void execute() {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(date)) {
            System.out.println("Invalid code and date parameters.");
            return;
        }

        if (cocoonConfiguration.isHoliday(date)) {
            System.out.println("It's holiday on " + date);
            return;
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
            System.out.println(String.format("%d slots saved.", slots.size()));
            if (this.pullSlotsListener != null) {
                this.pullSlotsListener.onPulled(slots);
            }
        } else {
            System.out.println(String.format("Slots for %s on %s already exist.", code, date));
        }
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
