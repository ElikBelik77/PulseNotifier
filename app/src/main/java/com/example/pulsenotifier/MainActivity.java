package com.example.pulsenotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<EventState> events = new ArrayList<EventState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        events.add(new EventState("today","69;420",0,0, ""));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("event", events.get(0));
        startActivity(intent);
        this.startForegroundService(new Intent(this, HeartbeatService.class));
    }
}