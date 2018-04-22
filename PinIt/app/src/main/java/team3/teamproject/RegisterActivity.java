package team3.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Used for creating a new account
 * Created by Mantas Sutas
 */
public class RegisterActivity extends AppCompatActivity {

    EditText mUserName;
    EditText mPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mUserName = (EditText) findViewById(R.id.reg_username_edit);
        mPassword = (EditText) findViewById(R.id.reg_password_edit);


    }

    public void onCreateAccountClick(View view) {
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        //In case the there is no response from the servers
        String response = "Something is wrong with the servers!";


        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            try {
                response = PostStreamReader.sendCreateString("createUser.php",
                        "name=" + username
                                + "&password=" + password);


            } catch (Exception e) {
                e.printStackTrace();
            }

            //need to add alerts to notify user what's going on
            //also need to sort out serverside stuff
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                //showAlertWindow("Registration was a success!");
            }


        }



    // will complete later
   /** public void showAlertWindow(String message) {
        AlertDialog.Builder window = new AlertDialog.Builder(RegisterActivity.this);
        window.setMessage(message);
        window.setCancelable(false);
        window.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        startActivity(login);
                    }
                }
        );
        window.create().show();
    }
    */

    public void onGoBackClick(View view) {
        Intent goBack = new Intent(this, LoginActivity.class);
        startActivity(goBack);
    }

}
