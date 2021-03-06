package team3.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Used for creating a new account
 * Created by Mantas Sutas
 */

//Controlls the registering activity for new users
public class RegisterActivity extends AppCompatActivity {

    EditText mUserName;
    EditText mPassword;
    TextView mNotifyUserRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mUserName = (EditText) findViewById(R.id.usernameReg);
        mPassword = (EditText) findViewById(R.id.passwordReg);
        mNotifyUserRegister = (TextView) findViewById(R.id.notifyUserRegister);

    }
    // used for creating an account
    public void onCreateAccountClick(View view) {
        // username and password messages
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        //used to test if response returns an id or an error
        int test = -1;
        String response = null;
        Handler logOutTimer = new Handler();

    // notification messages
        String error = "Username is already in use!";
        String success = "Registration was a success!";


        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            try {
                response = PostStreamReader.sendCreateString("createUser.php",
                        "name=" + username
                                + "&password=" + password);
                test = Integer.parseInt(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(test == -1)
            {
                mNotifyUserRegister.setTextColor(Color.RED);
                mNotifyUserRegister.setText(error);
            }
            else
            {
                mNotifyUserRegister.setTextColor(Color.GREEN);
                mNotifyUserRegister.setText(success);

                logOutTimer.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent logOut =
                                new Intent(getApplication(), LoginActivity.class);
                        startActivity(logOut);
                    }

                }, 1000L);
            }


            }


        }





    // used for getting back to the sign in screen
    public void onGoBackClick(View view) {
        Intent goBack = new Intent(this, LoginActivity.class);
        startActivity(goBack);
    }

}
