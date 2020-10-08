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
import com.google.android.gms.maps.SupportMapFragment;
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
        dateText.setText(event.date);

        this.eventLocation = new LatLng(event.xLoc, event.yLoc);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(31.920600,35.040350)));

//      markerPerth.setTag(0);
    }

    public void recordingButtonOnClick(View view) {
        final Button recordingButton = (Button) findViewById(R.id.recordingButton);
        if (isPlayingRecording) {
            isPlayingRecording = false;
            recordingButton.setText("Play");
            AudioPlayer.get().stopAudio();
        } else {
            isPlayingRecording = true;
            recordingButton.setText("Stop");
            AudioPlayer.get().playAudio(eventEntity.recordingPath);
        }
    }

    public void onReturnClick(View view) {
        // make sure we were clicked but the return button
        if (view.getId() == R.id.returnButton) {
            // return to main activity

            // DON'T KNOW IF IT WORKS. FOUND IT ON STACK OVERFLOW:
            // https://stackoverflow.com/questions/24610527/how-do-i-get-a-button-to-open-another-activity
            startActivity(new Intent(EventActivity.this, MainActivity.class));
        }
    }
}