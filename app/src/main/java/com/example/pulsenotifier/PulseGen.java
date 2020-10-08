package com.example.pulsenotifier;
import android.os.SystemClock;

import java.util.Random;


public class PulseGen {
    private int currentPulse;
    private boolean alive = true;
    private boolean stressed = false;
//    private static final int min_pulse = 20;
    private static final int normal_pulse_resting = 80;
    private static final int normal_pulse_deviation = 30;
    private static final int stress_pulse_deviation = 15;
//    private static final int max_pulse = 220;
    private int currentMeanPulse;
    private int currentStdPulse;

    public PulseGen() {
        this.currentPulse = normal_pulse_resting;
        this.currentMeanPulse = normal_pulse_resting;
        this.currentStdPulse = 30;
    }

    public int getCurrentPulse() {
        return this.currentPulse;
    }



    public void bitPulse() {
        Random rand = new Random();
        while(alive){
            this.currentPulse = (int)(rand.nextGaussian()*this.currentStdPulse + this.currentMeanPulse);
            if (!stressed && this.currentPulse > 120 && (int)rand.nextInt(100) >= 50){
                    this.currentMeanPulse = 120;
                    this.currentStdPulse = stress_pulse_deviation;
                    this.stressed = true;
            }

            if (!stressed && this.currentPulse < 50 && (int)rand.nextInt(101) >= 50){
                this.currentMeanPulse = 50;
                this.currentStdPulse = stress_pulse_deviation;
                this.stressed = true;
            }

            if(stressed && this.currentPulse <= 120 && this.currentPulse >= 50){
                stressed = false;
                this.currentMeanPulse = normal_pulse_resting;
                this.currentStdPulse = normal_pulse_deviation;
            }
            SystemClock.sleep(1000);
        }
    }
}
