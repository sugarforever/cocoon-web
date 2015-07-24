package com.wiysoft.cocoon.core.inf;

import com.wiysoft.cocoon.core.model.Slot;

import java.util.Date;
import java.util.List;

/**
 * Created by weiliyang on 7/17/15.
 */
public interface Runner {

    public List<Slot> pull(String code, Date date);
}
