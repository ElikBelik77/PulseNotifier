package com.example.pulsenotifier;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class HeartbeatService extends Service implements Observer<Float> {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "10001";
    private NotificationChannel notificationChannel;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //SensorManager sensorMgr;
        //Sensor heartRate;
        //sensorMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        //heartRate = sensorMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        // do your jobs here
        //sensorMgr.registerListener(this,heartRate,SensorManager.SENSOR_DELAY_NORMAL);
        Observable<Float> sensor = HeartrateSensor.get();

        sensor.add(this);

        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID,
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            this.notificationChannel.setDescription("Pulse Notifications");
            mNotificationManager.createNotificationChannel(this.notificationChannel);

        }
    }

    @Override
    public void notify(Float value, Observer<Float> sender) {

    }
}