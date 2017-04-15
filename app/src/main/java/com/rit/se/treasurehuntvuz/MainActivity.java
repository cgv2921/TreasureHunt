package com.rit.se.treasurehuntvuz;

import android.location.LocationManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// Jeffrey Haines 3/6/17
//    Created the main Activity, to get to Start, High Score, and About screens.
// Jeffrey HAines 4/15/17
//    High Score button now takes your to the High Score screen.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set start button
        String startButtonString = getString(R.string.start_button);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setText(startButtonString);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onStartButtonClick((Button) v);
            }
        });

        // Set high score button
        String highScoreButtonString = getString(R.string.high_score_button);
        Button highScoreButton = (Button) findViewById(R.id.high_score_button);
        highScoreButton.setText(highScoreButtonString);
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onHighScoreButtonClick((Button) v);
            }
        });

        // Set about button
        String aboutButtonString = getString(R.string.about_button);
        Button aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setText(aboutButtonString);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAboutButtonClick((Button) v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Go back to the android dashboard
        finish();
    }

    private void onStartButtonClick(Button startButton) {
        try {
            Intent startGameActivityIntent = new Intent(MainActivity.this, StartGameActivity.class);
            startActivity(startGameActivityIntent);
            finish();
        } catch (Exception exception) {
            Log.e("MainActivity", exception.getMessage());
        }
    }

    private void onHighScoreButtonClick(Button fileButton) {
        try {
            Intent showHighScoreActivityIntent = new Intent(MainActivity.this, ShowHighScoreActivity.class);
            startActivity(showHighScoreActivityIntent);
            finish();
        } catch (Exception exception) {
            Log.e("MainActivity", exception.getMessage());
        }
    }

    private void onAboutButtonClick(Button aboutButton) {
        try {
            Intent aboutActivityIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutActivityIntent);
            finish();
        } catch (Exception exception) {
            Log.e("MainActivity", exception.getMessage());
        }
    }
}
