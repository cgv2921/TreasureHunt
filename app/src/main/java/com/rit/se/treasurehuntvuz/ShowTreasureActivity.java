package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

// Jeffrey Haines 3/1/17
//    Link to create AnimationDrawable from GIF
//        http://tusharonweb.in/AndroidResourceTools/GifToAnimationDrawable/
//    Link that explains AnimtationDrawable
//        https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable.html
//    Stackoverflow question where Activities must be listed in the AndroidManifest.xml file
//        http://stackoverflow.com/questions/42522956/null-exception-starting-activity-with-intent/
//    Link that explains how to pass information over an intent
//        http://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-on-android
public class ShowTreasureActivity extends AppCompatActivity {

    private AnimationDrawable treasureAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtreasure);

        // Set the progress string
        int numCollected = getIntent().getIntExtra("NUM_COLLECTED", -1);
        int numTotal = getIntent().getIntExtra("NUM_TOTAL", -1);
        String progressString = String.format(getString(R.string.progress_string), numCollected, numTotal);
        TextView progressTextView = (TextView) findViewById(R.id.progressTextView);
        progressTextView.setText(progressString);
        progressTextView.setTextSize(26);

        // Set the treasure animation
        ImageView treasureImage = (ImageView) findViewById(R.id.treasureAnimationView);
        treasureImage.setBackgroundResource(R.drawable.anim_treasure);
        treasureAnimation = (AnimationDrawable) treasureImage.getBackground();
        treasureAnimation.setOneShot(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        treasureAnimation.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        treasureAnimation.setVisible(true, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        treasureAnimation.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        treasureAnimation.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        treasureAnimation.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // TODO: This should either return to FindTreasureActivity or EnterHighscoreActivity
        try {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("ShowTreasureActivity", exception.getMessage());
        }
    }
}
