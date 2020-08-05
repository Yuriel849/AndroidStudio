package vka.yuriel.helloandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ToggleButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {
    /*
        App creates MainActivity activity.
        onCreate() executed when activity is first created.
        setContentView() creates GUI from XML layout file.
            "R.layout.activity_main" => "activity_main.xml" in "layout" directory in "res" directory.
        Class R is an automatically generated Java class that holds
            resource identifiers as static constants. Resources are what are in the "res" directory,
            ex) "drawable", "layout", "strings"...
        Strings are kept in a string resource file.
            XML resource file is used for strings for localization (different languages).
        References to GUI elements are kept and strings are displayed in Java files.
     */

    private MapView mapView;
    private LocationListener listener;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context,
                PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_main);

        // Initialize the map
        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true); // Appears after touching display
        mapView.setMultiTouchControls(true);  // Resize using two fingers
        mapView.setUseDataConnection(true);
        mapView.getController().setZoom(16);
        GeoPoint haw = new GeoPoint(53.557078, 10.023109);
        mapView.getController().setCenter(haw);

        // Request location permission from the user (GPS)
        requestLocationPermissions();
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(this,
                PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void requestLocationPermissions() {
        // Check, if permission have been granted
        int checkFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int checkCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // Dialog to request permissions, if not granted
        if ((checkFine != PackageManager.PERMISSION_GRANTED) || (checkCoarse != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }

    /*
        Button events
            Layout defines which method to call from Java source code (i.e. method name)
            Java source code is where the method is implemented.
                Method signature must be "public void methodName(View view)"
     */
    public void onClickSetCoord(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // findViewById(ID) returns reference to GUI element with the given ID.
        EditText latitude = findViewById(R.id.edit_latitude);
        EditText longitude = findViewById(R.id.edit_longitude);

        findViewById(R.id.map).setPadding(0, 0, 0, 0);

        String latitude_string = latitude.getText().toString();
        String longitude_string = longitude.getText().toString();

        if((latitude_string != null && !latitude_string.isEmpty())
                && (longitude_string != null && !longitude_string.isEmpty())) {
            double latitude_int = Double.parseDouble(latitude_string);
            double longitude_int = Double.parseDouble(longitude_string);

            mapView.getController().setCenter(new GeoPoint(latitude_int, longitude_int));
        }
    }

    public void onClickSetGPS(View view) {
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()) {
            startLocationUpdates();
        } else {
            stopLocationUpdates();
        }
    }

    private LocationListener createLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    mapView.getController().setCenter(new GeoPoint(location));
                }
            }

            @ Override
            public void onStatusChanged(String provider, int status, Bundle bund) {
            }

            @ Override
            public void onProviderEnabled(String provider) {
            }

            @ Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    // Start regular updates of device location
    private void startLocationUpdates() {
        final long minTimeMs = 1000; // Update frequency (once per second)
        final float minDistanceMeter = 1.0f; // Minimum change of location
        listener = createLocationListener();
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMs, minDistanceMeter, listener);
        } catch (SecurityException exception) { // SecurityException occurs if location permissions have not been given
        }
    }

    // Remove regular updates of device location (e.g., GPS)
    private void stopLocationUpdates() {
        if ((manager != null) && (listener != null)) {
            manager.removeUpdates(listener);
            manager = null;
            listener = null;
        }
    }
}