package com.wiysoft.cocoon.schedule;

import com.wiysoft.cocoon.CocoonConfiguration;
import com.wiysoft.cocoon.services.SlotService;
import com.wiysoft.cocoon.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by weiliyang on 7/24/15.
 */
@Component
@Configurable
//@EnableScheduling
public class ScheduledTask {

    private Job pullSlotsJob;
    /*
    @Scheduled(fixedRateString = "${cocoon.application.fixedRateString}")
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + CommonUtil.getStrFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
    */

    @Scheduled(cron = "${cocoon.application.scheduledExpression}")
    public void scheduledPullSlots(){
        this.pullSlotsJob.execute();
        System.out.println(this.pullSlotsJob.toString());
    }
}
