package com.wiysoft.cocoon.restful.model;

/**
 * Created by weiliyang on 7/24/15.
 */
public class RestfulStatus {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RestfulStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public RestfulStatus() {
        this(null, null);
    }
}
