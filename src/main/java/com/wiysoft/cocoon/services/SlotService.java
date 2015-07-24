package com.wiysoft.cocoon.services;

import com.wiysoft.cocoon.core.model.Slot;

/**
 * Created by weiliyang on 7/24/15.
 */
public interface SlotService {

    public void saveAll(Iterable<Slot> slots);

    public boolean slotsWithCodeAndDateExist(String code, String date);
}
