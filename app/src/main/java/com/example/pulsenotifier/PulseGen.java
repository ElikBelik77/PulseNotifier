package com.example.pulsenotifier;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PulseGen implements Observable<Float> {
    private static PulseGen instance;
    private int currentPulse;
    private boolean alive = true;
    private boolean stressed = false;
    //    private static final int min_pulse = 20;
    private static final int normal_pulse_resting = 80;
    private static final int normal_pulse_deviation = 30;
    private static final int stress_pulse_deviation = 5;
    //    private static final int max_pulse = 220;
    private int currentMeanPulse;
    private int currentStdPulse;
    private List<Observer<Float>> _Observers;

    private PulseGen() {
        this.currentPulse = normal_pulse_resting;
        this.currentMeanPulse = normal_pulse_resting;
        this.currentStdPulse = 30;
        this._Observers = new ArrayList<>();
    }

    public static PulseGen get() {
        if(instance== null) {
            instance = new PulseGen();
        }
        return instance;
    }
    public int getCurrentPulse() {
        return this.currentPulse;
    }



    public void bitPulse() {
        Random rand = new Random();
        while(alive){
            this.currentPulse = (int)(rand.nextGaussian()*this.currentStdPulse + this.currentMeanPulse);
            if (!this.stressed && this.currentPulse > 115 && (int)rand.nextInt(100) >= 50){
                    this.currentMeanPulse = 130; // average rate in a flight-or-fight situation
                    this.currentStdPulse = stress_pulse_deviation;
                    this.stressed = true;
            }

            if (!this.stressed && this.currentPulse < 60 && (int)rand.nextInt(101) >= 50){
                this.currentMeanPulse = 45;
                this.currentStdPulse = stress_pulse_deviation;
                this.stressed = true;
            }

            if (this.stressed && this.currentPulse <= 115 && this.currentPulse >= 60) {
                this.stressed = false;
                this.currentMeanPulse = normal_pulse_resting;
                this.currentStdPulse = normal_pulse_deviation;
            }
            for (Observer<Float> observer : _Observers) {
                observer.notify((float)this.currentPulse,this);
            }
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void add(Observer<Float> observer) {
        _Observers.add(observer);
    }
}
