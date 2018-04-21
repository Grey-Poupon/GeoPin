package team3.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onRegistrationClick(View view){
        Intent mapsScreen = new Intent(this, MapsActivity.class);
        startActivity(mapsScreen);
    }

    /** without login click button listener
     */
    public void onWithoutLoginClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }
}
