/*
package sujan.gps.nepkoder.com.androidautogpstracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class GPSDemo extends AppCompatActivity  {

    protected static final String TAG = "LocationOnOff";
    private WebView webContent;

    public static double latitude;
    double longitude;

    public static String userId;

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

    LocationManager manager;

    private Location myLocation;

//    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        // Todo: Location manager
        manager = (LocationManager) GPSDemo.this.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(GPSDemo.this)) {
            Toast.makeText(GPSDemo.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
        }

        else if (!hasGPSDevice(GPSDemo.this)) {
            Toast.makeText(GPSDemo.this, "GPS not Supported", Toast.LENGTH_SHORT).show();
        }
        else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(GPSDemo.this))
        {
//            Log.e("Sujan","Gps already enabled");
            Toast.makeText(GPSDemo.this,"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
        } else {
            // nothing
        }
    }



    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(GPSDemo.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            int permissionLocation = ContextCompat.checkSelfPermission(GPSDemo.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
//                            getLocation();

                            if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                            }
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(GPSDemo.this, REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        enableLoc();
//                        Toast.makeText(GPSDemo.this, "on activity result", Toast.LENGTH_SHORT).show();

                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
//        ab.setTitle( Html.fromHtml("<font color='#FF7F27'>Do you want to exit?</font>"));
        ab.setTitle("Exit Fantasy Nepal");
        ab.setMessage("Are you sure you wish to exit Fantasy Nepal App");
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                ActivityCompat.finishAffinity(GPSDemo.this);
            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        Dialog dialog = ab.create();
        dialog.show();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(GPSDemo.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            enableLoc();
        }
    }


}
*/
