package com.example.pulsenotifier;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HeartbeatService extends Service implements Observer<Float> {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "10001";
    private NotificationChannel _notificationChannel;
    private BollingerBands _Bands;

    public HeartbeatService() {
        _Bands = new BollingerBands(30, 1);
    }

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

        FileNameManager.initialize(getExternalCacheDir().getAbsolutePath(), 5);
        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {

        final PulseGen sensor = PulseGen.get();
        sensor.add(this);

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this._notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID,
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            this._notificationChannel.setDescription("Pulse Notifications");
            mNotificationManager.createNotificationChannel(this._notificationChannel);
            setNotification("Pulse sensor is running the background");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                sensor.bitPulse();
            }
        }).start();

    }

    public void setNotification(String text) {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .build());
    }

    public void startWorking() {
        try {
            setNotification("Recording voice");
            VoiceRecorder recorder = VoiceRecorder.get();
            String recordingFileName = FileNameManager.get().getNext();
            recorder.StartRecording(recordingFileName);

        } catch (Exception e) {

        }
    }

    public void stopWorking() {
        setNotification("Pulse sensor is running in the background");
        try {
            VoiceRecorder recorder = VoiceRecorder.get();
            recorder.StopRecording();
            String recordingFileName = FileNameManager.get().getCurrent();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            MainActivity.events.add(new EventState(dateFormat.format(date), 0, 0, recordingFileName));
            MainActivity.listAdapter.notifyDataSetChanged();
            Log.e("New File", recordingFileName);
        } catch (Exception e) {
        }
    }

    public void notify(Float value, Observable<Float> sender) {
        if (_Bands.hasValue()) {
            try {
                if (!_Bands.checkValue(value) && !VoiceRecorder.get().isRecording) {
                    startWorking();
                } else if (_Bands.checkValue(value) && VoiceRecorder.get().isRecording) {
                    stopWorking();

                }
                if (!VoiceRecorder.get().isRecording) {
                    _Bands.appendData(value);
                }
            } catch (Exception e) {
                Log.e("Notify", "Bollinger band is not loaded");
            }
        } else {
            _Bands.appendData(value);
        }

    }
}