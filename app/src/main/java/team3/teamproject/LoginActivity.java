package team3.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;


import android.text.TextUtils;

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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used for logging in to application
 * Modified by Mantas Sutas
 */


//Controls the login on features
public class LoginActivity extends AppCompatActivity{
    private static LoginActivity loginActivity;



    private CallbackManager callbackManager; // used for facebook log in
    //private static final String EMAIL = "email";
    LoginButton facebookLoginButton; // facebook log in button
    private AccessTokenTracker accessTokenTracker; // tracks the log in status so that user
    // doesn't need to log in more than once

    EditText mUserName;
    EditText mPassword;
    TextView mNotifyUserLogin;


    private String facebookName;



    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        LoginManager.getInstance().logOut();
        //below is for facebook log in
        facebookLoginButton = (LoginButton) findViewById(R.id.bt_facebookLogin);
        facebookLoginButton.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                ((User) getLoginActivity().getApplication()).setIsGuest(false);
                ((User) getLoginActivity().getApplication()).setIsFacebook(true);

                Intent loadingScreen = new Intent(LoginActivity.this, LoadingBarActivity.class);
                startActivity(loadingScreen);

            }

            @Override
            public void onCancel() {
                // Just goes back to log in screen
            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
        loginActivity = this;

        mUserName = (EditText) findViewById(R.id.usernameLogin);
        mPassword = (EditText) findViewById(R.id.passwordLogin);
        mNotifyUserLogin = (TextView) findViewById(R.id.notifyUserLogIn);
    }


    //Displays the user info
    // used for setting facebook user details
    public void displayUserInfo(JSONObject object) {
        String id, first_name, last_name = null;
        URL profile_picture = null;
        try {
            id = object.getString("id");
            try {
                profile_picture =
                        new URL("https://graph.facebook.com/" +
                                id + "/picture?width=500&height=500");

                first_name = object.getString("first_name");
                last_name = object.getString("last_name");
                facebookName = first_name + " " + last_name;
                ((User) this.getApplication()).setUserName(facebookName);

                ((User) this.getApplication()).setUserImageURL(profile_picture);


                String newID = PostStreamReader.sendCreateString("createUser.php",
                        "name=" + facebookName + "&password=" + ""
                                + "&imageURL=" + profile_picture + "&facebookID=" + id);
                ((User) this.getApplication()).setUserID(newID);

                //used for getting numerical values out of string for id
                Pattern numbersID = Pattern.compile("\\d+");
                Matcher findID = numbersID.matcher(newID);
                findID.find();
                newID = findID.group();
                ((User) this.getApplication()).setUserID(newID);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    // passes the login results to the LoginManager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * sign in click button listener
     */
    public void onLoginSignClick(View view) {
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        // users wasn't able to log in
        String result = null;
        String error = "Your log in details are incorrect!";
        String id;


        if (!TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password)) {
            try {
                result = PostStreamReader.sendCreateString("validateUser.php",
                        "name=" + username
                                + "&password=" + password);
                result = result.substring(result.length() - 1);

                ((User) this.getApplication()).setUserName(username);

                id = PostStreamReader.sendCreateString("getUserID.php",
                        "name=" + username);
                ((User) this.getApplication()).setUserID(id);

                ((User) this.getApplication()).setUserImageURL(null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result.equals("1")) {
                ((User) this.getApplication()).setIsGuest(false);
                Intent loadingScreen = new Intent(this, LoadingBarActivity.class);
                startActivity(loadingScreen);
            } else {
                mNotifyUserLogin.setTextColor(Color.RED);
                mNotifyUserLogin.setText(error);
            }

        }
    }

    /**
     * register click button listener
     */
    public void onLoginRegisterClick(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }


    /**
     * without login click button listener
     */
    public void onWithoutLoginClick(View view) {
        ((User) this.getApplication()).setIsGuest(true);
        ((User) this.getApplication()).setIsFacebook(false);
        ((User) this.getApplication()).setUserName("GUEST");
        ((User) this.getApplication()).setUserID("GUEST");

        ((User) this.getApplication()).setUserImageURL(null);


        Intent loadingScreen = new Intent(this, LoadingBarActivity.class);
        startActivity(loadingScreen);
    }

    public static LoginActivity getLoginActivity() {
        return loginActivity;
    }
}

