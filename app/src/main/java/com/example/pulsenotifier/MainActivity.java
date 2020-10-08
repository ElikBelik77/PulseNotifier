package com.example.pulsenotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<EventState> events = new ArrayList<EventState>();
    public static ArrayAdapter listAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events.add(new EventState("hey",0,0,"hey"));
        events.add(new EventState("hey",0,0,"hey"));
        events.add(new EventState("hey",0,0,"hey"));
        events.add(new EventState("hey",0,0,"hey"));
        events.add(new EventState("hey",0,0,"hey"));
        setContentView(R.layout.activity_main);
        listAdapter = new ArrayAdapter<EventState>(this,
                R.layout.activity_listview, events);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        final MainActivity mainActivity = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(mainActivity, EventActivity.class);
                intent.putExtra("event", events.get(arg2));
                startActivity(intent);
            }
        });
        this.startForegroundService(new Intent(this, HeartbeatService.class));
    }
}