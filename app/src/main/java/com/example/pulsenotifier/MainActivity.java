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
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<EventState> events = new ArrayList<EventState>();
    final private int GREEN = 0xFF00FF00;
    final private int RED = 0xFFFF0000;
    private Boolean isActive = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter adapter = new ArrayAdapter<EventState>(this,
                R.layout.activity_listview, events);

        Button toggleButton = (Button) findViewById(R.id.appSwitchButton);
        toggleButton.setText(getString(R.string.app_switch_button_stop));
        toggleButton.setBackgroundColor(RED);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
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

    public void toggleButtonOnClick(View view) {
        Button toggleButton = (Button) findViewById(R.id.appSwitchButton);
        Log.d("EventActivity", "Toggle button pressed");
        if (!isActive) {
            toggleButton.setText(getString(R.string.app_switch_button_stop));
            toggleButton.setBackgroundColor(RED);
            isActive = true;
        } else {
            toggleButton.setText(getString(R.string.app_switch_button_start));
            toggleButton.setBackgroundColor(GREEN);
            isActive = false;
        }
    }
}