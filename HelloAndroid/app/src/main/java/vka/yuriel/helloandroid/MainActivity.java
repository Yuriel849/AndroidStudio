package vka.yuriel.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findViewById(ID) returns reference to GUI element with the given ID.
        TextView view = findViewById(R.id.text_output);
        view.setText("Text changed during loading.");
        // Set the text displayed in the text view.
    }

    /*
        Button events
            Layout defines which method to call from Java source code (i.e. method name)
            Java source code is where the method is implemented.
                Method signature must be "public void methodName(View view)"
     */
    public void onClickSetText(View view) {
        TextView textView = findViewById(R.id.text_output);
        textView.setText("Button clicked.");
    }
}