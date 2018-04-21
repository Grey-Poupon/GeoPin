package team3.teamproject;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
=======
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
>>>>>>> PinItmaster/master
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import android.view.WindowManager;
<<<<<<< HEAD
=======
import android.widget.EditText;
>>>>>>> PinItmaster/master
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class LoginActivity extends AppCompatActivity{


    private CallbackManager callbackManager; // used for facebook log in
    private static final String EMAIL = "email";
    LoginButton facebookLoginButton; // facebook log in button
    private AccessTokenTracker accessTokenTracker; // tracks the log in status so that user
    // doesn't need to log in more than once

<<<<<<< HEAD
=======
    EditText mUserName;
    EditText mPassword;



    private String facebookName;

    public static boolean isGuest;



>>>>>>> PinItmaster/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
<<<<<<< HEAD
=======
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

>>>>>>> PinItmaster/master

        // looks if the user is already logged in to the app
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        // checks if user is logged in
        updateWithToken(AccessToken.getCurrentAccessToken());

        //below is for facebook log in
        facebookLoginButton = (LoginButton)findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
<<<<<<< HEAD
                String userid = loginResult.getAccessToken().getUserId();
=======

                isGuest = false;
>>>>>>> PinItmaster/master

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object);
                            }
                        });

                Bundle parameters = new Bundle();
<<<<<<< HEAD
                parameters.putString("fields" , "first_name, last_name, email, id");
=======
                parameters.putString("fields" , "first_name, last_name");
>>>>>>> PinItmaster/master
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {
                // Just goes back to log in screen
            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

<<<<<<< HEAD

    }
    public void displayUserInfo(JSONObject object)
    {
        String first_name, last_name, email, id;
=======
        mUserName   = (EditText)findViewById(R.id.userNameLog);
        //if(mUserName.getText().toString())
        mPassword   = (EditText)findViewById(R.id.password);
    }
    public void displayUserInfo(JSONObject object)
    {
        String first_name, last_name;
>>>>>>> PinItmaster/master
        try
        {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
<<<<<<< HEAD
            email = object.getString("email");
            id = object.getString("id");
=======

            facebookName = first_name + " " + last_name;
            Log.v("test", facebookName);
>>>>>>> PinItmaster/master

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }


    // passes the login results to the LoginManager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    // looks if user is already logged in to facebook on the app
    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            Intent mapScreen = new Intent(this, MapsActivity.class);
            startActivity(mapScreen);
        } else {

        }
    }

<<<<<<< HEAD
    /** sign in click button listener
     */
    public void onLoginSignClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
=======

    /** sign in click button listener
     */
    public void onLoginSignClick(View view){
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        String response;

        String userID;
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
        {
            try {
                 response = PostStreamReader.sendCreateString("validateUser.php",
                         "name=" + username
                        + "&password=" + password);

                ((User)this.getApplication()).setUserName(username);
                ((User)this.getApplication()).setUserImageURL("test");

                //response = PostStreamReader.sendCreateString("getUserID.php",
                //       "?name=" + username);
                ((User)this.getApplication()).setUserID(response);

                Log.v("testoutput", ((User)this.getApplication()).getUserID());
                Intent mapScreen = new Intent(this, MapsActivity.class);
                startActivity(mapScreen);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
>>>>>>> PinItmaster/master
    }

    /** register click button listener
     */
<<<<<<< HEAD
    public void onLoginRegisterClick(View view){
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
=======
    public void onRegisterClick(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
>>>>>>> PinItmaster/master
    }


    /** without login click button listener
     */
    public void onWithoutLoginClick(View view){
<<<<<<< HEAD
        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }
}
=======
        isGuest = true;

        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }
}
>>>>>>> PinItmaster/master
