package team3.teamproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

//Controlls account details and activity

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

        if(!TextUtils.isEmpty(mCurrentPassAcc.getText())
                && !TextUtils.isEmpty(mCurrentPassAcc.getText()) ) {
            try {
                String response = PostStreamReader.sendCreateString("changePassword.php",
                        "userID=" + userID + "&password=" + oldPassword + "&newPassword="
                                + newPassword);
                if(response.equals("1"))
                {
                    mStatusBar.setTextColor(Color.GREEN);
                    mStatusBar.setText("Password change successful!");
                }
                else
                {
                    mStatusBar.setTextColor(Color.RED);
                    mStatusBar.setText("Password is incorrect!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            mStatusBar.setTextColor(Color.RED);
            mStatusBar.setText("Password fields cannot be empty!");
        }

    }

    public void onDeleteMeClick(View view){
        return;
    }

}
