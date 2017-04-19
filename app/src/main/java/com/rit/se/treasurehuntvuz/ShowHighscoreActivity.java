package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ShowHighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        try {
            Intent mainActivityIntent = new Intent(ShowHighscoreActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("AboutActivity", exception.getMessage());
        }
    }
}