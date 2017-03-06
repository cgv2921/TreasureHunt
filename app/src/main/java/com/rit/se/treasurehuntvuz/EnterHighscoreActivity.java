package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class EnterHighscoreActivity extends AppCompatActivity{

    private int highscore;
    private EditText enterScoreField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterhighscore);

        // Set highscore string
        highscore = calculateHighscore();
        String scoreString = String.format(getString(R.string.score_string), highscore);
        TextView progressTextView = (TextView) findViewById(R.id.scoreTextView);
        progressTextView.setText(scoreString);
        progressTextView.setTextSize(26);

        // set enter score text field
        enterScoreField = (EditText) findViewById(R.id.enterScoreField);
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
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("EnterHighscoreActivity", exception.getMessage());
        }
    }

    private boolean validatePlayerName() {
        String playerText = enterScoreField.getText().toString();

        if(playerText.length() > 0 && playerText.length() <= 3) {
            return true;
        }
        else {
            return false;
        }
    }

    private int calculateHighscore() {



        return -1;
    }
}
