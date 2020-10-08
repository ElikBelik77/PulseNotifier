package com.example.pulsenotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Boolean isPlayingRecording = false;
    private LatLng eventLocation;
    private EventState eventEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Intent intent = getIntent();
        final EventState event = (EventState) intent.getSerializableExtra("event");
        eventEntity = event;

        Log.d("EventActivity", "EventActivity Created successfully");
        assert event != null;
        Log.d("EventActivity", String.format("Event data: " +
                        "Date: %s, (x, y): (%d, %d)",
                event.date, event.xLoc, event.yLoc));
        TextView dateText = (TextView) findViewById(R.id.dateValueTextView);
        TextView timeText = (TextView) findViewById(R.id.timeValueTextView);
        dateText.setText(event.date);

        this.eventLocation = new LatLng(event.xLoc, event.yLoc);
    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        Marker marker = map.addMarker(new MarkerOptions().position(this.eventLocation));
//        markerPerth.setTag(0);
    }

    public void recordingButtonOnClick(View view) {
        Button recordingButton = (Button) findViewById(R.id.recordingButton);
        Log.d("EventActivity", "Recording button pressed");
        if (!isPlayingRecording) {
            recordingButton.setText(getString(R.string.media_player_button_stop));
        } else {
            recordingButton.setText(getString(R.string.media_player_button_play));
            isPlayingRecording = false;
        }
        recordingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlayingRecording) {
                    isPlayingRecording = false;

                    AudioPlayer.get().stopAudio();
                }
                else {
                    isPlayingRecording = true;
                    AudioPlayer.get().playAudio(eventEntity.recordingPath);
                }
            }
        });
    }
}