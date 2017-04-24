package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class StartGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);

        // Set where to play location text
        String whereToPlayString = getString(R.string.where_to_start);
        TextView whereToPlayTextView = (TextView) findViewById(R.id.whereToPlayTextView);
        whereToPlayTextView.setText(whereToPlayString);

        // Set random button
        String randomButtonString = getString(R.string.random_button);
        Button randomButton = (Button) findViewById(R.id.random_button);
        randomButton.setText(randomButtonString);
        randomButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRandomButtonClick((Button) v);
            }
        });

        // Set file button
        String fileButtonString = getString(R.string.file_button);
        Button fileButton = (Button) findViewById(R.id.file_button);
        fileButton.setText(fileButtonString);
        fileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onFileButtonClick((Button) v);
            }
        });

        // Set resume
        String resumeButtonString = getString(R.string.resume_button);
        Button resumeButton = (Button) findViewById(R.id.resume_button);
        resumeButton.setText(resumeButtonString);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onResumeButtonClick((Button) v);
            }
        });

        // Load previous game
        TreasuresSingleton.getTreasures().loadTreasureHuntGame();

        // Set resume visibility
        resumeButton.setEnabled(TreasuresSingleton.getTreasures().getResume());
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
        try {
            Intent mainActivityIntent = new Intent(StartGameActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            Log.d("StartGameActivity", "Going to MainActivity");
            finish();
        }
        catch(Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("StartGameActivity", exception.getMessage());
            } else {
                Log.e("StartGameActivity", "Exception without a message.");
            }
        }
    }

    private void onRandomButtonClick(Button randomButton) {
        try {
            Location locOne = new Location("");
            locOne.setLongitude(-70);
            locOne.setLatitude(42);
            TreasuresSingleton.getTreasures().addTreasure(locOne);

            Location locTwo = new Location("");
            locTwo.setLongitude(-70);
            locTwo.setLatitude(41);
            TreasuresSingleton.getTreasures().addTreasure(locTwo);

            // TODO: Make this a treasure near your location
            Location locThree = new Location("");
            locThree.setLongitude(-77.65521079301834);
            locThree.setLatitude(43.084121160172664);
            TreasuresSingleton.getTreasures().addTreasure(locThree);

            Intent randomActivityIntent = new Intent(StartGameActivity.this, FindTreasureActivity.class);
            startActivity(randomActivityIntent);
            Log.d("StartGameActivity", "Going to FindTreasureActivity");
            finish();
        } catch (Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("StartGameActivity", exception.getMessage());
            } else {
                Log.e("StartGameActivity", "Exception without a message.");
            }
        }
    }

    private void onFileButtonClick(Button fileButton) {

    }

    private void onResumeButtonClick(Button resumeButton) {
        if(TreasuresSingleton.getTreasures().getResume()) {
            try {
                Intent findTreasureActivityIntent = new Intent(StartGameActivity.this, FindTreasureActivity.class);
                startActivity(findTreasureActivityIntent);
                Log.d("StartGameActivity", "Going to FindTreasureActivity");
                finish();
            } catch (Exception exception) {
                if(exception.getMessage() != null) {
                    Log.e("StartGameActivity", exception.getMessage());
                } else {
                    Log.e("StartGameActivity", "Exception without a message.");
                }
            }
        }
    }
}
