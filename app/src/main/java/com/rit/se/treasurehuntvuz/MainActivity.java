package com.rit.se.treasurehuntvuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import static com.rit.se.treasurehuntvuz.Treasures.getTreasures;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //getTreasures().getList().add(0, new TreasurePoint(43.0861, 77.6705));
        //findTreasure();
        pickUpTreasure(4, 20);
    }

    // TODO: this code is duplicated from FindTreasureActivity, here for your convience.
    public boolean pickUpTreasure(int numCollected, int numTotal) {
        try {
            Intent showTreasureIntent = new Intent(MainActivity.this, ShowTreasureActivity.class);

            showTreasureIntent.putExtra("NUM_COLLECTED", numCollected);
            showTreasureIntent.putExtra("NUM_TOTAL", numTotal);

            MainActivity.this.startActivity(showTreasureIntent);
        }
        catch(Exception exception) {
            Log.e("MainActivity", exception.getMessage());
            return false;
        }

        return true;
    }

    public boolean findTreasure() {
        try {
            Intent findTreasureIntent = new Intent(this, FindTreasureActivity.class);
            startActivity(findTreasureIntent);
        }
        catch(Exception exception) {
            Log.e("MainActivity", exception.getMessage());
            return false;
        }

        return true;
    }
}
