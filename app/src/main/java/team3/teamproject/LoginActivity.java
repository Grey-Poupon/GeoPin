package team3.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /** sign in click button listener
     *  @Petr Makarov
     */
    public void onLoginSignClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }

    /** register click button listener
     *  @Petr Makarov
     */
    public void onLoginRegisterClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }

    /** facebook login click button listener
     *  @Petr Makarov
     */
    public void onLoginFacebookClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }

    /** without login click button listener
     *  @Petr Makarov
     */
    public void onWithoutLoginClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }
}
