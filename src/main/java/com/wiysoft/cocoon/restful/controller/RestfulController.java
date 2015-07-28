package com.wiysoft.cocoon.restful.controller;

import com.wiysoft.cocoon.CocoonConfiguration;
import com.wiysoft.cocoon.core.CoreRunner;
import com.wiysoft.cocoon.core.SymbolConvert;
import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.rawparser.DefaultCocoonLineParser;
import com.wiysoft.cocoon.rawparser.DefaultCocoonRawParser;
import com.wiysoft.cocoon.repositories.SlotRepository;
import com.wiysoft.cocoon.restful.model.RestfulStatus;
import com.wiysoft.cocoon.schedule.PullSlotsJob;
import com.wiysoft.cocoon.services.SlotService;
import com.wiysoft.cocoon.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    @Autowired
    private SlotRepository slotRepository;

    @RequestMapping("/matrix/netvalue/{code}")
    public Object hello(@PathVariable String code) {
        /*Object[][] matrix = new Object[][]{
                new Object[]{"gold", 3, 4},
                new Object[]{"silver", 4, 6},
                new Object[]{"platium", 7, 9},
        };*/
        return slotService.findDateAndNetValueMatrixByCode(code);
    }

    @RequestMapping("/max-date/{code}")
    public RestfulStatus getMaxDateByCode(@PathVariable String code) {
        Date date = slotService.findMaxDateByCode(code);
        return new RestfulStatus("ok", CommonUtil.getStrFromDate(date, "yyyy-MM-dd"));
    }

    @RequestMapping("/pull-slots/")
    public Object pullSlots() {
        final List<RestfulStatus> statuses = new ArrayList<RestfulStatus>();

        String[] codes = cocoonConfiguration.getCodesArray();
        List<String> holidays = Arrays.asList(cocoonConfiguration.getHolidaysArray());
        Date firstDay = CommonUtil.getDateFromStr(cocoonConfiguration.getFirstDay(), "yyyy-MM-dd");

        for (String code : codes) {
            Date currentLatest = slotService.findMaxDateByCode(code);
            Date start = firstDay;
            if (currentLatest != null && currentLatest.after(firstDay)) {
                start = currentLatest;
            }

            Date today = Calendar.getInstance().getTime();
            Calendar calendar = Calendar.getInstance();
            for (Date d = start; !d.after(today); ) {
                String strDate = CommonUtil.getStrFromDate(d, "yyyy-MM-dd");

                PullSlotsJob job = new PullSlotsJob(code, strDate);
                job.setCocoonConfiguration(cocoonConfiguration);
                job.setSlotService(slotService);
                job.setPullSlotsListener(new PullSlotsJob.PullSlotsListener() {
                    @Override
                    public void onPulled(List<Slot> slots) {
                        statuses.add(new RestfulStatus("ok", String.format("%d slots pulled and saved on %s.", slots.size(), strDate)));
                    }
                });
                job.execute();

                calendar.setTime(d);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                d = calendar.getTime();
            }
        }
        return statuses;
    }
}
