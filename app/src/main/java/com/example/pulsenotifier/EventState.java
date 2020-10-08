package com.example.pulsenotifier;

import java.io.Serializable;

public class EventState implements Serializable {
    public String date;
    public String time;
    public int xLoc;
    public int yLoc;

    public EventState(String date, String time, int xLoc, int yLoc) {
        this.date = date;
        this.time = time;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
