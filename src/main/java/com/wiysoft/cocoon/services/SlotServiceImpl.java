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

    public void setSlotRepository(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

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
        if (start == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date end = calendar.getTime();
        if (end == null) {
            return false;
        }

        return this.slotRepository.countByCodeAndDatesBetween(code, start, end) > 0;
    }

    @Override
    public Date findMaxDateByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        } else {
            return this.slotRepository.findMaxDateByCode(code);
        }
    }

    @Override
    public Object[][] findDateAndNetValueMatrixByCode(String code) {
        Object[] databaseResult = slotRepository.findAllDateAndNetValueByCode(code);
        if (databaseResult == null) {
            return new Object[0][0];
        }

        int len = databaseResult.length;
        Object[][] matrix = new Object[len][3];
        for (int i = 0; i < len; ++i) {
            Object[] oneResult = (Object[]) databaseResult[i];
            matrix[i][0] = oneResult[0];
            matrix[i][1] = oneResult[1];

            if (oneResult[1] != null && oneResult[1] instanceof Long) {
                Long netValue = (Long) oneResult[1];
                if (netValue > 0) {
                    matrix[i][2] = "red";
                } else if (netValue < 0) {
                    matrix[i][2] = "green";
                } else {
                    matrix[i][2] = "black";
                }
            }
        }

        return matrix;
    }
}
