package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaveGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        // Set high score string
        String saveGameString = getString(R.string.save_game_string);
        TextView saveGameTextView = (TextView) findViewById(R.id.save_game_text_view);
        saveGameTextView.setText(saveGameString);
        saveGameTextView.setTextSize(26);

        // Set start button
        String yesButtonString = getString(R.string.yes_button);
        Button yesButton = (Button) findViewById(R.id.yes_button);
        yesButton.setText(yesButtonString);
        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onYesButtonClick((Button) v);
            }
        });

        // Set no button
        String noButtonString = getString(R.string.no_button);
        Button noButton = (Button) findViewById(R.id.no_button);
        noButton.setText(noButtonString);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onNoButtonClick((Button) v);
            }
        });
    }

    private void saveGame(boolean resume) {
        try {
            TreasuresSingleton.getTreasures().saveTreasureHuntGame(resume);
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("SaveGameActivity", exception.getMessage());
            } else {
                Log.e("SaveGameActivity", "Exception without a message.");
            }
        }
    }

    private void onYesButtonClick(Button yesButton) {
        saveGame(true);
    }

    private void onNoButtonClick(Button noButton) {
        saveGame(false);
    }

    @Override
    public void onBackPressed() {
        saveGame(false);
    }
}
