package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class StartGameActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    protected GoogleApiClient mGoogleApiClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected static final String TAG = "basic-location-sample";


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);

        // Set where to play location text
        String whereToPlayString = getString(R.string.where_to_start);
        TextView whereToPlayTextView = (TextView) findViewById(R.id.whereToPlayTextView);
        whereToPlayTextView.setText(whereToPlayString);

        // Set random button
        String randomButtonString = getString(R.string.random_button);
        Button randomButton = (Button) findViewById(R.id.random_button);
        randomButton.setText(randomButtonString);
        randomButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRandomButtonClick((Button) v);
            }
        });

        // Set file button
        String fileButtonString = getString(R.string.file_button);
        Button fileButton = (Button) findViewById(R.id.file_button);
        fileButton.setText(fileButtonString);
        fileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onFileButtonClick((Button) v);
            }
        });

        // Set resume
        String resumeButtonString = getString(R.string.resume_button);
        Button resumeButton = (Button) findViewById(R.id.resume_button);
        resumeButton.setText(resumeButtonString);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onResumeButtonClick((Button) v);
            }
        });

        // Set resume visibility
        resumeButton.setEnabled(TreasuresSingleton.getTreasures().getResume());

        // Create an instance of GoogleAPIClient.
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        try {
            Intent mainActivityIntent = new Intent(StartGameActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        catch(Exception exception) {
            Log.e("StartGameActivity", exception.getMessage());
        }
    }

    private void onRandomButtonClick(Button randomButton) {
        if(TreasuresSingleton.getTreasures().getResume()) {
            try {
            /*
            Intent randomActivityIntent = new Intent(StartGameActivity.this, RandomSelectionActivity.class);
            startActivity(randomActivityIntent);
            finish();
            */
            } catch (Exception exception) {
                Log.e("StartGameActivity", exception.getMessage());
            }
        }
    }

    private void onFileButtonClick(Button fileButton) {

    }

    private void onResumeButtonClick(Button resumeButton) {

    }

    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }

            else {

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                    mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                }
            }
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
