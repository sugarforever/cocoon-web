package com.wiysoft.cocoon.schedule;

/**
 * Created by weiliyang on 7/24/15.
 */
public interface Job {

    public void execute();

    public boolean isRunning();

}
