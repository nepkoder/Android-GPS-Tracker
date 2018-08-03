package sujan.gps.nepkoder.com.androidautogpstracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.List;

/**
 * Created by newarbhai on 7/20/18.
 */

public class LocationOnOff extends AppCompatActivity {

    private Context mContext;

    public static final String TAG = "LocationOnOff";

    private GoogleApiClient googleApiClient;

    final static int REQUEST_LOCATION = 199;

    private Location myLocation;
    private double mLatitude;
    private double mLongitude;

     LocationManager manager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        // Todo Location Already on  ... start
        manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(mContext)) {

//            Toast.makeText(LocationOnOff.this,"Gps already enabled", Toast.LENGTH_SHORT).show();
        }
        // Todo Location Already on  ... end

        if(!hasGPSDevice(mContext)){
            Toast.makeText(mContext,"Gps not Supported",Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(mContext)) {
            Log.e("sujan","Gps already enabled");
            Toast.makeText(mContext,"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
        }else{
            Log.e("sujan","Gps already enabled");
            Toast.makeText(mContext,"Gps already enabled",Toast.LENGTH_SHORT).show();
        }
    }


    public boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    public Location enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(mContext)
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

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
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
                            int permissionLocation = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION);
                            if (permissionLocation == PackageManager.PERMISSION_GRANTED)
                            {
                                myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                            }
                            break;
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            try
//                            {
//                                status.startResolutionForResult(mContext,REQUEST_CHECK_SETTINGS_GPS);
//                            }
//                            catch (IntentSender.SendIntentException e)
//                            {
//                            }
//                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
        return myLocation;
    }


    public double getLatitude() {
        if (myLocation !=null) {
            mLatitude = myLocation.getLatitude();
        }
        return mLatitude;
    }

    public double getLongitude() {
        if (myLocation !=null) {
            mLongitude = myLocation.getLongitude();
        }
        return mLongitude;
    }

}
