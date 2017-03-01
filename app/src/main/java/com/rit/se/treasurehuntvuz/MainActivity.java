package com.rit.se.treasurehuntvuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

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
        pickUpTreasure(4, 20);
    }

    public boolean pickUpTreasure(int numCollected, int numTotal) {
        try {
            Intent showTreasureIntent = new Intent(this, ShowTreasureActivity.class);

            showTreasureIntent.putExtra("NUM_COLLECTED", numCollected);
            showTreasureIntent.putExtra("NUM_TOTAL", numTotal);

            startActivity(showTreasureIntent);
        }
        catch(Exception exception) {
            Log.e("MainActivity", exception.getMessage());
            return false;
        }

        return true;
    }
}
