package com.example.dipuj.smartbill.modal;

import java.util.Date;

public class Reading {

    private Long reading;
    private Date timestamp;

    public Reading(){

    }

    public Reading(Long reading, Date timestamp) {
        this.reading = reading;
        this.timestamp = timestamp;
    }

    public Long getReading() {
        return reading;
    }

    public void setReading(Long reading) {
        this.reading = reading;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
