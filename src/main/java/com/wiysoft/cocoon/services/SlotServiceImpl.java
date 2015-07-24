package com.wiysoft.cocoon.services;

import com.wiysoft.cocoon.core.model.Slot;
import com.wiysoft.cocoon.repositories.SlotRepository;
import com.wiysoft.cocoon.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by weiliyang on 7/24/15.
 */

@Service("slotService")
public class SlotServiceImpl implements SlotService {

    @Autowired
    private SlotRepository slotRepository;


    @Override
    public void saveAll(Iterable<Slot> slots) {
        if (slots != null) {
            this.slotRepository.save(slots);
        }
    }

    @Override
    public boolean slotsWithCodeAndDateExist(String code, String date) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(date)) {
            return false;
        }

        Date start = CommonUtil.getDateFromStr(date, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date end = calendar.getTime();

        return this.slotRepository.countByCodeAndDatesBetween(code, start, end) > 0;
    }
}
