package com.example.pulsenotifier;

import java.io.Serializable;

public class EventState implements Serializable {
    public String date;
    public int xLoc;
    public int yLoc;
    public String recordingPath;
    public EventState(String date, int xLoc, int yLoc, String recordingPath) {
        this.date = date;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.recordingPath = recordingPath;
    }
    @Override
    public String toString() {
        return String.format("%s", date);
    }
}
