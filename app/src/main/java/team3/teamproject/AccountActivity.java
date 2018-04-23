package team3.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class AccountActivity extends AppCompatActivity {

    EditText mCurrentPassAcc;
    EditText mNewPassAcc;
    EditText mCurrentPassDelAcc;
    EditText mStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        mCurrentPassAcc = (EditText)findViewById(R.id.currentPassAcc);
        mNewPassAcc = (EditText)findViewById(R.id.newPassAcc);
        mCurrentPassDelAcc = (EditText)findViewById(R.id.currentPassDelAcc);
        mStatusBar = (EditText)findViewById(R.id.statusBar);
    }

    public void onResetPassClick(View view){
        String userID = ((User) this.getApplication()).getUserID();
        String oldPassword = mCurrentPassAcc.getText().toString();
        String newPassword = mNewPassAcc.getText().toString();

        String success = "Password change successful! You will be logged out!";
        String incorrect = "Password is incorrect!";
        String empty = "Password fields cannot be empty!";

        //used for logging user out after a while
        Handler logOutTimer = new Handler();


        if(!mNewPassAcc.getText().toString().equals("")
                && !mCurrentPassAcc.getText().toString().equals("") ) {
            try {
                String response = PostStreamReader.sendCreateString("changePassword.php",
                        "userID=" + userID + "&password=" + oldPassword + "&newPassword="
                                + newPassword);
                if(response.equals("1"))
                {
                    mStatusBar.setTextColor(Color.GREEN);
                    mStatusBar.setText(success);

                    logOutTimer.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent logOut =
                                    new Intent(getApplication(), LoginActivity.class);
                            startActivity(logOut);
                        }

                    }, 3000L);
                }
                else
                {
                    mStatusBar.setTextColor(Color.RED);
                    mStatusBar.setText(incorrect);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            mStatusBar.setTextColor(Color.RED);
            mStatusBar.setText(empty);
        }

    }

    public void onDeleteMeClick(View view){
        String password = mCurrentPassDelAcc.getText().toString();
        String userName = ((User) this.getApplication()).getUserName();
        String response = "";
        boolean isFacebook = ((User) this.getApplication()).getIsFacebook();

        if(isFacebook) {
            try {
                response = PostStreamReader.sendCreateString("userDelete.php",
                        "name=" + userName + "&password=" + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!isFacebook && !TextUtils.isEmpty(password))
        {
            try {
                response = PostStreamReader.sendCreateString("userDelete.php",
                        "name=" + userName + "&password=" + password);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if(!isFacebook && TextUtils.isEmpty(password))
        {
            mStatusBar.setTextColor(Color.RED);
            mStatusBar.setText("Password cannot be empty if you're not using Facebook!");
        }
        if(response == "-1")
        {
            mStatusBar.setTextColor(Color.RED);
            mStatusBar.setText("Account deletion has failed!");
        }
        else if(response == "1")
        {
            mStatusBar.setTextColor(Color.GREEN);
            mStatusBar.setText("Account deletion has succeeded!");
        }

        }

}
