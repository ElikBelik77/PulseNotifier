package com.example.pulsenotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startForegroundService(new Intent(this, HeartbeatService.class));

//        Intent intent = new Intent(this, EventActivity.class);
//        EventState event = new EventState("today", "22:00", 0,0);
//        intent.putExtra("event", event);
//        startActivity(intent);
    }
}