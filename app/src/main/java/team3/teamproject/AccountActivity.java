package team3.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Used for changing password and deleting account
 */
//Controlls account details and activity
public class AccountActivity extends AppCompatActivity {

    EditText mCurrentPassAcc;
    EditText mNewPassAcc;
    EditText mCurrentPassDelAcc;
    TextView mStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        mCurrentPassAcc = (EditText)findViewById(R.id.currentPassAcc);
        mNewPassAcc = (EditText)findViewById(R.id.newPassAcc);
        mCurrentPassDelAcc = (EditText)findViewById(R.id.currentPassDelAcc);
        mStatusBar = (TextView)findViewById(R.id.statusBar);
    }

    //button used for submitting a request to change the password
    public void onResetPassClick(View view){
        // id is used to confirm which account's password to change

        AlertDialog.Builder builder1 = new AlertDialog.Builder(AccountActivity.this);
        builder1.setTitle("Reset password?")
                .setMessage("Are you sure you want to reset your account?")
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //current password used for confirmation and new one to replace old one
                                String userID = ((User) getApplication()).getUserID();
                                String oldPassword = mCurrentPassAcc.getText().toString();
                                String newPassword = mNewPassAcc.getText().toString();

                                // used to notify user what happened
                                String success = "Password change successful! You will be logged out!";
                                String incorrect = "Password is incorrect!";
                                String empty = "Password fields cannot be empty!";

                                //used for logging user out after a while
                                Handler logOutTimer = new Handler();


                                //checks if new password field is empty
                                if (!mNewPassAcc.getText().toString().equals("")
                                        && !mCurrentPassAcc.getText().toString().equals("")) {
                                    try {
                                        String response = PostStreamReader.sendCreateString("changePassword.php",
                                                "userID=" + userID + "&password=" + oldPassword + "&newPassword="
                                                        + newPassword);
                                        if (response.equals("1")) {
                                            mStatusBar.setTextColor(Color.GREEN);
                                            mStatusBar.setText(success);

                                            //logs out automatically when password change is successful
                                            logOutTimer.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {
                                                    Intent logOut =
                                                            new Intent(getApplication(), LoginActivity.class);
                                                    startActivity(logOut);
                                                }

                                            }, 3000L);
                                        } else {
                                            mStatusBar.setTextColor(Color.RED);
                                            mStatusBar.setText(incorrect);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    mStatusBar.setTextColor(Color.RED);
                                    mStatusBar.setText(empty);
                                }

                            }});
    AlertDialog alert = builder1.create();
        alert.show();
                }

    //used for sending an account deletion
    public void onDeleteMeClick(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle("Delete account?")
                .setMessage("Are you sure you want to delete your account?")
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String password = mCurrentPassDelAcc.getText().toString();
                                String userName = ((User) getApplication()).getUserName();
                                String response = "";
                                boolean isFacebook = ((User) getApplication()).getIsFacebook();

                                if(isFacebook) {
                                    try {
                                        response = PostStreamReader.sendCreateString("userDelete.php",
                                                "name=" + userName + "&password=" + "");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                        Handler logOutTimer = new Handler();

                                        //logs out automatically when password change is successful
                                        logOutTimer.postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                Intent logOut =
                                                        new Intent(getApplication(), LoginActivity.class);
                                                startActivity(logOut);
                                            }

                                        }, 3000L);

                                }
                                if(!isFacebook && !TextUtils.isEmpty(password))
                                {
                                    try {
                                        response = PostStreamReader.sendCreateString("userDelete.php",
                                                "name=" + userName + "&password=" + password);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //used to norify if account deletion has failed or succeeded

                                        Handler logOutTimer = new Handler();

                                        //logs out automatically when password change is successful
                                        logOutTimer.postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                Intent logOut =
                                                        new Intent(getApplication(), LoginActivity.class);
                                                startActivity(logOut);
                                            }

                                        }, 3000L);

                                    }

                                else if(!isFacebook && TextUtils.isEmpty(password))
                                {
                                    mStatusBar.setTextColor(Color.RED);
                                    mStatusBar.setText("Password cannot be empty if you're not using Facebook!");
                                }

                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
}

}
