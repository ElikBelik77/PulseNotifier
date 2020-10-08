package com.example.pulsenotifier;

import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer instance;
    private MediaPlayer player;

    private AudioPlayer() {

    }

    public void playAudio(String fileName) {
        try {
            this.player = new MediaPlayer();
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("playAudio", "prepare() failed");
        }
    }

    public static AudioPlayer get() {
        if(instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public void stopAudio() {
        player.stop();
    }
}
