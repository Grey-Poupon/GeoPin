package team3.teamproject;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.EditText;
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

    EditText mUserName;
    EditText mPassword;



    private String facebookName;

    public static boolean isGuest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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

                isGuest = false;

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object);
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields" , "first_name, last_name");
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

        mUserName   = (EditText)findViewById(R.id.userNameLog);
        //if(mUserName.getText().toString())
        mPassword   = (EditText)findViewById(R.id.password);
    }
    public void displayUserInfo(JSONObject object)
    {
        String first_name, last_name;
        try
        {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");

            facebookName = first_name + " " + last_name;
            Log.v("test", facebookName);

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
    }

    /** register click button listener
     */
    public void onRegisterClick(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }


    /** without login click button listener
     */
    public void onWithoutLoginClick(View view){
        isGuest = true;

        Intent mapScreen = new Intent(this, MapsActivity.class);
        startActivity(mapScreen);
    }
}