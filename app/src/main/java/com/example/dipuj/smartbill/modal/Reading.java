package com.example.dipuj.smartbill.modal;

import java.sql.Timestamp;
import java.util.Date;

public class Reading {

    private Object reading;
    private Object timestamp;

    public Reading(){

    }

    public Reading(Object reading, Object timestamp) {
        this.reading = reading;
        this.timestamp = timestamp;
    }

    public Object getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
