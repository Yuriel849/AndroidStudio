package vka.yuriel.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        References to GUI elements are kept and strings are displayed in Java files.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}