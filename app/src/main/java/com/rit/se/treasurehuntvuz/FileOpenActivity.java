package com.rit.se.treasurehuntvuz;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Created by vendi_000 on 3/22/2017.
 */

public class FileOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openfile);

        // Set file button
        String loadButtonString = getString(R.string.loadFile_button);
        Button loadButton = (Button) findViewById(R.id.loadFile_button);
        loadButton.setText(loadButtonString);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    onLoadButtonClick((Button) v);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        ActivityCompat.requestPermissions(FileOpenActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
            Intent startGameActivityIntent = new Intent(FileOpenActivity.this, StartGameActivity.class);
            startActivity(startGameActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("FileOpenActivity", exception.getMessage());
        }
    }

    private void onLoadButtonClick(Button startButton) throws FileNotFoundException {
        try {

            Intent showFilesActivityIntent = new Intent(FileOpenActivity.this, ShowFilesActivity.class);
            startActivity(showFilesActivityIntent);
            finish();
        } catch (Exception exception) {
            Log.e("FileOpenActivity", exception.getMessage());
        }

    }

}

