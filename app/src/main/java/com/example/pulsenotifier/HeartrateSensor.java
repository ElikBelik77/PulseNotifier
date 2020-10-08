package com.example.pulsenotifier;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

public class HeartrateSensor implements Observable<Float>, SensorEventListener {
    private static HeartrateSensor instance;
    private boolean hasValue = false;
    private int value = 0;
    private List<Observer<Float>> _Observers;

    private HeartrateSensor() {
        _Observers = new ArrayList<Observer<Float>>();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            for (Observer<Float> observer : this._Observers) {
                observer.notify(event.values[0], observer);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void add(Observer<Float> observer) {
        this._Observers.add(observer);
    }

    public static HeartrateSensor get() {
        if (instance == null) {
            instance = new HeartrateSensor();
        }
        return instance;
    }

}
