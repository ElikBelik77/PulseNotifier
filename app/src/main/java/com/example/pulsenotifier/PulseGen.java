package com.example.pulsenotifier;
import java.util.Random;


public class PulseGen {
    private int currentPulse;
    private boolean alive = true;
    private boolean stressed = false;
    private static final int min_pulse = 20;
    private static final int normal_pulse_resting = 80;
    private static final int max_pulse = 220;
    private int currentMeanPulse;
    private int currentStdPulse;

    public PulseGen() {
        this.currentPulse = normal_pulse_resting;
        this.currentMeanPulse = 80;
        this.currentStdPulse = 30;
    }

    public int getCurrentPulse() {
        return this.currentPulse;
    }



    public void bitPulse() {
        int newPulse;
        Random rand = new Random();
        while(alive){
            newPulse = (int)(rand.nextGaussian()*this.currentStdPulse + this.currentMeanPulse);
            if (!stressed && newPulse > 120 && (int)rand.nextInt(100) >= 50){
                    this.currentMeanPulse = 120;
                    this.currentStdPulse = 15;
                    this.stressed = true;
            }


        }
    }
}
