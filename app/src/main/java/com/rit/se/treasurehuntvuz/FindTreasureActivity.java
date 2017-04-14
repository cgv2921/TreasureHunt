package com.rit.se.treasurehuntvuz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.FieldPosition;
import java.text.MessageFormat;

// Jeffrey Haines 3/4/17
//    Made FindTreasureActivity a single instance so we can kill it when player finds all the treasure etc.
//        http://stackoverflow.com/questions/10379134/finish-an-activity-from-another-activity

public class FindTreasureActivity extends AppCompatActivity {

    public static Activity findTreasureActivity;

    //http://stackoverflow.com/questions/14295150/how-to-update-a-textview-in-an-activity-constantly-in-an-infinite-loop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findTreasureActivity = this;
        setContentView(R.layout.activity_findtreasure);
        final Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                lookForTreasure(null,null);
                handler.postDelayed(this,500); // set time here to refresh textView
            }
        });
    }

    public void lookForTreasure(Location currentLocation, Location[] treasures){
        float closestDis = currentLocation.distanceTo(treasures[0]);
        Location closestLoc = treasures[0];
        for (Location treasure : treasures){
            if(currentLocation.distanceTo(treasure) < closestDis){
                closestLoc = treasure;
                closestDis = currentLocation.distanceTo(treasure);
            }
        }
        float closetBearing = Math.abs(currentLocation.bearingTo(closestLoc) - currentLocation.getBearing());
        float cloestLocation = currentLocation.distanceTo(closestLoc);
        MessageFormat fmt = new MessageFormat("You are {0} and you are getting {1}.");
        String bearingStr = "";
        String distanceStr = "";
        if (closetBearing < 15){
            bearingStr = "blazinger";
        } else if(closetBearing < 30){
            bearingStr = "hoter";
        } else if(closetBearing < 60){
            bearingStr = "colder";
        } else{
            bearingStr = "frezzinger";
        }
        if (cloestLocation < 15){
            distanceStr = "blazing";
        } else if(cloestLocation < 30){
            distanceStr = "hot";
        } else if(cloestLocation < 60){
            distanceStr = "cold";
        } else{
            distanceStr = "frezzing";
        }
        Object[] results = {distanceStr,bearingStr};
        String resultStr = fmt.format(results);

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
        }
        catch(Exception exception) {
            Log.e("FindTreasureActivity", exception.getMessage());
        }
    }

    /**
     * CREDIT: http://stackoverflow.com/users/502162/david-george
     * Calculate distance between two points in latitude and longitude
     */
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        // TODO: we should unit test this function
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000; // convert to meters
    }

    public boolean pickUpTreasure(TreasurePoint treasure) {
        // found the treasure
        treasure.setFoundTime();
        treasure.setFound(true);
        TreasuresSingleton.getTreasures().incrementNamCollected();

        // display the treasure
        try {
            Intent showTreasureIntent = new Intent(FindTreasureActivity.this, ShowTreasureActivity.class);

            showTreasureIntent.putExtra("TREASURE", TreasuresSingleton.getTreasures().getNumCollected());
            showTreasureIntent.putExtra("NUM_TOTAL", TreasuresSingleton.getTreasures().getNumTotal());

            FindTreasureActivity.this.startActivity(showTreasureIntent);
        }
        catch(Exception exception) {
            Log.e("MainActivity", exception.getMessage());
            return false;
        }

        return true;
    }
}
