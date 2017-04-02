package com.rit.se.treasurehuntvuz;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private String mLatitudeText;
    private String mLongitudeText;

    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText = String.valueOf(mLastLocation.getLatitude());
                mLongitudeText = String.valueOf(mLastLocation.getLongitude());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
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
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
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
    protected void onStop(){
        mGoogleApiClient.disconnect();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
