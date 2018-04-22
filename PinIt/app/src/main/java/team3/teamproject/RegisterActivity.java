package team3.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText mUserName;
    EditText mPassword;
    EditText mImageURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mUserName   = (EditText)findViewById(R.id.usernameText);
        mPassword   = (EditText)findViewById(R.id.passwordText);
        mImageURL   = (EditText)findViewById(R.id.passwordText);

    }

    public void onCreateAccountClick(View view){
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        String imageurl = mImageURL.getText().toString();

        String response;


        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
        {
            try {
                response = PostStreamReader.sendCreateString("createUser.php",name=" + username+ "&password=" + password + "&email=" + "" + "&image=" + imageurl);
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void onGoBackClick(View view){
        Intent goBack = new Intent(this, LoginActivity.class);
        startActivity(goBack);
    }

}
