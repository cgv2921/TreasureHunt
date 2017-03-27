package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

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
                onLoadButtonClick((Button) v);
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
        try {
            Intent startGameActivityIntent = new Intent(FileOpenActivity.this, StartGameActivity.class);
            startActivity(startGameActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("FileOpenActivity", exception.getMessage());
        }
    }

    private void onLoadButtonClick(Button startButton) {
        TextView tv = (TextView)findViewById(R.id.textView);

        File dir = Environment.getExternalStorageDirectory();

        File file = new File(dir,"text.txt");
        if(file.exists())   // check if file exist
        {
            String text = new String();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                line = br.readLine();
                tv.setText(line);

            }
            catch (IOException e) {
                tv.setText(e.toString());
            }

        }
        else
        {
            tv.setText("Sorry file doesn't exist!!");
        }
    }
}
