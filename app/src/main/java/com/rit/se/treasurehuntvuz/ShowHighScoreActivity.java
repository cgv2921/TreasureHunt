package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowHighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);

        // set high score text view
        StringBuilder highScoreString = new StringBuilder();
        HighScores highScores = HighScoresSingleton.getHighScores();
        for(String scoreString : highScores.getHighScoreStrings()) {
            highScoreString.append(scoreString);
            highScoreString.append("\n");
        }
        TextView progressTextView = (TextView) findViewById(R.id.high_score_text_view);
        progressTextView.setText(highScoreString.toString());
        progressTextView.setTextSize(26);
    }

    @Override
    public void onBackPressed() {
        try {
            Intent mainActivityIntent = new Intent(ShowHighScoreActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("AboutActivity", exception.getMessage());
        }
    }
}
