package com.example.pulsenotifier;

import android.media.MediaRecorder;
import android.speech.tts.Voice;
import android.util.Log;

import java.io.IOException;

public class VoiceRecorder {
    private static VoiceRecorder instance;
    private MediaRecorder recorder;

    private VoiceRecorder() {
        this.recorder = new MediaRecorder();
        this.recorder.reset();
        this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }


    public void StartRecording(String fileName) {
        this.recorder.setOutputFile(fileName);
        try {
            this.recorder.prepare();
        } catch (IOException e) {
            Log.e("Record", "prepare() failed");
        }
        this.recorder.start();
    }

    public void StopRecording() {
        this.recorder.stop();
        this.recorder.release();
    }

    public static VoiceRecorder get() {
        if (instance == null){
             instance = new VoiceRecorder();
        }
        return instance;
    }
}
