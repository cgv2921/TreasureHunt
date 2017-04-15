package com.rit.se.treasurehuntvuz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

// Jeffrey Haines 3/4/17
//    Made FindTreasureActivity a single instance so we can kill it when player finds all the treasure etc.
//        http://stackoverflow.com/questions/10379134/finish-an-activity-from-another-activity

public class FindTreasureActivity extends AppCompatActivity {

    public static Activity findTreasureActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        findTreasureActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findtreasure);
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
            Intent saveGameActivityIntent = new Intent(this, SaveGameActivity.class);
            startActivity(saveGameActivityIntent);
            finish();
        }
        catch(Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("FindTreasureActivity", exception.getMessage());
            } else {
                Log.e("FindTreasureActivity", "Exception without a message.");
            }
        }
    }

    public boolean pickUpTreasure() {
        // display the treasure
        try {
            Intent showTreasureIntent = new Intent(FindTreasureActivity.this, ShowTreasureActivity.class);

            showTreasureIntent.putExtra("NUM_COLLECTED", TreasuresSingleton.getTreasures().getNumCollected());
            showTreasureIntent.putExtra("NUM_TOTAL", TreasuresSingleton.getTreasures().getNumTotal());

            FindTreasureActivity.this.startActivity(showTreasureIntent);
        }
        catch(Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("FindTreasureActivity", exception.getMessage());
            } else {
                Log.e("FindTreasureActivity", "Exception without a message.");
            }
            return false;
        }

        return true;
    }
}
