package com.rit.se.treasurehuntvuz;

import android.app.Application;
import android.location.LocationManager;

import java.io.*;

public class TreasureHuntVuzApp extends Application {
    protected static TreasureHuntVuzApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    static File getApplicationFilesDir() {
        return instance.getFilesDir();
    }
}
