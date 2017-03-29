package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class EnterHighscoreActivity extends AppCompatActivity{

    private String playerName;
    private int highscore;
    private EditText enterNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterhighscore);
        highscore = getIntent().getIntExtra("HIGHSCORE", -1);

        // Set highscore string
        String scoreString = String.format(getString(R.string.score_string), highscore);
        TextView progressTextView = (TextView) findViewById(R.id.score_text_view);
        progressTextView.setText(scoreString);
        progressTextView.setTextSize(26);

        // set enter score text field
        enterNameField = (EditText) findViewById(R.id.enter_name_field);

        // set confirm player name button
        String confirmPlayerNameButtonString = getString(R.string.confirm_player_name_button);
        Button confirmPlayerNameButton = (Button) findViewById(R.id.confirm_player_name_button);
        confirmPlayerNameButton.setText(confirmPlayerNameButtonString);
        confirmPlayerNameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onConfirmPlayerNameButtonClick((Button) v);
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
        showHighscoreLayout();
    }

    public void onConfirmPlayerNameButtonClick(Button confirmPlayerNameButton) {
        if(!validatePlayerName()) {
            // Save highscore to file
            try {
                File dir = Environment.getExternalStorageDirectory();
                File highscoreFile = new File(dir, "highscores.txt");

                FileOutputStream fos = new FileOutputStream(highscoreFile);

                if(highscoreFile.createNewFile()) {
                    fos.write();
                }


            }
            catch (Exception exception) {
                Log.e("EnterHighscoreActivity", exception.getMessage());
            }

            showHighscoreLayout();
        }
        else {
            // TODO: Player name is invalid, notify player.
        }
    }

    private void showHighscoreLayout() {
        try {
            Intent showHighscoreActivityIntent = new Intent(this, ShowHighscoreActivity.class);
            startActivity(showHighscoreActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("EnterHighscoreActivity", exception.getMessage());
        }
    }

    private boolean validatePlayerName() {
        playerName = enterNameField.getText().toString();

        if(playerName.length() > 0 && playerName.length() <= 3) {
            return true;
        }
        else {
            return false;
        }
    }
}
