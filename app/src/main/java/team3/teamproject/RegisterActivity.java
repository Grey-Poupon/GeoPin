package team3.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Used for creating a new account
 * Created by Mantas Sutas
 */
public class RegisterActivity extends AppCompatActivity {

    EditText mUserName;
    EditText mPassword;
    TextView mNotifyUserRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mUserName = (EditText) findViewById(R.id.reg_username_edit);
        mPassword = (EditText) findViewById(R.id.reg_password_edit);
        mNotifyUserRegister = (TextView) findViewById(R.id.notifyUserRegister);

    }

    public void onCreateAccountClick(View view) {
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        //used to test if response returns an id or an error
        int test = -1;
        String response = null;

        //In case the there is no response from the servers
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
            }


            }


        }






    public void onGoBackClick(View view) {
        Intent goBack = new Intent(this, LoginActivity.class);
        startActivity(goBack);
    }

}
