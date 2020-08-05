package vka.yuriel.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context,
                PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true); // Appears after touching display
        mapView.setMultiTouchControls(true);  // Resize using two fingers
        mapView.setUseDataConnection(true);
        mapView.getController().setZoom(16);
        GeoPoint haw = new GeoPoint(53.557078, 10.023109);
        mapView.getController().setCenter(haw);
    }

    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(this,
                PreferenceManager.getDefaultSharedPreferences(this));
    }

    /*
        Button events
            Layout defines which method to call from Java source code (i.e. method name)
            Java source code is where the method is implemented.
                Method signature must be "public void methodName(View view)"
     */
    public void onClickSetCoord(View view) {
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
}