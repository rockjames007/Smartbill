package com.example.dipuj.smartbill.modal;

import java.sql.Timestamp;
import java.util.Date;

public class Reading {

    private double reading;
    private Timestamp timestamp;

    public Reading(){

    }

    public Reading(double reading, Timestamp timestamp) {
        this.reading = reading;
        this.timestamp = timestamp;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
