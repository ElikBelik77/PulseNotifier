package com.example.pulsenotifier;

import java.util.ArrayList;
import java.util.List;

public class BollingerBands {
    private int k;
    private List<Float> data;
    private int n;

    public BollingerBands(int n, int k) {
        data = new ArrayList<Float>();
        this.k = k;
        this.n = n;
    }

    public boolean hasValue() {
        return data.size() == n;
    }

    public boolean checkValue(float value) throws Exception {
        if (this.hasValue()) {
            throw new Exception("Bollinger is not ready for data processsing");
        }
        return (value >= BollingerBands.mean(this.data) - this.k * BollingerBands.STD(this.data) && value <= BollingerBands.mean(this.data) + this.k * BollingerBands.STD(this.data)) ;
    }

    public void appendData(float value) {
        if (data.size() == n) {
            data.remove(0);
        }
        data.add(value);
    }


    private static float mean(List<Float> values) {
        float sum = 0.0f;
        for (float num : values) {
            sum += num;
        }
        return sum / values.size();
    }


    private static float STD(List<Float> values) {

        float sum = 0.0f, standardDeviation = 0.0f;

        for (float num : values) {
            sum += num;
        }

        double mean = sum / values.size();

        for (float num : values) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return (float) Math.sqrt((double) (standardDeviation / values.size()));
    }
}
}
