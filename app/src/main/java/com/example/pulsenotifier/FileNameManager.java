package com.example.pulsenotifier;

import android.util.Log;

import java.io.File;
import java.nio.file.Paths;

public class FileNameManager {
    private int current = 0;
    private int maxRecording = 0;
    private String baseDirectory = null;
    private static FileNameManager instance = null;

    private FileNameManager(String baseDir, int maxRecording) {
        this.baseDirectory = baseDir;
        this.maxRecording = maxRecording;
        this.resolveId();
    }

    private void resolveId() {
        File directory = new File(this.baseDirectory);
        File[] files = directory.listFiles();
        int maxName = 0;
        try {
            for (File file : files) {
                if (Integer.parseInt((Paths.get(file.getName()).getFileName().toString()).split("\\.")[0]) > maxName) {
                    maxName = Integer.parseInt((Paths.get(file.getName()).getFileName().toString()).split("\\.")[0]);
                }
            }
        } catch (NullPointerException e) {
            Log.e("ResolveId", "NullPointerException");
        }

        this.current = maxName == 5 ? 0 : maxName + 1;
    }

    public String getNext() {
        if (this.current == this.maxRecording) {
            this.current = 0;
        }

        String fileName = this.baseDirectory + "/" + this.current + ".3gp";
        this.current++;
        return fileName;
    }

    public String getCurrent() {
        return this.baseDirectory + "/" + (this.current-1) + ".3gp";
    }
    public static void initialize(String localDir, int maxRecordings) {
        if (instance == null) {
            instance = new FileNameManager(localDir, maxRecordings);
        }
    }

    public static FileNameManager get() throws Exception {
        if (instance == null) {
            throw new Exception("File name manager is not initialized");
        }
        return instance;
    }
}
