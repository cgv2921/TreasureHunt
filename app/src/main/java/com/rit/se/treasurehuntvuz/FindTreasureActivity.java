package com.rit.se.treasurehuntvuz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.rit.se.treasurehuntvuz.Treasures.getTreasures;

public class FindTreasureActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mOrient;
    private Canvas findTreasureCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findtreasure);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrient = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        Bitmap findTreasureBitmap = Bitmap.createBitmap(428, 200, Bitmap.Config.ARGB_8888);
        findTreasureCanvas = new Canvas(findTreasureBitmap);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mOrient, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        int yaw = (int)event.values[0];
        updateTreasures(yaw);
    }

    private void updateTreasures(int yaw) {
        // TODO: get real player location from GPS
        double playerLat = 20.000;
        double playerLon = 20.000;

        // update each treasure
        for(int i = 0; i < getTreasures().getList().size(); i++) {
            TreasurePoint tempPoint = getTreasures().getList().get(i);

            if(tempPoint.getFound())
                continue;

            /* TODO: The below code blocks relate to treasure proximity,
                     they should be in an updateProximity(TreasurePoint treasurePoint) returns distance function */
            // get player distance to treasure
            double treasurePointDistance = distance(playerLat,
                    tempPoint.getLat(), playerLon, tempPoint.getLon());
            if(treasurePointDistance > tempPoint.getFurthestDistance())
                tempPoint.setFurthestDistance(treasurePointDistance);

            // set treasure found, if distance is less than 10%
            if((int)(treasurePointDistance / tempPoint.getFurthestDistance()) * 100 < 10) {
                tempPoint.setFound(true);
                getTreasures().setNumCollected(getTreasures().getNumCollected() + 1);
                pickUpTreasure(getTreasures().getNumCollected(), getTreasures().getNumTotal());
                continue; // treasure should disappear
            }

            // set treasure proximity
            tempPoint.setProximity(
                    TreasurePoint.Proximity.getProximity(
                            (int)(treasurePointDistance / tempPoint.getFurthestDistance()) * 100 ) );

            // TODO: compute the yaw differential

            drawTreasure(tempPoint, 50);
        }
    }

    // x_center center of the treasure image,
    public void drawTreasure(TreasurePoint treasurePoint, int x_center) {

        Object drawableRes = TreasurePoint.Proximity.getDrawableRes(treasurePoint.getProximity());
        Bitmap treasureImageBitmap = BitmapFactory.decodeResource(getResources(), (int)drawableRes);

        findTreasureCanvas.drawBitmap(treasureImageBitmap, x_center, 100, null);

        // TODO: How do we draw the bitmap/canvas to the screen?
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

    public boolean pickUpTreasure(int numCollected, int numTotal) {
        try {
            Intent showTreasureIntent = new Intent(FindTreasureActivity.this, ShowTreasureActivity.class);

            showTreasureIntent.putExtra("NUM_COLLECTED", numCollected);
            showTreasureIntent.putExtra("NUM_TOTAL", numTotal);

            FindTreasureActivity.this.startActivity(showTreasureIntent);
        }
        catch(Exception exception) {
            Log.e("MainActivity", exception.getMessage());
            return false;
        }

        return true;
    }
}
